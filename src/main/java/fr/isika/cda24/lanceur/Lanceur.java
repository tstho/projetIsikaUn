package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;

public class Lanceur {

	public static void main(String[] args) {

		ListeStagiaire liste = new ListeStagiaire();
		
//		liste.ajouterStagiaire(new Stagiaire( "Salmon", "Thomas", 35, "CDA 24", 2023));
//		liste.ajouterStagiaire(new Stagiaire( "zerze", "Thomas", 35, "CDA 24", 2023));
//		liste.ajouterStagiaire(new Stagiaire( "Salmon", "zdzdz", 35, "zdz 24", 2023));
//		
//		liste.afficherStagiaire();
		
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
		liste.afficherStagiaire();

	}
	
	public void importFichier () {
		
	}

}
