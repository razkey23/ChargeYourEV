package backend.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Provider;
import backend.API.repository.ProviderRepository;

@Service
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	ProviderRepository providerRepository;
	
	@Override
	public Provider getProviderByProviderID(Long ProviderID) {
		return providerRepository.findById(ProviderID).get();
	}
	

}
