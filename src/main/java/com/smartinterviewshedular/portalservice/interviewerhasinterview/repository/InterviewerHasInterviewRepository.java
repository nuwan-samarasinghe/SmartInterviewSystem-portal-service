package com.smartinterviewshedular.portalservice.interviewerhasinterview.repository;

import com.smartinterviewshedular.commonlib.portalservice.model.InterviewerHasInterview;
import com.smartinterviewshedular.commonlib.portalservice.model.InterviewerInterviewIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewerHasInterviewRepository extends JpaRepository<InterviewerHasInterview, InterviewerInterviewIdentity> {
    List<InterviewerHasInterview> findByInterviewerInterviewIdentityInterviewId(Integer interviewId);

    List<InterviewerHasInterview> findByInterviewerInterviewIdentityInterviewerId(Integer interviewerId);
}
