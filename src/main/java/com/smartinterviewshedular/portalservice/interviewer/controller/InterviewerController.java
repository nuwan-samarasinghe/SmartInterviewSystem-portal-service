package com.smartinterviewshedular.portalservice.interviewer.controller;

import com.smartinterviewshedular.commonlib.portalservice.model.*;
import com.smartinterviewshedular.portalservice.interview.service.InterviewService;
import com.smartinterviewshedular.portalservice.interviewer.service.InterviewerService;
import com.smartinterviewshedular.portalservice.interviewerhasinterview.service.InterviewerHasInterviewService;
import com.smartinterviewshedular.portalservice.track.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/interviewers")
public class InterviewerController {
    private final InterviewerService interviewerService;
    private final TrackService trackService;
    private final InterviewerHasInterviewService interviewerHasInterviewService;
    private final InterviewService interviewService;

    public InterviewerController(
            InterviewerService interviewerService,
            TrackService trackService,
            InterviewerHasInterviewService interviewerHasInterviewService,
            InterviewService interviewService) {
        this.interviewerService = interviewerService;
        this.trackService = trackService;
        this.interviewerHasInterviewService = interviewerHasInterviewService;
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<Interviewer> createInterviewer(@RequestBody Interviewer interviewer) {
        log.info("creating an interviewer record {}", interviewer);
        Optional<Track> trackById = trackService.getTrackById(interviewer.getTrack().getId());
        interviewer.setTrack(trackById.get());
        return ResponseEntity.ok().body(interviewerService.createInterviewer(interviewer));
    }

    @PutMapping
    public ResponseEntity<Interviewer> updateInterviewer(@RequestBody Interviewer interviewer) {
        log.info("update an interviewer record {}", interviewer);
        Optional<Track> trackById = trackService.getTrackById(interviewer.getTrack().getId());
        interviewer.setTrack(trackById.get());
        return ResponseEntity.ok().body(interviewerService.updateInterviewer(interviewer));
    }

    @GetMapping
    public ResponseEntity<Object> getInterviewers() {
        log.info("get all tracks");
        List<Interviewer> allInterviewers = interviewerService.getAllInterviewers();
        if (allInterviewers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(allInterviewers);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Interviewer> getInterviewerById(@PathVariable Integer id) {
        log.info("get interviewer by id {}", id);
        Optional<Interviewer> interviwerById = interviewerService.getInterviwerById(id);
        return interviwerById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<Object> getInterviewerInterviewHistory(@PathVariable Integer id) {
        log.info("get interviews by id {}", id);
        ArrayList<InterviewerInterviewStatusResponse> interviewStatusResponses = new ArrayList<>();
        List<InterviewerHasInterview> byInterviewerInterviewIdentityInterviewId = interviewerHasInterviewService.findByInterviewerInterviewIdentityInterviewerId(id);
        for (InterviewerHasInterview interviewerHasInterview : byInterviewerInterviewIdentityInterviewId) {
            Optional<Interview> interviewById = interviewService.getInterviewById(interviewerHasInterview.getInterviewerInterviewIdentity().getInterviewId());
            InterviewerInterviewStatusResponse interviewerInterviewStatusResponse = new InterviewerInterviewStatusResponse();
            interviewerInterviewStatusResponse.setInterview(interviewById.get());
            interviewerInterviewStatusResponse.setStatus(interviewerHasInterview.getStatus());
            interviewStatusResponses.add(interviewerInterviewStatusResponse);
        }
        return ResponseEntity.ok().body(interviewStatusResponses);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Interviewer> deleteInterviewerById(@PathVariable Integer id) {
        log.info("delete interviewer by id {}", id);
        Optional<Interviewer> interviwerById = interviewerService.getInterviwerById(id);
        interviwerById.get().setIsDeleted(true);
        return ResponseEntity.ok().body(interviewerService.updateInterviewer(interviwerById.get()));
    }
}
