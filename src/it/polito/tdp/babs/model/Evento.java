package it.polito.tdp.babs.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	public enum EventType {
		PICK, DROP;
	}
	
	private Station stazione;
	private LocalDateTime time;
	private EventType tipo;
	private Trip trip;
	
	public Evento(Station stazione, LocalDateTime time, EventType tipo, Trip t) {
		super();
		this.stazione = stazione;
		this.time = time;
		this.tipo=tipo;
		this.trip=t;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Station getStazione() {
		return stazione;
	}

	public void setStazione(Station stazione) {
		this.stazione = stazione;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stazione == null) ? 0 : stazione.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((trip == null) ? 0 : trip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (stazione == null) {
			if (other.stazione != null)
				return false;
		} else if (!stazione.equals(other.stazione))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (tipo != other.tipo)
			return false;
		if (trip == null) {
			if (other.trip != null)
				return false;
		} else if (!trip.equals(other.trip))
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento arg0) {
		// TODO Auto-generated method stub
		return this.time.compareTo(arg0.time);
	}


	

}
