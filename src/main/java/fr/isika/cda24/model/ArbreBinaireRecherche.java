package fr.isika.cda24.model;

import java.util.ArrayList;
import java.util.List;

public class ArbreBinaireRecherche {
    private Noeud racine;

    public ArbreBinaireRecherche() {
        this.racine = null;
    }

    public void inserer(Stagiaire stagiaire) {
        Noeud nouveauNoeud = new Noeud(stagiaire);
        if (racine == null) {
            racine = nouveauNoeud;
        } else {
            racine.inserer(nouveauNoeud);
        }
    }

//    public Stagiaire rechercher(String nom) {
//        if (racine == null) {
//            return null;
//        } else {
//            return racine.rechercher(nom);
//        }
//    }
    
    public ArrayList<Stagiaire> rechercherTous(String nom) {
        ArrayList<Stagiaire> resultat = new ArrayList<>();
        rechercherTous(nom, racine, resultat);
        return resultat;
    }
    
    private void rechercherTous(String nom, Noeud noeud, ArrayList<Stagiaire> resultat) {
        if (noeud != null) {
            if (nom.equals(noeud.getStagiaire().getNom())) {
                resultat.add(noeud.getStagiaire());
            }
            rechercherTous(nom, noeud.getGauche(), resultat);
            rechercherTous(nom, noeud.getDroit(), resultat);
        }
    }

    public void afficher() {
        if (racine != null) {
            racine.afficher();
        }
    }

	public Noeud getRacine() {
		return racine;
	}

	public void setRacine(Noeud racine) {
		this.racine = racine;
	}
    
    
    
}


