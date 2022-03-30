package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;


public class Model {
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	
	public List<Corso> getCorsi() {
		return this.corsoDao.getTuttiICorsi();
	}
	
	public Map<Integer, Studente> getStudenti() {
		return this.studenteDao.getStudenti();
	}
	
	public List<Studente> cercaStudentiCorso(String corso) {
		return this.corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiPerStudente(int matricola) {
		return this.studenteDao.getCorsiPerStudente(matricola);
	}
	
	public Studente getStudenteByMatricola(int matricola) {
		return this.studenteDao.getStudenteByMatricola(matricola);
	}
	
	public Studente getStudenteIscrittoCorso(String codins, int matricola) {
		return this.studenteDao.getStudenteIscrittoCorso(codins, matricola);
	}
	
	public boolean iscriviStudenteACorso(int matricola, String codins) {
		return this.corsoDao.iscriviStudenteACorso(matricola, codins);
	}
}
