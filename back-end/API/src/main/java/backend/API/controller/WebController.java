package backend.API.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.API.model.Point;
import backend.API.model.Station;
import backend.API.model.Users;
import backend.API.model.Vehicle;

import backend.API.service.PointService;
import backend.API.service.StationService;
import backend.API.service.UsersService;
import backend.API.service.VehicleService;

@RestController
@RequestMapping("/web/")
public class WebController {
	
	@Autowired
	 private UsersService usersService;
	 @Autowired
	 private PointService pointService;
	 
	 @Autowired
	 private VehicleService vehicleService;
	 @Autowired
	 private StationService stationService;
	 
	 @Autowired
	 AuthenticationProvider provider;
	 
	 @GetMapping(value= {"points/{pointId}"},produces=MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Object> getPointById(HttpServletResponse response,@Validated@PathVariable("pointId") Long pointId) {
         Point point = pointService.getPointById(pointId);
         return new ResponseEntity<>(point,HttpStatus.OK);
     }
	 @GetMapping(value= {"vehicles/{ownerId}"},produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> getVehiclesPerOwner(HttpServletResponse response,@Validated@PathVariable("ownerId") String ownerId) {
		 	List<Vehicle> res = new ArrayList<Vehicle>();
		 	res = vehicleService.getVehicleByOwnerId(Long.parseLong(ownerId));
			return new ResponseEntity<>(res,HttpStatus.OK);
	 }
	 
	 @GetMapping(value= {"stations/chargingPoints/{stationId}"},produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> getPointsPerStation(HttpServletResponse response,@Validated@PathVariable("stationId") String stationId) {
		 	List<Point> res = pointService.getPointsbyStationID(Long.parseLong(stationId));
		 	return new ResponseEntity<>(res,HttpStatus.OK);
	 }
	 
	 @GetMapping(value= {"users/{username}"},produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> getUserIdFromUsername(HttpServletResponse response,@Validated@PathVariable("username") String username) {
		 Users user = usersService.getUserByUsername(username);
		 return new ResponseEntity<>(user,HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="allstations" ,method=RequestMethod.GET,produces= "application/json")
	 public List<Station> getStations(Model model) throws Exception{
		 return stationService.getAllStations();
	 }
	 
	 
	/* @GetMapping(value= {"allstations"},produces=MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> getAllStations(HttpServlet response) {
		 System.out.println("Got here");
		 List<Station> res= new ArrayList<Station>();
		 res = stationService.getAllStations();
		 return new ResponseEntity<>(res,HttpStatus.OK);
	 } */
	 
	 
	 
	 
}
