package fr.isika.cda24.projetIsikaUn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
	
	public GridPaneTableauStagiaires gpTableauStagiaire;
	public ArrayList<Stagiaire>listeStagiaire;
	public ListeStagiaire liste;
	
//	@Override
//	public void init() {
//		listeStagiaire =new ArrayList<Stagiaire>();
//	}
    @Override
    public void start(Stage stage) {
    	
    	liste = new ListeStagiaire();
    	
 
		
		try {
			//ouvre un flux entrant entre le fichier et le programme
			FileReader fr = new FileReader("C:\\Users\\tstho\\Documents\\ISIKA\\10_Projet1\\STAGIAIRES.DON");
			BufferedReader br = new BufferedReader(fr);
			
			String nom;
			String prenom;
			String departement;
			String formation;
			String annee;
			
			br.ready(); //retourne true s'il y a qqch à lire, false sinon 
			//tant que tu as qqch à lire
			while(br.ready()) {
				
					nom = br.readLine();
					prenom = br.readLine();
					departement = br.readLine();
					formation = br.readLine();
					annee =  br.readLine();
					liste.ajouterStagiaire(new Stagiaire (nom, prenom, departement, formation, annee));
					br.readLine();
					
			}
			
			//fermeture du flux
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	liste.afficherStagiaire();

	
		gpTableauStagiaire  = new GridPaneTableauStagiaires(liste);
    	
        Scene scene = new Scene(gpTableauStagiaire, 700,500);
        
    	
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}