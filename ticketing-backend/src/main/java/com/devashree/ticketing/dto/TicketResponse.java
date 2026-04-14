package com.devashree.ticketing.dto;

import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String createdBy;
    private String assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketResponse(Long id,String title,String description,String status,String createdBy,String assignedTo,LocalDateTime createdAt,LocalDateTime updatedAt){
        this.id=id;
        this.title=title;
        this.description=description;
        this.status=status;
        this.createdBy=createdBy;
        this.assignedTo=assignedTo;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    public Long getId(){return id;}
    public String getTitle(){return title;}
    public String getStatus(){return status;}
    public String getDescription(){return description;}
    public String getCreatedBy(){return createdBy;}
    public String getAssignedTo(){return assignedTo;}
    public LocalDateTime getCreatedAt() {
        return createdAt;}
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
