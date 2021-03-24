package backend.API.service;

import java.util.List;

import backend.API.model.Point;


public interface PointService {
	List<Point> getAllPoints();
	List<Point> getPointsbyStationID(Long StationID);
	Point getPointById(Long PointId);
}
