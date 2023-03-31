package fr.isika.cda24.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FichierStagiaire {

    public static ArrayList<Stagiaire> chargerStagiairesDepuisFichier(String cheminFichier) {
        ArrayList<Stagiaire> stagiaires = new ArrayList<>();

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
                stagiaires.add(new Stagiaire(nom, prenom, departement, formation, annee));
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
