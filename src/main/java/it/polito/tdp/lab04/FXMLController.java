/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCercaIscritti;
    
    @FXML
    private Button btnCercaStudIscritto;

    @FXML
    private Button btnIscriviti;

    @FXML
    private Button btnCerca;
    
    @FXML
    private Button btnOK;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cmbCorsi;

    @FXML
    private TextField txtMatr;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtCognome;
    
    @FXML
    void handleCercaIscritti(ActionEvent event) {
    	txtRisultato.setText("");
    	String corsoScelto = cmbCorsi.getValue();
    	
    	if(corsoScelto == null) {
    		txtRisultato.setText("Selezionare un corso");
    		return;
    	}
    	
    	if(corsoScelto.compareTo(" ") == 0) {
    		txtRisultato.setText("Non ci sono iscritti presenti al corso perchè non è stato selezionato nessun corso");
    		return;
    	}
    	
    	List<Corso> listaCorsi = this.model.getCorsi();
    	Corso c = getCodiceCorso(corsoScelto);
    	
    	if(c == null) {
    		txtRisultato.setText("Non è stato trovato nessun corso");
    		return;
    	}
    	
    	List<Studente> listaStudenti = this.model.cercaStudentiCorso(c.getCodins());
    	for(Studente s : listaStudenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    	
    }
    
    @FXML
    void handleCercaCorsi(ActionEvent event) {
    	int matricola;
    	txtRisultato.setText("");
    	
    	try {
    		matricola = Integer.parseInt(txtMatr.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserisci un periodo numerico");
    		return; // L'input è sbagliato perchè non contiene un numero
    	}
    	
    	Studente s = this.model.getStudenteByMatricola(matricola);
    	if(s == null) {
    		txtRisultato.setText("Non è presente uno studente con questo numero di matricola");
    		return;
    	}
    	else {
    		List<Corso> listaCorsi = this.model.getCorsiPerStudente(matricola);
    		for(Corso c : listaCorsi) {
    			txtRisultato.appendText(c + "\n");
    		}
    	}
    	
    }
    

    @FXML
    void handleCercaStudiscritto(ActionEvent event) {
    	/* Controlli di validità */
    	int matricola;
    	txtRisultato.setText("");
    	
    	try {
    		matricola = Integer.parseInt(txtMatr.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserisci un periodo numerico");
    		return; 
    	}
    	
    	String corsoScelto = cmbCorsi.getValue();
    	
    	if(corsoScelto == null) {
    		txtRisultato.setText("Selezionare un corso");
    		return;
    	}
    	
    	if(corsoScelto.compareTo(" ") == 0) {
    		txtRisultato.setText("Non ci sono iscritti presenti al corso perchè non è stato selezionato nessun corso");
    		return;
    	}
    	
    	/* Prendo il codice del corso associato alla combobox */
    	Corso c = getCodiceCorso(corsoScelto);
    	
    	if(c == null) {
    		txtRisultato.setText("Non è stato trovato nessun corso");
    		return;
    	}
    	
    	Studente s = this.model.getStudenteByMatricola(matricola);
    	if(s == null) {
    		txtRisultato.setText("Non è presente uno studente con questo numero di matricola");
    		return;
    	} else {
    		Studente stud = this.model.getStudenteIscrittoCorso(c.getCodins(), matricola);
    		if(stud == null) {
    			this.txtRisultato.setText("Non è presente uno studente iscritto al corso scelto");
    			return;
    		}
    		else {
    			this.txtRisultato.setText(stud.toString());
    		}
    	}
    	
    }
    
    /* Prendo il codice del corso associato alla combobox */
    public Corso getCodiceCorso(String corsoScelto) {
    	List<Corso> listaCorsi = this.model.getCorsi();
    	Corso c = null;
    	for(int i = 0; i < listaCorsi.size(); i++) {
    		if(listaCorsi.get(i).getNome().compareTo(corsoScelto) == 0) {
    			c = listaCorsi.get(i);
    			return c;
    		}
    	}
    	
    	return null;
    }

    @FXML
    void handleIscriviti(ActionEvent event) {

    }

    @FXML
    void handleOK(ActionEvent event) {
    	Map<Integer, Studente> mappaStudenti = this.model.getStudenti();
    	int matricola;
    	try {
    		matricola = Integer.parseInt(txtMatr.getText());
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserisci un periodo numerico");
    		return; // L'input è sbagliato perchè non contiene un numero
    	}
    	if(mappaStudenti.containsKey(matricola)) {
    		txtNome.setText(mappaStudenti.get(matricola).getNome());
    		txtCognome.setText(mappaStudenti.get(matricola).getCognome());
    	}
    	else {
    		txtRisultato.setText("Non è presente uno studente con questa matricola");
    	}
    	
    }

    @FXML
    void handleReset(ActionEvent event) {
    	txtMatr.setText("");
    	txtRisultato.setText("");
    	txtNome.setText("");
    	txtCognome.setText("");
    }

    @FXML
    void initialize() {
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscriviti != null : "fx:id=\"btnIscriviti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnOK != null : "fx:id=\"btnOK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatr != null : "fx:id=\"txtMatr\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

       
    }

	public void setModel(Model model) {
		this.model = model;	
		
		List<Corso> listaCorsi = this.model.getCorsi();
		List<String> nomeCorsi = new LinkedList<String>();
		
    	for(int i = 0; i < listaCorsi.size(); i++) {
    		nomeCorsi.add(listaCorsi.get(i).getNome());
    	}
    	
    	nomeCorsi.add(" ");
    	this.cmbCorsi.getItems().addAll(nomeCorsi);
		
	}

}
