package com.smartinterviewshedular.portalservice.candidatehasInterview.repository;

import com.smartinterviewshedular.commonlib.candidate.model.CandidateHasInterview;
import com.smartinterviewshedular.commonlib.candidate.model.CandidateInterviewIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateHasInterviewRepository extends JpaRepository<CandidateHasInterview, CandidateInterviewIdentity> {
    List<CandidateHasInterview> findByCandidateInterviewIdentityInterviewId(Integer interviewId);

    List<CandidateHasInterview> findByCandidateInterviewIdentityCandidateId(Integer candidateId);
}
