package controllers;

import dtos.GenerateTicketRequestDto;
import dtos.GenerateTicketResponseDto;
import dtos.Response;
import dtos.ResponseStatus;
import exceptions.InvalidRequestException;
import models.Ticket;
import services.TicketService;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public GenerateTicketResponseDto generateTicket(GenerateTicketRequestDto generateTicketRequestDto){
        GenerateTicketResponseDto responseDto=new GenerateTicketResponseDto();
        try {
            if (generateTicketRequestDto.getGateId() < 0) {
                throw new InvalidRequestException("Invalid gate id");
            }
            if (generateTicketRequestDto.getVehicleType() == null || generateTicketRequestDto.getVehicleType().equals("")) {
                throw new InvalidRequestException("gate type is mandatory");
            }
        }catch (Exception e){
            Response response=new Response();
            response.setResponseStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
            responseDto.setResponse(response);
            return responseDto;
        }
        Response response=new Response();
        try {
            Ticket ticket = ticketService.generateTicket(generateTicketRequestDto.getGateId(), generateTicketRequestDto.getVehicleNumber(), generateTicketRequestDto.getVehicleType());
            responseDto.setTicket(ticket);
            response.setResponseStatus(ResponseStatus.SUCCESS);

        }catch(Exception e){
            response.setResponseStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
        }
        responseDto.setResponse(response);
        return responseDto;
    }
}
