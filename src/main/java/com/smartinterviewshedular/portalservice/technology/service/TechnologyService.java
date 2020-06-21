package com.smartinterviewshedular.portalservice.technology.service;

import com.smartinterviewshedular.commonlib.portalservice.model.Technology;
import com.smartinterviewshedular.portalservice.technology.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyService {
    final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;

    }

    public Technology createTechnology(Technology technology) {
        return technologyRepository.save(technology);
    }

    public Technology updateTechnology(Technology technology) {
        return technologyRepository.save(technology);
    }

    public Optional<Technology> getTechnologyById(Integer id) {
        return technologyRepository.findById(id);
    }

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }
}
