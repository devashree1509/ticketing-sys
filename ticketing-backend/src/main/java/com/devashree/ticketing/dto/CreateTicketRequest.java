package com.devashree.ticketing.dto;

public class CreateTicketRequest {

    private String title;
    private String description;
    private Long createdBy;
    private Long assignedTo;

    public CreateTicketRequest() {
    }

    public String getTitle() {
        return title;
    }
        public void setTitle(String title){
            this.title=title;
        }

        public String getDescription(){
        return description;
        }

        public void setDescription(String description){
        this.description=description;
        }

        public Long getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(Long createdBy){
        this.createdBy=createdBy;
    }

    public Long getAssignedTo(){
        return assignedTo;
    }

    public void setAssignedTo(){
        this.assignedTo=assignedTo;
    }

}
