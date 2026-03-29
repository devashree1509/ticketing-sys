package com.devashree.ticketing.dto;

import com.devashree.ticketing.entity.TicketStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateTicketStatusRequest {

    private TicketStatus status;
}
