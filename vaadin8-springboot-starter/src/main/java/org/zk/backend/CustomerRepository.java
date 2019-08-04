package org.zk.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import org.zk.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
