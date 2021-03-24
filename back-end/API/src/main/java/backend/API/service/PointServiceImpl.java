package backend.API.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.API.model.Point;
import backend.API.repository.PointRepository;


@Service
public class PointServiceImpl implements PointService{
	
	@Autowired
	private PointRepository pointRepository;
	
	@Override
	public List<Point> getAllPoints() {
		return pointRepository.findAll();
		// TODO Auto-generated method stub
	}
	
	public List<Point> getPointsbyStationID(Long StationID) {
		/*List<Point> points= pointRepository.findAll();
		List<Point> res=new ArrayList<Point>();
		for (int i=0; i<points.size();i++) {
			if(points.get(i).getPstation().getStationID()==StationID) {
				res.add(points.get(i));
			}
		}
		return res;*/
		return pointRepository.findPointsByStationID(StationID);
	}

	@Override
	public Point getPointById(Long PointId) {
		// TODO Auto-generated method stub
		return pointRepository.findById(PointId).get();
	}

}
