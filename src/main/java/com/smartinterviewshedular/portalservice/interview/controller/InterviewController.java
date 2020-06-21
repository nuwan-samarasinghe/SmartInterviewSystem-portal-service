package com.smartinterviewshedular.portalservice.interview.controller;

import com.smartinterviewshedular.commonlib.portalservice.model.*;
import com.smartinterviewshedular.portalservice.candidate.service.CandidateService;
import com.smartinterviewshedular.portalservice.candidatehasInterview.service.CandidateHasInterviewService;
import com.smartinterviewshedular.portalservice.interview.service.InterviewService;
import com.smartinterviewshedular.portalservice.interviewer.service.InterviewerService;
import com.smartinterviewshedular.portalservice.interviewerhasinterview.service.InterviewerHasInterviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final InterviewerService interviewerService;
    private final InterviewerHasInterviewService interviewerHasInterviewService;
    private final CandidateService candidateService;
    private final CandidateHasInterviewService candidateHasInterviewService;

    public InterviewController(
            InterviewService interviewService,
            InterviewerService interviewerService,
            InterviewerHasInterviewService interviewerHasInterviewService,
            CandidateService candidateService,
            CandidateHasInterviewService candidateHasInterviewService) {
        this.interviewService = interviewService;
        this.interviewerService = interviewerService;
        this.interviewerHasInterviewService = interviewerHasInterviewService;
        this.candidateService = candidateService;
        this.candidateHasInterviewService = candidateHasInterviewService;
    }


    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('create_interview')")
    public ResponseEntity<Interview> createInterview(@RequestBody Interview interview) {
        log.info("creating an interview record {}", interview);
        if (interview.getInterviewers().isEmpty()) {
            log.info("no interviewer record found");
        }
        Interview interviewSaved = interviewService.createInterview(interview);
        saveInterviewerInterviewStatus(interview, interviewSaved);
        return ResponseEntity.ok().body(interviewSaved);
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('update_interview')")
    public ResponseEntity<Interview> updateInterview(@RequestBody Interview interview) {
        log.info("updating an interview record {}", interview);
        if (interview.getInterviewers().isEmpty()) {
            log.info("no interviewer record found");
        }
        Interview updateInterview = interviewService.updateInterview(interview);
        saveInterviewerInterviewStatus(interview, updateInterview);
        return ResponseEntity.ok().body(interviewService.updateInterview(updateInterview));
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('get_interview')")
    public ResponseEntity<Object> getInterviews() {
        log.info("get all interviews");
        List<Interview> allInterviews = interviewService.getAllInterviews();
        for (Interview interview : allInterviews) {
            ArrayList<InterviewerInterviewStatusResponse> interviewerInterviewStatusResponses = new ArrayList<>();
            List<InterviewerHasInterview> byInterviewerInterviewIdentityInterviewId = interviewerHasInterviewService.findByInterviewerInterviewIdentityInterviewId(interview.getId());
            buildInterviewerHasInterview(byInterviewerInterviewIdentityInterviewId, interviewerInterviewStatusResponses);
            ArrayList<CandidateInterviewStatusResponse> candidateInterviewStatusResponses = new ArrayList<>();
            List<CandidateHasInterview> byCandidateInterviewIdentityInterviewId = candidateHasInterviewService.findByCandidateInterviewIdentityInterviewId(interview.getId());
            buildCandidateHasInterview(byCandidateInterviewIdentityInterviewId, candidateInterviewStatusResponses);

            interview.setCandidateInterviewStatusResponses(candidateInterviewStatusResponses);
            interview.setInterviewerInterviewStatusResponses(interviewerInterviewStatusResponses);
        }
        if (allInterviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(allInterviews);
        }
    }

    @GetMapping(value = "/{id}/interviewers")
    @PreAuthorize(value = "hasAnyAuthority('get_interviewers')")
    public ResponseEntity<Object> getInterviewInterviewers(@PathVariable Integer id) {
        log.info("get interviewers for the interview id {}", id);
        ArrayList<InterviewerInterviewStatusResponse> interviewerInterviewStatusResponses = new ArrayList<>();
        List<InterviewerHasInterview> byInterviewerInterviewIdentityInterviewId = interviewerHasInterviewService.findByInterviewerInterviewIdentityInterviewId(id);
        buildInterviewerHasInterview(byInterviewerInterviewIdentityInterviewId, interviewerInterviewStatusResponses);
        if (byInterviewerInterviewIdentityInterviewId.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(interviewerInterviewStatusResponses);
        }
    }

    private void buildInterviewerHasInterview(List<InterviewerHasInterview> byInterviewerInterviewIdentityInterviewId, ArrayList<InterviewerInterviewStatusResponse> interviewerInterviewStatusResponses) {
        for (InterviewerHasInterview interviewerHasInterview : byInterviewerInterviewIdentityInterviewId) {
            InterviewerInterviewStatusResponse interviewerInterviewStatusResponse = new InterviewerInterviewStatusResponse();
            Optional<Interviewer> interviwerById = interviewerService.getInterviwerById(interviewerHasInterview.getInterviewerInterviewIdentity().getInterviewerId());
            interviewerInterviewStatusResponse.setInterviewer(interviwerById.get());
            interviewerInterviewStatusResponse.setStatus(interviewerHasInterview.getStatus());
            interviewerInterviewStatusResponses.add(interviewerInterviewStatusResponse);
        }
    }

    private void buildCandidateHasInterview(List<CandidateHasInterview> byCandidateInterviewIdentityInterviewId, ArrayList<CandidateInterviewStatusResponse> candidateInterviewStatusResponses) {
        for (CandidateHasInterview candidateHasInterview : byCandidateInterviewIdentityInterviewId) {
            Candidate candidate = candidateService.getCandidateById(candidateHasInterview.getCandidateInterviewIdentity().getCandidateId()).get();
            CandidateInterviewStatusResponse candidateInterviewStatusResponse = new CandidateInterviewStatusResponse();
            candidateInterviewStatusResponse.setCandidate(candidate);
            candidateInterviewStatusResponse.setIsSelected(candidateHasInterview.getIsSelected());
            candidateInterviewStatusResponse.setPosition(candidateHasInterview.getPosition());
            candidateInterviewStatusResponses.add(candidateInterviewStatusResponse);
        }
    }

    private void saveInterviewerInterviewStatus(@RequestBody Interview interview, Interview interviewSaved) {
        for (InterviewerInterviewStatus interviewerInterviewStatus : interview.getInterviewers()) {
            InterviewerInterviewIdentity interviewerInterviewIdentity = new InterviewerInterviewIdentity();
            interviewerInterviewIdentity.setInterviewId(interviewSaved.getId());

            Interviewer interviewer = interviewerService.getInterviwerById(interviewerInterviewStatus.getId()).get();
            interviewerInterviewIdentity.setInterviewerId(interviewer.getId());

            InterviewerHasInterview interviewerHasInterview = new InterviewerHasInterview();
            interviewerHasInterview.setInterviewerInterviewIdentity(interviewerInterviewIdentity);
            interviewerHasInterview.setStatus(interviewerInterviewStatus.getStatus());

            interviewerHasInterviewService.createOrUpdateInterviewerHasInterview(interviewerHasInterview);
        }
        CandidateInterviewStatus candidate = interview.getCandidate();
        Candidate candidateById = candidateService.getCandidateById(candidate.getId()).get();
        CandidateInterviewIdentity candidateInterviewIdentity = new CandidateInterviewIdentity();
        candidateInterviewIdentity.setCandidateId(candidateById.getId());
        candidateInterviewIdentity.setInterviewId(interviewSaved.getId());

        CandidateHasInterview candidateHasInterview = new CandidateHasInterview();
        candidateHasInterview.setCandidateInterviewIdentity(candidateInterviewIdentity);
        candidateHasInterview.setIsSelected(candidate.getIsSelected());
        candidateHasInterview.setPosition(candidate.getPosition());

        candidateHasInterviewService.createOrUpdateCandidateHasInterview(candidateHasInterview);
    }

    @GetMapping(value = "/{id}/candidate")
    @PreAuthorize(value = "hasAnyAuthority('get_interview_candidate')")
    public ResponseEntity<Object> getInterviewCandidate(@PathVariable Integer id) {
        log.info("get candidate for the interview id {}", id);
        ArrayList<CandidateInterviewStatusResponse> candidateInterviewStatusResponses = new ArrayList<>();
        List<CandidateHasInterview> byCandidateInterviewIdentityInterviewId = candidateHasInterviewService.findByCandidateInterviewIdentityInterviewId(id);
        buildCandidateHasInterview(byCandidateInterviewIdentityInterviewId, candidateInterviewStatusResponses);
        if (candidateInterviewStatusResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(candidateInterviewStatusResponses);
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyAuthority('get_interview')")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Integer id) {
        log.info("get interviewer by id {}", id);
        Interview interview = interviewService.getInterviewById(id).get();
        ArrayList<InterviewerInterviewStatusResponse> interviewerInterviewStatusResponses = new ArrayList<>();
        List<InterviewerHasInterview> byInterviewerInterviewIdentityInterviewId = interviewerHasInterviewService.findByInterviewerInterviewIdentityInterviewId(id);
        buildInterviewerHasInterview(byInterviewerInterviewIdentityInterviewId, interviewerInterviewStatusResponses);
        ArrayList<CandidateInterviewStatusResponse> candidateInterviewStatusResponses = new ArrayList<>();
        List<CandidateHasInterview> byCandidateInterviewIdentityInterviewId = candidateHasInterviewService.findByCandidateInterviewIdentityInterviewId(id);
        buildCandidateHasInterview(byCandidateInterviewIdentityInterviewId, candidateInterviewStatusResponses);

        interview.setCandidateInterviewStatusResponses(candidateInterviewStatusResponses);
        interview.setInterviewerInterviewStatusResponses(interviewerInterviewStatusResponses);
        return ResponseEntity.ok().body(interview);
    }
}
