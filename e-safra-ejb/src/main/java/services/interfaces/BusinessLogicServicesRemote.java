package services.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import domain.Bus;
import domain.Line;
import domain.Station;
import domain.Stop;
import domain.User;

@Remote
public interface BusinessLogicServicesRemote {

	User identification(String login, String password);

	Boolean setLineStations(Line line, Map<Integer, Station> stations);

	List<Station> findStationsByLineId(Integer id);

	Boolean reportBusStop(Integer nbFreePlaces, Bus bus, Station station);

	List<Line> findLinesByStationId(Integer id);

	List<Bus> findBusesByLineId(Integer id);

	List<Station> findAllPreviousStationsByStationAndLine(Line line, Station station);
	
	Boolean setLineBuses(Line line, List<Bus> buses);
	
	List<Bus> findAllAvailableBuses();
	
	Integer findStationOrderByLineId(Integer idStation, Integer idLine);

	Stop findLastStopByBusId(Integer idBus);

	List<Bus> findComingSoonBuses(Station station);
	
	Bus findBusByNumber(String number);

	Station findStationByName(String name);
	
	Line findLineByName(String name);
	
}
