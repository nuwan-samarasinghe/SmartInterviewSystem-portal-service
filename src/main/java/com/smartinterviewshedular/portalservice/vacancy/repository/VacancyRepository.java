package com.smartinterviewshedular.portalservice.vacancy.repository;

import com.smartinterviewshedular.commonlib.vacancy.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy,Integer> {
}
