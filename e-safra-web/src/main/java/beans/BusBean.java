package beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import services.interfaces.BusServicesLocal;
import services.interfaces.BusinessLogicServicesLocal;
import domain.Bus;

@ManagedBean
@ViewScoped
public class BusBean {

	@EJB
	private BusinessLogicServicesLocal businessLogicServicesLocal;

	@EJB
	private BusServicesLocal busServicesLocal;

	Bus bus = new Bus();

	public String doSome() {
		return "";
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

}
