package com.smartinterviewshedular.portalservice.track.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartinterviewshedular.commonlib.track.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
}
