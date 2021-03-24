package backend.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.API.model.Station;

@Repository
public interface StationRepository extends JpaRepository<Station,Long> {

}
