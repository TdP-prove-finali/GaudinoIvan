package it.polito.tdp.RacePlanner.model;

import java.time.LocalDate;

public class Race implements Comparable<Race>{
	
	private Integer raceUID;
	private Integer year;
	private LocalDate date;
	private String raceTitle;
	private int nParticipants;
	private String raceCategory;
	private double distance;
	private int elevationGain;
	private double meanFinishTime;
	private double winningTime;
	private double lastTime;
	private int nDNF;
	private int nWomen;
	private String rawLocation;
	private String continent;
	private String country;
	
	public Race(Integer raceUID, Integer year, LocalDate date, String raceTitle, int nParticipants, String raceCategory,
			double distance, int elevationGain, double meanFinishTime, double winningTime, double lastTime, int nDNF,
			int nWomen, String rawLocation, String continent, String country) {
		super();
		this.raceUID = raceUID;
		this.year = year;
		this.date = date;
		this.raceTitle = raceTitle;
		this.nParticipants = nParticipants;
		this.raceCategory = raceCategory;
		this.distance = distance;
		this.elevationGain = elevationGain;
		this.meanFinishTime = meanFinishTime;
		this.winningTime = winningTime;
		this.lastTime = lastTime;
		this.nDNF = nDNF;
		this.nWomen = nWomen;
		this.rawLocation = rawLocation;
		this.continent = continent;
		this.country = country;
	}

	public Integer getRaceUID() {
		return raceUID;
	}

	public void setRaceUID(Integer raceUID) {
		this.raceUID = raceUID;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getRaceTitle() {
		return raceTitle;
	}

	public void setRaceTitle(String raceTitle) {
		this.raceTitle = raceTitle;
	}

	public int getnParticipants() {
		return nParticipants;
	}

	public void setnParticipants(int nParticipants) {
		this.nParticipants = nParticipants;
	}

	public String getRaceCategory() {
		return raceCategory;
	}

	public void setRaceCategory(String raceCategory) {
		this.raceCategory = raceCategory;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getElevationGain() {
		return elevationGain;
	}

	public void setElevationGain(int elevationGain) {
		this.elevationGain = elevationGain;
	}

	public double getMeanFinishTime() {
		return meanFinishTime;
	}

	public void setMeanFinishTime(double meanFinishTime) {
		this.meanFinishTime = meanFinishTime;
	}

	public double getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(double winningTime) {
		this.winningTime = winningTime;
	}

	public double getLastTime() {
		return lastTime;
	}

	public void setLastTime(double lastTime) {
		this.lastTime = lastTime;
	}

	public int getnDNF() {
		return nDNF;
	}

	public void setnDNF(int nDNF) {
		this.nDNF = nDNF;
	}

	public int getnWomen() {
		return nWomen;
	}

	public void setnWomen(int nWomen) {
		this.nWomen = nWomen;
	}

	public String getRawLocation() {
		return rawLocation;
	}

	public void setRawLocation(String rawLocation) {
		this.rawLocation = rawLocation;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raceUID == null) ? 0 : raceUID.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Race other = (Race) obj;
		if (raceUID == null) {
			if (other.raceUID != null)
				return false;
		} else if (!raceUID.equals(other.raceUID))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return raceTitle;
	}

	@Override
	public int compareTo(Race other) {
		return this.raceTitle.compareTo(other.raceTitle);
	}
}
