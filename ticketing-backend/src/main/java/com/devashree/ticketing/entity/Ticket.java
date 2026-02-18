package com.devashree.ticketing.entity;
 import jakarta.persistence.*;


 @Entity
 @Table(name="tickets")
public class Ticket {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)

     private  Long id;
     private  String title;
     private  String description;
     private  String status;

     public Ticket(){}

     public long getid(){
         return id;
     }

     public String getTitle(){
         return title;
     }

     public void setTitle(){
         this.title=title;
     }

     public String getDescription(){
         return description;
     }

     public void setDescription(){
         this.description=description;
     }

     public String getStatus(){
         return status;
     }

     public void setStatus(){
         this.status=status;
     }
}
