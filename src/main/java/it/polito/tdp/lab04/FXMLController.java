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
    
    /* PUNTO 1: reset di tutti i campi */
    @FXML
    void handleReset(ActionEvent event) {
    	txtMatr.setText("");
    	txtRisultato.setText("");
    	txtNome.setText("");
    	txtCognome.setText("");
    }
    
    /* PUNTO 2: data la matricola di uno studente far comparire nome e cognome */    
    @FXML
    void handleOK(ActionEvent event) {
    	Map<Integer, Studente> mappaStudenti = this.model.getStudenti();
    	int matricola = this.controllaInserimentoMatricola();
    	
    	if(mappaStudenti.containsKey(matricola)) {
    		txtNome.setText(mappaStudenti.get(matricola).getNome());
    		txtCognome.setText(mappaStudenti.get(matricola).getCognome());
    	}
    	else {
    		txtRisultato.setText("Non è presente uno studente con questa matricola");
    	}
    	
    }
    
    /* PUNTO 3: ricerca gli studenti iscritti ad un corso */
    @FXML
    void handleCercaIscritti(ActionEvent event) {
    	txtRisultato.setText("");
    	String corsoScelto = controllaComboCorso();
    	Corso c = null;
    	
    	// Controlla che il corso sia stato inserito
    	try {
    		c = getCodiceCorso(corsoScelto);
    	} catch (NullPointerException e) {
    		txtRisultato.setText("Non è stato trovato nessun corso");
    		return;
    	}
    	
    	List<Studente> listaStudenti = this.model.cercaStudentiCorso(c.getCodins());
    	for(Studente s : listaStudenti) {
    		txtRisultato.appendText(s + "\n");
    	}
    	
    }
    
    /* PUNTO 4: ricerca dei corsi a cui è iscritto uno studente */
    @FXML
    void handleCercaCorsi(ActionEvent event) {
    	int matricola = this.controllaInserimentoMatricola();    	
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

    /* PUNTO 5: ricerca se uno studente è iscritto ad un corso */
    @FXML
    void handleCercaStudiscritto(ActionEvent event) {
    	/* Controlli di validità */
    	txtRisultato.setText("");
    	int matricola = controllaInserimentoMatricola();
    	String corsoScelto = controllaComboCorso();
    	
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
    			txtNome.setText(s.getNome());
    			txtCognome.setText(s.getCognome());
    			this.txtRisultato.setText("Studente già iscritto a questo corso!");
    		}
    	}
    	
    }
    
    /* PUNTO 6: iscrivere lo studente ad un corso */
    @FXML
    void handleIscriviti(ActionEvent event) {
    	int matricola = controllaInserimentoMatricola();
    	String corsoScelto = controllaComboCorso();
    	
    	// Controllo che la matricola che inserisco sia di uno studente iscritto al corso
    	Studente s = this.model.getStudenteByMatricola(matricola);
    	if(s == null) {
    		txtRisultato.setText("Non è presente uno studente con questo numero di matricola");
    		return;
    	}
    	
    	// Controllo che i campi siano stati inseriti correttamente
    	if(matricola != -1 && corsoScelto != null && corsoScelto != " ") {
    		Corso c = getCodiceCorso(corsoScelto);
        	
        	if(c == null) {
        		txtRisultato.setText("Non è stato trovato nessun corso");
        		return;
        	}
        	
        	boolean risultato = this.model.iscriviStudenteACorso(matricola, c.getCodins()); // Restituisce true se l'inserimento è andato a buon fine
        	if(risultato == true) {
        		txtRisultato.setText("Studente iscritto correttamente al corso");
        	}
        	else {
        		txtRisultato.setText("Errore nell'inserimento dello studente al corso");
        	}
    	}
    	else {
    		txtRisultato.setText("Inserire tutti i dati!");
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

    /* Controllo validità campi */
    public int controllaInserimentoMatricola() {
    	int matricola;
    	try {
    		matricola = Integer.parseInt(txtMatr.getText());
    		return matricola;
    	} catch(NumberFormatException e) {
    		txtRisultato.setText("Inserisci una matricola numerica");
    		return -1; 
    	}
    }
    
    public String controllaComboCorso() {
    	String corsoScelto = cmbCorsi.getValue();
    	
    	if(corsoScelto == null || corsoScelto == " ") {
    		txtRisultato.setText("Selezionare un corso");
    		return null;
    	}
    	else
    		return corsoScelto;
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
		
		/* PUNTO 1.2: popolo la combobox con i corsi presenti nel database */
		List<Corso> listaCorsi = this.model.getCorsi();
		
    	for(int i = 0; i < listaCorsi.size(); i++) {
    		cmbCorsi.getItems().add(listaCorsi.get(i).getNome());
    	}
    	
    	cmbCorsi.getItems().add(" "); // Inserisco l'elemento vuoto nella combo	
	}

}
