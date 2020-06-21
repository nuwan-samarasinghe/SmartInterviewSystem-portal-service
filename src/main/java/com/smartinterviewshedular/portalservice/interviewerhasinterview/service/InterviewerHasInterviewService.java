package com.smartinterviewshedular.portalservice.interviewerhasinterview.service;

import com.smartinterviewshedular.commonlib.portalservice.model.InterviewerHasInterview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smartinterviewshedular.portalservice.interviewerhasinterview.repository.InterviewerHasInterviewRepository;

import java.util.List;

@Service
public class InterviewerHasInterviewService {
    @Autowired
    private final InterviewerHasInterviewRepository interviewerHasInterviewRepository;

    public InterviewerHasInterviewService(InterviewerHasInterviewRepository interviewerHasInterviewRepository) {
        this.interviewerHasInterviewRepository = interviewerHasInterviewRepository;
    }

    public InterviewerHasInterview createOrUpdateInterviewerHasInterview(InterviewerHasInterview interviewerHasInterview) {
        return interviewerHasInterviewRepository.save(interviewerHasInterview);
    }

    public List<InterviewerHasInterview> findByInterviewerInterviewIdentityInterviewId(Integer id) {
        return interviewerHasInterviewRepository.findByInterviewerInterviewIdentityInterviewId(id);
    }

    public List<InterviewerHasInterview> findByInterviewerInterviewIdentityInterviewerId(Integer id) {
        return interviewerHasInterviewRepository.findByInterviewerInterviewIdentityInterviewerId(id);
    }
}
