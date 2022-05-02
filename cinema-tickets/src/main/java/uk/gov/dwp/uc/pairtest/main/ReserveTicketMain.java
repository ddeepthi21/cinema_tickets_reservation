package uk.gov.dwp.uc.pairtest.main;

import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public class ReserveTicketMain {
	public static void main(String[] args) {
		TicketService ticketService = new TicketServiceImpl();
		Long id = new Long(1);
		TicketTypeRequest ttr[] = new TicketTypeRequest[] {
		 			 new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 2),
		 			 new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2),
		 			 new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2),};
		ticketService.purchaseTickets(id);
	}

}
