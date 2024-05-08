package controllers;

import dtos.GenerateInvoiceRequestDto;
import dtos.GenerateInvoiceResponseDto;
import dtos.Response;
import dtos.ResponseStatus;
import exceptions.InvalidRequestException;
import models.Invoice;
import services.InvoiceService;

public class InvoiceController {
    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public GenerateInvoiceResponseDto generateInvoice(GenerateInvoiceRequestDto requestDto){
        GenerateInvoiceResponseDto responseDto=new GenerateInvoiceResponseDto();
        try{
            if(requestDto.getTicketId()<0){
                throw new InvalidRequestException("Ticket id cannot be negative");
            }
            if(requestDto.getGateId()<0){
                throw new InvalidRequestException("Gate id cannot be negative");
            }
        }catch (Exception e){
            Response response=new Response();
            response.setResponseStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
            responseDto.setResponse(response);
            return responseDto;
        }
        try{
            Invoice invoice = invoiceService.generateInvoice(requestDto.getTicketId(), requestDto.getGateId());
            Response response=new Response();
            response.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setResponse(response);
            responseDto.setInvoice(invoice);
            return responseDto;
        }catch (Exception e){
            Response response=new Response();
            response.setResponseStatus(ResponseStatus.FAILED);
            response.setErrorMessage(e.getMessage());
            responseDto.setResponse(response);
            return responseDto;
        }

    }
}
