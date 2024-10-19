package it.polito.tdp.RacePlanner;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.controlsfx.control.CheckComboBox;

import it.polito.tdp.RacePlanner.model.Model;
import it.polito.tdp.RacePlanner.model.Race;

public class FXMLController {
	
	private Model model;
	private boolean isFavRaceEmpty;
	Map<TextField, ComboBox<Race>> txtTimeMap;
	Map<Race, LocalTime> raceTimeMap;
	String livelloAbilita;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnGare;

    @FXML
    private Button btnKm;

    @FXML
    private Button btnNazioni;

    @FXML
    private Button btnSave;

    @FXML
    private CheckComboBox<String> ckCmbContinents;

    @FXML
    private CheckComboBox<String> ckCmbMonths;

    @FXML
    private CheckComboBox<String> ckCmbNations;

    @FXML
    private ComboBox<String> cmbCatR1;

    @FXML
    private ComboBox<String> cmbCatR2;

    @FXML
    private ComboBox<String> cmbCatR3;

    @FXML
    private ComboBox<String> cmbCatR4;

    @FXML
    private ComboBox<String> cmbCatR5;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private ComboBox<Race> cmbFavRace;

    @FXML
    private ComboBox<String> cmbLevel;

    @FXML
    private ComboBox<Race> cmbNameR1;

    @FXML
    private ComboBox<Race> cmbNameR2;

    @FXML
    private ComboBox<Race> cmbNameR3;

    @FXML
    private ComboBox<Race> cmbNameR4;

    @FXML
    private ComboBox<Race> cmbNameR5;

    @FXML
    private ComboBox<String> cmbNationR1;

    @FXML
    private ComboBox<String> cmbNationR2;

    @FXML
    private ComboBox<String> cmbNationR3;

    @FXML
    private ComboBox<String> cmbNationR4;

    @FXML
    private ComboBox<String> cmbNationR5;

    @FXML
    private ComboBox<Integer> cmbYear;

    @FXML
    private ComboBox<Integer> cmbYearR1;

    @FXML
    private ComboBox<Integer> cmbYearR2;

    @FXML
    private ComboBox<Integer> cmbYearR3;

    @FXML
    private ComboBox<Integer> cmbYearR4;

    @FXML
    private ComboBox<Integer> cmbYearR5;

    @FXML
    private TableColumn<Race, String> colCategory;

    @FXML
    private TableColumn<Race, LocalDate> colDate;

    @FXML
    private TableColumn<Race, Integer> colElevGain;

    @FXML
    private TableColumn<Race, Float> colKm;

    @FXML
    private TableColumn<Race, String> colName;

    @FXML
    private TableColumn<Race, String> colPlace;

    @FXML
    private Label lblInstructions;

    @FXML
    private Label lblLevel;

    @FXML
    private Label lblWarnings;

    @FXML
    private Tab tabLevel;

    @FXML
    private Tab tabMain;
    
    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<Race> tblRaces;

    @FXML
    private TextField txtKmMaxRace;

    @FXML
    private TextField txtNMaxRaces;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtTimeR1;

    @FXML
    private TextField txtTimeR2;

    @FXML
    private TextField txtTimeR3;

    @FXML
    private TextField txtTimeR4;

    @FXML
    private TextField txtTimeR5;

    @FXML
    void doFindLevel(ActionEvent event) {
    	lblLevel.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
    	int cntNotNull = 0;
    	for(TextField txtTime : this.txtTimeMap.keySet()) {
    		if(!txtTime.getText().equals(""))
    			cntNotNull++;
    		if(cntNotNull==2)
    			break;
    	}
    	
    	if(cntNotNull<2) {
    		lblLevel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
    		lblLevel.setText("Inserisci i dati di almeno 2 gare.");
    		return;
    	}
    	
    	this.raceTimeMap = new HashMap<>();
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm:ss");
    	
    	for(TextField txtTime : this.txtTimeMap.keySet()) {
    		LocalTime tempoDiGara;
    		if(!txtTime.getText().equals("")) {
    			try {
    				tempoDiGara = LocalTime.parse(txtTime.getText(), timeFormat);
    				Race gara = this.txtTimeMap.get(txtTime).getValue();
    				if(this.raceTimeMap.containsKey(gara)) {
    					lblLevel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
    					lblLevel.setText("Inserisci i dati di almeno 2 gare diverse.");
    		    		return;
    				}
    				this.raceTimeMap.put(gara, tempoDiGara);
    			} catch (DateTimeParseException e){
    				lblLevel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
    				lblLevel.setText("Attenzione: il tempo deve essere nel formato ore:minuti:secondi");
    				return;
    			}
    		}
    	}
    	
    	this.livelloAbilita = model.findLevel(this.raceTimeMap);
    	if(this.livelloAbilita==null) {
    		lblLevel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
    		lblLevel.setText("Si è verificato un errore. Riprova.");
    		return;
    	}
    	lblLevel.setText("Il tuo livello di abilità è: "+this.livelloAbilita);
    }
    
    @FXML
    void doSave(ActionEvent event) {
    	if(this.livelloAbilita!=null) {
    		cmbLevel.setValue(this.livelloAbilita);
        	tabPane.getSelectionModel().select(tabMain);
    	}
    }

    @FXML
    void doMaximizeKm(ActionEvent event) {
    	txtResult.clear();
    	tblRaces.getItems().clear();
    	lblWarnings.setText("");
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat;
    		if(this.isFavRaceEmpty)
    			favCat = null;
    		else
    			favCat = cmbCategory.getValue();
    		
        	Race favRace = cmbFavRace.getValue();
        	
        	Integer maxGare = null;
        	if(!txtNMaxRaces.getText().equals("")) {
            	try {
            		maxGare = Integer.parseInt(txtNMaxRaces.getText());
            	} catch(NumberFormatException e) {
            		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido.");
            		return;
            	}
        	}
        	
        	Float maxKm = null;
        	if(!txtKmMaxRace.getText().equals("")) {
        		try {
	        		maxKm = Float.parseFloat(txtKmMaxRace.getText());
	        	} catch(NumberFormatException e) {
	        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido.");
	        		return;
	        	}
        	}
        	
    		List<Race> racePlan = model.massimizza("Km", lvl, favCat, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    			txtResult.appendText("Numero di gare: "+racePlan.size()+"\n");
    			txtResult.appendText("Kilometri totali: "+model.getKmTot()+" km\n");
    			List<String> nazioni = new ArrayList<>(model.getNazioniSoluzione());
    			Collections.sort(nazioni);
    			txtResult.appendText("Numero di nazioni: "+nazioni.size()+" (");
    			for(int i=0; i<nazioni.size(); i++) {
    				txtResult.appendText(nazioni.get(i));
    				if(i<nazioni.size()-1)
    					txtResult.appendText(", ");
    				else
    					txtResult.appendText(")");
    			}
    		} else {
    			lblWarnings.setText("Nessuna soluzione trovata. Prova a modificare le tue scelte");
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: \"Livello di abilità\" e \"Anno\" sono campi obbligatori.");
    	}
    }

    @FXML
    void doMaximizeNations(ActionEvent event) {
    	txtResult.clear();
    	tblRaces.getItems().clear();
    	lblWarnings.setText("");
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat;
    		if(this.isFavRaceEmpty)
    			favCat = null;
    		else
    			favCat = cmbCategory.getValue();
    		
        	Race favRace = cmbFavRace.getValue();
        	
        	Integer maxGare = null;
        	if(!txtNMaxRaces.getText().equals("")) {
            	try {
            		maxGare = Integer.parseInt(txtNMaxRaces.getText());
            	} catch(NumberFormatException e) {
            		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido.");
            		return;
            	}
        	}
        	
        	Float maxKm = null;
        	if(!txtKmMaxRace.getText().equals("")) {
        		try {
	        		maxKm = Float.parseFloat(txtKmMaxRace.getText());
	        	} catch(NumberFormatException e) {
	        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido.");
	        		return;
	        	}
        	}
        	
    		List<Race> racePlan = model.massimizza("Nazioni", lvl, favCat, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    			txtResult.appendText("Numero di gare: "+racePlan.size()+"\n");
    			txtResult.appendText("Kilometri totali: "+model.getKmTot()+" km\n");
    			List<String> nazioni = new ArrayList<>(model.getNazioniSoluzione());
    			Collections.sort(nazioni);
    			txtResult.appendText("Numero di nazioni: "+nazioni.size()+" (");
    			for(int i=0; i<nazioni.size(); i++) {
    				txtResult.appendText(nazioni.get(i));
    				if(i<nazioni.size()-1)
    					txtResult.appendText(", ");
    				else
    					txtResult.appendText(")");
    			}
    		} else {
    			lblWarnings.setText("Nessuna soluzione trovata. Prova a modificare le tue scelte");
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: \"Livello di abilità\" e \"Anno\" sono campi obbligatori.");
    	}
    }

    @FXML
    void doMaximizeRaces(ActionEvent event) {
    	txtResult.clear();
    	tblRaces.getItems().clear();
    	lblWarnings.setText("");
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat;
    		if(this.isFavRaceEmpty)
    			favCat = null;
    		else
    			favCat = cmbCategory.getValue();
    		
    		
        	Race favRace = cmbFavRace.getValue();
        	
        	Integer maxGare = null;
        	if(!txtNMaxRaces.getText().equals("")) {
            	try {
            		maxGare = Integer.parseInt(txtNMaxRaces.getText());
            	} catch(NumberFormatException e) {
            		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido.");
            		return;
            	}
        	}
        	
        	Float maxKm = null;
        	if(!txtKmMaxRace.getText().equals("")) {
        		try {
	        		maxKm = Float.parseFloat(txtKmMaxRace.getText());
	        	} catch(NumberFormatException e) {
	        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido.");
	        		return;
	        	}
        	}
        	
    		List<Race> racePlan = model.massimizza("Gare", lvl, favCat, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    			txtResult.appendText("Numero di gare: "+racePlan.size()+"\n");
    			txtResult.appendText("Kilometri totali: "+model.getKmTot()+" km\n");
    			List<String> nazioni = new ArrayList<>(model.getNazioniSoluzione());
    			Collections.sort(nazioni);
    			txtResult.appendText("Numero di nazioni: "+nazioni.size()+" (");
    			for(int i=0; i<nazioni.size(); i++) {
    				txtResult.appendText(nazioni.get(i));
    				if(i<nazioni.size()-1)
    					txtResult.appendText(", ");
    				else
    					txtResult.appendText(")");
    			}
    		} else {
    			lblWarnings.setText("Nessuna soluzione trovata. Prova a modificare le tue scelte");
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: \"Livello di abilità\" e \"Anno\" sono campi obbligatori.");
    	}
    }

    @FXML
    void doOpenGuide(ActionEvent event) {
    	try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Guide.fxml"));
    		Parent guide = (Parent) fxmlLoader.load();
    		Stage stage = new Stage();
    		stage.setTitle("Guida");
    		stage.setScene(new Scene(guide));  
    		stage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("Can't load new window");
    	}
    }

    @FXML
    void doResetLevel(ActionEvent event) {
    	cmbYearR1.setValue(null);
    	cmbYearR2.setValue(null);
    	cmbYearR3.setValue(null);
    	cmbYearR4.setValue(null);
    	cmbYearR5.setValue(null);
    	cmbCatR1.setValue(null);
    	cmbCatR2.setValue(null);
    	cmbCatR3.setValue(null);
    	cmbCatR4.setValue(null);
    	cmbCatR5.setValue(null);
    	cmbNationR1.setValue(null);
    	cmbNationR2.setValue(null);
    	cmbNationR3.setValue(null);
    	cmbNationR4.setValue(null);
    	cmbNationR5.setValue(null);
    	cmbNameR1.setValue(null);
    	cmbNameR2.setValue(null);
    	cmbNameR3.setValue(null);
    	cmbNameR4.setValue(null);
    	cmbNameR5.setValue(null);
    	txtTimeR1.clear();
    	txtTimeR2.clear();
    	txtTimeR3.clear();
    	txtTimeR4.clear();
    	txtTimeR5.clear();
    }

    @FXML
    void doResetMain(ActionEvent event) {
    	cmbLevel.setValue(null);
    	cmbCategory.setValue(null);
    	cmbYear.setValue(null);
    	ckCmbContinents.getCheckModel().clearChecks();
    	ckCmbNations.getCheckModel().clearChecks();
    	ckCmbMonths.getCheckModel().clearChecks();
    	cmbFavRace.setValue(null);
    	txtNMaxRaces.clear();
    	txtKmMaxRace.clear();
    	lblWarnings.setText("");
    	tblRaces.getItems().clear();
    	txtResult.clear();
    }
    
    // gestisco l'azione di modifica selezione su cmbLevel
    @FXML
    void handleCmbLevel(ActionEvent event) {
    	String livello = cmbLevel.getValue();
    	if(livello!=null) {
    		cmbCategory.getItems().clear();
    		cmbCategory.getItems().addAll(model.getAtleta(livello).getCategorieValide());
    		this.handleCmbAction(event);
    	}
    }
    
    // gestisco cmbFavRace in base ai valori selezionati
    @FXML
    void handleCmbAction(ActionEvent event) {
    	cmbFavRace.getItems().clear();
    	lblWarnings.setText("");
    	btnGare.setDisable(false);
		btnKm.setDisable(false);
		btnNazioni.setDisable(false);
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue(); 
    	List<String> continenti = ckCmbContinents.getCheckModel().getCheckedItems();
    	List<String> nazioni = ckCmbNations.getCheckModel().getCheckedItems();
    	List<String> mesiNo = ckCmbMonths.getCheckModel().getCheckedItems();
    	// le condizioni sulle CheckComboBox risolvono un bug della libreria ControlsFX
    	if(lvl!=null && anno!=null && !continenti.contains("null") 
    			&& !nazioni.contains("null") && !mesiNo.contains("null")) {
    		String favCat = cmbCategory.getValue();
    		try {
    			List<Race> filteredRaces = model.getFilteredRaces(lvl, favCat, anno, continenti, nazioni, mesiNo);
    			cmbFavRace.setDisable(false);
    			this.isFavRaceEmpty = false;
    			Collections.sort(filteredRaces);
    			cmbFavRace.getItems().addAll(filteredRaces);
    		} catch(IllegalArgumentException e) {
    			btnGare.setDisable(true);
    			btnKm.setDisable(true);
    			btnNazioni.setDisable(true);
    			lblWarnings.setText(e.getMessage());
    		} catch(IllegalStateException e) {
    			cmbFavRace.setDisable(true);
    			this.isFavRaceEmpty = true;
    		}
    		
    	} else {
    		cmbFavRace.setDisable(true);
    	}
    }
    
    // gestisco l'azione di modifica selezione su cmbContinents
    private void handleCkCmbContinents() {
    	ckCmbNations.getItems().clear();
    	List<String> continentiSelezionati = ckCmbContinents.getCheckModel().getCheckedItems();
    	List<String> nazioniDiContinente = new ArrayList<>();
    	// la condizione sulla CheckComboBox risolve un bug della libreria ControlsFX
    	if(!continentiSelezionati.isEmpty() && !continentiSelezionati.contains("null")) {
    		for(String continente : continentiSelezionati) {
    			nazioniDiContinente.addAll(model.getCountriesByContinent(continente));
        	}
    		Collections.sort(nazioniDiContinente);
    		ckCmbNations.getItems().addAll(nazioniDiContinente);
    		
    	} else {
    		ckCmbNations.getItems().addAll(model.getCountries());
    	}
    }
    
    // gestisco l'azione di modifica selezione sulle ComboBox nel tab "Scopri il tuo livello"
    @FXML
    void handleCmbFYLR1(ActionEvent event) {
    	handleCmbFYLGeneric(cmbYearR1, cmbCatR1, cmbNationR1, cmbNameR1, txtTimeR1);
    }
    
    @FXML
    void handleCmbFYLR2(ActionEvent event) {
    	handleCmbFYLGeneric(cmbYearR2, cmbCatR2, cmbNationR2, cmbNameR2, txtTimeR2);
    }
    
    @FXML
    void handleCmbFYLR3(ActionEvent event) {
    	handleCmbFYLGeneric(cmbYearR3, cmbCatR3, cmbNationR3, cmbNameR3, txtTimeR3);
    }
    
    @FXML
    void handleCmbFYLR4(ActionEvent event) {
    	handleCmbFYLGeneric(cmbYearR4, cmbCatR4, cmbNationR4, cmbNameR4, txtTimeR4);    
    }
    
    @FXML
    void handleCmbFYLR5(ActionEvent event) {
		handleCmbFYLGeneric(cmbYearR5, cmbCatR5, cmbNationR5, cmbNameR5, txtTimeR5);
    }
    
    private void handleCmbFYLGeneric(ComboBox<Integer> cmbYear, ComboBox<String> cmbCat,
    		ComboBox<String> cmbNation, ComboBox<Race> cmbName, TextField txtTime) {
    	txtTime.clear();
    	cmbName.getItems().clear();
    	Integer anno = cmbYear.getValue();
    	String categoria = cmbCat.getValue();
    	String nazione = cmbNation.getValue();
    	if(anno!=null && categoria!=null && nazione!=null) {
    		List<Race> racesFYL = model.getRacesFYL(anno, categoria, nazione);
    		if(racesFYL!=null && !racesFYL.isEmpty()) {
    			cmbName.setDisable(false);
    			cmbName.getItems().addAll(racesFYL);
    		} else
    			cmbName.setDisable(true);
    	} else 
    		cmbName.setDisable(true);
    }
    
    @FXML
    void handleCmbNameFYLR1(ActionEvent event) {
    	handleCmbNameFYLGeneric(cmbNameR1, txtTimeR1);
    }
    
    @FXML
    void handleCmbNameFYLR2(ActionEvent event) {
    	handleCmbNameFYLGeneric(cmbNameR2, txtTimeR2);
    }
    
    @FXML
    void handleCmbNameFYLR3(ActionEvent event) {
    	handleCmbNameFYLGeneric(cmbNameR3, txtTimeR3);
    }
    
    @FXML
    void handleCmbNameFYLR4(ActionEvent event) {
    	handleCmbNameFYLGeneric(cmbNameR4, txtTimeR4);
    }
    
    @FXML
    void handleCmbNameFYLR5(ActionEvent event) {
    	handleCmbNameFYLGeneric(cmbNameR5, txtTimeR5);
    }
    
    private void handleCmbNameFYLGeneric(ComboBox<Race> cmbName, TextField txtTime) {
    	txtTime.clear();
    	if(cmbName.getValue()!=null)
    		txtTime.setDisable(false);
    	else
    		txtTime.setDisable(true);
    }

    @FXML
    void initialize() {
    	assert btnGare != null : "fx:id=\"btnGare\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnKm != null : "fx:id=\"btnKm\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbContinents != null : "fx:id=\"ckCmbContinents\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbMonths != null : "fx:id=\"ckCmbMonths\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbNations != null : "fx:id=\"ckCmbNations\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR1 != null : "fx:id=\"cmbCatR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR2 != null : "fx:id=\"cmbCatR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR3 != null : "fx:id=\"cmbCatR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR4 != null : "fx:id=\"cmbCatR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR5 != null : "fx:id=\"cmbCatR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCategory != null : "fx:id=\"cmbCategory\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbFavRace != null : "fx:id=\"cmbFavRace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLevel != null : "fx:id=\"cmbLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR1 != null : "fx:id=\"cmbNameR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR2 != null : "fx:id=\"cmbNameR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR3 != null : "fx:id=\"cmbNameR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR4 != null : "fx:id=\"cmbNameR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR5 != null : "fx:id=\"cmbNameR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR1 != null : "fx:id=\"cmbNationR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR2 != null : "fx:id=\"cmbNationR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR3 != null : "fx:id=\"cmbNationR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR4 != null : "fx:id=\"cmbNationR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR5 != null : "fx:id=\"cmbNationR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYear != null : "fx:id=\"cmbYear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR1 != null : "fx:id=\"cmbYearR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR2 != null : "fx:id=\"cmbYearR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR3 != null : "fx:id=\"cmbYearR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR4 != null : "fx:id=\"cmbYearR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR5 != null : "fx:id=\"cmbYearR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colCategory != null : "fx:id=\"colCategory\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colElevGain != null : "fx:id=\"colElevGain\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colKm != null : "fx:id=\"colKm\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colPlace != null : "fx:id=\"colPlace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblInstructions != null : "fx:id=\"lblInstructions\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblLevel != null : "fx:id=\"lblLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblWarnings != null : "fx:id=\"lblWarnings\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabLevel != null : "fx:id=\"tabLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabMain != null : "fx:id=\"tabMain\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblRaces != null : "fx:id=\"tblRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtKmMaxRace != null : "fx:id=\"txtKmMaxRace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNMaxRaces != null : "fx:id=\"txtNMaxRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR1 != null : "fx:id=\"txtTimeR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR2 != null : "fx:id=\"txtTimeR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR3 != null : "fx:id=\"txtTimeR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR4 != null : "fx:id=\"txtTimeR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR5 != null : "fx:id=\"txtTimeR5\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.txtTimeMap = new HashMap<>();
        this.txtTimeMap.put(txtTimeR1, cmbNameR1);
        this.txtTimeMap.put(txtTimeR2, cmbNameR2);
        this.txtTimeMap.put(txtTimeR3, cmbNameR3);
        this.txtTimeMap.put(txtTimeR4, cmbNameR4);
        this.txtTimeMap.put(txtTimeR5, cmbNameR5);
        
        txtResult.setStyle("-fx-font-family: monospace");
        
        lblInstructions.setText("Qui puoi calcolare il tuo livello di abilità sulla base dei risultati che hai ottenuto"
        		+ " nelle tue gare. Inserisci i dati di almeno 2 gare (anno, categoria gara , nazione,"
        		+ " nome gara, tempo ottenuto) nelle righe disponibili. Puoi inserire fino a 5 gare."
        		+ " Dopo aver compilato, premi \"Scopri il tuo livello\" per calcolare il tuo livello di abilità."
        		+ " Una volta ottenuto il livello, premi \"Salva\" per confermare e tornare alla tab Principale.");
        
        colDate.setCellValueFactory(new PropertyValueFactory<Race, LocalDate>("date"));
        colPlace.setCellValueFactory(new PropertyValueFactory<Race, String>("rawLocation"));
        colName.setCellValueFactory(new PropertyValueFactory<Race, String>("raceTitle"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Race, String>("raceCategory"));
        colKm.setCellValueFactory(new PropertyValueFactory<Race, Float>("distance"));
        colElevGain.setCellValueFactory(new PropertyValueFactory<Race, Integer>("elevationGain"));
        
        List<String> livelliAbilita = new ArrayList<>();
        livelliAbilita.addAll(Arrays.asList("Principiante", "Intermedio", "Esperto"));
        cmbLevel.getItems().addAll(livelliAbilita);
        
        List<String> categorie = new ArrayList<>();
        categorie.addAll(Arrays.asList("20K", "50K", "100K", "100M"));
        cmbCategory.getItems().addAll(categorie);
        cmbCatR1.getItems().addAll(categorie);
        cmbCatR2.getItems().addAll(categorie);
        cmbCatR3.getItems().addAll(categorie);
        cmbCatR4.getItems().addAll(categorie);
        cmbCatR5.getItems().addAll(categorie);
        
        // monitora le modifiche negli elementi selezionati dell'oggetto ckCmbContinents
        ckCmbContinents.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                handleCmbAction(null);
                handleCkCmbContinents();
            }
        });
        
        // monitora le modifiche negli elementi selezionati dell'oggetto ckCmbNations
        ckCmbNations.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                handleCmbAction(null);
            }
        });
        
        // monitora le modifiche negli elementi selezionati dell'oggetto ckCmbMonths
        ckCmbMonths.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                handleCmbAction(null);
            }
        });

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	cmbYear.getItems().addAll(model.getYears());
    	cmbYearR1.getItems().addAll(model.getYears());
    	cmbYearR2.getItems().addAll(model.getYears());
    	cmbYearR3.getItems().addAll(model.getYears());
    	cmbYearR4.getItems().addAll(model.getYears());
    	cmbYearR5.getItems().addAll(model.getYears());
    	ckCmbContinents.getItems().addAll(model.getContinents());
    	ckCmbNations.getItems().addAll(model.getCountries());
    	cmbNationR1.getItems().addAll(model.getCountries());
    	cmbNationR2.getItems().addAll(model.getCountries());
    	cmbNationR3.getItems().addAll(model.getCountries());
    	cmbNationR4.getItems().addAll(model.getCountries());
    	cmbNationR5.getItems().addAll(model.getCountries());
    	ckCmbMonths.getItems().addAll(model.getMesi());
    }

}
