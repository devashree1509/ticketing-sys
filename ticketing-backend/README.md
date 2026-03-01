#Ticketing-system backend

##Features
-User Authentication
-Create Ticket
-Assign Ticket
-View Tickets

##Tech Stack
-Java 17
-Spring Boot
-MySQL
-Spring Data JPA
-Maven

##How to run
1. Open project in IntelliJ
2. Configure database in application.properties
3. Run TicketingBackendApplication.java
4. Open http://localhost:8080

#API Endpoint(v0.2)
### Tickets
 
POST /api/tickets
GET /api/tickets
GET /api/tickets/{id}
PUT /api/tickets/{id}
DELETE /api/tickets/{id}

Supports query parameters:
-page
-size
-status
-priority
-search

Example:
GET/api/ticktes?status=OPEN&priority=MEDIUM

---
Comments

POST/api/tickets/{ticketId}/comments
GET/api/tickets/{ticketId}/comments

---

##Postman
Postman collection and enviorment files are available in the '/postman' folder.

Import the collection and select the enviorment before testing  

