package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda24.model.ArbreBinaireRecherche;
import fr.isika.cda24.model.ListeStagiaire;
import fr.isika.cda24.model.Stagiaire;

public class Lanceur {

    public static void main(String[] args) {

        ListeStagiaire liste = new ListeStagiaire();

        // charger les stagiaires depuis le fichier
        try {
            FileReader fr = new FileReader("C:\\Users\\tstho\\Documents\\ISIKA\\10_Projet1\\STAGIAIRES.DON");
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
                liste.ajouterStagiaire(new Stagiaire(nom, prenom, departement, formation, annee));
                br.readLine();

            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //liste.afficherStagiaire();

        // créer et remplir l'arbre binaire de recherche
        ArbreBinaireRecherche abr = new ArbreBinaireRecherche();

        for (Stagiaire stagiaire : liste.getListeStagiaire()) {
            abr.inserer(stagiaire);
        }

        // afficher l'arbre binaire de recherche
        //abr.afficher();
        System.out.println();
        
        
//        recherche dans l'arbre binaire de recherche
//        String nomRecherche = "ZHANG";
//        Stagiaire resultat = abr.rechercher(nomRecherche);
//        if (resultat == null) {
//            System.out.println("Aucun stagiaire trouvé avec le nom " + nomRecherche);
//        } else {
//            System.out.println("Le stagiaire trouvé est : " + resultat);
//        }
        
        
//        ArrayList<Stagiaire> resultat1 = abr.rechercherTous("ZHANG");
//        if (resultat1.isEmpty()) {
//            System.out.println("Rien");
//        } else {
//            for (Stagiaire stagiaire : resultat1) {
//                System.out.println(stagiaire);
//            }
//        }
        
        
//      recherche multicritere dans l'arbre binaire de recherche
        System.out.println();
        
        String nomRecherche = "ZHANG";
        String prenomRecherche = null;
        String departementRecherche = "94";
        String formationRecherche = "AI 95";
        String anneeRecherche = null;
        
        
        ArrayList<Stagiaire> resultatsRecherche = abr.rechercherMultiCritere(nomRecherche, prenomRecherche, departementRecherche, formationRecherche, anneeRecherche);
        
        if (resultatsRecherche.isEmpty()) {
            System.out.println("Rien");
        } else {
            for (Stagiaire stagiaire : resultatsRecherche) {
                System.out.println(stagiaire);
            }
        }


    }
}