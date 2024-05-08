package services;

import models.Ticket;

public interface TicketService {
    Ticket generateTicket(int gateId,String vehicleNumber,String vehicleType) throws Exception;

    Ticket getTicketById(int ticketId);
}
