/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
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
    private Button btnIscriviti;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<?> cmbCorsi;

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

    }

    @FXML
    void handleIscriviti(ActionEvent event) {

    }

    @FXML
    void handleOK(ActionEvent event) {

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
    	
    	this.cmbCorsi.getValue().addAll(nomeCorsi);
	}

}
