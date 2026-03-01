package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.dto.UpdateTicketRequest;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.service.TicketService;
import com.devashree.ticketing.util.ApiResponse;
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
    public ResponseEntity<ApiResponse<?>> createTicket(@RequestBody CreateTicketRequest request){
        TicketResponse response = ticketService.createTicket(request);
        return ResponseEntity.ok(
                ApiResponse.success("Ticket created successfullyy",response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>>getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String search
    ){
      Page<TicketResponse> tickets=ticketService.getTickets(page, size, status,priority,search);
        return ResponseEntity.ok(ApiResponse.success("Ticket fetched successfully",tickets));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTicketById(@PathVariable Long id){
       TicketResponse response = ticketService.getTicketById(id);
       return ResponseEntity.ok(ApiResponse.success("Ticket fetched successfully",response));
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
       return ResponseEntity.ok(ApiResponse.success("Ticket deleted Successfully",null));
    }






}
