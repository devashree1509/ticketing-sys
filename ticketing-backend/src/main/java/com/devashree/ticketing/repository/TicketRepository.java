package com.devashree.ticketing.repository;

import com.devashree.ticketing.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface  TicketRepository extends JpaRepository<Ticket, Long> {
    @Override
    Page<Ticket> findAll(Pageable pageable);
    List<Ticket> findByStatus(String status);
    List<Ticket> findByPriority(String priority);
    List<Ticket> findByTitleContainingIgnoreCase(String keyword);

}

