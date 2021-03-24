package backend.API.controller;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.http.HttpHeaders;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.API.security.models.AuthenticationRequest;
import backend.API.security.models.AuthenticationResponse;
import backend.API.security.services.MyUserDetailsService;
import backend.API.security.util.JwtUtil;

import backend.API.model.Point;
import backend.API.model.Provider;
import backend.API.model.Session;
import backend.API.model.Station;
import backend.API.model.Users;
import backend.API.model.Vehicle;
import backend.API.service.JwtBlacklistService;
import backend.API.service.PointService;
import backend.API.service.ProviderService;
import backend.API.service.SessionService;
import backend.API.service.StationService;
import backend.API.service.UsersService;
import backend.API.service.VehicleService;
import backend.API.views.CsvUtil;
import backend.API.views.SessionsPerEV.CsvViewSPEV;
import backend.API.views.SessionsPerEV.SessionsPerEV;
import backend.API.views.SessionsPerEV.VehicleChargingSession;
import backend.API.views.SessionsPerPoint.SessionsPerPoint;
import backend.API.views.SessionsPerProvider.ProviderSessionSummary;
import backend.API.views.SessionsPerProvider.SessionsPerProvider;
import backend.API.views.SessionsPerStation.CsvViewSPS;
import backend.API.views.SessionsPerStation.SessionSummary;
import backend.API.views.SessionsPerStation.SessionsPerStation;
import backend.API.views.SessionsPerPoint.ChargingSession;
import backend.API.views.SessionsPerPoint.CsvViewSPPoint;

@RestController
@RequestMapping("/evcharge/api")
public class APIController {
 /*Useful functions*/
public static java.sql.Date convert(java.util.Date date)
{
        return new java.sql.Date(date.getTime());
}
public String addChar(String str, char ch, int position) {
	    StringBuilder sb = new StringBuilder(str);
	    sb.insert(position, ch);
	    return sb.toString();
}	
 @Autowired
 private UsersService usersService;
 @Autowired
 private PointService pointService;
 
 @Autowired
 private VehicleService vehicleService;
 
 @Autowired
 AuthenticationProvider provider;
 
 @Autowired
 private StationService stationService;
 @Autowired
 private SessionService sessionService;
 
 @Autowired
 private ProviderService providerService;
 
 @Autowired 
private AuthenticationManager authenticationManager;
	
@Autowired
private PasswordEncoder passwordEncoder;
	
@Autowired
private MyUserDetailsService userDetailsService;
	
@Autowired
private JwtUtil jwtTokenUtil; 

@Autowired
private JwtBlacklistService jwtBlacklistService;
 


/*Initial Authentication*/
@RequestMapping(value="/login",method=RequestMethod.POST,consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<?> createAuthenticationToken(@ModelAttribute AuthenticationRequest authenticationRequest) throws Exception{
		try {
			//ovider.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),passwordEncoder.encode(authenticationRequest.getPassword())));
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password",e);
		}

		final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt=jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

@RequestMapping(value="/logout",method=RequestMethod.POST)
public ResponseEntity<Object> logout(@RequestHeader("X-OBSERVATORY-AUTH") String jwtToken) throws Exception{
	jwtBlacklistService.saveJwtBlacklist(jwtToken);
	return new ResponseEntity<>(HttpStatus.OK);
}

/* EVERYTHING ADMIN RELATED */ 
 @RequestMapping(value="/admin/usermod/{username}/{password}" ,method=RequestMethod.POST,produces= "application/json")
public ResponseEntity<Object> createUser(@Validated@PathVariable("username") String username,@Validated@PathVariable("password") String password,
		@RequestParam(required=false) String isAdmin,
		@RequestParam(required=false) String isProvider,
		@RequestParam(required=false) String name) throws Exception {
	 Users temp = usersService.getUserByUsername(username);
	 
	 System.out.println(username);
	 System.out.println(password);
	 Users NewUser = new Users();
	 if (temp != null) {
		 usersService.deleteUser(temp);
		 if (isAdmin != null)  {
			 if (isAdmin.equals("true")) NewUser.setAdmin(true);
			 else  NewUser.setAdmin(false);
		 }
		 else NewUser.setAdmin(temp.isAdmin());
		 if (isProvider != null)  {
			 if (isProvider.equals("true")) NewUser.setProvider(true);
			 else  NewUser.setProvider(false);
		 }
		 else NewUser.setProvider(temp.isProvider());
		
		 if(name!=null) {
			 NewUser.setName(name);
		 }
		 NewUser.setUsername(username);
		 NewUser.setUserid(temp.getUserid());
		 NewUser.setPassword(passwordEncoder.encode(password));
		 usersService.deleteUser(temp);
	 }
	 else {
		 if (isAdmin != null)  {
			 if (isAdmin.equals("true")) NewUser.setAdmin(true);
			 else  NewUser.setAdmin(false);
		 }
		 else NewUser.setAdmin(false);
		 if (isProvider != null)  {
			 if (isProvider.equals("true")) NewUser.setProvider(true);
			 else  NewUser.setProvider(false);
		 }
		 else NewUser.setProvider(false);
		
		 if(name!=null) {
			 NewUser.setName(name);
		 }
		 else NewUser.setName("JohnDoe");
		 NewUser.setUsername(username);
		 NewUser.setPassword(passwordEncoder.encode(password));
	 }
	 usersService.insertUser(NewUser);
	 return new ResponseEntity<>(HttpStatus.OK);
}
 
 
 @RequestMapping(value="/admin/users/{username}" ,method=RequestMethod.GET)
 public Users findUserbyUsername(@Validated@PathVariable("username") String username) throws Exception {
	 return usersService.getUserByUsername(username);
 }
 
 
 @RequestMapping(value="/admin/system/sessionupd" ,method=RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
 public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws FileNotFoundException, IOException, ParseException {
	 File convertFile=new File(file.getOriginalFilename());
	 int totalCounter=0;
	 	try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	 		//Object strCurrentLine;
	 		//reader.readLine();
	 		//System.out.println()
	 		String thisLine=null;
	 		thisLine = reader.readLine();
	 		System.out.println(thisLine);
	 		int counter=0;
	 		totalCounter=0;
	 		List <Session> curr =new ArrayList<Session>();
	 		while((thisLine = reader.readLine()) != null) {
	 			totalCounter+=1;
	 			counter+=1;
	 			Session temp = new Session();
	 			List<String> elephantList =  Arrays.asList(thisLine.split(","));
	 			for (int i=0;i<elephantList.size();i++) {	 				
	 				SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 				if (i==0) temp.setSessionID(Long.parseLong(elephantList.get(i)));
	 
	 				else if (i==1) {
	 					String val = elephantList.get(i).substring(1,elephantList.get(i).length()-1);
	 					java.util.Date parsedDate= formatter1.parse(val);
	 					Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	 					temp.setConnectionTime(timestamp);
	 				}
	 				else if (i==2) {
	 					temp.setCost_per_kwh(Float.parseFloat(elephantList.get(i)));
	 				}
	 				else if (i==3) {
	 					String val = elephantList.get(i).substring(1,elephantList.get(i).length()-1);
	 					java.util.Date parsedDate= formatter1.parse(val);
	 					Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	 					temp.setDisconnectTime(timestamp);
	 				}
	 				else if (i==4) {
	 					String val = elephantList.get(i).substring(1,elephantList.get(i).length()-1);
	 					java.util.Date parsedDate= formatter1.parse(val);
	 					Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
	 					temp.setDoneChargingTime(timestamp);
	 				}
	 				else if (i==5) {
	 					temp.setEnergyDelivered(Float.parseFloat(elephantList.get(i)));
	 				}
	 				else if (i==6) {
	 					temp.setPricePolicyRef(elephantList.get(i));
	 				}
	 				else if (i==7) {
	 					Point tempPoint=pointService.getPointById(Long.parseLong(elephantList.get(i)));
	 					temp.setSPoint(tempPoint);
	 				}
	 				else if (i==8) {
	 					Vehicle tempVehicle= vehicleService.getVehicleById(Long.parseLong(elephantList.get(i)));
	 					temp.setSVehicle(tempVehicle);
	 				}
	 				else if (i==9) {
	 					temp.setPayment(elephantList.get(i));
	 				}
	 				else if (i==10) {
	 					temp.setProtocol(elephantList.get(i));
	 				}				
	 			}
	 			curr.add(temp);
	 			if (counter==100) {
		 			System.out.println(curr);
		 			counter=0;
		 			sessionService.saveAllSessions(curr);
		 			curr =new ArrayList<Session>();
		 		}
	 		}
	 	}
	 Long num = sessionService.getNumberOfAllSessions();
	 String jsonobj = new JSONObject().put("SessionsInUploadedFile",totalCounter).put("SessionsImported",totalCounter).put("TotalSessionsInDatabase",num).toString();
	
	 return new ResponseEntity<>(jsonobj,HttpStatus.OK);
 } 
 
 @RequestMapping(value="/admin/healthcheck" ,method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<Object> healthcheck() {
	 String jsonString;
	 Users u = usersService.getUserByUsername("razkey");
	 if (u == null) {
		 jsonString = new JSONObject().put("status","failed").toString();
		 
	 }
	 else {
		 jsonString = new JSONObject().put("status","OK").toString();
	 }
	 return new ResponseEntity<>(jsonString,HttpStatus.OK);
 }
 
 @RequestMapping(value="/admin/resetsessions" ,method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<Object> resetSessions() {
	 String jsonString;
	 String password="petrol4ever";
	 sessionService.deleteSession();
	 List<Session> check = new ArrayList<Session>();
	 check=sessionService.getAllSessions();
	 Users temp = usersService.getUserByUsername("admin");
	 if (temp != null) usersService.deleteUser(temp);
	Users newAdmin = new Users();
	newAdmin.setAdmin(true);
	newAdmin.setUsername("admin");
	newAdmin.setProvider(true);
	newAdmin.setPassword(passwordEncoder.encode(password));
	usersService.insertUser(newAdmin);
	 
	 if (check.isEmpty() && usersService.getUserByUsername("admin") != null)  jsonString = new JSONObject().put("status","OK").toString();
	 else jsonString = new JSONObject().put("status","failed").toString();
	 return new ResponseEntity<>(jsonString,HttpStatus.OK);
 }
 
 
 
 /*ADMIN RELATED ENDS HERE */
 
 
 /*First Endpoint SessionsPerPoint */
 @GetMapping(value= {"SessionsPerPoint/{pointID}/{YYYYMMDD_from}/{YYYYMMDD_to}"},produces=MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<Object> getSessionsPerPoint(HttpServletResponse response,
		 @Validated@PathVariable("pointID") Long pointID,
		 @Validated@PathVariable("YYYYMMDD_from") String From,
		 @Validated@PathVariable("YYYYMMDD_to") String To,
		 				@RequestParam(required=false) String format) 
		 				throws Exception, CsvRequiredFieldEmptyException, ParseException{
 	List<Session> curr = sessionService.getSessionByPointID(pointID);
 	SessionsPerPoint spp = new SessionsPerPoint();
 	List<ChargingSession> empty = new ArrayList<ChargingSession>();
 	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
 
 	
 	SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	java.util.Date RequestTimestamp = new java.util.Date(System.currentTimeMillis());
	spp.setRequestTimestamp(formatter1.format(RequestTimestamp).toString());
 	
 	
	 java.util.Date date1= formatter.parse(From);
	 java.sql.Date dateFrom=convert(date1);
	 java.util.Date date= formatter.parse(To);
	 java.sql.Date dateTo=convert(date);
	 
	 spp.setPeriodFrom(From);
	 spp.setPeriodTo(To);
	 spp.setPoint(pointID.toString()); 
	 int counter=0;
	 for(int i=0;i<curr.size();i++) {
		 ChargingSession cs=new ChargingSession();
		 Session temp =curr.get(i);
		 Timestamp tstamp = temp.getConnectionTime();
		 java.sql.Date connectionDate = Date.valueOf(tstamp.toLocalDateTime().toLocalDate());
		 int random = dateFrom.compareTo(connectionDate);
		 int random2 =  dateTo.compareTo(connectionDate);
		 if (random2 !=-1 && random!=1) {
			 counter+=1;
			 cs.setEnergyDelivered(temp.getEnergyDelivered());
			 cs.setFinishedOn(temp.getDisconnectTime().toString());
			 cs.setStartedOn(temp.getConnectionTime().toString());
			 cs.setSessionID(temp.getSessionID().toString());
			 cs.setSessionIndex(counter);
			 cs.setPayment(temp.getPayment());
			 cs.setProtocol(temp.getProtocol());
			 cs.setVehicleType(temp.getSVehicle().getVehicleType());
			 empty.add(cs);
			 spp.setPointOperator(temp.getSPoint().getPointOperator());
		 }
	 }
	 if(empty.isEmpty())return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 spp.setChargingSessionsList(empty);
	 spp.setNumberOfChargingSessions(counter);
	 
	 
	 /*Only for csv output*/
	 if (format!=null) {
		 if (format.equals("csv")) { 
			 List<CsvViewSPPoint> csvOutput=new ArrayList<CsvViewSPPoint>();
			 CsvUtil csvUtil=new CsvUtil();
			 csvOutput = csvUtil.getViewFromSPPoint(spp);
			 String filename="SessionsPerPoint";
			 response.setContentType("text/csv");
			 response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
			 StatefulBeanToCsv<CsvViewSPPoint> writer;
			 try {
					writer = new StatefulBeanToCsvBuilder<CsvViewSPPoint>(response.getWriter())
							 						.withQuotechar(CSVWriter.NO_ESCAPE_CHARACTER)
							 						.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
							 						.withOrderedResults(false)
							 						.build();
					writer.write(csvOutput);
					return new ResponseEntity<>(HttpStatus.OK);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		 }
	 } 
	 /*end of csv output*/
	 
	 return new ResponseEntity<>(spp,HttpStatus.OK);
 }
 

 
/* Second API endpoint ,SessionsPerStation*/
@ResponseStatus(HttpStatus.BAD_REQUEST)
@GetMapping(value= {"SessionsPerStation/{stationID}/{YYYYMMDD_from}/{YYYYMMDD_to}"},produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<Object> getSessionsPerStation(HttpServletResponse response, 
		 @Validated@PathVariable("stationID") Long stationID,
		  @Validated@PathVariable("YYYYMMDD_from") String From,
		 @Validated@PathVariable("YYYYMMDD_to") String To,
		 @RequestParam(required=false) String format) 
		 throws Exception, CsvRequiredFieldEmptyException, ParseException{
	SessionsPerStation res = new SessionsPerStation();
	List<Point> points =pointService.getPointsbyStationID(stationID);
	List<SessionSummary> sessionsList=new ArrayList<SessionSummary>();
	
	SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	java.util.Date RequestTimestamp = new java.util.Date(System.currentTimeMillis());
	res.setRequestTimestamp(formatter1.format(RequestTimestamp).toString());  //Current Time
	
	String From_new=From.substring(0, 8);
	String To_new=To.substring(0, 8);
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	java.util.Date date1= new java.util.Date();
	java.util.Date date=new java.util.Date();
	
	date1= formatter.parse(From_new);
	
	
	java.sql.Date dateFrom=convert(date1);
	SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	date= formatter2.parse(To_new);
	java.sql.Date dateTo=convert(date);              //CONVERT INPUT DATES TO DATES FOR COMPARISON
	 
	res.setPeriodFrom(dateFrom.toString());
	res.setPeriodTo(dateTo.toString());
	res.setStationID(stationID.toString());
	res.setOperator(stationService.getStationsbyId(stationID).getStationOperator());
	int counter=0;
	//res.setNumberOfActivePoints(o);
	float totalEnergy=0;
	int numberofchargingsessions=0;
	for (int i=0;i<points.size();i++) {
		int pointSessions=0;
		float pointEnergy=0;
		SessionSummary temp1= new SessionSummary();
		List <Session> curr=sessionService.getSessionByPointID(points.get(i).getPointID());
		for (int j=0;j<curr.size();j++) {
			Session temp=curr.get(j);
			Timestamp tstamp=temp.getConnectionTime();
			java.sql.Date connectionDate = Date.valueOf(tstamp.toLocalDateTime().toLocalDate());
			int random = dateFrom.compareTo(connectionDate);
			 int random2 =  dateTo.compareTo(connectionDate);
			 if (random2 !=-1 && random!=1) {
				 pointSessions+=1;
				 pointEnergy+=curr.get(j).getEnergyDelivered();
			 }		 
		}
		if (pointSessions != 0 ) {
			counter+=1; //ActivePointsCounter
			numberofchargingsessions+=pointSessions; 
			totalEnergy+=pointEnergy;
			temp1.setPointID(points.get(i).getPointID().toString());
			temp1.setPointSessions(pointSessions);
			temp1.setEnergyDelivered(pointEnergy);
			sessionsList.add(temp1);
		}
	}
	if(sessionsList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	res.setNumberOfChargingSessions(numberofchargingsessions);
	res.setNumberOfActivePoints(counter);
	res.setTotalEnergyDelivered(totalEnergy);
	res.setSessionsSummary(sessionsList);
	
	
	
	 if (format!=null) {
		 if (format.equals("csv")) {
			 List<CsvViewSPS> csvOutput=new ArrayList<CsvViewSPS>();
			 CsvUtil csvUtil=new CsvUtil();
			 csvOutput = csvUtil.geViewFromSPS(res);
			// csvOutput=res.getCSVView();
			 String filename="SessionsPerStation";
	
			 response.setContentType("text/csv");
			 response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
			 StatefulBeanToCsv<CsvViewSPS> writer;
			 try {
					writer = new StatefulBeanToCsvBuilder<CsvViewSPS>(response.getWriter())
							 						.withQuotechar(CSVWriter.NO_ESCAPE_CHARACTER)
							 						.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
							 						.withOrderedResults(false)
							 						.build();
					writer.write(csvOutput);
					return new ResponseEntity<>(HttpStatus.OK);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		 }
	 }
	
	
	
	return new ResponseEntity<>(res,HttpStatus.OK);
 }

 /*Third API endpoint, SessionsPerEV */
//ResponseEntity<Object>
 
 @GetMapping(value= {"SessionsPerEV/{vehicleID}/{YYYYMMDD_from}/{YYYYMMDD_to}"},produces =MediaType.APPLICATION_JSON_VALUE)
 @ResponseBody
 @ResponseStatus(HttpStatus.OK)
 public ResponseEntity<Object> getSessionsPerEV(HttpServletResponse response,
		 @Validated@PathVariable("vehicleID") Long vehicleID,
		 @Validated@PathVariable("YYYYMMDD_from") String From,
		 @Validated@PathVariable("YYYYMMDD_to") String To,
		 @RequestParam(required=false) String format) 
		throws  Exception, CsvRequiredFieldEmptyException, ParseException{
	 SessionsPerEV res=new SessionsPerEV();
	 //Function that finds all sessions for a Vehicle
	 List <VehicleChargingSession> empty= new ArrayList<VehicleChargingSession>();
	 List<Session> curr= sessionService.getSessionsByVehicleID(vehicleID);
	 Set<Long> hash_Set=new HashSet<Long>();
	 SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 java.util.Date RequestTimestamp = new java.util.Date(System.currentTimeMillis());	 	
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	 java.util.Date date1= formatter.parse(From);
	 java.sql.Date dateFrom=convert(date1);
	 java.util.Date date= formatter.parse(To);
	 java.sql.Date dateTo=convert(date);
	 
	 res.setPeriodFrom(dateFrom.toString());
	 res.setPeriodTo(dateTo.toString());
	 res.setVehicleID(vehicleID.toString());
	 res.setRequestTimestamp(formatter1.format(RequestTimestamp).toString());  //Current Time
	 
	 float totalEnergy=0;
	 int numberofchargingsessions=0;
	 int flag=0;
	 for (int i=0;i<curr.size();i++) {
		 VehicleChargingSession vcs = new VehicleChargingSession();
		 Session temp =curr.get(i);
		 Timestamp tstamp = temp.getConnectionTime();
		 java.sql.Date connectionDate = Date.valueOf(tstamp.toLocalDateTime().toLocalDate());
		 int random = dateFrom.compareTo(connectionDate);
		 int random2 =  dateTo.compareTo(connectionDate);
		 if (random2 !=-1 && random!=1) {
			 flag=1;
			 numberofchargingsessions+=1;
			 float sessionCost = temp.getEnergyDelivered()*temp.getSPoint().getCostPerKwh(); 
			 vcs.setSessionCost(sessionCost);
			 vcs.setEnergyDelivered(temp.getEnergyDelivered());
			 vcs.setCostPerKwh(temp.getSPoint().getCostPerKwh());
			 vcs.setSessionIndex(numberofchargingsessions);
			 vcs.setStartedOn(temp.getConnectionTime().toString());
			 vcs.setFinishedOn(temp.getDisconnectTime().toString());
			 vcs.setEnergyProvider(temp.getSPoint().getPstation().getEnergyProvider().getProviderName());
			 vcs.setPricePolicyRef(temp.getPricePolicyRef());
			 vcs.setSessionID(temp.getSessionID().toString());
			 hash_Set.add(temp.getSPoint().getPointID());
			 totalEnergy+=temp.getEnergyDelivered(); 
			 empty.add(vcs);
		 }
		 else {
			 if (flag==1) break;
		 }
	 }
	 if(empty.isEmpty())return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 res.setTotalEnergyConsumed(totalEnergy);
	 res.setNumberofVisitedPoints(hash_Set.size());
	 res.setVehicleChargingSessionList(empty);
	 res.setNumberOfVehicleChargingSessions(numberofchargingsessions);
	 
	 
	 /* Only For CSV */
	 if (format != null) {
		 if (format.equals("csv")) {

			 List<CsvViewSPEV> csvOutput = new ArrayList<CsvViewSPEV>();
			 CsvUtil csvUtil=new CsvUtil();
			 csvOutput=csvUtil.getViewFromSPEV(res);
			 //csvOutput=res.getViewFromSPEV();
			 String filename="SessionsPerEV";
			 response.setContentType("text/csv");
			 response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
			 StatefulBeanToCsv<CsvViewSPEV> writer;
			 try {
				 writer = new StatefulBeanToCsvBuilder<CsvViewSPEV>(response.getWriter())
					 						.withQuotechar(CSVWriter.NO_ESCAPE_CHARACTER)
					 						.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					 						.withOrderedResults(false)
					 						.build();
				 writer.write(csvOutput);
				 return new ResponseEntity<>(HttpStatus.OK);
			
			 } catch (IOException e) {
			// TODO Auto-generated catch block
				 e.printStackTrace();
			 } 
		 }
	 }
   /*CSV OUTPUT ENDS HERE*/
  return new ResponseEntity<>(res,HttpStatus.OK);

 }


 /*4th API Endpoint SessionsPerProvider */
 //@GetMapping(value= {"SessionsPerProvider/{providerID}/{YYYYMMDD_from}/{YYYYMMDD_to}"},produces =MediaType.APPLICATION_JSON_VALUE)
 @GetMapping(value= {"SessionsPerProvider/{providerID}/{YYYYMMDD_from}/{YYYYMMDD_to}"})
 @ResponseBody
 @ResponseStatus(HttpStatus.OK)
 public ResponseEntity<Object> getSessionsPerProvider(HttpServletResponse response, 
		 @Validated@PathVariable("providerID") Long providerID,
		 @Validated@PathVariable("YYYYMMDD_from") String From,
		 @Validated@PathVariable("YYYYMMDD_to") String To,
		 @RequestParam(required=false) String format) 
		 throws Exception, CsvRequiredFieldEmptyException, ParseException{
	 SessionsPerProvider res = new SessionsPerProvider();
	 List<ProviderSessionSummary> empty=new ArrayList<ProviderSessionSummary>();
	 List<Session> curr=sessionService.getSessionsByProviderID(providerID);
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	 java.util.Date date1= formatter.parse(From);
	 java.sql.Date dateFrom=convert(date1);
	 java.util.Date date= formatter.parse(To);
	 java.sql.Date dateTo=convert(date);
	 
	 for (int i=0;i<curr.size();i++) {
		 ProviderSessionSummary pss = new ProviderSessionSummary();
		 Session temp = curr.get(i);
		 Timestamp tstamp = temp.getConnectionTime();
		 java.sql.Date connectionDate = Date.valueOf(tstamp.toLocalDateTime().toLocalDate());
		 int random = dateFrom.compareTo(connectionDate);
		 int random2 =  dateTo.compareTo(connectionDate);
		 if (random2 !=-1 && random!=1) {
			 pss.setCostPerKwh(temp.getSPoint().getCostPerKwh());
			 pss.setEnergyDelivered(temp.getEnergyDelivered());
			 pss.setFinishedOn(temp.getDisconnectTime().toString());
			 pss.setPricePolicyRef(temp.getPricePolicyRef());
			 pss.setProviderID(providerID.toString());
			 pss.setProviderName(providerService.getProviderByProviderID(providerID).getProviderName());
			 pss.setSessionID(temp.getSessionID().intValue());
			 pss.setStartedOn(temp.getConnectionTime().toString());
			 pss.setStationID(temp.getSPoint().getPstation().getStationID().toString());
			 pss.setTotalCost(temp.getSPoint().getCostPerKwh()*temp.getEnergyDelivered());
			 pss.setVehicleID(temp.getSVehicle().getVehicleID().toString());
			 empty.add(pss);
		 } 
	 }
	 if(empty.isEmpty())return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 res.setProviderSessionSummary(empty); 
	 
	 /*Check for CSV output*/
	 if (format!=null) {
		 if (format.equals("csv")) {
			 String filename="SessionsPerProvider";
			 response.setContentType("text/csv");
			 response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"");
			 StatefulBeanToCsv<ProviderSessionSummary> writer;
			 try {
					writer = new StatefulBeanToCsvBuilder<ProviderSessionSummary>(response.getWriter())
							 						.withQuotechar(CSVWriter.NO_ESCAPE_CHARACTER)
							 						.withSeparator(CSVWriter.DEFAULT_SEPARATOR)
							 						.withOrderedResults(false)
							 						.build();
					writer.write(empty);
					return new ResponseEntity<>(HttpStatus.OK);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		 }
	 }
	 /*In case CSV didn't return*/
	 return new ResponseEntity<>(empty,HttpStatus.OK);
 }
 

 
 //ONLY FOR TESTING PURPOSES USELESS ENDPOINTS
 

 
 @GetMapping(value= {"points"},produces=MediaType.APPLICATION_JSON_VALUE)
 public List<Point> getAllPoints(HttpServletResponse response){
	 return pointService.getAllPoints();
 }
 @GetMapping(value= {"users"},produces=MediaType.APPLICATION_JSON_VALUE)
 public List<Users> getAllDepartmentLocations(Model model) {
		//model.addAttribute("listLocations",deptLocationService.getAllDeptLocations());
		List<Users> users=usersService.getAllUsers();
		return users;
 }
 
}
