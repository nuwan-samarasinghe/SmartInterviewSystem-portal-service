package com.smartinterviewshedular.portalservice.candidate.controller;

import com.smartinterviewshedular.commonlib.candidate.model.Candidate;
import com.smartinterviewshedular.commonlib.candidate.model.CandidateHasInterview;
import com.smartinterviewshedular.commonlib.candidate.model.CandidateInterviewStatusResponse;
import com.smartinterviewshedular.commonlib.interview.model.Interview;
import com.smartinterviewshedular.commonlib.technology.model.Technology;
import com.smartinterviewshedular.commonlib.track.model.Track;
import com.smartinterviewshedular.portalservice.candidate.service.CandidateService;
import com.smartinterviewshedular.portalservice.candidatehasInterview.service.CandidateHasInterviewService;
import com.smartinterviewshedular.portalservice.interview.service.InterviewService;
import com.smartinterviewshedular.portalservice.technology.service.TechnologyService;
import com.smartinterviewshedular.portalservice.track.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/candidates")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private CandidateHasInterviewService candidateHasInterviewService;
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private TechnologyService technologyService;

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        log.info("creating a candidate record {}", candidate);
        Optional<Track> trackById = trackService.getTrackById(candidate.getTrack().getId());
        List<Technology> technologyList = candidate.getTechnologyList();
        List<Technology> technologyList1 = new ArrayList<>();

        technologyList.iterator().forEachRemaining(technology -> {
            technologyList1.add(technologyService.getTechnologyById(technology.getId()).get());
        });
        candidate.setTechnologyList(technologyList1);
        candidate.setTrack(trackById.get());
        return ResponseEntity.ok().body(candidateService.createCandidate(candidate));
    }

    @PutMapping
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) {
        log.info("updating a candidate record {}", candidate);
        Optional<Track> trackById = trackService.getTrackById(candidate.getTrack().getId());
        List<Technology> technologyList = candidate.getTechnologyList();
        List<Technology> technologyList1 = new ArrayList<>();

        technologyList.iterator().forEachRemaining(technology -> {
            technologyList1.add(technologyService.getTechnologyById(technology.getId()).get());
        });
        candidate.setTechnologyList(technologyList1);
        candidate.setTrack(trackById.get());
        return ResponseEntity.ok().body(candidateService.updateCandidate(candidate));
    }

    @GetMapping
    public ResponseEntity<Object> getCandidates() {
        log.info("get all candidates");
        List<Candidate> allCandidates = candidateService.getAllCandidates();
        if (allCandidates.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(allCandidates);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Integer id) {
        log.info("get candidate by id {}", id);
        Optional<Candidate> candidateById = candidateService.getCandidateById(id);
        return candidateById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<Object> getCandidateInterviewHistory(@PathVariable Integer id) {
        log.info("get candidate interview history by id {}", id);
        ArrayList<CandidateInterviewStatusResponse> candidateInterviewStatusResponses = new ArrayList<>();
        List<CandidateHasInterview> byCandidateInterviewIdentityCandidateId = candidateHasInterviewService.findByCandidateInterviewIdentityCandidateId(id);
        for (CandidateHasInterview candidateHasInterview : byCandidateInterviewIdentityCandidateId) {
            CandidateInterviewStatusResponse candidateInterviewStatusResponse = new CandidateInterviewStatusResponse();
            Optional<Interview> interviewById = interviewService.getInterviewById(candidateHasInterview.getCandidateInterviewIdentity().getInterviewId());
            candidateInterviewStatusResponse.setInterview(interviewById.get());
            candidateInterviewStatusResponse.setPosition(candidateHasInterview.getPosition());
            candidateInterviewStatusResponse.setIsSelected(candidateHasInterview.getIsSelected());
            candidateInterviewStatusResponses.add(candidateInterviewStatusResponse);
        }
        return ResponseEntity.ok().body(candidateInterviewStatusResponses);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Candidate> deleteCandidateById(@PathVariable Integer id) {
        log.info("delete candidate by id {}", id);
        Optional<Candidate> candidateById = candidateService.getCandidateById(id);
        candidateById.get().setIsDeleted(true);
        return ResponseEntity.ok().body(candidateService.updateCandidate(candidateById.get()));
    }
}
