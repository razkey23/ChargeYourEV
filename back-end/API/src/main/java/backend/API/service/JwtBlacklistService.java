package backend.API.service;


public interface JwtBlacklistService {
	boolean isInJwtBlacklist(String jwt);
	void saveJwtBlacklist(String jwt);
}
