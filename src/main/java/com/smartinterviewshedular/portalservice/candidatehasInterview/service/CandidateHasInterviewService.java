package com.smartinterviewshedular.portalservice.candidatehasInterview.service;

import com.smartinterviewshedular.commonlib.portalservice.model.CandidateHasInterview;
import com.smartinterviewshedular.portalservice.candidatehasInterview.repository.CandidateHasInterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateHasInterviewService {
    private final CandidateHasInterviewRepository candidateHasInterviewRepository;

    public CandidateHasInterviewService(CandidateHasInterviewRepository candidateHasInterviewRepository) {
        this.candidateHasInterviewRepository = candidateHasInterviewRepository;
    }

    public CandidateHasInterview createOrUpdateCandidateHasInterview(CandidateHasInterview candidateHasInterview) {
        return candidateHasInterviewRepository.save(candidateHasInterview);
    }

    public List<CandidateHasInterview> findByCandidateInterviewIdentityInterviewId(Integer interviewId) {
        return candidateHasInterviewRepository.findByCandidateInterviewIdentityInterviewId(interviewId);
    }

    public List<CandidateHasInterview> findByCandidateInterviewIdentityCandidateId(Integer candidateId) {
        return candidateHasInterviewRepository.findByCandidateInterviewIdentityCandidateId(candidateId);
    }
}
