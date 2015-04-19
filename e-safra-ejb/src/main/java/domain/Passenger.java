package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Entity implementation class for Entity: Passenger
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Passenger extends User implements Serializable {

	private Double cash;
	private static final long serialVersionUID = 1L;

	public Passenger() {
		super();
	}

	public Passenger(String firstName, String lastName, Character gender,
			Date birthDay, String login, String password, Double cash) {
		super(firstName, lastName, gender, birthDay, login, password);
		this.cash = cash;
	}

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		return "Passenger [" + super.toString() + ", cash=" + cash + "]";
	}

}
