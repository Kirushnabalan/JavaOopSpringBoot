package com.example.finaljava.Repository;


import com.example.finaljava.Entity.DetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<DetailsEntity, Integer> {
}

