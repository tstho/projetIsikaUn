package fr.isika.cda24.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Noeud {
    private Stagiaire stagiaire;
    private Noeud gauche;
    private Noeud droit;

    public Noeud(Stagiaire stagiaire) {
        this.stagiaire = stagiaire;
        this.gauche = null;
        this.droit = null;
    }

    public Stagiaire getStagiaire() {
        return stagiaire;
    }

    public Noeud getGauche() {
        return gauche;
    }

    public void setGauche(Noeud gauche) {
        this.gauche = gauche;
    }

    public Noeud getDroit() {
        return droit;
    }

    public void setDroit(Noeud droit) {
        this.droit = droit;
    }

    public void inserer(Noeud nouveauNoeud) {
        if (nouveauNoeud.getStagiaire().getNom().compareTo(stagiaire.getNom()) < 0) {
            if (gauche == null) {
                gauche = nouveauNoeud;
            } else {
                gauche.inserer(nouveauNoeud);
            }
        } else {
            if (droit == null) {
                droit = nouveauNoeud;
            } else {
                droit.inserer(nouveauNoeud);
            }
        }
    }
    
    
//    public Stagiaire rechercher(String nom) {
//        if (nom.compareTo(stagiaire.getNom()) == 0) {
//            return stagiaire;
//        } else if (nom.compareTo(stagiaire.getNom()) < 0 && gauche != null) {
//            return gauche.rechercher(nom);
//        } else if (nom.compareTo(stagiaire.getNom()) > 0 && droit != null) {
//            return droit.rechercher(nom);
//        } else {
//            return null;
//        }
//    }
    
    

    public void afficher() {
        if (gauche != null) {
            gauche.afficher();
        }
        System.out.println(stagiaire.toString());
        if (droit != null) {
            droit.afficher();
        }
    }
}
