package models;

import java.util.Date;
import java.util.List;

public class Invoice extends BaseModel{
    private Ticket ticket;
    private Date exitTime;
    private double totalAmount;
    private List<InvoiceDetail> details;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    private void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetail> details) {
        this.details = details;
        double totalAmt=0;
        for (InvoiceDetail detail : this.details) {
            totalAmt+=detail.getPrice();
        }
        setTotalAmount(totalAmt);
    }
}
