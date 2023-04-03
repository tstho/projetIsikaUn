package fr.isika.cda24.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArbreBinaireRecherche {
	// attributs
	private Noeud racine;

	// constructeur
	public ArbreBinaireRecherche() {
		this.racine = null;
	}

	// Getters et Setters
	public Noeud getRacine() {
		return racine;
	}

	public void setRacine(Noeud racine) {
		this.racine = racine;
	}

	// méthode pour supprimer le fichier binaire s'il existe déjà
	public void supprimerFichier() {
		String file_name = "src/fichiers/stagiaires.bin";
		Path path = Paths.get(file_name);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// methode pour ajouter un stagiaire à l'arbre
	public void ajoutStagiaire(Stagiaire stagiaire) {
		// on vérifie s'l y a une racine
		if (racine == null) {
			// on commence par supprimer le fichier s'il existe
			supprimerFichier();
			// s'il n'y en a pas on créé le premier noeud et on l'affecte en racine de
			// l'arbre
			racine = new Noeud(stagiaire);
			// on écrit le stagiaire dans le fichier
			racine.ecritureStagiaire(stagiaire);
		} else {
			// s'il y a déjà une racine, on insert le nouveau stagiaire dans l'arbre

			this.racine.inserer(stagiaire, 0);
		}
	}

	// methode pour lire un stagiaire dans l'arbre
	public Noeud lireUnNoeud(int index) {
		// on vérifie s'l y a une racine
		if (racine == null) {
			System.out.println("l'arbre est vide");
			return null;
		} else {
			return racine.lireUnNoeud(index);
		}
	}

	// appel de la méthode affichage infixe
	public ListeStagiaire affichageInfixe(ListeStagiaire resultat) {
		if (racine != null) {
			this.racine.affichageInfixeNoeud(0, resultat);
		} else {
			System.out.println("Arbre vide");
		}
		return resultat;
	}

	// Recherche (tous les noeud ayant le même nom)
	public ListeStagiaire rechercher(String attribut, String valeur) {
		ListeStagiaire resultat = new ListeStagiaire();
		if (racine == null) {
			return resultat;
		} else {
			resultat = racine.rechercherNoeud(attribut, valeur, 0, resultat);
		}
		return resultat;
	}
	//Methode pour supprimer un stagiaire
	public void supprimerStagiaire(Stagiaire stagiaire) {
		
		if(stagiaire == null) {
			throw new NullPointerException();
		}
		
		if (racine == null) {
			System.out.println("l'arbre est vide");
		} else {
			int index = racine.rechercherIndexNoeud(stagiaire, 0);
			if(index==-1) {
				System.out.println("Le Stagiaire que vous essayiez de supprimer n'existe pas");
			}else {
				System.out.println(index);
			}
		}
	}

}
