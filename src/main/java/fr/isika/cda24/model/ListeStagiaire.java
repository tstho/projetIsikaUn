package fr.isika.cda24.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Cette classe permet de crée une liste de stagiaires.
 *
 */
public class ListeStagiaire {
	/**
	 * Attributs 
	 */
	private ArrayList<Stagiaire> listeStagiaire;
	/**
	 * Constructeur : crée une nouvelle liste de stagiaires vide.
	 */
	public ListeStagiaire() {
		this.listeStagiaire = new ArrayList<>();
	}
	/**
	 * Ajoute un stagiaire à la liste.
	 *
	 * @param stagiaire le stagiaire à ajouter.
	 */
	public void ajouterStagiaire(Stagiaire stagiaire) {
		listeStagiaire.add(stagiaire);
	}
	/**
	 * Affiche tous les stagiaires de la liste.
	 */
	public void afficherStagiaire() {
		for (Stagiaire stagiaire : listeStagiaire) {
			System.out.println(stagiaire.toString());
		}
	}
	/**
	 * Getter de la liste de stagiaire sous forme d'ArrayList
	 * @return 		ArrayList<Stagiaire>
	 */
	public ArrayList<Stagiaire> getListeStagiaire() {
		return listeStagiaire;
	}
/**
 * Setter d'une ArrayList<Stagiaire> en tant qu'argument
 * 
 * @param listeStagiaire	la liste de Stagiaire sous forme d'ArrayList
 */
	public void setListeStagiaire(ArrayList<Stagiaire> listeStagiaire) {
		this.listeStagiaire = listeStagiaire;
	}
	
	/**
	 * Charge une liste de stagiaires depuis un fichier texte.
	 *
	 * @param cheminFichier le chemin d'accès au fichier texte contenant la liste des stagiaires.
	 * @return une liste contenant les stagiaires chargés depuis le fichier.
	 */
	 public ListeStagiaire chargerStagiairesDepuisFichier(String cheminFichier) {
	    	
	       ListeStagiaire stagiaires = new ListeStagiaire();

	        try {
	            FileReader fr = new FileReader(cheminFichier);
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
	                stagiaires.ajouterStagiaire(new Stagiaire(nom, prenom, departement, formation, annee));
	                br.readLine();
	            }

	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return stagiaires;
	    }

}
