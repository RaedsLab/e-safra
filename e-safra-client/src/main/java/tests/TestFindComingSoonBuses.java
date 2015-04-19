package tests;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import services.interfaces.BusinessLogicServicesRemote;
import domain.Bus;
import domain.Station;

public class TestFindComingSoonBuses {

	public static void main(String[] args) throws NamingException {
		Context context = new InitialContext();

		BusinessLogicServicesRemote businessLogicServicesRemote = (BusinessLogicServicesRemote) context
				.lookup("/e-safra-ejb/BusinessLogicServices!services.interfaces.BusinessLogicServicesRemote");

		Station station = businessLogicServicesRemote
				.findStationByName("Sidi Daoud");
		
		List<Bus> buses = businessLogicServicesRemote
				.findComingSoonBuses(station);

		System.out.println("There are " + buses.size()
				+ " that will pass by this station soon !");
		
		for (Bus bus : buses) {
			System.out.println("Bus number : " + bus.getNumber());
		}
	}

}
