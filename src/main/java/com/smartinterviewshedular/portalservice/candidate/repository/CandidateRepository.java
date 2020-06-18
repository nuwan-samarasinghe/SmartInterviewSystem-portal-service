package com.smartinterviewshedular.portalservice.candidate.repository;

import com.smartinterviewshedular.commonlib.candidate.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Integer> {
}
