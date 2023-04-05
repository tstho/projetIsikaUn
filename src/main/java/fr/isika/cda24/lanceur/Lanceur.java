package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Noeud;
import fr.isika.cda24.model.Stagiaire;

public class Lanceur {

	public static void main(String[] args) {

		ListeStagiaire liste = new ListeStagiaire();

		// charger les stagiaires depuis le fichier
		liste = liste.chargerStagiairesDepuisFichier("src/fichiers/DOUZESTAGIAIRES.DON");
		

		// écriture du fichier binaire
		ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
		int index = 0;
		for (Stagiaire stagiaire : liste.getListeStagiaire()) {
			abr.ajoutStagiaire(stagiaire);
			index++;
		}
//		ListeStagiaire resultat = abr.rechercher("nom","GARIJO");
//		resultat.afficherStagiaire();
		ArrayList<Noeud> ln = new ArrayList<Noeud>();
		ln = abr.affichageInfixeNoeud();
		for (Noeud noeud : ln) {
			System.out.println(noeud.getStagiaire().getNom() + " " + noeud.getStagiaire().getPrenom() +
					" FG : " + noeud.getGauche() + " FD : " + noeud.getDroit() + " doub : " + noeud.getDoublon());
		}
		System.out.println("---Après suppression -------");
		Stagiaire stag = new Stagiaire ("POTIN","Thomas","75","ATOD 21", "2014");
		Stagiaire stag2 = new Stagiaire ("UNG","Jet-Ming","75","ATOD 16 CP", "2012");
		Stagiaire stag3 = new Stagiaire ("GARIJO","Rosie","75","AI 79", "2011");
		Stagiaire stag4 = new Stagiaire ("AKHIAD","Brahim","92","AI 60", "2003");
		Stagiaire stag5 = new Stagiaire ("LACROIX","Pascale","91","BOBI 5", "2008");
		Stagiaire stag6 = new Stagiaire ("CHAVENEAU","Kim Anh","92","ATOD 22", "2014");
		abr.supprimerStagiaire(stag5);
		ln = abr.affichageInfixeNoeud();
		for (Noeud noeud : ln) {
			System.out.println(noeud.getStagiaire().getNom()+ " " + noeud.getStagiaire().getPrenom() + " FG : " + noeud.getGauche() + " FD : " + noeud.getDroit() + " doub : " + noeud.getDoublon());
		}

	}
}