package com.paymentchain.customer.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Customer service is up and running");
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(
            customerRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(
            customerRepository.save(customer)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        existingCustomer.setName(customer.getName());
        existingCustomer.setPhone(customer.getPhone());
        return ResponseEntity.ok(
            customerRepository.save(existingCustomer)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.delete(existingCustomer);
        return ResponseEntity.ok().build();
    }
}
