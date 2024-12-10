package com.example.finaljava.Repository;

import com.example.finaljava.Entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    // You can define custom queries here if needed
}
