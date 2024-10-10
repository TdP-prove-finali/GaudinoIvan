package it.polito.tdp.RacePlanner;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

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
import org.controlsfx.control.CheckComboBox;

import it.polito.tdp.RacePlanner.model.Model;

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
    private ComboBox<?> cmbFavRace;

    @FXML
    private ComboBox<String> cmbLevel;

    @FXML
    private ComboBox<?> cmbNameR1;

    @FXML
    private ComboBox<?> cmbNameR2;

    @FXML
    private ComboBox<?> cmbNameR3;

    @FXML
    private ComboBox<?> cmbNameR4;

    @FXML
    private ComboBox<?> cmbNameR5;

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
    private TableColumn<?, String> colCategory;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colElevGain;

    @FXML
    private TableColumn<?, ?> colKm;

    @FXML
    private TableColumn<?, String> colName;

    @FXML
    private TableColumn<?, String> colPlace;

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
    private TableView<?> tblRaces;

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

    }

    @FXML
    void doMaximizeNations(ActionEvent event) {

    }

    @FXML
    void doMaximizeRaces(ActionEvent event) {

    }

    @FXML
    void doOpenGuide(ActionEvent event) {

    }

    @FXML
    void doResetLevel(ActionEvent event) {

    }

    @FXML
    void doResetMain(ActionEvent event) {

    }

    @FXML
    void doSave(ActionEvent event) {

    }
    
    @FXML
    void handleCmbLevel(ActionEvent event) {
    	String livello = cmbLevel.getValue();
    	switch(livello) {
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
    	}
    }
    
    @FXML
    void handleCmbAction(ActionEvent event) {
    	if(cmbLevel.getValue()!=null && cmbCategory.getValue()!=null && cmbYear.getValue()!=null) {
    		if(ckCmbContinents.getCheckModel().getCheckedItems().isEmpty()) {
    			lblWarnings.setText("nessun continente selezionato");
    		} else {
    			lblWarnings.setText("posso aggiungere, con continente");
    		}	
    	} else {
    		lblWarnings.setText("errore devi compilare tutto");
    	}
    }
    
    // gestisco il riempimento di ckCmbNations in base alla selezione su ckCmbContinents
    private void handleCkCmbContinents() {
    	ckCmbNations.getItems().clear();
    	List<String> continentiSelezionati = ckCmbContinents.getCheckModel().getCheckedItems();
    	List<String> nazioniDiContinente = new ArrayList<>();
    	if(!continentiSelezionati.isEmpty()) {
    		for(String continente : continentiSelezionati) {
    			//ckCmbNations.getItems().addAll(model.getCountriesByContinent(continente));
    			nazioniDiContinente.addAll(model.getCountriesByContinent(continente));
    			// continente qui è il nome, ho modificato la query nel DAO (FUNZIONA)
    			// conviene ordinare in ordine alfabetico, l'ho fatto qui (FUNZIONA)
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
        
        List<String> mesi = new ArrayList<>();
        mesi.addAll(Arrays.asList("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto",
        		"Settembre", "Ottobre", "Novembre", "Dicembre"));
        ckCmbMonths.getItems().addAll(mesi);
        
        ckCmbContinents.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                handleCmbAction(null);
                handleCkCmbContinents();
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
    }

}
