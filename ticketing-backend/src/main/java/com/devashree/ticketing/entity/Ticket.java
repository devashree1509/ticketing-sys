package com.devashree.ticketing.entity;
 import jakarta.persistence.*;
 import lombok.*;

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
     private String priority;

     @ManyToOne //MANY tickets can created by ONE User
     @JoinColumn(name="created_by")
     private User createdBy;

     @ManyToOne // MANY tickets can assigned to ONE User
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
}

