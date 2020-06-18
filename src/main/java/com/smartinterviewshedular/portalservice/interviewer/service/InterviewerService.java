package com.smartinterviewshedular.portalservice.interviewer.service;

import com.smartinterviewshedular.commonlib.interviwer.model.Interviewer;
import com.smartinterviewshedular.portalservice.interviewer.repository.InterviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewerService {
    @Autowired
    private  InterviewerRepository interviewerRepository;

    public InterviewerService(InterviewerRepository interviewerRepository) {
        this.interviewerRepository = interviewerRepository;
    }

    public Interviewer createInterviewer(Interviewer interviewer){
        return interviewerRepository.save(interviewer);
    }

    public Interviewer updateInterviewer(Interviewer interviewer){
        return interviewerRepository.save(interviewer);
    }

    public List<Interviewer> getAllInterviewers(){
        return interviewerRepository.findAll(Sort.by(Sort.Direction.DESC,"rating"));
    }

    public Optional<Interviewer> getInterviwerById(Integer id) {
        return interviewerRepository.findById(id);
    }

//    public List<Interview> getInterviewerHistory(Integer id){
//        return interviewerRepository.findByInterviews(id);
//    }
}
