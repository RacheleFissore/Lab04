package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	/* Restituisce l'elenco degli studenti */
	public Map<Integer, Studente> getStudenti() {
		String sql = "SELECT * FROM studente";
		Map<Integer, Studente> result = new TreeMap<Integer, Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.put(rs.getInt("matricola"), new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS")));
			}
			
			st.close();
			rs.close();
			conn.close();
			return result;
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
		
	}
	
	/* Data la matricola di uno studente restituisce i corsi a cui è iscritto */
	public List<Corso> getCorsiPerStudente(int matricola) {
		List<Corso> listaCorsi = new LinkedList<Corso>();
		String sql = "SELECT c.* "
				+ "FROM studente s, iscrizione i, corso c "
				+ "WHERE s.matricola = i.matricola "
				+ "AND i.codins = c.codins "
				+ "AND s.matricola = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				listaCorsi.add(new Corso(codins, numeroCrediti, nome, periodoDidattico)); 
			}
			
			st.close();
			rs.close();
			conn.close();
			return listaCorsi;
			
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
	
	}
	
	/* Data la matricola di uno studente mi restituisce lo studente stesso */
	public Studente getStudenteByMatricola(int matricola) {
		String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";
		
		Studente s = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			}
			
			st.close();
			rs.close();
			conn.close();
			return s;
			
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
	}
	
	/* Data la matricola di uno studente e il codice di un corso, restituisce lo studente se è iscritto al corso */
	public Studente getStudenteIscrittoCorso(String codins, int matricola) {
		String sql = "SELECT s.* "
				+ "FROM studente s, iscrizione i, corso c "
				+ "WHERE s.matricola = i.matricola "
				+ "AND i.codins = c.codins "
				+ "AND i.codins = ? "
				+ "AND s.matricola = ?";
		
		Studente s = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			st.setInt(2, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
			}
			
			st.close();
			rs.close();
			conn.close();
			return s;
			
		} catch(SQLException e) {
			System.err.println("Errore nel DAO");
			e.printStackTrace();
			return null;
		}
	}
	
}
