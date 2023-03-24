package com.example.blogservice.repository;

import com.example.blogservice.entity.AuditActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditActivity, Long> {

}
