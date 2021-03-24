package backend.API.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.API.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long>{

}
