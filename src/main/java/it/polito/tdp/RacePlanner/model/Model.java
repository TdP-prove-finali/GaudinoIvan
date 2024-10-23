package it.polito.tdp.RacePlanner.model;

import java.time.LocalDate;
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

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

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
	private int cntFavCat;
	private int cnt100K;
	private int cnt100M;
	private float cntKm;
	private Map<String, Integer> mapNazioni;
	private Set<String> setNazioni;
	
	private Graph<Race, DefaultWeightedEdge> grafo;
	
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
		if(this.gareValide.size()>=200) 
			System.out.println(this.gareValide.size()+" valid entries - Slow recursion - Refine the filter selection");
		else if(this.gareValide.size()<200 && this.gareValide.size()!=0)
			System.out.println(this.gareValide.size()+" valid entries - Recursion can find solution in reasonable time");
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
	
	private void creaGrafo(String lvl) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.gareValide);
		
		Collections.sort(this.gareValide, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		
		Atleta atleta = this.getAtleta(lvl);
		for(int i=0; i<gareValide.size(); i++) {
			Race r1 = gareValide.get(i);
			LocalDate dateR1= r1.getDate();
			int giorniMin = atleta.getMapCategoriaGiorni().get(r1.getRaceCategory());
			for(int j=i+1; j<gareValide.size(); j++) {
				Race r2 = gareValide.get(j);
				LocalDate dateR2= r2.getDate();
				long giorniTraGare = ChronoUnit.DAYS.between(dateR1, dateR2);
				if(giorniTraGare >= giorniMin && giorniTraGare >= 15) { //la minima distanza in giorni assoluta è 15
					Graphs.addEdge(grafo, r1, r2, giorniTraGare);
				}
			}
		}
	}
	
	public List<Race> massimizza(String button, String lvl, String favCat, Race favRace, Integer maxGare, Float maxKm) {
		this.creaGrafo(lvl);
		this.best = new ArrayList<>();
		this.kmCount = 0;
		this.nazioniCount = 0;
		
		List<Race> parziale = new ArrayList<>();
		
		switch(button) {
		case "Gare":
			for(Race r : grafo.vertexSet()) {
				if(!isAggiuntaValida(parziale, r, maxKm, favCat, lvl))
					continue;
				this.cntFavCat = 0;
				this.cnt100K = 0;
				this.cnt100M = 0;
				parziale.add(r);
				this.incrementaContatori(button, r, favCat, lvl);
				massimizzaGare(button, lvl, parziale, favCat, maxGare, maxKm);
				parziale.clear();
			}
			break;
		case "Km":
			for(Race r : grafo.vertexSet()) {
				if(!isAggiuntaValida(parziale, r, maxKm, favCat, lvl))
					continue;
				this.cntFavCat = 0;
				this.cnt100K = 0;
				this.cnt100M = 0;
				this.cntKm = 0;
				parziale.add(r);
				this.incrementaContatori(button, r, favCat, lvl);
				massimizzaKm(button, lvl, parziale, favCat, maxGare, maxKm);
				parziale.clear();
			}
			break;
		case "Nazioni":
			for(Race r : grafo.vertexSet()) {
				if(!isAggiuntaValida(parziale, r, maxKm, favCat, lvl))
					continue;
				this.cntFavCat = 0;
				this.cnt100K = 0;
				this.cnt100M = 0;
				this.mapNazioni = new HashMap<>();
				this.setNazioni = new HashSet<>();
				parziale.add(r);
				this.incrementaContatori(button, r, favCat, lvl);
				massimizzaNazioni(button, lvl, parziale, favCat, maxGare, maxKm);
				parziale.clear();
			}
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

	private void massimizzaGare(String button, String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
		}
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(parziale.get(parziale.size()-1))) {
			Race gara = Graphs.getOppositeVertex(grafo, e, parziale.get(parziale.size()-1));
			if(!parziale.contains(gara) && isAggiuntaValida(parziale, gara, maxKm, favCat, lvl)) {
				parziale.add(gara);
				this.incrementaContatori(button, gara, favCat, lvl);
				
				massimizzaGare(button, lvl, parziale, favCat, maxGare, maxKm);
				
				parziale.remove(parziale.size()-1);
				this.decrementaContatori(button, gara, favCat, lvl);
			}
		}
	}
	
	private void massimizzaKm(String button, String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(this.cntKm > this.kmCount) {
			this.best = new ArrayList<>(parziale);
			this.kmCount = this.cntKm;
		}
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(parziale.get(parziale.size()-1))) {
			Race gara = Graphs.getOppositeVertex(grafo, e, parziale.get(parziale.size()-1));
			if(!parziale.contains(gara) && isAggiuntaValida(parziale, gara, maxKm, favCat, lvl)) {
				parziale.add(gara);
				this.incrementaContatori(button, gara, favCat, lvl);
				
				massimizzaKm(button, lvl, parziale, favCat, maxGare, maxKm);
				
				parziale.remove(parziale.size()-1);
				this.decrementaContatori(button, gara, favCat, lvl);
			}
		}	
	}
	
	private void massimizzaNazioni(String button, String lvl, List<Race> parziale, String favCat, Integer maxGare, Float maxKm) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		setNazioni = mapNazioni.keySet();
		int cntNazioni = setNazioni.size();
		if(cntNazioni > this.nazioniCount) {
			this.best = new ArrayList<>(parziale);
			this.nazioniCount = cntNazioni;
		}
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(parziale.get(parziale.size()-1))) {
			Race gara = Graphs.getOppositeVertex(grafo, e, parziale.get(parziale.size()-1));
			if(!parziale.contains(gara) && isAggiuntaValida(parziale, gara, maxKm, favCat, lvl)) {
				parziale.add(gara);
				this.incrementaContatori(button, gara, favCat, lvl);
				
				massimizzaNazioni(button, lvl, parziale, favCat, maxGare, maxKm);
				
				parziale.remove(parziale.size()-1);
				this.decrementaContatori(button, gara, favCat, lvl);
			}
		}
	}
	
	private boolean isAggiuntaValida(List<Race> parziale, Race gara, Float maxKm, String favCat, String lvl) {
		if(maxKm!=null && gara.getDistance() > maxKm)
			return false;
		
		if(favCat!=null && !gara.getRaceCategory().equals(favCat) && this.cntFavCat < (parziale.size()+2)/2)
			return false;
		
		if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K") && this.cnt100K >= 4)
			return false;
		
		if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M") && this.cnt100M >= 4)
			return false;
		
		return true;
	}
	
	private void incrementaContatori(String button, Race gara, String favCat, String lvl) {
		if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
			this.cntFavCat++;
		}
		if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
			this.cnt100K++;
		}
		if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
			this.cnt100M++;
		}
		
		if(button.equals("Km"))
			this.cntKm += gara.getDistance();
		
		if(button.equals("Nazioni")) {
			String country = gara.getCountry();
			mapNazioni.put(country, mapNazioni.getOrDefault(country, 0) + 1);
		}
	}
	
	private void decrementaContatori(String button, Race gara, String favCat, String lvl) {
		if(favCat!=null && gara.getRaceCategory().equals(favCat)) {
			this.cntFavCat--;
		}
		if(lvl.equals("Intermedio") && gara.getRaceCategory().equals("100K")) {
			this.cnt100K--;
		}
		if(lvl.equals("Esperto") && gara.getRaceCategory().equals("100M")) {
			this.cnt100M--;
		}
		
		if(button.equals("Km"))
			this.cntKm -= gara.getDistance();
		
		if(button.equals("Nazioni")) {
			String country = gara.getCountry();
			if(mapNazioni.get(country) == 1) {
				mapNazioni.remove(country);
			} else {
				mapNazioni.put(country, mapNazioni.get(country) - 1);
			}
		}
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
		
		if(media >= 0 && media <= 0.5)
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
