import controllers.InvoiceController;
import controllers.TicketController;
import dtos.GenerateInvoiceRequestDto;
import dtos.GenerateInvoiceResponseDto;
import dtos.GenerateTicketRequestDto;
import dtos.GenerateTicketResponseDto;
import factories.CalculateFeesStrategyFactory;
import models.*;
import repositories.*;
import services.*;
import strategies.spot_assignment.AssignSpotStrategy;
import strategies.spot_assignment.NearestSpotAssignmentStrategy;
import java.util.*;

public class ParkingLotRunner {
    public static void main(String[] args) {
        //Dummy data;
        Gate gate1=new Gate();
        gate1.setName("Gate 1");
        gate1.setGateType(GateType.ENTRY);
        Operator operator1=new Operator();
        operator1.setName("Raju");
        operator1.setEmail("raju@gmail.com");
        gate1.setOperator(operator1);
        gate1.setId(1);
        Gate gate2=new Gate();
        gate2.setName("Gate 2");
        gate2.setGateType(GateType.EXIT);
        Operator operator2=new Operator();
        operator1.setName("Rahul");
        operator1.setEmail("rahul@gmail.com");
        gate2.setOperator(operator2);
        gate2.setId(2);
        Map<Integer,Gate> gateMap=new HashMap<>(){{
            put(1,gate1);
            put(2,gate2);
        }};
        List<Spot> spots= Arrays.asList(
                new Spot("A1",SpotStatus.UNOCCUPIED,VehicleType.CAR),
                new Spot("A2",SpotStatus.UNOCCUPIED,VehicleType.BIKE)
        );
        List<Section> sections=new ArrayList<>();
        Section section1=new Section();
        section1.setName("A");
        section1.setId(1);
        section1.setSpots(spots);
        sections.add(section1);
        List<Floor> floors=new ArrayList<>();
        Floor floor1=new Floor();
        floor1.setId(1);
        floor1.setFloorNum(1);
        floor1.setFloorStatus(FloorStatus.OPERATIONAL);
        floor1.setSections(sections);
        floors.add(floor1);
        List<Gate> gates=new ArrayList<>();
        gates.add(gate1);
        gates.add(gate2);
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setId(1);
        parkingLot.setFloors(floors);
        parkingLot.setGates(gates);
        Map<Integer,ParkingLot> parkingLotMap=new HashMap<>(){{
            put(1,parkingLot);

        }};

        Slab slab1=new Slab(1,VehicleType.CAR,0,2,10);
        Slab slab2=new Slab(2,VehicleType.CAR,2,4,20);
        Slab slab3=new Slab(3,VehicleType.CAR,4,8,25);
        Slab slab4=new Slab(4,VehicleType.CAR,8,-1,40);

        Map<Integer,Slab> slabMap=new HashMap<>(){{
            put(1,slab1);
            put(2,slab2);
            put(3,slab3);
            put(4,slab4);
        }};
        SlabRepository slabRepository=new SlabRepository(slabMap);
        InvoiceRepository invoiceRepository=new InvoiceRepository();
        CalculateFeesStrategyFactory calculateFeesStrategyFactory=new CalculateFeesStrategyFactory(slabRepository);
        GateRepository gateRepository=new GateRepository(gateMap);
        ParkingLotRepository parkingLotRepository=new ParkingLotRepository(parkingLotMap);
        TicketRepository ticketRepository=new TicketRepository();
        VehicleRepository vehicleRepository=new VehicleRepository();
        GateService gateService=new GateServiceImpl(gateRepository);
        AssignSpotStrategy assignSpotStrategy=new NearestSpotAssignmentStrategy();
        TicketServiceImpl ticketService = new TicketServiceImpl(gateService, vehicleRepository, assignSpotStrategy, parkingLotRepository, ticketRepository);
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(ticketService, gateService, calculateFeesStrategyFactory, invoiceRepository);
        TicketController ticketController = new TicketController(ticketService);

        InvoiceController invoiceController=new InvoiceController(invoiceService);
        GenerateTicketRequestDto generateTicketRequestDto=new GenerateTicketRequestDto();
        generateTicketRequestDto.setGateId(1);
        generateTicketRequestDto.setVehicleNumber("AX879");
        generateTicketRequestDto.setVehicleType(VehicleType.CAR.toString());

        GenerateTicketResponseDto ticketResponseDto = ticketController.generateTicket(generateTicketRequestDto);
        List data=new ArrayList();
        data.add(ticketResponseDto.getTicket().getGate().getName());
        data.add(ticketResponseDto.getTicket().getVehicle().getVehicleNumber());
        data.add(ticketResponseDto.getResponse().getResponseStatus());
        data.add(ticketResponseDto.getTicket().getAssignedSpot().getName());
        data.add(ticketResponseDto.getTicket().getEntryTime());
        System.out.println(data);
        int ticketId=ticketResponseDto.getTicket().getId();
        GenerateInvoiceRequestDto generateInvoiceRequestDto=new GenerateInvoiceRequestDto();
        generateInvoiceRequestDto.setTicketId(ticketId);
        generateInvoiceRequestDto.setGateId(gate2.getId());
        GenerateInvoiceResponseDto invoiceResponseDto = invoiceController.generateInvoice(generateInvoiceRequestDto);
        System.out.println(invoiceResponseDto);
    }
}