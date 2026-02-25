package com.devashree.ticketing.controller;

import com.devashree.ticketing.dto.CreateCommentRequest;
import com.devashree.ticketing.entity.TicketComment;
import com.devashree.ticketing.service.CommentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST /api/tickets/{id}/comments
    @PostMapping
    public TicketComment addComment(
            @PathVariable Long ticketId,
            @RequestBody CreateCommentRequest request) {

        Long currentUserId = 1L; // TEMP (later from JWT)

        return commentService.addComment(ticketId, request, currentUserId);
    }

    // GET /api/tickets/{id}/comments
    @GetMapping
    public List<TicketComment> getComments(@PathVariable Long ticketId) {

        return commentService.getComments(ticketId);
    }
}