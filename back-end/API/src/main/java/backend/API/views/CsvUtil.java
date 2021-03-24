package backend.API.views;

import java.util.ArrayList;
import java.util.List;

import backend.API.views.SessionsPerEV.CsvViewSPEV;
import backend.API.views.SessionsPerEV.SessionsPerEV;
import backend.API.views.SessionsPerPoint.CsvViewSPPoint;
import backend.API.views.SessionsPerPoint.SessionsPerPoint;
import backend.API.views.SessionsPerStation.CsvViewSPS;
import backend.API.views.SessionsPerStation.SessionsPerStation;

public class CsvUtil {

	
	public List<CsvViewSPEV> getViewFromSPEV(SessionsPerEV spev){
		List<CsvViewSPEV> res = new ArrayList<CsvViewSPEV>();
		for (int i=0;i<spev.getVehicleChargingSessionList().size();i++) {
			CsvViewSPEV temp=new CsvViewSPEV();
			temp.setCostPerKwh(spev.getVehicleChargingSessionList().get(i).getCostPerKwh());
			temp.setEnergyDelivered(spev.getVehicleChargingSessionList().get(i).getEnergyDelivered());
			temp.setEnergyProvider(spev.getVehicleChargingSessionList().get(i).getEnergyProvider());
			temp.setFinishedOn(spev.getVehicleChargingSessionList().get(i).getFinishedOn());
			temp.setNumberOfVehicleChargingSessions(spev.getNumberOfVehicleChargingSessions());
			temp.setNumberofVisitedPoints(spev.getNumberofVisitedPoints());
			temp.setPeriodFrom(spev.getPeriodFrom());
			temp.setPeriodTo(spev.getPeriodTo());
			temp.setPricePolicyRef(spev.getVehicleChargingSessionList().get(i).getPricePolicyRef());
			temp.setRequestTimestamp(spev.getRequestTimestamp());
			temp.setSessionCost(spev.getVehicleChargingSessionList().get(i).getSessionCost());
			temp.setSessionID(spev.getVehicleChargingSessionList().get(i).getSessionID());
			temp.setSessionIndex(spev.getVehicleChargingSessionList().get(i).getSessionIndex());
			temp.setStartedOn(spev.getVehicleChargingSessionList().get(i).getStartedOn());
			temp.setTotalEnergyConsumed(spev.getTotalEnergyConsumed());
			temp.setVehicleID(spev.getVehicleID());
			res.add(temp);
		}
		return res;
	}
	
	public List<CsvViewSPPoint> getViewFromSPPoint(SessionsPerPoint spp){
		List<CsvViewSPPoint> res = new ArrayList<CsvViewSPPoint>();
		//List<ChargingSession> cs = spp.getChargingSessionsList();
		for (int i=0;i<spp.getChargingSessionsList().size();i++) {
			CsvViewSPPoint temp = new CsvViewSPPoint();
			temp.setPoint(spp.getPoint());
			temp.setFinishedOn(spp.getChargingSessionsList().get(i).getFinishedOn());
			temp.setNumberOfChargingSessions(spp.getNumberOfChargingSessions());
			temp.setPayment(spp.getChargingSessionsList().get(i).getPayment());
			temp.setPeriodFrom(spp.getPeriodFrom());
			temp.setPeriodTo(spp.getPeriodTo());
			temp.setPointOperator(spp.getPointOperator());
			temp.setProtocol(spp.getChargingSessionsList().get(i).getProtocol());
			temp.setRequestTimestamp(spp.getRequestTimestamp());
			temp.setSessionID(spp.getChargingSessionsList().get(i).getSessionID());
			temp.setSessionIndex(spp.getChargingSessionsList().get(i).getSessionIndex());
			temp.setStartedOn(spp.getChargingSessionsList().get(i).getStartedOn());
			temp.setVehicleType(spp.getChargingSessionsList().get(i).getVehicleType());
			temp.setEnergyDelivered(spp.getChargingSessionsList().get(i).getEnergyDelivered());
			res.add(temp);
		}
		return res;
	}
	
	public List<CsvViewSPS> geViewFromSPS(SessionsPerStation sps){
		List<CsvViewSPS> res=new ArrayList<CsvViewSPS>();
		for (int i=0;i<sps.getSessionsSummary().size();i++ ) {
			CsvViewSPS temp=new CsvViewSPS();
			temp.setEnergyDelivered(sps.getSessionsSummary().get(i).getEnergyDelivered());
			temp.setNumberOfActivePoints(sps.getNumberOfActivePoints());
			temp.setNumberOfChargingSessions(sps.getNumberOfChargingSessions());
			temp.setOperator(sps.getOperator());
			temp.setPeriodFrom(sps.getPeriodFrom());
			temp.setPeriodTo(sps.getPeriodTo());
			temp.setPointID(sps.getSessionsSummary().get(i).getPointID());
			temp.setPointSessions(sps.getSessionsSummary().get(i).getPointSessions());
			temp.setRequestTimestamp(sps.getRequestTimestamp());
			temp.setStationID(sps.getStationID());
			temp.setTotalEnergyDelivered(sps.getTotalEnergyDelivered());
			res.add(temp);
		}
		return res;
	}
}
