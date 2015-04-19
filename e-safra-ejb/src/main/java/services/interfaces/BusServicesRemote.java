package services.interfaces;

import java.util.List;

import javax.ejb.Remote;

import domain.Bus;

@Remote
public interface BusServicesRemote {

	Boolean addBus(Bus Bus);

	Boolean deleteBus(Bus Bus);

	Boolean deleteBusById(Integer id);

	Boolean updateBus(Bus Bus);

	Bus findBusById(Integer id);

	List<Bus> findAllBuses();

}
