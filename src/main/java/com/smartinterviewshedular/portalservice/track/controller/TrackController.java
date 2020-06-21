package com.smartinterviewshedular.portalservice.track.controller;

import com.smartinterviewshedular.commonlib.portalservice.model.Track;
import com.smartinterviewshedular.portalservice.track.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/tracks")
public class TrackController {
    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('create_track')")
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        log.info("creating a track record {}", track);
        return ResponseEntity.ok().body(trackService.createTrack(track));
    }

    @PutMapping
    @PreAuthorize(value = "hasAnyAuthority('update_track')")
    public ResponseEntity<Track> updateTrack(@RequestBody Track track) {
        log.info("update a track record {}", track);
        return ResponseEntity.ok().body(trackService.updateTrack(track));
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('get_track')")
    public ResponseEntity<Object> getTracks(@RequestParam(name = "name", required = false) String name) {
        log.info("get all tracks");
        List<Track> allTracks = trackService.getAllTracks();
        if (allTracks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(allTracks);
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyAuthority('get_track')")
    public ResponseEntity<Track> getTrackById(@PathVariable Integer id) {
        log.info("get track by id {}", id);
        Optional<Track> trackById = trackService.getTrackById(id);
        return trackById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }


}
