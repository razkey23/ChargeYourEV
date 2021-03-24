package backend.API.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import backend.API.model.JwtBlacklist;
import backend.API.repository.JwtBlacklistRepository;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {
	@Autowired
	private JwtBlacklistRepository jwtRepo;
	@Override
	public boolean isInJwtBlacklist(String jwt) {
		// TODO Auto-generated method stub
		boolean flag=false;
		List<JwtBlacklist> curr = jwtRepo.findAll();
		
		for (int i=0;i<curr.size();i++) {
			if (curr.get(i).getJwt().equals(jwt)) flag=true;
		}
		if (curr.size()==10) {
			jwtRepo.deleteAll(curr);
		}
		return flag;
	}
	@Override
	public void saveJwtBlacklist(String jwt) {
		JwtBlacklist temp= new JwtBlacklist();
		temp.setJwt(jwt);
		jwtRepo.save(temp);
	}

}
