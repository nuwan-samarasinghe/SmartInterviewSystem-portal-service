package com.smartinterviewshedular.portalservice.technology.controller;

import com.smartinterviewshedular.commonlib.portalservice.model.Technology;
import com.smartinterviewshedular.portalservice.technology.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/technology")
public class TechnologyController {
    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technology> getTechnologyById(@PathVariable Integer id) {
        log.info("get Technology by id {}", id);
        Optional<Technology> technologyById = technologyService.getTechnologyById(id);
        return technologyById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/")
    public ResponseEntity<Technology> createTechnology(@RequestBody Technology technology) {
        log.info("creating the technology {}", technology);
        return ResponseEntity.ok().body(technologyService.createTechnology(technology));

    }

    @GetMapping("/")
    public ResponseEntity<List<Technology>> getAllTechnology() {
        List<Technology> allTechnologies = technologyService.getAllTechnologies();
        if (!allTechnologies.isEmpty()) {
            return ResponseEntity.ok().body(allTechnologies);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Technology> updateTechnology(@RequestBody Technology technology) {
        log.info("updated the technology {}", technology);
        return ResponseEntity.ok().body(technologyService.updateTechnology(technology));
    }
}
