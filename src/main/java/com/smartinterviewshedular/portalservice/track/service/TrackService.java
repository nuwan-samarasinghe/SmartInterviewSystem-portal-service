package com.smartinterviewshedular.portalservice.track.service;

import com.smartinterviewshedular.commonlib.track.model.Track;
import com.smartinterviewshedular.portalservice.track.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {
    @Autowired
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track updateTrack(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Optional<Track> getTrackById(Integer id) {
        return trackRepository.findById(id);
    }
}
