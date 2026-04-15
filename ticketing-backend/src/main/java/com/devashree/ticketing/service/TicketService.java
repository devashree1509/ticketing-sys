package com.devashree.ticketing.service;
import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.dto.UpdateTicketRequest;
import com.devashree.ticketing.dto.UpdateTicketStatusRequest;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.entity.TicketStatus;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.exception.BadRequestException;
import com.devashree.ticketing.exception.ForbiddenException;
import com.devashree.ticketing.exception.NotFoundException;
import com.devashree.ticketing.repository.TicketRepository;
import com.devashree.ticketing.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final Logger logger=LoggerFactory.getLogger(TicketService.class);

    public TicketService(TicketRepository ticketRepository,UserRepository userRepository){
        this.ticketRepository=ticketRepository;
        this.userRepository=userRepository;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public TicketResponse createTicket(CreateTicketRequest request){
        User user = getCurrentUser();

        if(!user.isActive()){
            throw new BadRequestException("User not found");
        }

        Ticket ticket=new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription((request.getDescription()));
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(request.getPriority());
        ticket.setCreatedBy(user);


        Ticket saved = ticketRepository.save(ticket);

        logger.info("Ticket created with ID:{}",saved.getId());

        return new TicketResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus().name(),
                saved.getCreatedBy().getName(),
                null,
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    public void assignTicket(Long ticketId, Long userId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setAssignedTo(user);

        ticketRepository.save(ticket);
    }

    public TicketResponse getTicketById(Long id){

        logger.info("Fetching ticket with ID:{}",id);

        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new NotFoundException("Ticket not found with id:"+id));
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus().name(),
                ticket.getCreatedBy().getName(),
                ticket.getAssignedTo()!=null? ticket.getAssignedTo().getName():"Not Assigned",
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }

    public Ticket updateTicket(Long id, UpdateTicketRequest request) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));

        TicketStatus currentStatus=ticket.getStatus();
        TicketStatus newStatus= TicketStatus.valueOf(request.getStatus().toUpperCase());


        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setStatus(TicketStatus.valueOf(request.getStatus()));
        ticket.setPriority(request.getPriority());

        String role = getCurrentUserRole();

        if(!isValidTransition(currentStatus, newStatus,role)){
            throw new IllegalArgumentException("Invalid ticket status transition");
        }


        Ticket updated = ticketRepository.save(ticket);

        logger.info("Ticket updated with ID:{}", updated.getId());
        logger.info("Ticket updated with status: {}", updated.getStatus());

        return updated;
    }

    public void updateStatus(Long ticketId, TicketStatus newStatus){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String role=auth.getAuthorities().iterator().next().getAuthority();
        Ticket ticket=ticketRepository.findById(ticketId).orElseThrow(()->new NotFoundException("Ticket not found"));

        TicketStatus currentStatus = ticket.getStatus();

        if(role.equals("ROLE_CUSTOMER")){
            throw new ForbiddenException("Customer cannot update ticket");
        }
        if(!isValidTransition(currentStatus,newStatus,role)){
            throw new BadRequestException("Invald status transition");
        }
        ticket.setStatus(newStatus);
        ticketRepository.save(ticket);

    }
    private String getCurrentUserRole(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getAuthorities().isEmpty()){
            throw new RuntimeException("No roles found for user");
        }

        return auth.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_UNKNOWN");

    }
    private boolean isValidTransition(TicketStatus current,TicketStatus next,String role){
        if(role.equals("ROLE_AGENT")){
            if(current==TicketStatus.OPEN && next == TicketStatus.IN_PROGRESS)
            return true;

            if(current==TicketStatus.IN_PROGRESS && next == TicketStatus.RESOLVED)
                return true;

            return false;
        }
        if(role.equals("ROLE_ADMIN")){
            if(current==TicketStatus.RESOLVED && next == TicketStatus.CLOSED)
                return true;
            if(current==TicketStatus.CLOSED && next == TicketStatus.OPEN)
                return true;
            if(current==TicketStatus.OPEN && next == TicketStatus.IN_PROGRESS)
                return true;
            if(current==TicketStatus.IN_PROGRESS && next == TicketStatus.RESOLVED)
                return true;
        }
        return false;
    }
   public void deleteTicket(Long id){
        Ticket ticket=ticketRepository.findById(id).orElseThrow(()->new NotFoundException("Ticket not found"));

        ticketRepository.delete(ticket);
   }
   public Page<Ticket> getAllTickets(int page,int size){
        Pageable pageable=PageRequest.of(page,size,Sort.by("createdBy").descending());

        return ticketRepository.findAll(pageable);
   }

   public Page<TicketResponse> getTickets(
           int page,
           int size,
           String status,
           String priority,
           String search
   ){
        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());

        logger.info("Fetching all tickets");

        Page<Ticket> ticketPage=ticketRepository.findAll(pageable);

        List<TicketResponse> filtered= ticketPage.getContent().stream()
                .filter(t -> status == null || t.getStatus().name().equalsIgnoreCase(status))
                .filter((t-> priority == null || t.getPriority().equalsIgnoreCase(priority)))
                .filter(t-> search == null || t.getTitle().toLowerCase().contains(search.toLowerCase()))
                .map(t ->new TicketResponse(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getStatus().name(),
                        t.getCreatedBy().getName(),
                        t.getAssignedTo() !=null? t.getAssignedTo().getName(): "Unassigned",
                        t.getCreatedAt(),
                        t.getUpdatedAt()

                ))
                .toList();
        return new PageImpl<>(filtered,pageable, filtered.size());

    }
    private User getCurrentUser() {
        return userRepository.findById(1L)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

}
