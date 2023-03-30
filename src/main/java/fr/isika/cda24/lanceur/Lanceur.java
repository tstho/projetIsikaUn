package fr.isika.cda24.lanceur;

import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;

public class Lanceur {

	public static void main(String[] args) {
		
		ListeStagiaire liste = new ListeStagiaire();
		
		liste.ajouterStagiaire(new Stagiaire("CDA", 24, 2023, "Salmon", "Thomas", 35));
		liste.ajouterStagiaire(new Stagiaire("CDA", 24, 2023, "Jean-François", "Magdaline", 28));
		liste.ajouterStagiaire(new Stagiaire("AL", 8, 2024, "Simon", "Brendan", 66));
		
		liste.afficherStagiaire();

	}

}
