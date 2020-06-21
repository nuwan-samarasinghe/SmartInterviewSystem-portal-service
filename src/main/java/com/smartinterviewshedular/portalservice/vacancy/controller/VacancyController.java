package com.smartinterviewshedular.portalservice.vacancy.controller;

import com.smartinterviewshedular.commonlib.portalservice.model.Technology;
import com.smartinterviewshedular.commonlib.portalservice.model.Vacancy;
import com.smartinterviewshedular.portalservice.technology.service.TechnologyService;
import com.smartinterviewshedular.portalservice.vacancy.service.VacancyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/vacancy")
public class VacancyController {
    private final VacancyService vacancyService;
    private final TechnologyService technologyService;

    public VacancyController(VacancyService vacancyService, TechnologyService technologyService) {
        this.vacancyService = vacancyService;
        this.technologyService = technologyService;
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('get_vacancy')")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Integer id) {
        log.info("get Vacancy by id {}", id);
        Optional<Vacancy> vacancyById = vacancyService.getVacancyById(id);
        return vacancyById
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build()
                );
    }

    @PostMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('create_vacancy')")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Optional<Technology> technology = technologyService.getTechnologyById(vacancy.getTechnology().getId());
        vacancy.setTechnology(technology.get());
        log.info("creating the vacancy {}", vacancy);
        return ResponseEntity.ok().body(vacancyService.createVacancy(vacancy));

    }

    @GetMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('get_vacancy')")
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        List<Vacancy> allVacancies = vacancyService.getAllVacancies();
        if (!allVacancies.isEmpty()) {
            return ResponseEntity.ok().body(allVacancies);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/")
    @PreAuthorize(value = "hasAnyAuthority('update_vacancy')")
    public ResponseEntity<Vacancy> updateVacancy(@RequestBody Vacancy vacancy) {
        Optional<Technology> technology = technologyService.getTechnologyById(vacancy.getTechnology().getId());
        vacancy.setTechnology(technology.get());
        log.info("updated the vacancy {}", vacancy);
        return ResponseEntity.ok().body(vacancyService.updateVacancy(vacancy));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyAuthority('delete_vacancy')")
    public ResponseEntity<Vacancy> deleteVacancyById(@PathVariable Integer id) {
        log.info("Delete the vacancy by Id {}", id);
        Optional<Vacancy> vacancyById = vacancyService.getVacancyById(id);
        vacancyById.get().setDeleted(true);
        return ResponseEntity.ok().body(vacancyService.updateVacancy(vacancyById.get()));


    }
}
