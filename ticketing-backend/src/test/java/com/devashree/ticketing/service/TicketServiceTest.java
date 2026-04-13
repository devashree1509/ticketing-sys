package com.devashree.ticketing.service;

import com.devashree.ticketing.dto.CreateTicketRequest;
import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.entity.TicketStatus;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.exception.BadRequestException;
import com.devashree.ticketing.exception.ForbiddenException;
import com.devashree.ticketing.repository.TicketRepository;


import com.devashree.ticketing.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TicketServiceTest {


    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testGetTicketById() {

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus(TicketStatus.OPEN);

        User user = new User();
        user.setName("John");
        ticket.setCreatedBy(user);

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(ticket));

        TicketResponse result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(ticketRepository, times(1)).findById(1L);

    }
    @Test
    void testGetTicketById_notFound(){

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> {
            ticketService.getTicketById(1L);
        });

        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTicket_success(){

        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("Bug Issue");
        request.setDescription("Something broken");

        User user = new User();
        user.setId(1L);
        user.setName("Test User");

        List<User> users = new ArrayList<>();
        users.add(user);

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setTitle("Bug Issue");
        savedTicket.setCreatedBy(user);
        savedTicket.setStatus(TicketStatus.OPEN);

        when(userRepository.findAll()).thenReturn(users);
        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(savedTicket);

        TicketResponse response = ticketService.createTicket(request);

        assertNotNull(response);
        assertEquals("Bug Issue",response.getTitle());

        verify(ticketRepository, times(1)).save(any(Ticket.class));

    }

    @Test
    void testAssignTicket_success(){

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus(TicketStatus.OPEN);

        User agent = new User();
        agent.setId(2L);
        agent.setName("Agent User");

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(ticket));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(agent));

        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(ticket);

        ticketService.assignTicket(1L,2L);

        assertEquals(agent,ticket.getAssignedTo());

        verify(ticketRepository,times(1)).save(ticket);

    }

    @Test
    void testInvalidStatusTranistion() {

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus(TicketStatus.CLOSED);

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(ticket));

        Authentication auth = mock(Authentication.class);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_AGENT"));

        when(auth.getAuthorities()).thenReturn((Collection)authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(BadRequestException.class, () -> {
            ticketService.updateStatus(1L,TicketStatus.OPEN);
        });

        verify(ticketRepository, never()).save(any());
    }

    @Test
    void testCutomerCannotUpdateStatus(){

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus(TicketStatus.OPEN);

        when(ticketRepository.findById(1L))
                .thenReturn(Optional.of(ticket));

        Authentication auth = mock(Authentication.class);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        when(auth.getAuthorities()).thenReturn((Collection) authorities );
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(ForbiddenException.class,() -> {
            ticketService.updateStatus(1L,TicketStatus.CLOSED);
        });

        verify(ticketRepository,never()).save(any());
    }
}
