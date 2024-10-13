package it.polito.tdp.RacePlanner.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.RacePlanner.db.RacePlannerDAO;

public class Model {
	
	private RacePlannerDAO dao;
	//private Map<String, String> mapContinents;
	private Atleta principiante;
	private Atleta intermedio;
	private Atleta esperto;
	
	public Model() {
		this.dao = new RacePlannerDAO();
		//mapContinents = new HashMap<>();
		
		//creo atleta principiante
		List<String> categoriePrincipiante = Arrays.asList("20K","50K"); //lista fissa non modificabile
		//List<String> categoriePrincipiante = new ArrayList<>(Arrays.asList("20K","50K"));
		Map<String, Integer> giorniPrincipiante = new HashMap<>();
		giorniPrincipiante.put("20K", 40);
		giorniPrincipiante.put("50K", 70);
		this.principiante = new Atleta("Principiante", categoriePrincipiante, giorniPrincipiante);
		
		//creo atleta intermedio
		List<String> categorieIntermedio = Arrays.asList("20K","50K","100K"); //lista fissa non modificabile
		Map<String, Integer> giorniIntermedio = new HashMap<>();
		giorniPrincipiante.put("20K", 25);
		giorniPrincipiante.put("50K", 40);
		giorniPrincipiante.put("100K", 70);
		this.intermedio = new Atleta("Intermedio", categorieIntermedio, giorniIntermedio);
		
		//creo atleta esperto
		List<String> categorieEsperto = Arrays.asList("20K","50K","100K","100M"); //lista fissa non modificabile
		Map<String, Integer> giorniEsperto = new HashMap<>();
		giorniEsperto.put("20K", 15);
		giorniEsperto.put("50K", 25);
		giorniEsperto.put("100K", 40);
		giorniEsperto.put("100M", 70);
		this.esperto = new Atleta("Esperto", categorieEsperto, giorniEsperto);
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
	
	public Atleta getAtleta(String livelloAbilita) {
		if(livelloAbilita==null) {
			return null;
		}
		
//		switch(livelloAbilita) {
//		case("Principiante"):
//			return this.principiante;
//		case("Intermedio"):
//			return this.intermedio;
//		case("Esperto"):
//			return this.esperto;
//		}
		
		if(livelloAbilita.equals("Principiante")) {
			return this.principiante;
		} else if(livelloAbilita.equals("Intermedio")) {
			return this.intermedio;
		} else if(livelloAbilita.equals("Esperto")) {
			return this.esperto;
		}
		
		return null;
	}

	public List<Race> getRacesByFilters(String lvl, String favCat, Integer anno, List<String> continenti,
			List<String> nazioni, List<String> mesiNo) {
		List<String> categorie = this.getAtleta(lvl).getCategorieValide();
		
		List<Race> gare = dao.getRacesByFilters(anno, favCat, categorie, continenti, nazioni);
		//devo escludere mesiNO (forse meglio mettere oggetti Month nel controller)
		//devo ordinare per nome
		return gare;
	}

}
