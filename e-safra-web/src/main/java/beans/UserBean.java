package beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import services.interfaces.BusinessLogicServicesLocal;
import domain.BusManager;
import domain.Driver;
import domain.Passenger;
import domain.User;

@ManagedBean
@SessionScoped
public class UserBean {

	@EJB
	private BusinessLogicServicesLocal businessLogicServicesLocal;

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String doLogin() {
		String navigateTo = "";
		User userFound = businessLogicServicesLocal.identification(
				user.getLogin(), user.getPassword());
		if (userFound != null) {
			if (userFound instanceof Passenger) {
				navigateTo = "/pages/passenger/home";
			} else if (userFound instanceof BusManager) {
				navigateTo = "/pages/busmanager/home";
			} else if (userFound instanceof Driver) {
				navigateTo = "/pages/driver/home";
			}
		} else {
			navigateTo = "/error";
		}
		return navigateTo;
	}
}