package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Noeud;
import fr.isika.cda24.model.Stagiaire;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Lanceur {

	public static void main(String[] args) {

		ListeStagiaire liste = new ListeStagiaire();

		// charger les stagiaires depuis le fichier
		liste = liste.chargerStagiairesDepuisFichier("src/fichiers/DOUZESTAGIAIRES.DON");

		// écriture du fichier binaire
		ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
		abr.ajoutListeStagiaire(liste);

//		= abr.rechercher("nom","GARIJO");
//		liste.afficherStagiaire();

		ListeStagiaire liste2 = new ListeStagiaire();
		abr.affichageInfixe(liste2);

		System.out.println("---Après tri -------");
		liste2.afficherStagiaire();

		Stagiaire stag = new Stagiaire("POTIN", "Thomas", "75", "ATOD 21", "2014");
		Stagiaire stag2 = new Stagiaire("UNG", "Jet-Ming", "75", "ATOD 16 CP", "2012");
		Stagiaire stag3 = new Stagiaire("GARIJO", "Rosie", "75", "AI 79", "2011");
		Stagiaire stag4 = new Stagiaire("AKHIAD", "Brahim", "92", "AI 60", "2003");
		Stagiaire stag5 = new Stagiaire("LACROIX", "Pascale", "91", "BOBI 5", "2008");
		Stagiaire stag6 = new Stagiaire("CHAVENEAU", "Kim Anh", "92", "ATOD 22", "2014");
		Stagiaire stag7 = new Stagiaire("GARIJO", "Albert", "76", "CDA 40", "2016");
		Stagiaire stag8 = new Stagiaire("GARIJO", "Rosie", "89", "CDA 67", "2016");

	//	abr.supprimerStagiaire(stag8);

		Stagiaire stagBis = new Stagiaire("POTIN", "Toto", "67", "AI 54", "2222");
		Stagiaire stag5Bis = new Stagiaire("PACROIX", "Pascale", "91", "BOBI 5", "2111");
		Stagiaire stag3bis = new Stagiaire("GARIJO", "Brendan", "75", "AI 79", "2222");
		Stagiaire stag3ter = new Stagiaire("BARIJO", "Brendan", "75", "AI 79", "2222");
		Stagiaire stag7bis = new Stagiaire("BARIJO", "Bebert", "53", "BOBI 5", "1839");

		abr.ajoutStagiaire(stag3ter);

		abr.modifierStagiaire(stag3ter, stag7bis);
		System.out.println("---Après modification -------");

		// Création de la liste de stagiaire et de l'arbre
		ListeStagiaire listeModif = new ListeStagiaire();
		ArbreBinaireRecherche arbreModif = new ArbreBinaireRecherche();
		// Si le fichier binaire existe déjà, on l'utilise pour remplir l'abre et la
		// liste
		File fichierBinaire = new File("src/fichiers/stagiaires.bin");
		if (fichierBinaire.isFile()) {
			arbreModif.initialiserArbreFichier();
			listeModif = arbreModif.affichageInfixe(listeModif);
		}
		listeModif.afficherStagiaire();
		

	}
}