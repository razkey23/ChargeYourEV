package backend.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.API.model.Users;

@Repository 
public interface UsersRepository extends JpaRepository<Users,Long>{

}
