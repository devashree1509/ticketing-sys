package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.dto.UpdateTicketRequest;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id){
        return ResponseEntity.ok(ticketService.getTicketById(id));
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
