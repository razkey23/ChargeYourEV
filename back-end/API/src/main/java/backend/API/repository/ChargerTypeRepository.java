package backend.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.API.model.ChargerType;

@Repository
public interface ChargerTypeRepository  extends JpaRepository<ChargerType,Long> {

}
