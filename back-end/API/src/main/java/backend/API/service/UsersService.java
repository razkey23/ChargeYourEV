package backend.API.service;

import java.util.List;

import backend.API.model.Users;



public interface UsersService {
	
	List<Users> getAllUsers();
	//void inserUser(String fname,String lname);
	Users getUserByUsername(String username);
	void insertUser(Users user);
	void deleteUser(Users user);
}
