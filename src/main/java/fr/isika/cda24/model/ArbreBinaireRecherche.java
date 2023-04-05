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
/**
 * Cette classe représente un arbre binaire de recherche, utilisé pour stocker et rechercher des stagiaires.
 * 
 */
public class ArbreBinaireRecherche {
	/** La racine de l'arbre. */
	private Noeud racine;
	
	/**
	 * Constructeur par défaut.
	 */
	public ArbreBinaireRecherche() {
		this.racine = null;
	}
	/**
	 * Retourne la racine de l'arbre.
	 *
	 * @return la racine de l'arbre.
	 */
	public Noeud getRacine() {
		return racine;
	}
	/**
	 * Modifie la racine de l'arbre.
	 *
	 * @param racine la nouvelle racine de l'arbre.
	 */
	public void setRacine(Noeud racine) {
		this.racine = racine;
	}

	/**
	 * Supprime le fichier binaire s'il existe déjà.
	 */
	public void supprimerFichier() {
		String file_name = "src/fichiers/stagiaires.bin";
		Path path = Paths.get(file_name);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ajoute un stagiaire à l'arbre.
	 *
	 * @param stagiaire le stagiaire à ajouter.
	 */
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
	/**
	 * Lit un noeud à l'indice spécifié dans l'arbre.
	 *
	 * @param index l'indice du noeud à lire.
	 * @return le noeud lu, ou null si l'arbre est vide.
	 */
	public Noeud lireUnNoeud(int index) {
		// on vérifie s'l y a une racine
		if (racine == null) {
			System.out.println("l'arbre est vide");
			return null;
		} else {
			return racine.lireUnNoeud(index);
		}
	}

	/**
	 * Affiche les stagiaires de l'arbre selon un parcours infixe.
	 *
	 * @param resultat la liste de stagiaires dans laquelle les stagiaires seront
	 *                 ajoutés.
	 * @return la liste de stagiaires lu dans l'arbre de manière infixe, ou une
	 *         liste vide si l'arbre est vide.
	 */
	public ListeStagiaire affichageInfixe(ListeStagiaire resultat) {
		if (racine != null) {
			this.racine.affichageInfixeNoeud(0, resultat);
		} else {
			System.out.println("Arbre vide");
		}
		return resultat;
	}

	// METHODE TEST AFFICHAGE NOEUD
	public ArrayList<Noeud> affichageInfixeNoeud() {
		ArrayList<Noeud> resultat = new ArrayList<Noeud>();
		if (racine != null) {
			this.racine.affichageInfixeNoeudTest(0, resultat);
		} else {
			System.out.println("Arbre vide");
		}
		return resultat;
	}
	/**
	 * Recherche tous les noeuds ayant une valeur spécifique pour un attribut donné.
	 *
	 * @param attribut l'attribut à chercher.
	 * @param valeur   la valeur de l'attribut à chercher.
	 * @return la liste contenant les stagiaires trouvés, ou une liste vide si
	 *         l'arbre est vide.
	 */
	public ListeStagiaire rechercher(String attribut, String valeur) {
		ListeStagiaire resultat = new ListeStagiaire();
		if (racine == null) {
			return resultat;
		} else {
			resultat = racine.rechercherNoeud(attribut, valeur, 0, resultat);
		}
		return resultat;
	}
	/**
	 * Recherche les noeuds qui correspondent à plusieurs critères.
	 *
	 * @param nom         le nom à chercher.
	 * @param prenom      le prénom à chercher.
	 * @param departement le département à chercher.
	 * @param formation   la formation à chercher.
	 * @param annee       l'année à chercher.
	 * @return la liste contenant les stagiaires trouvés, ou une liste vide si
	 *         l'arbre est vide.
	 */
	public ListeStagiaire rechercherMultiCritere(String nom, String prenom, String departement, String formation,
			String annee) {
		ListeStagiaire resultatMulti = new ListeStagiaire();
		// ListeStagiaire resultatMulti = new ListeStagiaire();
		if (racine == null) {
			return resultatMulti;

		}
		resultatMulti = racine.rechercherMultiCritere(nom, prenom, departement, formation, annee, 0, resultatMulti);
		if (resultatMulti == null)
			resultatMulti = new ListeStagiaire();

		return resultatMulti;
	}
/**
 * Supprime un stagiaire de l'arbre
 * 
 * @param stagiaire
 */
	public void supprimerStagiaire(Stagiaire stagiaire) {
		int indexASupprimer = racine.rechercherIndexNoeud(stagiaire, 0);

		if (stagiaire == null) {
			throw new NullPointerException();
		}

		if (racine == null) {
			System.out.println("l'arbre est vide");
		} else {
			if (indexASupprimer == -1) {
				System.out.println("Le Stagiaire que vous essayiez de supprimer n'existe pas");
			} else {
				racine.supprimerNoeud(indexASupprimer);
			}
		}
	}
/**
 * methode pour vider l'arbre avant d'importer
 */
	//Existe déjà avec setRacine(null) ??
	public void vider() {
		racine = null;
	}

}
