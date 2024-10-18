package it.polito.tdp.RacePlanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXMLGuideController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblGuide;

    @FXML
    void initialize() {
        assert lblGuide != null : "fx:id=\"lblGuide\" was not injected: check your FXML file 'Guide.fxml'.";
        
        lblGuide.setText("L'applicazione ti permette di pianificare un calendario personalizzato di gare di"
        		+ " Trail Running in base alle tue preferenze e obiettivi. Utilizza i filtri e le opzioni disponibili"
        		+ " per creare la tua pianificazione ottimale. Puoi anche calcolare il tuo livello di abilità"
        		+ " aprendo la scheda \"Scopri il tuo livello\" e seguendo le istruzioni.\r\n"
        		+ "Dopo aver selezionato i filtri, puoi scegliere quale obiettivo vuoi perseguire nella tua pianificazione: \r\n"
        		+ "• Massimizzare il numero di gare\r\n"
        		+ "• Massimizzare i km percorsi\r\n"
        		+ "• Massimizzare il numero di nazioni visitate\r\n"
        		+ "Scopri quali sfide ti aspettano!");

    }

}
