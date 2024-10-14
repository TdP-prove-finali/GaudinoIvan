package it.polito.tdp.RacePlanner;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.controlsfx.control.CheckComboBox;

import it.polito.tdp.RacePlanner.model.Model;
import it.polito.tdp.RacePlanner.model.Race;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<Race, Double> colKm;

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

    }

    @FXML
    void doMaximizeKm(ActionEvent event) {
    	txtResult.clear();
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat = cmbCategory.getValue();
    		List<String> continenti = ckCmbContinents.getCheckModel().getCheckedItems();
        	List<String> nazioni = ckCmbNations.getCheckModel().getCheckedItems();
        	List<String> mesiNo = ckCmbMonths.getCheckModel().getCheckedItems();
        	Race favRace = cmbFavRace.getValue();
        	
        	int maxGare;
        	try {
        		maxGare = Integer.parseInt(txtNMaxRaces.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido");
        		return;
        	}
        	
        	double maxKm;
        	try {
        		maxKm = Double.parseDouble(txtKmMaxRace.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido");
        		return;
        	}
        	
    		List<Race> racePlan = model.massimizza(lvl, favCat, anno, continenti, nazioni, mesiNo, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    		} else {
    			lblWarnings.setText("ERRORE NELLA RICORSIONE"); //DA MODIFICARE
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: seleziona almeno il livello di abilità e l'anno per proseguire");
    	}
    }

    @FXML
    void doMaximizeNations(ActionEvent event) {
    	txtResult.clear();
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat = cmbCategory.getValue();
    		List<String> continenti = ckCmbContinents.getCheckModel().getCheckedItems();
        	List<String> nazioni = ckCmbNations.getCheckModel().getCheckedItems();
        	List<String> mesiNo = ckCmbMonths.getCheckModel().getCheckedItems();
        	Race favRace = cmbFavRace.getValue();
        	
        	int maxGare;
        	try {
        		maxGare = Integer.parseInt(txtNMaxRaces.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido");
        		return;
        	}
        	
        	double maxKm;
        	try {
        		maxKm = Double.parseDouble(txtKmMaxRace.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido");
        		return;
        	}
        	
    		List<Race> racePlan = model.massimizza(lvl, favCat, anno, continenti, nazioni, mesiNo, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    		} else {
    			lblWarnings.setText("ERRORE NELLA RICORSIONE"); //DA MODIFICARE
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: seleziona almeno il livello di abilità e l'anno per proseguire");
    	}
    }

    @FXML
    void doMaximizeRaces(ActionEvent event) {
    	txtResult.clear();
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue();
    	
    	if(lvl!=null && anno!=null) {
    		String favCat = cmbCategory.getValue();
    		List<String> continenti = ckCmbContinents.getCheckModel().getCheckedItems();
        	List<String> nazioni = ckCmbNations.getCheckModel().getCheckedItems();
        	List<String> mesiNo = ckCmbMonths.getCheckModel().getCheckedItems();
        	Race favRace = cmbFavRace.getValue();
        	
        	int maxGare;
        	try {
        		maxGare = Integer.parseInt(txtNMaxRaces.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Numero massimo di gare\" non valido");
        		return;
        	}
        	
        	double maxKm;
        	try {
        		maxKm = Double.parseDouble(txtKmMaxRace.getText());
        	} catch(NumberFormatException e) {
        		lblWarnings.setText("Attenzione: campo \"Km massimi per gara\" non valido");
        		return;
        	}
        	
    		List<Race> racePlan = model.massimizza(lvl, favCat, anno, continenti, nazioni, mesiNo, favRace, maxGare, maxKm);
    		if(racePlan!=null && !racePlan.isEmpty()) {
    			tblRaces.setItems(FXCollections.observableArrayList(racePlan));
    		} else {
    			lblWarnings.setText("ERRORE NELLA RICORSIONE"); //DA MODIFICARE
    		}
    		
    	} else {
    		lblWarnings.setText("Attenzione: seleziona almeno il livello di abilità e l'anno per proseguire");
    	}
    }

    @FXML
    void doOpenGuide(ActionEvent event) {

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
    	//resetto anche la table e txtResult?
    }

    @FXML
    void doSave(ActionEvent event) {

    }
    
    // gestisco l'azione di modifica selezione su cmbLevel
    @FXML
    void handleCmbLevel(ActionEvent event) {
    	String livello = cmbLevel.getValue();
    	if(livello!=null) {
    		/*switch(livello) {
        	case "Principiante":
        		cmbCategory.getItems().clear();
        		cmbCategory.getItems().addAll(Arrays.asList("20K","50K"));
        		break;
        		
        	case "Intermedio":
        		cmbCategory.getItems().clear();
        		cmbCategory.getItems().addAll(Arrays.asList("20K","50K","100K"));
        		break;
        		
        	case "Esperto":
        		cmbCategory.getItems().clear();
        		cmbCategory.getItems().addAll(Arrays.asList("20K","50K","100K","100M"));
        		break;
        	}*/
    		
    		cmbCategory.getItems().clear();
    		cmbCategory.getItems().addAll(model.getAtleta(livello).getCategorieValide());
    		this.handleCmbAction(event);
    	}
    }
    
    // gestisco cmbFavRace in base ai valori selezionati
    // forse per cmbCategory non serve monitorare questo evento, CONTROLLARE
    @FXML
    void handleCmbAction(ActionEvent event) {
    	cmbFavRace.getItems().clear();
    	String lvl = cmbLevel.getValue();
    	Integer anno = cmbYear.getValue(); 
    	List<String> continenti = ckCmbContinents.getCheckModel().getCheckedItems();
    	List<String> nazioni = ckCmbNations.getCheckModel().getCheckedItems();
    	List<String> mesiNo = ckCmbMonths.getCheckModel().getCheckedItems();
    	// le condizioni sulle CheckComboBox risolvono un bug della libreria ControlsFX
    	if(lvl!=null && anno!=null && !continenti.contains("null") 
    			&& !nazioni.contains("null") && !mesiNo.contains("null")) {
    		cmbFavRace.setDisable(false);
    		String favCat = cmbCategory.getValue();
    		model.getRaces(lvl, anno, continenti, nazioni);
    		List<Race> filteredRaces = model.getFilteredRaces(lvl, favCat, anno, continenti, nazioni, mesiNo);
    		cmbFavRace.getItems().addAll(filteredRaces);
    		
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
    			//ckCmbNations.getItems().addAll(model.getCountriesByContinent(continente));
    			nazioniDiContinente.addAll(model.getCountriesByContinent(continente));
    			// continente qui è il nome, ho modificato la query nel DAO (OK)
    			// conviene ordinare in ordine alfabetico, l'ho fatto qui (OK)
        	}
    		Collections.sort(nazioniDiContinente);
    		ckCmbNations.getItems().addAll(nazioniDiContinente);
    		
    	} else {
    		ckCmbNations.getItems().addAll(model.getCountries());
    	}
    }

    @FXML
    void initialize() {
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
        assert tblRaces != null : "fx:id=\"tblRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtKmMaxRace != null : "fx:id=\"txtKmMaxRace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNMaxRaces != null : "fx:id=\"txtNMaxRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR1 != null : "fx:id=\"txtTimeR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR2 != null : "fx:id=\"txtTimeR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR3 != null : "fx:id=\"txtTimeR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR4 != null : "fx:id=\"txtTimeR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR5 != null : "fx:id=\"txtTimeR5\" was not injected: check your FXML file 'Scene.fxml'.";
        
        colDate.setCellValueFactory(new PropertyValueFactory<Race, LocalDate>("date"));
        colPlace.setCellValueFactory(new PropertyValueFactory<Race, String>("rawLocation"));
        colName.setCellValueFactory(new PropertyValueFactory<Race, String>("raceTitle"));
        colCategory.setCellValueFactory(new PropertyValueFactory<Race, String>("raceCategory"));
        colKm.setCellValueFactory(new PropertyValueFactory<Race, Double>("distance"));
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
        
//        List<String> mesi = new ArrayList<>();
//        mesi.addAll(Arrays.asList("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
//        		"Settembre", "Ottobre", "Novembre", "Dicembre"));
//        ckCmbMonths.getItems().addAll(mesi);
        
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
