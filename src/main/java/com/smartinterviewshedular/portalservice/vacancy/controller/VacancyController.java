package com.smartinterviewshedular.portalservice.vacancy.controller;


import com.smartinterviewshedular.commonlib.technology.model.Technology;
import com.smartinterviewshedular.commonlib.vacancy.model.Vacancy;
import com.smartinterviewshedular.portalservice.technology.service.TechnologyService;
import com.smartinterviewshedular.portalservice.vacancy.service.VacancyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/vacancy")
public class VacancyController {
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private TechnologyService technologyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Integer id) {
        log.info("get Vacancy by id {}", id);
        Optional<Vacancy> vacancyById = vacancyService.getVacancyById(id);
        return vacancyById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build()
                );
    }

    @PostMapping("/")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Optional<Technology> technology = technologyService.getTechnologyById(vacancy.getTechnology().getId());
        vacancy.setTechnology(technology.get());
        log.info("creating the vacancy {}", vacancy);
        return ResponseEntity.ok().body(vacancyService.createVacancy(vacancy));

    }

    @GetMapping("/")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> allVacancies = vacancyService.getAllVacancies();
        if (!allVacancies.isEmpty()) {
            return ResponseEntity.ok().body(allVacancies);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Vacancy> updateVacancy(@RequestBody Vacancy vacancy) {
        Optional<Technology> technology = technologyService.getTechnologyById(vacancy.getTechnology().getId());
        vacancy.setTechnology(technology.get());
        log.info("updated the vacancy {}", vacancy);
        return ResponseEntity.ok().body(vacancyService.updateVacancy(vacancy));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vacancy> deleteVacancyById(@PathVariable Integer id) {
        log.info("Delete the vacancy by Id {}", id);
        Optional<Vacancy> vacancyById = vacancyService.getVacancyById(id);
        vacancyById.get().setDeleted(true);
        return ResponseEntity.ok().body(vacancyService.updateVacancy(vacancyById.get()));


    }
}
