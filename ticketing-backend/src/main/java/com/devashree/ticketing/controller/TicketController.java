package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.*;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.service.TicketService;
import com.devashree.ticketing.entity.TicketStatus;
import com.devashree.ticketing.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> createTicket( @Valid @RequestBody CreateTicketRequest request){
        TicketResponse response = ticketService.createTicket(request);
        return ResponseEntity.ok(
                ApiResponse.success("Ticket created successfully", response)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/test")
    public String adminTest(){
        return "Admin access Granted";
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<ApiResponse<Void>> assignTicket(
            @PathVariable Long id,
            @RequestParam Long agentId
    ){
        ticketService.assignTicket(id,agentId);
        return ResponseEntity.ok(ApiResponse.success("Ticket assigned successfully:",null));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<TicketResponse>>> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String search
    ){
        Page<TicketResponse> tickets = ticketService.getTickets(page, size, status, priority, search);

        return ResponseEntity.ok(
                ApiResponse.success("Tickets fetched successfully", tickets)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicket(@PathVariable Long id){

        TicketResponse ticket = ticketService.getTicketById(id);

        if(ticket == null){
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Ticket not found", "TICKET_NOT_FOUND"));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Ticket fetched successfully", ticket)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketRequest request){

        Ticket updatedTicket = ticketService.updateTicket(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Ticket updated successfully", updatedTicket)
        );
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateTicketStatusRequest request){

        ticketService.updateStatus(id, request.getStatus());

        return ResponseEntity.ok(
                ApiResponse.success("Status updated successfully", null)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(@PathVariable Long id){
        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                ApiResponse.success("Ticket deleted successfully", null)
        );
    }
}