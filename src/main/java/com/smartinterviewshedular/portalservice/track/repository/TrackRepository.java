package com.smartinterviewshedular.portalservice.track.repository;


import com.smartinterviewshedular.commonlib.portalservice.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
}
