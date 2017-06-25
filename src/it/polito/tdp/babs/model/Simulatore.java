package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.*;

import it.polito.tdp.babs.model.Evento.EventType;

public class Simulatore {
		//solo ti serva il model durante la simulazione
		Model model;
		
		
		// Parametri di simulazione(parametri che puoi cambiare 
		//prima di avviare la simulazione ma una volta avviata
		//restano costanti)
		private double K;
		
		// Stato del modello(variabili che durante la simulazione 
		//variano il loro valore)
		private Map<Station, Integer> stazioni;
		private Map<Integer, Station> map;
		
		// Variabili di interesse(che sono il risultato della simulazione)
		private RisultatiSimulazione rs;
		
		// Lista degli eventi
		private PriorityQueue<Evento> queue;
		
		
		public Simulatore(Model model, double k, LocalDate ld){
			this.model=model;
			K=k;
			this.stazioni= new HashMap<>();
			this.queue= new PriorityQueue<>();
			
			this.caricaStazioni();
			map= model.getMappaStazioni();
			rs= new RisultatiSimulazione();
		}


		private void caricaStazioni() {
			for(Station s: model.getStazioni()){
				stazioni.put(s, (int) (K*s.getDockCount()));
			}
		}
		
		public void caricaEventi(List<Trip> listp, List<Trip> listd) {
			for(Trip t:  listp){
				System.out.println("cao");
				queue.add(new Evento(map.get(t.getStartStationID()), t.getStartDate(), EventType.PICK, t));
			}
			for(Trip t:  listd){
				queue.add(new Evento(map.get(t.getEndStationID()), t.getEndDate(), EventType.DROP, t));
			}
		}


		public void run() {
			
			while(!queue.isEmpty()){
				Evento e = queue.poll();
				System.out.println(e.toString());
				
				switch (e.getTipo()) {
				case PICK:
					processPICKEvent(e);
					break;
				case DROP:
					processDropEvent(e);
					break;
				}
			}
		}


		private void processDropEvent(Evento e) {
			int occupazione = stazioni.get(e.getStazione());
			
			if(occupazione< e.getStazione().getDockCount()){
				//lascio bici
				stazioni.put(e.getStazione(), occupazione+1);
			} else {
				rs.addNumDropMiss();
			}
			
		}


		private void processPICKEvent(Evento e) {
			int occupazione = stazioni.get(e.getStazione());
			
			if(occupazione>0){
				//prendo bici
				stazioni.put(e.getStazione(), occupazione-1);
			} else {
				//no bici
				rs.addNumPickMiss();
				queue.remove(new Evento(map.get(e.getTrip().getEndStationID()), e.getTrip().getEndDate(), EventType.DROP, e.getTrip()));
			}	
		}


		public RisultatiSimulazione getResults() {
		
			return rs;
		}

}
