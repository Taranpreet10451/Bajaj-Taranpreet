package com.bajajfinserv.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sql_problems")
public class SqlProblem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "reg_no")
    private String regNo;
    
    @Column(name = "question_type")
    private String questionType; // "ODD" or "EVEN"
    
    @Column(name = "problem_description", columnDefinition = "TEXT")
    private String problemDescription;
    
    @Column(name = "solution_query", columnDefinition = "TEXT")
    private String solutionQuery;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "webhook_url")
    private String webhookUrl;
    
    @Column(name = "access_token")
    private String accessToken;

    public SqlProblem() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getSolutionQuery() {
        return solutionQuery;
    }

    public void setSolutionQuery(String solutionQuery) {
        this.solutionQuery = solutionQuery;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
