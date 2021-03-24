package backend.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.API.model.JwtBlacklist;


@Repository
public interface JwtBlacklistRepository  extends JpaRepository<JwtBlacklist,String> {

}
