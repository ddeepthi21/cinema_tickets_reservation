package uk.gov.dwp.uc.pairtest;

import java.util.Arrays;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {
	public static final int PARENT_TKT_PRICE = 20;
	public static final int CHILD_TKT_PRICE = 10;
	public static final String ACCOUNID_ERROR = "Account Id provided is incorrect";
	public static final String COUNT_ERROR = "Number of Tickets to be booked should be less than or equal to 20";
	public static final String SUCCESS = "Reservation Successful";
	public static final String ADULT_NOT_PRESENT = "No Adult present in the group";
	
	public TicketPaymentService ticketService = new TicketPaymentServiceImpl();
	public SeatReservationService reserveService = new SeatReservationServiceImpl();

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
    	verifyDetails(accountId,ticketTypeRequests);
    	int totalTickets = 0;
    	int totalAmount =0;
    	if(ticketTypeRequests.length ==0)
    		System.out.println("No requests to process");
    	else {
	    	if(!isAdultAvailable(ticketTypeRequests))
	    		throw new InvalidPurchaseException(ADULT_NOT_PRESENT);
	    	for(TicketTypeRequest ticket : ticketTypeRequests) {
	    		if(TicketTypeRequest.Type.CHILD == ticket.getTicketType()) {
	    			totalAmount += ticket.getNoOfTickets()*CHILD_TKT_PRICE;
					totalTickets += ticket.getNoOfTickets();
					//System.out.println(totalAmount);
	    			
	    		}else if(ticket.getTicketType()==TicketTypeRequest.Type.ADULT) {
	    			totalTickets += ticket.getNoOfTickets();
	    			totalAmount += ticket.getNoOfTickets()*PARENT_TKT_PRICE;
	    		}
	    		if(totalTickets > 20) {
	    			throw new InvalidPurchaseException(COUNT_ERROR);
	    		}
	    	}
	    	
	    	ticketService.makePayment(accountId, totalAmount);
	    	reserveService.reserveSeat(accountId, totalTickets);
	    	System.out.println(SUCCESS);
	    	System.out.println("No.of tickets reserved : "+totalTickets); 
	    	System.out.println("Billed Amount is : "+totalAmount); 
    	}
    }
    
    private boolean isAdultAvailable(TicketTypeRequest... ticketTypeRequests) {
    	TicketTypeRequest request = Arrays
    	        .stream(ticketTypeRequests)
    	        .filter(tktRequest -> tktRequest.getTicketType() == TicketTypeRequest.Type.ADULT)
    	        .findFirst().orElse(null);
    	return null == request ? false : true;
    	
    }
    
    private void verifyDetails(Long accountId, TicketTypeRequest... ticketTypeRequests) {
    	if(ticketTypeRequests.length > 20)
    		throw new InvalidPurchaseException(COUNT_ERROR);
    	if(accountId <= 0)
    		throw new InvalidPurchaseException(ACCOUNID_ERROR);
    }
}
