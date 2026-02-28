package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.dto.UpdateTicketRequest;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService=ticketService;
    }

    @PostMapping
    public TicketResponse createTicket(@RequestBody CreateTicketRequest request){
        return ticketService.createTicket(request);
    }

    @GetMapping
    public Page<Ticket> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ticketService.getAllTickets(page,size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
    @GetMapping("/status/{status}")
    public List<Ticket> filterByStatus(@PathVariable String status){
        return ticketService.getByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Ticket> filterByPriority(@PathVariable String priority){
        return ticketService.getByPriority(priority);
    }

    @GetMapping("/search")
    public List<Ticket> search(@RequestBody String keyword){
        return ticketService.searchByTitle(keyword);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketRequest request){
        Ticket updateTicket = ticketService.updateTicket(id,request);
        return ResponseEntity.ok(updateTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted successfullyy");
    }






}
