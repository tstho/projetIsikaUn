package fr.isika.cda24.model;

import java.util.ArrayList;
import java.util.List;

public class ArbreBinaireRecherche {
    private Noeud racine;

    public ArbreBinaireRecherche() {
        this.racine = null;
    }

    // Appel de la méthode  pour insérer un noeud
    public void inserer(Stagiaire stagiaire) {
        Noeud nouveauNoeud = new Noeud(stagiaire);
        if (racine == null) {
            racine = nouveauNoeud;
        } else {
            racine.inserer(nouveauNoeud);
        }
    }

    
    
    // Recherche (tous les noeud ayant le même nom)
    public ArrayList<Stagiaire> rechercherTous(String nom) {
        ArrayList<Stagiaire> resultat = new ArrayList<>();
        rechercherTous(nom, racine, resultat);
        return resultat;
    }
    
    

    private void rechercherTous(String nom, Noeud noeud, ArrayList<Stagiaire> resultat) {
        if (noeud != null) {
            if (nom.equalsIgnoreCase(noeud.getStagiaire().getNom())) {
                resultat.add(noeud.getStagiaire());
            }
            rechercherTous(nom, noeud.getGauche(), resultat);
            rechercherTous(nom, noeud.getDroit(), resultat);
        }
    }
    
    
    // Recherche Multi-Critere
    public ArrayList<Stagiaire> rechercherMultiCritere(String nom, String prenom, String departement, String formation, String annee) {
        ArrayList<Stagiaire> resultat2 = new ArrayList<>();
        rechercherMultiCritere(nom, prenom, departement, formation, annee, racine, resultat2);
        return resultat2;
    }

    private void rechercherMultiCritere(String nom, String prenom, String departement, String formation, String annee, Noeud noeud, ArrayList<Stagiaire> resultat2) {
        if (noeud != null) {
            Stagiaire stagiaire = noeud.getStagiaire();
            boolean verification = true;
            if (nom != null && !nom.isEmpty() && !stagiaire.getNom().equalsIgnoreCase(nom)) {
                verification = false;
            }
            if (prenom != null && !prenom.isEmpty() && !stagiaire.getPrenom().equalsIgnoreCase(prenom)) {
                verification = false;
            }
            if (departement != null && !departement.isEmpty() && !stagiaire.getDepartement().equalsIgnoreCase(departement)) {
                verification = false;
            }
            if (formation != null && !formation.isEmpty() && !stagiaire.getFormation().equalsIgnoreCase(formation)) {
                verification = false;
            }
            if (annee != null && !annee.isEmpty() && !stagiaire.getAnnee().equalsIgnoreCase(annee)) {
                verification = false;
            }
            if (verification) {
                resultat2.add(noeud.getStagiaire());
            }
            rechercherMultiCritere(nom, prenom, departement, formation, annee, noeud.getGauche(), resultat2);
            rechercherMultiCritere(nom, prenom, departement, formation, annee, noeud.getDroit(), resultat2);
        }
    }
    
    // Appel de la méthode afficher (infixe) définie dans Noeud
    public void afficher() {
        if (racine != null) {
            racine.afficher();
        }
    }
    
    // Getters et Setters
	public Noeud getRacine() {
		return racine;
	}

	public void setRacine(Noeud racine) {
		this.racine = racine;
	}
    
    
    
}


