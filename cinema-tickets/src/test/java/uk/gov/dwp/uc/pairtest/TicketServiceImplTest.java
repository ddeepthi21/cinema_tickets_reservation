package uk.gov.dwp.uc.pairtest;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {
	@InjectMocks
	TicketService ticketService = new TicketServiceImpl();
	
	@Test
	public void purchaseTicketsTestSuccess() {
		TicketTypeRequest ttr[] = new TicketTypeRequest[] {
	 			 new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),};
		ticketService.purchaseTickets((long) 1, ttr);
	}
	
	@Test(expected=InvalidPurchaseException.class)
	public void purchaseTicketsCountErrorTest() {
		TicketTypeRequest ttr[] = new TicketTypeRequest[] {
	 			 new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
	 			 new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 25),};
		ticketService.purchaseTickets((long) 1, ttr);
	}
	

}
