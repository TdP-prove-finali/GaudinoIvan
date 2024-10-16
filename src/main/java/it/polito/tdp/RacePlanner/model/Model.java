package it.polito.tdp.RacePlanner.model;

import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.polito.tdp.RacePlanner.db.RacePlannerDAO;

public class Model {
	
	private RacePlannerDAO dao;
	//private Map<String, String> mapContinents;
	private Map<Month, String> mapMonths;
	private Atleta principiante;
	private Atleta intermedio;
	private Atleta esperto;
	
	private List<Race> gareValide;
	
	private List<Race> best;
	private double kmCount;
	private int nazioniCount;
	
	public Model() {
		this.dao = new RacePlannerDAO();
		//mapContinents = new HashMap<>();
		
		// creo mappa mesi
		this.mapMonths = new LinkedHashMap<>();
		for(Month month : Month.values()) {
			String mese = month.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
			mapMonths.put(month, mese);
		}
		
		// creo atleta principiante
		List<String> categoriePrincipiante = Arrays.asList("20K","50K"); //lista fissa non modificabile
		//List<String> categoriePrincipiante = new ArrayList<>(Arrays.asList("20K","50K"));
		Map<String, Integer> giorniPrincipiante = new HashMap<>();
		giorniPrincipiante.put("20K", 40);
		giorniPrincipiante.put("50K", 70);
		this.principiante = new Atleta("Principiante", categoriePrincipiante, giorniPrincipiante);
		
		// creo atleta intermedio
		List<String> categorieIntermedio = Arrays.asList("20K","50K","100K"); //lista fissa non modificabile
		Map<String, Integer> giorniIntermedio = new HashMap<>();
		giorniIntermedio.put("20K", 25);
		giorniIntermedio.put("50K", 40);
		giorniIntermedio.put("100K", 70);
		this.intermedio = new Atleta("Intermedio", categorieIntermedio, giorniIntermedio);
		
		// creo atleta esperto
		List<String> categorieEsperto = Arrays.asList("20K","50K","100K","100M"); //lista fissa non modificabile
		Map<String, Integer> giorniEsperto = new HashMap<>();
		giorniEsperto.put("20K", 15);
		giorniEsperto.put("50K", 25);
		giorniEsperto.put("100K", 40);
		giorniEsperto.put("100M", 70);
		this.esperto = new Atleta("Esperto", categorieEsperto, giorniEsperto);
	}
	
	// TODO servono i controlli? Se sì, gestire errore nel controller
	public List<String> getMesi() {
		if(this.mapMonths!=null && !this.mapMonths.keySet().isEmpty()) {
			List<String> mesi = new ArrayList<>(this.mapMonths.values());
			return mesi;
		}
		return null;
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

	/*public List<Race> getRacesByFilters(String lvl, String favCat, Integer anno, List<String> continenti,
			List<String> nazioni, List<String> mesiNo) {
		List<String> categorie = this.getAtleta(lvl).getCategorieValide();
		List<Race> gare = dao.getRacesByFilters(anno, favCat, categorie, continenti, nazioni);
		
		if(mesiNo!=null && !mesiNo.isEmpty()) {
			List<Race> gareFiltered = new ArrayList<>();
			for(Race gara : gare) {
				if(!mesiNo.contains(this.mapMonths.get(gara.getDate().getMonth()))) {
					gareFiltered.add(gara);
				}
			}
			Collections.sort(gareFiltered);
			return gareFiltered;
		}
		
		Collections.sort(gare);
		return gare;
	}*/
	
	// ottengo la lista di gare valide
	public List<Race> getRaces(String lvl, Integer anno, List<String> continenti, List<String> nazioni) {
		List<String> categorie = this.getAtleta(lvl).getCategorieValide();
		this.gareValide = new ArrayList<>(dao.getRaces(anno, categorie, continenti, nazioni));
		return this.gareValide;
		// TODO ritorno una lista non ordinata, devo ordinarla?
	}
	
	// applico i filtri di "Categoria preferita" e "Mesi da escludere" sulla lista di gare valide
	public List<Race> getFilteredRaces(String favCat, List<String> mesiNo) {
		if(this.gareValide!=null && !this.gareValide.isEmpty()) {
			if(favCat!=null || (mesiNo!=null && !mesiNo.isEmpty())) {
				List<Race> gareFiltered = new ArrayList<>();
				if(favCat!=null) {
					// filtro solo gare di categoria favCat
					for(Race gara : this.gareValide) {
						if(gara.getRaceCategory().equals(favCat)) {
							gareFiltered.add(gara);
						}
					}
				}
				
				if(mesiNo!=null && !mesiNo.isEmpty()) {
					// filtro sui mesi esclusi
					for(Race gara : this.gareValide) {
						if(!mesiNo.contains(this.mapMonths.get(gara.getDate().getMonth())) 
								&& !gareFiltered.contains(gara)) {
							gareFiltered.add(gara);
						}
					}
				}
				
				if(!gareFiltered.isEmpty())
					return gareFiltered;
				else 
					return null;
			}
			
		} else {
			return null;
		}

		return this.gareValide;
	}
	
	// TODO controllare se servono tutti i parametri, cancellare quelli inutili
	public List<Race> massimizza(String button, String lvl, String favCat, Integer anno, List<String> continenti, 
			List<String> nazioni, List<String> mesiNo, Race favRace, Integer maxGare, Double maxKm) {
		if(this.gareValide==null || this.gareValide.isEmpty()) {
			return null;
		}
		
//		Collections.sort(this.gareValide, new Comparator<Race>() {
//			@Override
//			public int compare(Race r1, Race r2) {
//				return r1.getDate().compareTo(r2.getDate());
//			}	
//		});
		
		this.best = new ArrayList<>();
		this.kmCount = 0;
		this.nazioniCount = 0;
		List<Race> parziale = new ArrayList<>();
		if(favRace!=null) {
			parziale.add(favRace);
		}
		
		switch(button) {
		case "Gare":
			//chiamo metodo massimizzaGare
			massimizzaGare(lvl, parziale, favCat, maxGare, maxKm);
			break;
		case "Km":
			//chiamo metodo massimizzaKm
			
			break;
		case "Nazioni":
			//chiamo metodo massimizzaNazioni
			
			break;
		}
		
		Collections.sort(this.best, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		return this.best;
	}

	private void massimizzaGare(String lvl, List<Race> parziale, String favCat, Integer maxGare, Double maxKm) {
		// TODO controlli di terminazione
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
		}
	
		for(Race gara : this.gareValide) {
			if(!parziale.contains(gara) && aggiuntaValida(lvl, parziale, gara, favCat, maxKm)) {		
				parziale.add(gara);
				massimizzaGare(lvl, parziale, favCat, maxGare, maxKm);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	private boolean aggiuntaValida(String lvl, List<Race> parziale, Race gara, String favCat, Double maxKm) {		
		if(maxKm!=null && gara.getDistance() > maxKm)
			return false;
		
		List<Race> listaGare = new ArrayList<>(parziale);
		listaGare.add(gara);
		
		int countFavCat = 0;
		int countIntermedio = 0;
		int countEsperto = 0;
		
		for(Race r : listaGare) {
			if(favCat!=null && r.getRaceCategory().equals(favCat))
				countFavCat++;
			if(lvl.equals("Intermedio") && r.getRaceCategory().equals("100K"))
				countIntermedio++;
			if(lvl.equals("Esperto") && r.getRaceCategory().equals("100M"))
				countEsperto++;
		}
		
		if(favCat!=null && countFavCat < (listaGare.size()+1)/2) 
			return false;
		
		if(lvl.equals("Intermedio") && countIntermedio>4)
			return false;
		
		if(lvl.equals("Esperto") && countEsperto>4)
			return false;
		
		Collections.sort(listaGare, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		Atleta atleta = this.getAtleta(lvl);
		for(int i=0; i<listaGare.size()-1; i++) {
			int giorniMin = atleta.getMapCategoriaGiorni().get(listaGare.get(i).getRaceCategory());
			long giorniTraGare = ChronoUnit.DAYS.between(listaGare.get(i).getDate(), listaGare.get(i+1).getDate());
			if(giorniTraGare < giorniMin)
				return false;
		}
		
//		if(!parziale.isEmpty()) {
//			Atleta atleta = this.getAtleta(lvl);
//			int giorniMin = atleta.getMapCategoriaGiorni().get(parziale.get(parziale.size()-1).getRaceCategory());
//			long giorniTraGare = ChronoUnit.DAYS.between(parziale.get(parziale.size()-1).getDate(), gara.getDate());
//			if(giorniTraGare < giorniMin)
//				return false;
//		}
		
		return true;
	}

}
