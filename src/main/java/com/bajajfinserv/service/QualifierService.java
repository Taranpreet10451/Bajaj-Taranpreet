package com.bajajfinserv.service;

import com.bajajfinserv.dto.WebhookRequest;
import com.bajajfinserv.dto.WebhookResponse;
import com.bajajfinserv.dto.SolutionRequest;
import com.bajajfinserv.entity.SqlProblem;
import com.bajajfinserv.repository.SqlProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QualifierService {

    @Autowired
    private SqlProblemRepository sqlProblemRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String WEBHOOK_GENERATION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SOLUTION_SUBMISSION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

    public void executeQualifier() {
        try {
            System.out.println("Step 1: Generating webhook...");

            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                System.out.println("Failed to generate webhook. Exiting...");
                return;
            }

            System.out.println("Webhook generated successfully!");
            System.out.println("Webhook URL: " + webhookResponse.getWebhook());
            System.out.println("Access Token: " + webhookResponse.getAccessToken());

            // Step 2: Determine question type and solve problem
            String regNo = "22BCE10451"; // From the task requirements
            String questionType = determineQuestionType(regNo);
            System.out.println("Registration Number: " + regNo);
            System.out.println("Question Type: " + questionType);

            // Step 3: Solve the SQL problem
            String solutionQuery = solveSqlProblem(questionType);
            System.out.println("SQL Solution generated: " + solutionQuery);

            // Step 4: Store the problem and solution
            SqlProblem sqlProblem = new SqlProblem();
            sqlProblem.setRegNo(regNo);
            sqlProblem.setQuestionType(questionType);
            sqlProblem.setProblemDescription("SQL Problem for " + questionType + " question type");
            sqlProblem.setSolutionQuery(solutionQuery);
            sqlProblem.setWebhookUrl(webhookResponse.getWebhook());
            sqlProblem.setAccessToken(webhookResponse.getAccessToken());

            sqlProblemRepository.save(sqlProblem);
            System.out.println("Problem and solution stored in database");

            // Step 5: Submit the solution
            System.out.println("Step 5: Submitting solution...");
            boolean submissionSuccess = submitSolution(solutionQuery, webhookResponse.getAccessToken());

            if (submissionSuccess) {
                System.out.println("Solution submitted successfully!");
            } else {
                System.out.println("Failed to submit solution.");
            }

        } catch (Exception e) {
            System.err.println("Error during qualifier execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest("Taranpreet Kaur", "22BCE10451", "tarnpreetkaur1641@gmail.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                    WEBHOOK_GENERATION_URL, entity, WebhookResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }

        } catch (Exception e) {
            System.err.println("Error generating webhook: " + e.getMessage());
        }
        return null;
    }

    private String determineQuestionType(String regNo) {
        // Extract last two digits and determine if odd or even
        if (regNo.length() >= 2) {
            String lastTwoDigits = regNo.substring(regNo.length() - 2);
            try {
                int lastTwo = Integer.parseInt(lastTwoDigits);
                return (lastTwo % 2 == 0) ? "EVEN" : "ODD";
            } catch (NumberFormatException e) {
                System.err.println("Error parsing registration number: " + e.getMessage());
            }
        }
        // Default to ODD if parsing fails
        return "ODD";
    }

    private String solveSqlProblem(String questionType) {
        if ("ODD".equals(questionType)) {
            // Question 1 solution - Find highest salary not on 1st day of month with
            // employee details
            return "SELECT " +
                    "p.AMOUNT as SALARY, " +
                    "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME, " +
                    "FLOOR(DATEDIFF(CURDATE(), e.DOB) / 365.25) as AGE, " +
                    "d.DEPARTMENT_NAME " +
                    "FROM PAYMENTS p " +
                    "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                    "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                    "WHERE DAY(p.PAYMENT_TIME) != 1 " +
                    "ORDER BY p.AMOUNT DESC " +
                    "LIMIT 1";
        } else {
            // Question 2 solution
            return "SELECT d.department_name, " +
                    "COUNT(p.patient_id) as patient_count, " +
                    "AVG(DATEDIFF(p.discharge_date, p.admission_date)) as avg_length_of_stay " +
                    "FROM departments d " +
                    "LEFT JOIN patients p ON d.department_id = p.department_id " +
                    "WHERE p.admission_date >= '2023-01-01' " +
                    "AND p.discharge_date IS NOT NULL " +
                    "GROUP BY d.department_id, d.department_name " +
                    "HAVING patient_count > 0 " +
                    "ORDER BY avg_length_of_stay DESC";
        }
    }

    private boolean submitSolution(String solutionQuery, String accessToken) {
        try {
            SolutionRequest request = new SolutionRequest(solutionQuery);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);

            HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    SOLUTION_SUBMISSION_URL, entity, String.class);

            System.out.println("Solution submission response: " + response.getStatusCode());
            if (response.getBody() != null) {
                System.out.println("Response body: " + response.getBody());
            }

            return response.getStatusCode() == HttpStatus.OK;

        } catch (Exception e) {
            System.err.println("Error submitting solution: " + e.getMessage());
            return false;
        }
    }
}
