package services;

import exceptions.InvalidGateException;
import exceptions.InvalidTicketException;
import factories.CalculateFeesStrategyFactory;
import models.*;
import repositories.InvoiceRepository;
import strategies.pricing_strategy.CalculateFeesStrategy;

import java.util.Arrays;
import java.util.Date;

public class InvoiceServiceImpl implements InvoiceService{
    private TicketService ticketService;
    private GateService gateService;
    private CalculateFeesStrategyFactory calculateFeesStrategyFactory;
    private InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(TicketService ticketService, GateService gateService, CalculateFeesStrategyFactory calculateFeesStrategyFactory, InvoiceRepository invoiceRepository) {
        this.ticketService = ticketService;
        this.gateService = gateService;
        this.calculateFeesStrategyFactory = calculateFeesStrategyFactory;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice generateInvoice(int tickedId, int gateId) throws InvalidTicketException, InvalidGateException {
        Ticket ticket = ticketService.getTicketById(tickedId);
        if(ticket==null){
            throw new InvalidTicketException("Ticket is not present in database");
        }
        Gate gate = gateService.getGateById(gateId);
        if(gate==null){
            throw new InvalidGateException("Gate is not present in database");
        }
        if(gate.getGateType().equals(GateType.ENTRY)){
            throw new InvalidGateException("Invoice cannot be created at entry gate");
        }
        Date entryDate = ticket.getEntryTime();
        Date exitDate=new Date();
        CalculateFeesStrategy calculateFeesStrategy = calculateFeesStrategyFactory.getCalculateFeesStrategy(exitDate);
        double totalAmount = calculateFeesStrategy.calculateFees(entryDate, exitDate, ticket.getVehicle().getVehicleType());
        InvoiceDetail invoiceDetail= new InvoiceDetail();
        invoiceDetail.setName("Parking fees");
        invoiceDetail.setPrice(totalAmount);

        Invoice invoice=new Invoice();
        invoice.setTicket(ticket);
        invoice.setDetails(Arrays.asList(invoiceDetail));
        invoice.setExitTime(exitDate);
        return invoiceRepository.insertInvoice(invoice);

    }
}
