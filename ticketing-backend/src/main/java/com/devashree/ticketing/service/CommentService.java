package com.devashree.ticketing.service;

import com.devashree.ticketing.dto.CreateCommentRequest;
import com.devashree.ticketing.entity.*;
import com.devashree.ticketing.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final TicketCommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentService(TicketCommentRepository commentRepository,
                          TicketRepository ticketRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // POST comment
    public TicketComment addComment(Long ticketId, CreateCommentRequest request, Long currentUserId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User author = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(author);
        comment.setMessage(request.getMessage());

        return commentRepository.save(comment);
    }

    // GET comments
    public List<TicketComment> getComments(Long ticketId) {

        ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return commentRepository.findByTicketId(ticketId);
    }
}