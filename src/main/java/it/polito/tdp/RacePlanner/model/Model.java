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
import java.util.TreeSet;

import it.polito.tdp.RacePlanner.db.RacePlannerDAO;

public class Model {
	
	private RacePlannerDAO dao;
	
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
		//return this.gareValide;
		// TODO ritorno una lista non ordinata, devo ordinarla?
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
	
	// TODO controllare se servono tutti i parametri, cancellare quelli inutili
	public List<Race> massimizza(String button, String lvl, String favCat, Race favRace, Integer maxGare, Double maxKm) {
//		if(this.gareValide==null || this.gareValide.isEmpty()) {
//			return null;
//		}
		
		/*Comparator<Race> ordinaPerData = new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}
		};
		
		Collections.sort(this.gareValide, ordinaPerData);*/
		
		Collections.sort(this.gareValide, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		
		this.best = new ArrayList<>();
		this.kmCount = 0;
		this.nazioniCount = 0;
		//TreeSet<Race> parziale = new TreeSet<>(ordinaPerData);
		List<Race> parziale = new ArrayList<>();
		
		int cntFavCat = 0;
		int cntFavCatBest = 0;
		int cnt100K = 0;
		int cnt100M = 0;
		
		switch(button) {
		case "Gare":
			//chiamo metodo massimizzaGare
			// TODO potrei passare atleta anziché lvl
			//massimizzaGare(0, lvl, parziale, favCat, maxGare, maxKm);
			massimizzaGare2(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cntFavCatBest, cnt100K, cnt100M);
			break;
		case "Km":
			//chiamo metodo massimizzaKm
			
			break;
		case "Nazioni":
			//chiamo metodo massimizzaNazioni
			
			break;
		}
		
//		Collections.sort(this.best, new Comparator<Race>() {
//			@Override
//			public int compare(Race r1, Race r2) {
//				return r1.getDate().compareTo(r2.getDate());
//			}	
//		});
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
	
	private void massimizzaGare2(String lvl, List<Race> parziale, String favCat, Integer maxGare, Double maxKm,
			int cntFavCat, int cntFavCatBest, int cnt100K, int cnt100M) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(parziale.size() > best.size()) {
			if(favCat!=null) {
				if(cntFavCat > cntFavCatBest || parziale.size()==1)
					cntFavCatBest = cntFavCat;
				else
					return;
			}
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
			massimizzaGare2(lvl, parziale, favCat, maxGare, maxKm, cntFavCat, cntFavCatBest, cnt100K, cnt100M);
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
	
	private boolean vincoloGiorni(String lvl, List<Race> parziale, Race gara) {
		/*if(!parziale.isEmpty()) {
			Atleta atleta = this.getAtleta(lvl);
			int giorniMin = atleta.getMapCategoriaGiorni().get(gara.getRaceCategory());
			Race prec = parziale.lower(gara);
			Race succ = parziale.higher(gara);
			if (prec!=null) {
		        long giorniTraGare = ChronoUnit.DAYS.between(prec.getDate(), gara.getDate());
		        if (giorniTraGare < giorniMin) {
		            return false;
		        }
		    }
			if (succ!=null) {
		        long giorniTraGare = ChronoUnit.DAYS.between(gara.getDate(), succ.getDate());
		        if (giorniTraGare < giorniMin) {
		            return false; // Non soddisfa il vincolo
		        }
		    }
		}
		
		return true;*/
		
//		List<Race> listaGare = new ArrayList<>(parziale);
//		listaGare.add(gara);
		
		/*Collections.sort(listaGare, new Comparator<Race>() {
			@Override
			public int compare(Race r1, Race r2) {
				return r1.getDate().compareTo(r2.getDate());
			}	
		});
		Atleta atleta = this.getAtleta(lvl);
		for(int i=0; i<listaGare.size()-1; i++) {
			Race r1 = listaGare.get(i);
			Race r2 = listaGare.get(i+1);
			int giorniMin = atleta.getMapCategoriaGiorni().get(r1.getRaceCategory());
			long giorniTraGare = ChronoUnit.DAYS.between(r1.getDate(), r2.getDate());
			if(giorniTraGare < giorniMin)
				return false;
		}*/
		
		// se ordino gareValide per data prima di lanciare la ricorsione
		// ma così facendo avrei problemi con FavRace
		if(!parziale.isEmpty()) {
			//List<Race> listaGare = new ArrayList<>(parziale);
			Atleta atleta = this.getAtleta(lvl);
			int giorniMin = atleta.getMapCategoriaGiorni().get(parziale.get(parziale.size()-1).getRaceCategory());
			long giorniTraGare = ChronoUnit.DAYS.between(parziale.get(parziale.size()-1).getDate(), gara.getDate());
			if(giorniTraGare < giorniMin)
				return false;
		}
		
		return true;
	}
	
	private void massimizzaGare(int L, String lvl, List<Race> parziale, String favCat, Integer maxGare, Double maxKm) {
		if(maxGare!=null && parziale.size() > maxGare) {
			return;
		}
		
		if(parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
		}
		
		if(L == this.gareValide.size())
			return;
		
		if(aggiuntaValida(lvl, parziale, this.gareValide.get(L), favCat, maxKm)) {
			parziale.add(this.gareValide.get(L));
			massimizzaGare(L+1, lvl, parziale, favCat, maxGare, maxKm);
			parziale.remove(this.gareValide.get(L));
			massimizzaGare(L+1, lvl, parziale, favCat, maxGare, maxKm);
		}
		
		massimizzaGare(L+1, lvl, parziale, favCat, maxGare, maxKm);
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
		
		// se ordino gareValide per data prima di lanciare la ricorsione
		// ma così facendo avrei problemi con FavRace
		/*if(!parziale.isEmpty()) {
			Atleta atleta = this.getAtleta(lvl);
			int giorniMin = atleta.getMapCategoriaGiorni().get(parziale.get(parziale.size()-1).getRaceCategory());
			long giorniTraGare = ChronoUnit.DAYS.between(parziale.get(parziale.size()-1).getDate(), gara.getDate());
			if(giorniTraGare < giorniMin)
				return false;
		}*/
		
		return true;
	}

}
