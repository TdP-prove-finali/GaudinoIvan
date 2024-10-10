package it.polito.tdp.RacePlanner.model;

import java.util.Collections;
import java.util.List;

import it.polito.tdp.RacePlanner.db.RacePlannerDAO;

public class Model {
	
	private RacePlannerDAO dao;
	//private Map<String, String> mapContinents;
	
	public Model() {
		dao = new RacePlannerDAO();
		//mapContinents = new HashMap<>();
	}
	
	public List<Integer> getYears() {
		return dao.getYears();
	}
	
	public List<String> getContinents() {
		List<String> continents = dao.getContinents();
		Collections.sort(continents);
		return continents;
	}
	
	/*public List<String> getContinentsByMap() {
		dao.getContinentsMap(mapContinents);
		List<String> continents = new ArrayList<>(mapContinents.values());
		Collections.sort(continents);
		return continents;
	}*/

	public List<String> getCountries() {
		List<String> countries = dao.getCountries();
		Collections.sort(countries);
		return countries;
	}
	
	public List<String> getCountriesByContinent(String continent) {
		List<String> countriesFiltered = dao.getCountriesByContinent(continent);
		Collections.sort(countriesFiltered);
		return countriesFiltered;
	}

}
