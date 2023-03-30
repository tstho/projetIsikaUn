package fr.isika.cda24.model;

import java.util.ArrayList;

public class ListeStagiaire {
	//attributs
	private ArrayList<Stagiaire> listeStagiaire;
	//constructeur
	public ListeStagiaire() {
		this.listeStagiaire = new ArrayList<>();
	}
	//méthode pour ajouter un stagiaire
	public void ajouterStagiaire(Stagiaire stagiaire) {
		listeStagiaire.add(stagiaire);
	}
	//méthode pour afficher les stagiaires de la liste
	public void afficherStagiaire() {
		for (Stagiaire stagiaire : listeStagiaire) {
			System.out.println(stagiaire.toString());
		}
	}
	//getters & setters
	public ArrayList<Stagiaire> getListeStagiaire() {
		return listeStagiaire;
	}

	public void setListeStagiaire(ArrayList<Stagiaire> listeStagiaire) {
		this.listeStagiaire = listeStagiaire;
	}

}
