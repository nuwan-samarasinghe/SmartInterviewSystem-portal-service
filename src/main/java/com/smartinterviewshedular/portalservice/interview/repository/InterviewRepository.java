package com.smartinterviewshedular.portalservice.interview.repository;

import com.smartinterviewshedular.commonlib.portalservice.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {
}
