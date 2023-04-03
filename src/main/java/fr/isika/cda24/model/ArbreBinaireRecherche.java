package fr.isika.cda24.model;

import java.io.IOException;
import java.io.RandomAccessFile;
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

	// methode pour ajouter un stagiaire à l'arbre
	public void ajoutStagiaire(Stagiaire stagiaire) {
		// on vérifie s'l y a une racine
		if (racine == null) {
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

//	public List<Stagiaire> rechercher(String attribut, String valeur) {
//		List<Stagiaire> resultat = new ArrayList<>();
//		if (racine == null) {
//			System.out.println("Aucun résultat de recherche");
//			return resultat;
//		} else {
//			rechercherNoeud(attribut, valeur, 0, resultat);
//			return resultat;
//		}
//	}
//
//	public List<Stagiaire> rechercherNoeud(String attribut, String valeur, int index, List<Stagiaire> resultat) {
//
//		Noeud noeud = lireUnNoeud(index);
//		Stagiaire stagiaire = noeud.getStagiaire();
//		String valeurAttribut = "";
//		switch (attribut) {
//		case "nom":
//			valeurAttribut = stagiaire.getNom();
//			break;
//		case "prenom":
//			valeurAttribut = stagiaire.getPrenom();
//			break;
//		case "departement":
//			valeurAttribut = stagiaire.getDepartement();
//			break;
//		case "formation":
//			valeurAttribut = stagiaire.getFormation();
//			break;
//		case "annee":
//			valeurAttribut = stagiaire.getAnnee();
//			break;
//		}
//		System.out.println("Je teste" + valeurAttribut);
//		int comparaison = valeur.compareTo(valeurAttribut);
//		if (comparaison == 0) {
//			resultat.add(Noeud.supprimerAsterisques(stagiaire));
//			if (noeud.getDoublon() != -1) {
//				resultat.addAll(rechercherNoeud(attribut, valeur, noeud.getDoublon(), resultat));
//			}
//		} else if (comparaison < 0) {
//			resultat = rechercherNoeud(attribut, valeur, noeud.getGauche(), resultat);
//		} else {
//			resultat = rechercherNoeud(attribut, valeur, noeud.getDroit(), resultat);
//		}
//
//		return resultat;
//	}

	// Recherche (tous les noeud ayant le même nom)
//    public ArrayList<Stagiaire> rechercherTous(String nom) {
//        ArrayList<Stagiaire> resultat = new ArrayList<>();
//        rechercherTous(nom, racine, resultat);
//        return resultat;
//    }
//    
//    
//
//    private void rechercherTous(String nom, Noeud noeud, ArrayList<Stagiaire> resultat) {
//        if (noeud != null) {
//            if (nom.equalsIgnoreCase(noeud.getStagiaire().getNom())) {
//                resultat.add(noeud.getStagiaire());
//            }
//            rechercherTous(nom, noeud.getGauche(), resultat);
//            rechercherTous(nom, noeud.getDroit(), resultat);
//        }
//    }
//    
//    
//    // Recherche Multi-Critere
//    public ArrayList<Stagiaire> rechercherMultiCritere(String nom, String prenom, String departement, String formation, String annee) {
//        ArrayList<Stagiaire> resultat2 = new ArrayList<>();
//        rechercherMultiCritere(nom, prenom, departement, formation, annee, racine, resultat2);
//        return resultat2;
//    }
//
//    private void rechercherMultiCritere(String nom, String prenom, String departement, String formation, String annee, Noeud noeud, ArrayList<Stagiaire> resultat2) {
//        if (noeud != null) {
//            Stagiaire stagiaire = noeud.getStagiaire();
//            boolean verification = true;
//            if (nom != null && !nom.isEmpty() && !stagiaire.getNom().equalsIgnoreCase(nom)) {
//                verification = false;
//            }
//            if (prenom != null && !prenom.isEmpty() && !stagiaire.getPrenom().equalsIgnoreCase(prenom)) {
//                verification = false;
//            }
//            if (departement != null && !departement.isEmpty() && !stagiaire.getDepartement().equalsIgnoreCase(departement)) {
//                verification = false;
//            }
//            if (formation != null && !formation.isEmpty() && !stagiaire.getFormation().equalsIgnoreCase(formation)) {
//                verification = false;
//            }
//            if (annee != null && !annee.isEmpty() && !stagiaire.getAnnee().equalsIgnoreCase(annee)) {
//                verification = false;
//            }
//            if (verification) {
//                resultat2.add(noeud.getStagiaire());
//            }
//            rechercherMultiCritere(nom, prenom, departement, formation, annee, noeud.getGauche(), resultat2);
//            rechercherMultiCritere(nom, prenom, departement, formation, annee, noeud.getDroit(), resultat2);
//        }
//    }
//    
	// Appel de la méthode afficher (infixe) définie dans Noeud
//	public void afficher() {
//		if (racine != null) {
//			racine.afficher();
//		}
//	}

}
