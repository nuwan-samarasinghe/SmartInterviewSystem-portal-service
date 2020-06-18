package com.smartinterviewshedular.portalservice.candidatehasInterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartinterviewshedular.commonlib.candidate.model.CandidateHasInterview;
import com.smartinterviewshedular.portalservice.candidatehasInterview.repository.CandidateHasInterviewRepository;

import java.util.List;

@Service
public class CandidateHasInterviewService {
    @Autowired
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
