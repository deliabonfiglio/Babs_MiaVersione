package it.polito.tdp.babs.model;

public class RisultatiSimulazione {

	private int numPickMiss;
	private int numDropMiss;
	
	public RisultatiSimulazione() {
		super();
		this.numPickMiss = 0;
		this.numDropMiss = 0;
	}
	public int getNumPickMiss() {
		return numPickMiss;
	}
	public void addNumPickMiss() {
		this.numPickMiss = numPickMiss+1;
	}
	public int getNumDropMiss() {
		return numDropMiss;
	}
	public void addNumDropMiss() {
		this.numDropMiss+=1;
	}
	
	
}
