package repositories;

import models.Invoice;

import java.util.HashMap;
import java.util.Map;

public class InvoiceRepository {
    private Map<Integer, Invoice> map;

    public InvoiceRepository(Map<Integer, Invoice> map) {
        this.map = map;
    }

    public InvoiceRepository() {
        this.map=new HashMap<>();
    }
    private static  int ID=1;
    public Invoice insertInvoice(Invoice invoice){
        invoice.setId(ID);
        map.put(ID++,invoice);
        return invoice;
    }
}
