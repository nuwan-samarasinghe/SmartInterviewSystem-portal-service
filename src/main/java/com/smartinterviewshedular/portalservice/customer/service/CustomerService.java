package com.smartinterviewshedular.portalservice.customer.service;

import com.smartinterviewshedular.portalservice.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.smartinterviewshedular.commonlib.customer.model.Customer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        customer.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        customer.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        customer.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
