package fr.isika.cda24.front;


import javafx.scene.image.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * JavaFX App
 */
public class App extends Application {
	private static Scene sceneAuthentification;
	private static Stage stage;
	private ListeStagiaire liste = new ListeStagiaire();
	private ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
	
	public void init() {
		//Si le fichier binaire existe déjà, on l'utilise pour remplir l'abre et la liste
		File fichierBinaire = new File("src/fichiers/stagiaires.bin");
		if(fichierBinaire.isFile())
		{ 
			abr.initialiserArbreFichier();
			liste = abr.affichageInfixe(liste);
			
		}
	}
	
    @Override
    public void start(Stage stageMain) throws FileNotFoundException {
    	
    	stage = stageMain;
    	
    	FenetreAuthentification fenetreAuthentification = new FenetreAuthentification(liste, abr);
    	
    	sceneAuthentification = new Scene (fenetreAuthentification, 450, 600);
    	
    	stage.setTitle(" Annuaire de Stagiaires");
    	Image icone = new Image(new FileInputStream("src/image/icone.png"));
    	stage.getIcons().add(icone);
        stage.setScene(sceneAuthentification);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    
	public static Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public static Scene getSceneAuthentification() {
		return sceneAuthentification;
	}

	public void setSceneAuthentification(Scene sceneAuthentification) {
		this.sceneAuthentification = sceneAuthentification;
	}  
    
}