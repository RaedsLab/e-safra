package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import domain.Station;

@Local
public interface StationServicesLocal {

	Boolean addStation(Station Station);

	Boolean deleteStation(Station Station);

	Boolean deleteStationById(Integer id);

	Boolean updateStation(Station Station);

	Station findStationById(Integer id);

	List<Station> findAllStations();

}
