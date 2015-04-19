package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import domain.User;

@Local
public interface UserServicesLocal {

	Boolean addUser(User User);

	Boolean deleteUser(User User);

	Boolean deleteUserById(Integer id);

	Boolean updateUser(User User);

	User findUserById(Integer id);

	List<User> findAllUsers();
}
