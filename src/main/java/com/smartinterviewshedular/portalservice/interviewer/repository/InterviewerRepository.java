package com.smartinterviewshedular.portalservice.interviewer.repository;

import com.smartinterviewshedular.commonlib.interviwer.model.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Integer> {
}
