package fr.isika.cda24.lanceur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
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
			FileReader fr = new FileReader("C:\\Users\\tstho\\Documents\\ISIKA\\10_Projet1\\DOUZESTAGIAIRES.DON");
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

		// écriture du fichier binaire

		ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
		int index = 0;
		for (Stagiaire stagiaire : liste.getListeStagiaire()) {
			// System.out.println("----- Nouvel ajout ------");
			abr.ajoutStagiaire(stagiaire);
//					for (int i = 0; i <= index; i++) {
//						System.out.println(abr.lireUnNoeud(i));
//					}
			index++;
		}
		abr.affichageInfixe();

//		System.out.println(abr.lireUnStagiaire(2));

//		try {
//			// lecture de tous les stagiaires
//			raf.seek(0);
//			int nbStagiaire = (int) raf.length() / Stagiaire.TAILLE_STAGIAIRE_OCTET;
//			for (int i = 0; i < nbStagiaire; i++) {
//				String nom = "";
//				for (int j = 0; j < Stagiaire.TAILLE_NOM_MAX; j++) {
//					nom += raf.readChar();
//				}
//				String prenom = "";
//				for (int j = 0; j < Stagiaire.TAILLE_PRENOM_MAX; j++) {
//					prenom += raf.readChar();
//				}
//				String departement = "";
//				for (int j = 0; j < Stagiaire.TAILLE_DEPARTEMENT_MAX; j++) {
//					departement += raf.readChar();
//				}
//				String formation = "";
//				for (int j = 0; j < Stagiaire.TAILLE_FORMATION_MAX; j++) {
//					formation += raf.readChar();
//				}
//				String annee = "";
//				for (int j = 0; j < Stagiaire.TAILLE_ANNEE_MAX; j++) {
//					annee += raf.readChar();
//				}
//				System.out.println("Nom : " + nom + " /Prenom : " + prenom + " /Departement : " + departement
//						+ " /Formation : " + formation + " /Année : " + annee);
//			}
//			
//			//lire uniquement la 3eme tarte
//			// je déplace le curseur à la fin de la 2eme tarte
//			raf.seek(2*Tarte.TAILLE_TARTE_OCTET);
//			//je peux lire la 3e tarte
//			String parfum = "";
//			for (int j=0; j < Tarte.TAILLE_PARFUM_MAX; j++) {
//				parfum += raf.readChar();					
//			}
//			System.out.println("Parfum : " + parfum + "/taille : " + raf.readInt());
//
//			raf.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//        liste.afficherStagiaire();
//
//        // créer et remplir l'arbre binaire de recherche
//        ArbreBinaireRecherche abr = new ArbreBinaireRecherche();
//
//        for (Stagiaire stagiaire : liste.getListeStagiaire()) {
//            abr.inserer(stagiaire);
//        }

		// afficher l'arbre binaire de recherche
		// abr.afficher();

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
//        System.out.println();
//        
//        String nomRecherche = "ZHANG";
//        String prenomRecherche = null;
//        String departementRecherche = "94";
//        String formationRecherche = "AI 95";
//        String anneeRecherche = null;
//        
//        
//        ArrayList<Stagiaire> resultatsRecherche = abr.rechercherMultiCritere(nomRecherche, prenomRecherche, departementRecherche, formationRecherche, anneeRecherche);
//        
//        if (resultatsRecherche.isEmpty()) {
//            System.out.println("Rien");
//        } else {
//            for (Stagiaire stagiaire : resultatsRecherche) {
//                System.out.println(stagiaire);
//            }
//        }

	}
}