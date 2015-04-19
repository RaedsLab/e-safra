package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import domain.Bus;

@Local
public interface BusServicesLocal {
	
	Boolean addBus(Bus Bus);

	Boolean deleteBus(Bus Bus);

	Boolean deleteBusById(Integer id);

	Boolean updateBus(Bus Bus);

	Bus findBusById(Integer id);

	List<Bus> findAllBuses();

}
