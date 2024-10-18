package it.polito.tdp.RacePlanner.model;

import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.RacePlanner.db.RacePlannerDAO;

public class Model {
	
	private RacePlannerDAO dao;
	
	private Map<Month, String> mapMonths;
	private Atleta principiante;
	private Atleta intermedio;
	private Atleta esperto;
	
	private List<Race> gareValide;
	
	private List<Race> best;
	private float kmCount;
	private int nazioniCount;
	
	public Model() {
		this.dao = new RacePlannerDAO();
		
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
	
	public List<String> getMesi() {
		List<String> mesi = new ArrayList<>(this.mapMonths.values());
		return mesi;
	}
	
	public List<Integer> getYears() {
		return dao.getYears();
	}
	
	public List<String> getContinents() {
		List<String> continents = dao.getContinents();
		Collections.sort(continents);
		return continents;
	}

	public List<String> getCountries() {
		List<String> countries = dao.getCountries();
		Collections.sort(countries);
		return countries;
	}
	
	public List<String> getCountriesByContinent(String continent) {
		List<String> countriesFiltered = dao.getCountriesByContinent(continent);
		return countriesFiltered;
	}
	
	public Atleta getAtleta(String livelloAbilita) {
		if(livelloAbilita!=null) {
			if(livelloAbilita.equals("Principiante")) {
				return this.principiante;
			} else if(livelloAbilita.equals("Intermedio")) {
				return this.intermedio;
			} else if(livelloAbilita.equals("Esperto")) {
				return this.esperto;
			}
		}
		
		return null;
	}
	
	// riempio la lista di gare valide
	public void getRaces(String lvl, Integer anno, List<String> continenti, List<String> nazioni, List<String> mesiNo) {
		List<String> categorie = this.getAtleta(lvl).getCategorieValide();
		this.gareValide = new ArrayList<>(dao.getRaces(anno, categorie, continenti, nazioni));
		if(mesiNo!=null && !mesiNo.isEmpty()) {
			List<Race> gareValideFiltered = new ArrayList<>();
			for(Race gara : this.gareValide) {
				if(!mesiNo.contains(this.mapMonths.get(gara.getDate().getMonth()))) 
					gareValideFiltered.add(gara);
			}
			this.gareValide = gareValideFiltered;
		}
		System.out.println(this.gareValide.size()); // TODO DA TOGLIERE
	}
	
	// applico il filtro di "Categoria preferita" sulla lista di gare valide
	public List<Race> getFilteredRaces(String lvl, String favCat, Integer anno, List<String> continenti,
			List<String> nazioni, List<String> mesiNo) throws IllegalArgumentException, IllegalStateException {
		getRaces(lvl, anno, continenti, nazioni, mesiNo);
		if(this.gareValide==null || this.gareValide.isEmpty()) {
			throw new IllegalArgumentException("Nessuna gara disponibile. Prova a selezionare altri valori.");
		}
		
		if(favCat!=null) {
			List<Race> gareFiltered = new ArrayList<>();
			for(Race gara : this.gareValide) {
				if(gara.getRaceCategory().equals(favCat)) {
					gareFiltered.add(gara);
				}
			}
			
			if(!gareFiltered.isEmpty())
				return gareFiltered;
			else
				throw new IllegalStateException("Nessuna gara disponibile per la categoria indicata.");
		}
		
		return this.gareValide;
	}
	
	public List<Race> getRacesFYL(int anno, String categoria, String nazione) {
		List<Race> racesFYL = dao.getRacesFYL(anno, categoria, nazione);
		if(racesFYL==null || racesFYL.isEmpty())
			return null;
		
		Collections.sort(racesFYL);
		return racesFYL;
	}
	
	public List<Race> massimizza(String button, String lvl, String favCat, Race favRace, Integer maxGare, Float maxKm) {
		Collections.sort(this.gareValide, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		
		this.best = new ArrayList<>();
		this.kmCount = 0;
		this.nazioniCount = 0;
		List<Race> parziale = new ArrayList<>();
		
		int cntFavCat = 0;
		int cnt100K = 0;
		int cnt100M = 0;
		float cntKm = 0;
		//List<String> listNazioni = new ArrayList<>();
		Map<String, Integer> mapNazioni = new HashMap<>();
		Set<String> setNazioni = new HashSet<>();
		
		switch(button) {
		case "Gare":
			// TODO implementare filtro favCat (o eliminarlo)
			massimizzaGare(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M);
			break;
		case "Km":
			massimizzaKm(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M, cntKm);
			break;
		case "Nazioni":
			massimizzaNazioni(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M, mapNazioni, setNazioni);
			break;
		}
		
		if(favRace!=null) {
			parziale.add(favRace);
			int index = 0;
			for(int i=0; i<best.size(); i++) {
				if (best.get(i).getDate().compareTo(favRace.getDate()) > 0) {
					index = i;
					break;
				}
				index = i+1;
			}
			best.add(index, favRace);
			
			try {
				Race prec = best.get(best.indexOf(favRace)-1);
				int giorniMin = getAtleta(lvl).getMapCategoriaGiorni().get(prec.getRaceCategory());
				long giorniTraGare = ChronoUnit.DAYS.between(prec.getDate(), favRace.getDate());
		        if (giorniTraGare < giorniMin) 
		            best.remove(prec);
			} catch(IndexOutOfBoundsException e) {
				
			}
			
			try {
				Race succ = best.get(best.indexOf(favRace)+1);
				int giorniMin = getAtleta(lvl).getMapCategoriaGiorni().get(favRace.getRaceCategory());
				long giorniTraGare = ChronoUnit.DAYS.between(favRace.getDate(), succ.getDate());
				if (giorniTraGare < giorniMin)
		            best.remove(succ);
			} catch(IndexOutOfBoundsException e) {
				
			}
		}
	
		return this.best;
	}

	private void massimizzaGare(String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm,
			int cntFavCat, int cnt100K, int cnt100M) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(parziale.size() > best.size()) {
			/*if(favCat!=null) {
				int minGareFavCat = (parziale.size()+1)/2;
				if(cntFavCat < minGareFavCat)
					return;
			}*/
			this.best = new ArrayList<>(parziale);
		}
	
		for(Race gara : this.gareValide) {
			if(parziale.contains(gara)) 
				continue;
				
			if(maxKm!=null && gara.getDistance() > maxKm)
				continue;
			
//			if(favCat!=null && !gara.getRaceCategory().equals(favCat) && cntFavCat < (parziale.size()+2)/2)
//				continue;
			
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K") && cnt100K >= 4)
				continue;
			
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M") && cnt100M >= 4)
				continue;
			
			if(!vincoloGiorni(lvl, parziale, gara))
				continue;
			
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat++;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K++;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M++;
			}
			parziale.add(gara);
			massimizzaGare(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M);
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat--;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K--;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M--;
			}
			parziale.remove(parziale.size()-1);
		}
	}
	
	private void massimizzaKm(String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm,
			int cntFavCat, int cnt100K, int cnt100M, float cntKm) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(cntKm > this.kmCount) {
			this.best = new ArrayList<>(parziale);
			this.kmCount = cntKm;
		}
	
		for(Race gara : this.gareValide) {
			if(parziale.contains(gara)) 
				continue;
				
			if(maxKm!=null && gara.getDistance() > maxKm)
				continue;
			
//			if(favCat!=null && !gara.getRaceCategory().equals(favCat) && cntFavCat < (parziale.size()+2)/2)
//				continue;
			
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K") && cnt100K >= 4)
				continue;
			
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M") && cnt100M >= 4)
				continue;
			
			if(!vincoloGiorni(lvl, parziale, gara))
				continue;
			
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat++;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K++;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M++;
			}
			cntKm += gara.getDistance();
			parziale.add(gara);
			massimizzaKm(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M, cntKm);
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat--;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K--;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M--;
			}
			cntKm -= gara.getDistance();
			parziale.remove(parziale.size()-1);
		}
	}
	
	private void massimizzaNazioni(String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm,
			int cntFavCat, int cnt100K, int cnt100M, Map<String, Integer> mapNazioni, Set<String> setNazioni) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		//setNazioni = new HashSet<>(listNazioni);
		setNazioni = mapNazioni.keySet();
		int cntNazioni = setNazioni.size();
		if(cntNazioni > this.nazioniCount) {
			this.best = new ArrayList<>(parziale);
			this.nazioniCount = cntNazioni;
		}
	
		for(Race gara : this.gareValide) {
			if(parziale.contains(gara)) 
				continue;
				
			if(maxKm!=null && gara.getDistance() > maxKm)
				continue;
			
//			if(favCat!=null && !gara.getRaceCategory().equals(favCat) && cntFavCat < (parziale.size()+2)/2)
//				continue;
			
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K") && cnt100K >= 4)
				continue;
			
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M") && cnt100M >= 4)
				continue;
			
			if(!vincoloGiorni(lvl, parziale, gara))
				continue;
			
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat++;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K++;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M++;
			}
			
			//listNazioni.add(gara.getCountry());
			
			String country = gara.getCountry();
			mapNazioni.put(country, mapNazioni.getOrDefault(country, 0) + 1);
			
			parziale.add(gara);
			massimizzaNazioni(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cnt100K, cnt100M, mapNazioni, setNazioni);
			if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
				cntFavCat--;
			}
			if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
				cnt100K--;
			}
			if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
				cnt100M--;
			}
			
			//listNazioni.remove(parziale.get(parziale.size()-1).getCountry());	
			
			if(mapNazioni.get(country) == 1) {
				mapNazioni.remove(country);
			} else {
				mapNazioni.put(country, mapNazioni.get(country) - 1);
			}
			parziale.remove(parziale.size()-1);
		}		
	}

	private boolean vincoloGiorni(String lvl, List<Race> parziale, Race gara) {
		if(!parziale.isEmpty()) {
			Atleta atleta = this.getAtleta(lvl);
			int giorniMin = atleta.getMapCategoriaGiorni().get(parziale.get(parziale.size()-1).getRaceCategory());
			long giorniTraGare = ChronoUnit.DAYS.between(parziale.get(parziale.size()-1).getDate(), gara.getDate());
			if(giorniTraGare < giorniMin)
				return false;
		}
		return true;
	}
	
	public Float getKmTot() {
		if(this.best==null || this.best.isEmpty())
			return null;
		
		float kmTot = 0;
		for(Race r : this.best) {
			kmTot += r.getDistance();
		}
		return Math.round(kmTot * 10) / 10.0f;
	}
	
	public Set<String> getNazioniSoluzione() {
		if(this.best==null || this.best.isEmpty())
			return null;
		
		Set<String> nazioni = new HashSet<>();
		for(Race r : this.best) {
			nazioni.add(r.getCountry());
		}
		return nazioni;
	}
	
	public String findLevel(Map<Race, LocalTime> raceTimeMap) {
		List<Integer> listLevel = new ArrayList<>();
		for(Race gara : raceTimeMap.keySet()) {
			double inf = (gara.getMeanFinishTime() + gara.getLastTime()) / 2;
			double sup = (gara.getMeanFinishTime() + gara.getWinningTime()) / 2;
			double tempoDecimale = localTimeToDecimalHours(raceTimeMap.get(gara));
			if(tempoDecimale >= inf)
				listLevel.add(0);
			else if(tempoDecimale < inf && tempoDecimale > sup)
				listLevel.add(1);
			else if(tempoDecimale <= sup)
				listLevel.add(2);
		}
		
		int sum = 0;
		for(Integer lvl : listLevel) {
			sum += lvl;
		}
		
		double media = sum / listLevel.size();
		
		if(media > 0 && media <= 0.5)
			return "Principiante";
		else if(media > 0.5 && media <= 1.4)
			return "Intermedio";
		else if(media > 1.4 && media <= 2)
			return "Esperto";
		
		return null;
	}

	private double localTimeToDecimalHours(LocalTime time) {
		int ore = time.getHour();
		int minuti = time.getMinute();
		int secondi = time.getSecond();
		double oreDecimali = ore + (minuti/60.0) + (secondi/3600.0);
		return oreDecimali;
		
	}
	
}
