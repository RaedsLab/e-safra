package beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import services.interfaces.BusinessLogicServicesLocal;
import services.interfaces.StationServicesLocal;
import domain.Station;

@ManagedBean
@ViewScoped
public class StationBean {

	@EJB
	private BusinessLogicServicesLocal businessLogicServicesLocal;

	@EJB
	private StationServicesLocal stationServicesLocal;

	Station station = new Station();

	public String doSome() {
		return "";
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

}
