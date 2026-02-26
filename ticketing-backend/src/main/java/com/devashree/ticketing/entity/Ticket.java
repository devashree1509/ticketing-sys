package com.devashree.ticketing.entity;
 import jakarta.persistence.*;
 import lombok.*;
 import java.time.LocalDateTime;

@Entity
 @Getter
 @Setter
 @NoArgsConstructor
 @AllArgsConstructor
 @Builder
 @Table(name="tickets")

public class Ticket {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)

     private  Long id;
     private  String title;
     private  String description;
     private  String status;
     private String category;
     private String priority;

     @Column(name="created_at")
     private LocalDateTime createdAt;

     @PrePersist
     protected void onCreate(){
         this.createdAt=LocalDateTime.now();
     }

     @ManyToOne //MANY tickets can created by ONE User
     @JoinColumn(name="created_by")
     private User createdBy;

     @ManyToOne // MANY tickets can assigned to ONE User
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

