package com.example.finaljava.Repository;

import com.example.finaljava.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepo extends JpaRepository<CustomerEntity, Long> {
    // You can add custom queries if needed
}
