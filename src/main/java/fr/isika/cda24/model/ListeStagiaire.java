package fr.isika.cda24.model;

import java.util.ArrayList;

public class ListeStagiaire {
	
	private ArrayList<Stagiaire> listeStagiaire;

	public ListeStagiaire() {
		this.listeStagiaire = new ArrayList<>();
	}
	
	public void ajouterStagiaire (Stagiaire stagiaire) {
		listeStagiaire.add(stagiaire);
	}
	
	public void afficherStagiaire() {
		for (Stagiaire stagiaire : listeStagiaire) {
			System.out.println(stagiaire.toString());
		}
	}
	
	
	
}
