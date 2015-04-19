package services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import services.interfaces.BusServicesLocal;
import services.interfaces.BusinessLogicServicesLocal;
import services.interfaces.BusinessLogicServicesRemote;
import services.interfaces.LineServicesLocal;
import services.interfaces.StationServicesLocal;
import domain.Bus;
import domain.Line;
import domain.Station;
import domain.Stop;
import domain.Type;
import domain.User;

/**
 * Session Bean implementation class BusinessLogicServices
 */
@Stateless
public class BusinessLogicServices implements BusinessLogicServicesRemote,
		BusinessLogicServicesLocal {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private LineServicesLocal lineServicesLocal;

	@EJB
	private StationServicesLocal stationServicesLocal;

	@EJB
	private BusServicesLocal busServicesLocal;

	/**
	 * Default constructor.
	 */
	public BusinessLogicServices() {

	}

	public User identification(String login, String password) {
		try {
			String jpql = "select u from User u where u.login = :x and u.password = :y";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("x", login);
			query.setParameter("y", password);
			return (User) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean setLineStations(Line line, Map<Integer, Station> stations) {
		Boolean b = false;
		try {
			// On a ajouté un persist sur line car pour créer un type il faut
			// passer en paramétre une ligne qui doit etre managed
			entityManager.persist(line);
			String typeName = "";
			for (int i = 0; i < stations.size(); i++) {
				if (i == 0) {
					typeName = "Departure Terminal";
				} else if (i == stations.size() - 1) {
					typeName = "Arrival Terminal";
				} else {
					typeName = "Intermediate Station";
				}
				Type type = new Type(typeName, i, stations.get(i), line);
				entityManager.persist(type);
			}
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public Boolean setLineBuses(Line line, List<Bus> buses) {
		Boolean b = false;
		try {
			entityManager.merge(line);
			line.linkBusesToThisLine(buses);
			entityManager.merge(line);
			for (Bus bus : buses) {
				entityManager.merge(bus);
			}
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public Boolean reportBusStop(Integer nbFreePlaces, Bus bus, Station station) {
		Boolean b = false;
		try {
			Stop stop = new Stop(nbFreePlaces, bus, station);
			entityManager.persist(stop);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Station> findStationsByLineId(Integer id) {
		List<Station> stations = new ArrayList<>();
		try {
			Line line = lineServicesLocal.findLineById(id);
			String jpql = "select s from Station s, Line l, Type t "
					+ "where t.station = s and t.line = l and t.line = :x order by t.stationOrder";
			// String jpql2 =
			// "SELECT s FROM Station s joins s.types ts WHERE ts.line = :x ORDER BY ts.stationOrder";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("x", line);
			stations = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return stations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Line> findLinesByStationId(Integer id) {
		List<Line> lines = new ArrayList<>();
		try {
			Station station = stationServicesLocal.findStationById(id);
			String jpql = "select l from Station s, Line l, Type t "
					+ "where t.station = s and t.line = l and t.station = :x";
			// String jpql2 =
			// "SELECT l FROM Line l joins l.types ts WHERE ts.station = :x ";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("x", station);
			lines = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return lines;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bus> findBusesByLineId(Integer id) {
		List<Bus> buses = new ArrayList<>();
		try {
			Line line = lineServicesLocal.findLineById(id);
			String jpql = "select b froom Bus b where b.line = :param1";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("param1", line);
			buses = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buses;
	}

	@Override
	public List<Station> findAllPreviousStationsByStationAndLine(Line line,
			Station station) {

		List<Station> stations = new ArrayList<>();
		try {

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return stations;
	}

	@SuppressWarnings("unchecked")
	public List<Bus> findAllAvailableBuses() {
		List<Bus> buses = new ArrayList<>();
		try {
			String jpql = "select b from Bus b where b.line is null";
			Query query = entityManager.createQuery(jpql);
			buses = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buses;
	}

	@Override
	public List<Bus> findComingSoonBuses(Station station) {
		List<Bus> buses = new ArrayList<>();
		List<Line> lines = findLinesByStationId(station.getId());
		System.out.println(lines);
		for (Line l : lines) {
			List<Bus> buses2 = findBusesByLineId(l.getId());
			System.out.println(l + "" + buses2);
			for (Bus b : buses2) {
				Stop lastOne = findLastStopByBusId(b.getId());
				if (lastOne != null) {
					Station lastStation = lastOne.getStation();
					Integer lastStationOrder = findStationOrderByLineId(
							lastStation.getId(), l.getId());
					Integer thisStationOrder = findStationOrderByLineId(
							station.getId(), l.getId());
					System.out.println(b + " last stop "
							+ lastOne.getStation().getName() + " this order"
							+ thisStationOrder + " last order"
							+ lastStationOrder);
					if (thisStationOrder != null && lastStationOrder != null
							&& lastStationOrder < thisStationOrder) {
						buses.add(b);
					}
				}
			}
		}
		return buses;
	}

	@Override
	public Stop findLastStopByBusId(Integer idBus) {
		Stop stop = null;
		try {
			String jpql = "select s from Stop s where s.bus.id = :param1 order by s.stopId.date desc";
			TypedQuery<Stop> query = entityManager
					.createQuery(jpql, Stop.class)
					.setParameter("param1", idBus);
			List<Stop> stops = query.getResultList();
			if (stops.size() != 0) {
				stop = stops.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return stop;
	}

	@Override
	public Integer findStationOrderByLineId(Integer idStation, Integer idLine) {
		Integer order = null;
		try {
			String jpql = "select t.stationOrder from Type t where t.station.id = :param1 and t.line.id =:param2";
			TypedQuery<Integer> query = entityManager.createQuery(jpql,
					Integer.class);
			query.setParameter("param1", idStation);
			query.setParameter("param2", idLine);
			order = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return order;
	}

	@Override
	public Bus findBusByNumber(String number) {
		Bus bus = null;
		try {
			String jpql = "select b from Bus b where b.number = :param1";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("param1", number);
			bus = (Bus) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bus;
	}

	@Override
	public Station findStationByName(String name) {
		Station station = null;
		try {
			String jpql = "select s from Station s where s.name = :param1";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("param1", name);
			station = (Station) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return station;
	}

	@Override
	public Line findLineByName(String name) {
		Line line = null;
		try {
			String jpql = "select l from Line l where l.name = :param1";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("param1", name);
			line = (Line) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return line;
	}
}
