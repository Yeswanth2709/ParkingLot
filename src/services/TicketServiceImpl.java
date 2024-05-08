package services;

import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import strategies.spot_assignment.AssignSpotStrategy;

import java.util.Date;

public class TicketServiceImpl implements TicketService{
    private GateService gateService;
    private VehicleRepository vehicleRepository;
    private AssignSpotStrategy assignSpotStrategy;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;
    public TicketServiceImpl(GateService gateService,VehicleRepository vehicleRepository,AssignSpotStrategy assignSpotStrategy,ParkingLotRepository parkingLotRepository,TicketRepository ticketRepository){
        this.gateService = gateService;
        this.vehicleRepository=vehicleRepository;
        this.assignSpotStrategy=assignSpotStrategy;
        this.parkingLotRepository=parkingLotRepository;
        this.ticketRepository=ticketRepository;
    }


    @Override
    public Ticket generateTicket(int gateId, String vehicleNumber, String vehicleType) throws Exception{
        Gate gate = gateService.getGateById(gateId);
        VehicleType type = VehicleType.getTypeFromStr(vehicleType);
        Vehicle vehicle = vehicleRepository.createIfNotExists(vehicleNumber, type);
        ParkingLot parkingLot = parkingLotRepository.getParkingLotByGateId(gateId);
        if(parkingLot==null){
            throw  new Exception("Invalid gate id");
        }
        Spot spot = assignSpotStrategy.assignSpot(type, parkingLot);
        Ticket ticket=new Ticket();
        ticket.setAssignedSpot(spot);
        ticket.setGate(gate);
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(new Date());
        return ticketRepository.insertTicket(ticket);
    }

    @Override
    public Ticket getTicketById(int ticketId) {
        return ticketRepository.getTicketById(ticketId);
    }
}
