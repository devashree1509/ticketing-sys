package com.devashree.ticketing.service;
import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.repository.TicketRepository;
import com.devashree.ticketing.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,UserRepository userRepository){
        this.ticketRepository=ticketRepository;
        this.userRepository=userRepository;
    }

    public TicketResponse createTicket(CreateTicketRequest request){
        User createdBy = userRepository.findById(request.getCreatedBy()).orElseThrow(()->new RuntimeException("Created by user not found"));
        User assignedTo = userRepository.findById(request.getAssignedTo()).orElseThrow(()->new RuntimeException("Assigned user not found"));

        Ticket ticket=new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription((request.getDescription()));
        ticket.setStatus("OPEN");
        ticket.setPriority("MEDIUM");
        ticket.setCreatedBy(createdBy);
        ticket.setAssignedTo(assignedTo);

        Ticket saved = ticketRepository.save(ticket);

        return new TicketResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getCreatedBy().getName(),
                saved.getAssignedTo().getName()
        );
    }

    public TicketResponse getTicket(Long id){

        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket not found"));
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedBy().getName(),
                ticket.getAssignedTo().getName()
        );
    }

}
