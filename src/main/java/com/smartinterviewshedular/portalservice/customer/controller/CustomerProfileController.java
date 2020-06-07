package com.smartinterviewshedular.portalservice.customer.controller;

import com.smartinterviewshedular.portalservice.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.smartinterviewshedular.commonlib.customer.model.Customer;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/profile")
public class CustomerProfileController {

    private final CustomerService customerService;

    public CustomerProfileController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/customers")
    @PreAuthorize(value = "hasAnyAuthority('create_customer')")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        log.info("creating the customer profile {}", customer);
        return ResponseEntity.ok().body(customerService.createCustomer(customer));
    }

    @GetMapping(value = "/customers/{id}")
    @PreAuthorize(value = "hasAnyAuthority('read_customer')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        log.debug("get customer by id {}", id);
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer
                .map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/customers")
    @PreAuthorize(value = "hasRole('ROLE_admin')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        log.debug("get all customers");
        List<Customer> customer = customerService.getAllCustomers();
        if (!customer.isEmpty()) {
            return ResponseEntity.ok().body(customer);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping(value = "/customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        log.info("update the customer profile {}", customer);
        return ResponseEntity.ok().body(customerService.updateCustomer(customer));
    }

}
