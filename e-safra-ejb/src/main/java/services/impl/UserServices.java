package services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import services.interfaces.UserServicesLocal;
import services.interfaces.UserServicesRemote;
import domain.User;

/**
 * Session Bean implementation class UserServices
 */
@Stateless
public class UserServices implements UserServicesRemote, UserServicesLocal {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public UserServices() {

	}

	@Override
	public Boolean addUser(User user) {
		Boolean b = false;
		try {
			entityManager.persist(user);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public Boolean deleteUser(User user) {
		Boolean b = false;
		try {
			entityManager.remove(entityManager.merge(user));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public Boolean deleteUserById(Integer id) {
		Boolean b = false;
		try {
			User user = findUserById(id);
			entityManager.remove(user);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public Boolean updateUser(User user) {
		Boolean b = false;
		try {
			entityManager.merge(user);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}

	@Override
	public User findUserById(Integer id) {
		try {
			return entityManager.find(User.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		try {
			String jpql = "select u from User u";
			Query query = entityManager.createQuery(jpql);
			users = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

}
