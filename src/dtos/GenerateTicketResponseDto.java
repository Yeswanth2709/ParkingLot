package dtos;

import models.Ticket;

public class GenerateTicketResponseDto {
    private Ticket ticket;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Response response;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }


}
