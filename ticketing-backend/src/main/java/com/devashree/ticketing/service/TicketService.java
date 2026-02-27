package com.devashree.ticketing.service;
import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.dto.UpdateTicketRequest;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.exception.NotFoundException;
import com.devashree.ticketing.repository.TicketRepository;
import com.devashree.ticketing.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,UserRepository userRepository){
        this.ticketRepository=ticketRepository;
        this.userRepository=userRepository;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
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

    public TicketResponse getTicketById(Long id){

        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new NotFoundException("Ticket not found with id:"+id));
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedBy().getName(),
                ticket.getAssignedTo().getName()
        );
    }

    public Ticket updateTicket(Long id, UpdateTicketRequest request){
        Ticket ticket=ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket not found"));

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());

        return ticketRepository.save(ticket);
    }

   public void deleteTicket(Long id){
        Ticket ticket=ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket not found"));

        ticketRepository.delete(ticket);
   }
   public Page<Ticket> getAllTickets(int page,int size){
        Pageable pageable=PageRequest.of(page,size,Sort.by("createdBy").descending());

        return ticketRepository.findAll(pageable);
   }
}
