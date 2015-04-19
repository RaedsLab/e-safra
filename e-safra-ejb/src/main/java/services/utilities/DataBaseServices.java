package services.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import services.interfaces.BusinessLogicServicesLocal;
import domain.Bus;
import domain.BusManager;
import domain.Driver;
import domain.Line;
import domain.Passenger;
import domain.Station;

/**
 * Session Bean implementation class DBPopulation
 */
@Singleton
@Startup
public class DataBaseServices {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	BusinessLogicServicesLocal businessLogicServicesLocal;

	/**
	 * Default constructor.
	 */
	public DataBaseServices() {

	}

	@PostConstruct
	public void populate() {
		try {
			Driver driver1 = new Driver("Amine", "Jedidi", 'M',
					new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1991"),
					"amine_jedidi", "esprit", 10, "10:00 AM");
			entityManager.persist(driver1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Driver driver2 = new Driver("Mohamed Amine", "Trabelsi", 'M',
					new SimpleDateFormat("MM/dd/yyyy").parse("01/02/1991"),
					"mohamed_amine_trabelsi", "esprit", 5, "00:00 PM");
			entityManager.persist(driver2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Passenger passenger1 = new Passenger("Nizar", "Boussarsar", 'M',
					new SimpleDateFormat("MM/dd/yyyy").parse("02/16/1991"),
					"nizar_boussarsar", "esprit", 50.6D);
			entityManager.persist(passenger1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			Passenger passenger2 = new Passenger("Meriem Azza", "Triki", 'F',
					new SimpleDateFormat("MM/dd/yyyy").parse("19/09/1991"),
					"meriem_azza_triki", "esprit", 100D);
			entityManager.persist(passenger2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			BusManager busManager = new BusManager("Mohamed Ali", "Bettaieb",
					'M',
					new SimpleDateFormat("MM/dd/yyyy").parse("01/03/1991"),
					"mohamed_ali_bettaieb", "esprit", 1);
			entityManager.persist(busManager);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Bus bus1 = new Bus("B01");
		entityManager.persist(bus1);
		Bus bus2 = new Bus("B02");
		entityManager.persist(bus2);
		Bus bus3 = new Bus("B03");
		entityManager.persist(bus3);
		Bus bus4 = new Bus("B04");
		entityManager.persist(bus4);
		Bus bus5 = new Bus("B05");
		entityManager.persist(bus5);
		Bus bus6 = new Bus("B06");
		entityManager.persist(bus6);
		Bus bus7 = new Bus("B07");
		entityManager.persist(bus7);
		Bus bus8 = new Bus("B08");
		entityManager.persist(bus8);
		Bus bus9 = new Bus("B09");
		entityManager.persist(bus9);
		Bus bus10 = new Bus("B10");
		entityManager.persist(bus10);
		Bus bus11 = new Bus("B11");
		entityManager.persist(bus11);
		Bus bus12 = new Bus("B12");
		entityManager.persist(bus12);
		Bus bus13 = new Bus("B13");
		entityManager.persist(bus13);
		Bus bus14 = new Bus("B14");
		entityManager.persist(bus14);
		Bus bus15 = new Bus("B15");
		entityManager.persist(bus15);
		Bus bus16 = new Bus("B16");
		entityManager.persist(bus16);

		Station station1 = new Station("Marsa");
		entityManager.persist(station1);
		Station station2 = new Station("Sidi Daoud");
		entityManager.persist(station2);
		Station station3 = new Station("La Goulette");
		entityManager.persist(station3);
		Station station4 = new Station("Rades");
		entityManager.persist(station4);
		Station station5 = new Station("Megrine");
		entityManager.persist(station5);
		Station station6 = new Station("Ain Zaghouan");
		entityManager.persist(station6);
		Station station7 = new Station("Les berges du lac");
		entityManager.persist(station7);
		Station station8 = new Station("Charguia");
		entityManager.persist(station8);
		Station station9 = new Station("Borj Louzir");
		entityManager.persist(station9);

		List<Bus> listBuses1 = new ArrayList<>();
		listBuses1.add(bus1);
		listBuses1.add(bus2);
		List<Bus> listBuses2 = new ArrayList<>();
		listBuses2.add(bus3);
		listBuses2.add(bus4);
		List<Bus> listBuses3 = new ArrayList<>();
		listBuses3.add(bus5);
		listBuses3.add(bus6);
		List<Bus> listBuses4 = new ArrayList<>();
		listBuses4.add(bus7);
		listBuses4.add(bus8);

		Line line1 = new Line("Marsa - Megrine");
		line1.linkBusesToThisLine(listBuses1);
		entityManager.persist(line1);

		Line line2 = new Line("Marsa - Borj Louzir");
		line2.linkBusesToThisLine(listBuses2);
		entityManager.persist(line2);

		Line line3 = new Line("Megrine - Marsa");
		line3.linkBusesToThisLine(listBuses3);
		entityManager.persist(line3);

		Line line4 = new Line("Borj Louzir - Marsa");
		line4.linkBusesToThisLine(listBuses4);
		entityManager.persist(line4);

		// Link some specific stations to a specific line
		Map<Integer, Station> stations1 = new HashMap<>();
		stations1.put(0, station1); //Marsa
		stations1.put(1, station2); //Sidi Daoud
		stations1.put(2, station3); //La Goulette
		stations1.put(3, station4); //Rades
		stations1.put(4, station5); //Megrin
		businessLogicServicesLocal.setLineStations(line1, stations1);

		// Link some specific stations to a specific line
		Map<Integer, Station> stations2 = new HashMap<>();
		stations2.put(0, station1); //Marsa
		stations2.put(1, station6); //Ain Zaghouan
		stations2.put(2, station7); //Les berges du lac
		stations2.put(3, station8); //Charguia
		stations2.put(4, station9); //Borj Louzir
		businessLogicServicesLocal.setLineStations(line2, stations2);

		// Link some specific stations to a specific line
		Map<Integer, Station> stations3 = new HashMap<>();
		stations3.put(0, station5); //Megrin
		stations3.put(1, station4); //Rades
		stations3.put(2, station3); //La Goulette
		stations3.put(3, station2); //Sidi Daoud
		stations3.put(4, station1); //Marsa
		businessLogicServicesLocal.setLineStations(line3, stations3);

		// Link some specific stations to a specific line
		Map<Integer, Station> stations4 = new HashMap<>();
		stations4.put(0, station9); //Borj Louzir
		stations4.put(1, station8); //Charguia
		stations4.put(2, station7); //Les berges du lac
		stations4.put(3, station6); //Ain Zaghouan
		stations4.put(4, station1); //Marsa
		businessLogicServicesLocal.setLineStations(line4, stations4);

	}
}