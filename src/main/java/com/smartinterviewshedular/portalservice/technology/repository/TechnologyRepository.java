package com.smartinterviewshedular.portalservice.technology.repository;


import com.smartinterviewshedular.commonlib.portalservice.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Integer> {
}
