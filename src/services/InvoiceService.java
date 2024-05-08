package services;

import exceptions.InvalidGateException;
import exceptions.InvalidTicketException;
import models.Invoice;

public interface InvoiceService {
    Invoice generateInvoice(int tickedId,int gateId) throws InvalidTicketException, InvalidGateException;
}
