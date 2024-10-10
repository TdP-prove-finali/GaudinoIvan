package it.polito.tdp.RacePlanner.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	/*public void getContinentsMap(Map<String, String> mapContinents) {
		String sql = "SELECT DISTINCT c.* "
				+ "FROM races r, continents c "
				+ "WHERE r.Continent = c.Code";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				if(!mapContinents.containsKey(res.getString("Code"))) {
					mapContinents.put(res.getString("Code"), res.getString("Name"));
				}
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}*/
	
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
 
}
