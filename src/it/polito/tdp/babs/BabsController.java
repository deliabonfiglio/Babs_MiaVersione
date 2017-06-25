package it.polito.tdp.babs;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.RisultatiSimulazione;
import it.polito.tdp.babs.model.Statistics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker pickData;

	@FXML
	private Slider sliderK;

	@FXML
	private TextArea txtResult;

	@FXML
	void doContaTrip(ActionEvent event) {
		//PER OGNI STAZIONE Il  numero  totale  di  trip  in partenza da tale stazione (pick)
		//e il numero totale di trip in arrivo in quella stazione (drop) nella data selezionata
		//Le stazioni devono essere elencate in ordine di latitudine (partendo da Nord ed andando verso Sud). 
		//Nel caso in cui nella data selezionata non siano presenti trip, indicarlo con un messaggio d’errore. 
		
		txtResult.clear();

		LocalDate ld = pickData.getValue();
		//GRAZIE AL FATTO CHE ABBIAMO IMPOSTATO UNA DATA DI DEFAULT AL PICKDATA
		//NEL METODO INITIALIZE , ld non sarà mai null , effettuiamo ugualmente il controllo
		if (ld == null) {
			txtResult.setText("Selezionare una data");
			return;
		}

		List<Statistics> stats = model.getStats(ld);
		//compare to per Statistics basato sulla latitudine 
		//potrei anche usare un comporatore per ordinare la lista 
		Collections.sort(stats);

		for (Statistics stat : stats) {
			if (stat.getPick() == 0 && stat.getDrop()==0) {
				txtResult.appendText(String.format("WARNING!! Stazione %s con 0 TRIP\n", stat.getStazione().getName()));
			} else {
				txtResult.appendText(String.format("%s %d %d\n", stat.getStazione().getName(), stat.getPick(), stat.getDrop()));
			}
		}

	}

	@FXML
	void doSimula(ActionEvent event) {
		txtResult.clear();
		LocalDate ld = pickData.getValue();
		
		if(ld==null){
			txtResult.appendText("");
			return;
		}

		double k = sliderK.getValue()/100;
		System.out.println(k);
		
		RisultatiSimulazione rs = model.Simula(k, ld);
		txtResult.appendText("Numero Pick mancati: "+rs.getNumPickMiss()+"\n");
		txtResult.appendText("Numero Drop mancati: "+rs.getNumDropMiss()+"\n");
		
		
	}

	@FXML
	void initialize() {
		assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
		assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

		pickData.setValue(LocalDate.of(2013, 9, 1));
	}
}
