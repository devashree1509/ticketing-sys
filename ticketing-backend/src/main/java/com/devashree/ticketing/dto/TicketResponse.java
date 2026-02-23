package com.devashree.ticketing.dto;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String createdBy;
    private String assignedTo;

    public TicketResponse(Long id,String title,String description,String status,String createdBy,String assignedTo){
        this.id=id;
        this.title=title;
        this.description=description;
        this.status=status;
        this.createdBy=createdBy;
        this.assignedTo=assignedTo;
    }

    public Long getId(){return id;}
    public String getTitle(){return title;}
    public String getStatus(){return status;}
    public String getDescription(){return description;}
    public String getCreatedBy(){return createdBy;}
    public String getAssignedTo(){return assignedTo;}


}
