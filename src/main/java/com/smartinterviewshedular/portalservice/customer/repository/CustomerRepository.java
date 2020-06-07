package com.smartinterviewshedular.portalservice.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartinterviewshedular.commonlib.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
