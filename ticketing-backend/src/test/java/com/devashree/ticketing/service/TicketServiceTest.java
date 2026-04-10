package com.devashree.ticketing.service;

import com.devashree.ticketing.dto.TicketResponse;
import com.devashree.ticketing.entity.Ticket;
import com.devashree.ticketing.entity.TicketStatus;
import com.devashree.ticketing.entity.User;
import com.devashree.ticketing.repository.TicketRepository;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TicketServiceTest {


    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testGetTicketById(){

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
        assertEquals(1l,result.getId());

        verify(ticketRepository,times(1)).findById(1L);

    }
}
