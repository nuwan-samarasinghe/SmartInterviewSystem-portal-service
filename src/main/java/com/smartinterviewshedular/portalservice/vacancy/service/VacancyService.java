package com.smartinterviewshedular.portalservice.vacancy.service;

import com.smartinterviewshedular.commonlib.vacancy.model.Vacancy;
import org.springframework.stereotype.Service;
import com.smartinterviewshedular.portalservice.vacancy.repository.VacancyRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    private final VacancyRepository vacancyRepository;

    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }
    public Vacancy updateVacancy(Vacancy vacancy) {
        vacancy.setUpdatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        return vacancyRepository.save(vacancy);
    }

    public Optional<Vacancy> getVacancyById(Integer id) {
        return vacancyRepository.findById(id);
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }
}
