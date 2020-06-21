package com.smartinterviewshedular.portalservice.customer.repository;

import com.smartinterviewshedular.commonlib.portalservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
