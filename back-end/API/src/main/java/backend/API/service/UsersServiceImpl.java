package backend.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Users;
import backend.API.repository.UsersRepository;


@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	
	public List<Users> getAllUsers() {
		return usersRepository.findAll();
	}
	
	public void deleteUser(Users user) {
		usersRepository.delete(user);
	}
	public Users getUserByUsername(String username) {
		List<Users> temp=usersRepository.findAll();
		
		for (int i=0;i<temp.size();i++) {
			if (temp.get(i).getUsername().equals(username)) {
				Users res=new Users();
				res=temp.get(i);	
				return res;
			}
		}
		return null;
	}

	@Override
	public void insertUser(Users user) {
		usersRepository.save(user);
		/*Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		usersRepository.save(user); */
		//usersRepository.
	}
}
