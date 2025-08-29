package com.bajajfinserv.repository;

import com.bajajfinserv.entity.SqlProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlProblemRepository extends JpaRepository<SqlProblem, Long> {
    SqlProblem findByRegNo(String regNo);
}
