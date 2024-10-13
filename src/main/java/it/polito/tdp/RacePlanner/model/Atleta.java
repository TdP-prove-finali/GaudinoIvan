package it.polito.tdp.RacePlanner.model;

import java.util.List;
import java.util.Map;

public class Atleta {
	
	private String livelloAbilita;
	private List<String> categorieValide;
	private Map<String, Integer> mapCategoriaGiorni;
	
	public Atleta(String livelloAbilita, List<String> categorieValide, Map<String, Integer> mapCategoriaGiorni) {
		super();
		this.livelloAbilita = livelloAbilita;
		this.categorieValide = categorieValide;
		this.mapCategoriaGiorni = mapCategoriaGiorni;
	}

	public String getLivelloAbilita() {
		return livelloAbilita;
	}

	public void setLivelloAbilita(String livelloAbilita) {
		this.livelloAbilita = livelloAbilita;
	}

	public List<String> getCategorieValide() {
		return categorieValide;
	}

	public void setCategorieValide(List<String> categorieValide) {
		this.categorieValide = categorieValide;
	}

	public Map<String, Integer> getMapCategoriaGiorni() {
		return mapCategoriaGiorni;
	}

	public void setMapCategoriaGiorni(Map<String, Integer> mapCategoriaGiorni) {
		this.mapCategoriaGiorni = mapCategoriaGiorni;
	}

}
