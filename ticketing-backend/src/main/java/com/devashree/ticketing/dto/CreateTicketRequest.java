package com.devashree.ticketing.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    @Size(min=3, max=100)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min=5,max=100)
    private String description;

    @NotBlank(message = "CreatedBy is required")
    private Long createdBy;

    private Long assignedTo;

    @NotBlank(message = "Category is required")
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
