package it.polito.tdp.RacePlanner.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.RacePlanner.model.Race;

public class RacePlannerDAO {
	
	public List<Integer> getYears() {
		String sql = "SELECT DISTINCT Year FROM races";
		List<Integer> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getInt("Year"));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}
	
	public List<String> getContinents() {
		String sql = "SELECT DISTINCT c.Name AS Continent "
				+ "FROM races r, continents c "
				+ "WHERE r.Continent = c.Code";
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getString("Continent"));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}
	
	public List<String> getCountries() {
		String sql = "SELECT DISTINCT c.Name AS Country "
				+ "FROM races r, countries c "
				+ "WHERE r.Country = c.Code ";
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getString("Country"));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}
	
	public List<String> getCountriesByContinent(String continent) {
		String sql = "SELECT DISTINCT n.Name AS Country "
				+ "FROM races r, countries n, continents c "
				+ "WHERE r.Country = n.Code AND r.Continent = c.Code AND c.Name = ? "
				+ "ORDER BY Country";
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, continent);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getString("Country"));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	} 
	
	public List<Race> getRaces(Integer anno, List<String> categorie,
			List<String> continenti, List<String> nazioni) {
		StringBuilder sql = new StringBuilder("SELECT r.*, n.Name FROM races r, continents c, countries n "
				+ "WHERE r.Continent=c.Code AND r.Country=n.Code AND r.Year=? ");
		
		if(categorie!=null && !categorie.isEmpty()) {
			sql.append("AND r.RaceCategory IN (");
			for(int i=0; i<categorie.size(); i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
		}
		
		if(continenti!=null && !continenti.isEmpty()) {
			sql.append("AND c.Name IN (");
			for(int i=0; i<continenti.size(); i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
		}
		
		if(nazioni!=null && !nazioni.isEmpty()) {
			sql.append("AND n.Name IN (");
			for(int i=0; i<nazioni.size(); i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") ");
		}
		
		List<Race> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql.toString());
			int index = 1;
			st.setInt(index++, anno);
			
			if(categorie!=null && !categorie.isEmpty()) {
				for(String cat : categorie) {
					st.setString(index++, cat);
				}
			}
			
			if(continenti!=null && !continenti.isEmpty()) {
				for(String continente : continenti) {
					st.setString(index++, continente);
				}
			}
			
			if(nazioni!=null && !nazioni.isEmpty()) {
				for(String nazione : nazioni) {
					st.setString(index++, nazione);
				}
			}
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				LocalDate data = dateFromNumDays(res.getInt("Year"), res.getInt("Day"));
				result.add(new Race(res.getInt("RaceUID"), res.getInt("Year"), data,
						res.getString("RaceTitle"), res.getInt("NParticipants"), res.getString("RaceCategory"),
						res.getFloat("Distance"), res.getInt("ElevationGain"), res.getDouble("MeanFinishTime"),
						res.getDouble("WinningTime"), res.getDouble("LastTime"), res.getInt("NDNF"), res.getInt("NWomen"),
						res.getString("RawLocation"), res.getString("Continent"), res.getString("Name")));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			System.out.println(sql.toString());
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}

	// converte il numero di giorni contati dal 01-01 dell'anno year alla data corrispondente
	private LocalDate dateFromNumDays(int year, int day) {
		LocalDate inizio = LocalDate.of(year, 1, 1);
		LocalDate data = inizio.plusDays(day);
		return data;
	}
	
	public List<Race> getRacesFYL(int anno, String categoria, String nazione) {
		String sql = "SELECT  r.*, n.Name "
				+ "FROM races r, countries n WHERE r.Country=n.Code "
				+ "AND r.Year=? AND r.RaceCategory=? AND n.Name=?";
		List<Race> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setString(2, categoria);
			st.setString(3, nazione);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				LocalDate data = dateFromNumDays(res.getInt("Year"), res.getInt("Day"));
				result.add(new Race(res.getInt("RaceUID"), res.getInt("Year"), data,
						res.getString("RaceTitle"), res.getInt("NParticipants"), res.getString("RaceCategory"),
						res.getFloat("Distance"), res.getInt("ElevationGain"), res.getDouble("MeanFinishTime"),
						res.getDouble("WinningTime"), res.getDouble("LastTime"), res.getInt("NDNF"), res.getInt("NWomen"),
						res.getString("RawLocation"), res.getString("Continent"), res.getString("Name")));
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
	}
 
}
