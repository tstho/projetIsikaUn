package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;

public class Lanceur {

	public static void main(String[] args) {

		ListeStagiaire liste = new ListeStagiaire();

		// charger les stagiaires depuis le fichier
		try {
			FileReader fr = new FileReader("C:\\Users\\tstho\\Documents\\ISIKA\\10_Projet1\\DOUZESTAGIAIRES.DON");
			BufferedReader br = new BufferedReader(fr);

			String nom;
			String prenom;
			String departement;
			String formation;
			String annee;

			br.ready();
			while (br.ready()) {

				nom = br.readLine();
				prenom = br.readLine();
				departement = br.readLine();
				formation = br.readLine();
				annee = br.readLine();
				liste.ajouterStagiaire(new Stagiaire(nom, prenom, departement, formation, annee));
				br.readLine();

			}

			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// écriture du fichier binaire
		ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
		int index = 0;
		for (Stagiaire stagiaire : liste.getListeStagiaire()) {
			abr.ajoutStagiaire(stagiaire);
			index++;
		}
//		
//		ListeStagiaire resultat = abr.rechercher("nom","GARIJO");
//		resultat.afficherStagiaire();
		
		Stagiaire stag = new Stagiaire ("POTIN","Thomas","75","ATOD 21", "2014");
		abr.supprimerStagiaire(stag);
		
		

	}
}