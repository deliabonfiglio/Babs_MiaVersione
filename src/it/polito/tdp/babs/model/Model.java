package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {

	BabsDAO babsDAO;
	List<Station> stazioni;
	HashMap<Integer,Station> stazioniMap;
	
	public Model(){
		babsDAO = new BabsDAO();
	}
	
	public Map<Integer, Station> getMappaStazioni(){
		return stazioniMap;
	}
	
	public List<Station> getStazioni() {
		if (stazioni == null){
			stazioni = babsDAO.getAllStations();
			stazioniMap =new HashMap<Integer,Station>();
				for(Station s:stazioni){
					stazioniMap.put(s.getStationID(), s);
				}
			}
		return stazioni;
	}
	
	public Station getStationPerId(int id){
		return stazioniMap.get(id);
	}
	
	public List<Statistics> getStats(LocalDate ld) {
	//restituisce una lista di Statisc (stazione, pick=bici prese,drop=bici lasciate per una precisa data)
		List<Statistics> stats = new ArrayList<Statistics>();
		
		for (Station stazione : getStazioni()){
			int picks = babsDAO.getPickNumber(stazione, ld);
			int drops = babsDAO.getDropNumber(stazione, ld);
			Statistics stat = new Statistics(stazione, picks, drops);
			stats.add(stat);
		}
		
		return stats;
	}
	public RisultatiSimulazione Simula(double k,LocalDate ld) {
		Simulatore simulatore =new Simulatore(this,k,ld);
		//inizializzo la coda degli eventi
		//gli eventi sono creati a secondo delle rilevaioni delle fiume che per ogni fiume sono caricate in modo pigro.
		//if(river.getRilevamenti()==null){
		//	river.setRilevamenti(dao.getRilevamenti(river));
		//}
		simulatore.caricaEventi(babsDAO.getTripsForLocalDayPick(ld),babsDAO.getTripsForLocalDayDrop(ld));
		//effettuo la simulazionebabsDAO.getTripsForLocalDayDrop(ld)
		simulatore.run();
		//restitusico il risultato
		return simulatore.getResults();
	}
}
