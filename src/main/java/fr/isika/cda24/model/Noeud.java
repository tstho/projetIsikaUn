package fr.isika.cda24.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Noeud {

	// attributs
	public final static int TAILLE_NOEUD_OCTET = Stagiaire.TAILLE_STAGIAIRE_OCTET + 4 * 3;

	private Stagiaire stagiaire;
	private int gauche;
	private int droit;
	private int doublon;

	// constructeurs
	public Noeud(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
		this.gauche = -1;
		this.droit = -1;
		this.doublon = -1;
	}

	public Noeud(Stagiaire stagiaire, int gauche, int droit, int doublon) {

		this.stagiaire = stagiaire;
		this.gauche = gauche;
		this.droit = droit;
		this.doublon = doublon;
	}

	// getters & setters
	public Stagiaire getStagiaire() {
		return stagiaire;
	}

	public int getGauche() {
		return gauche;
	}

	public void setGauche(int gauche) {
		this.gauche = gauche;
	}

	public int getDroit() {
		return droit;
	}

	public void setDroit(int droit) {
		this.droit = droit;
	}

	public int getDoublon() {
		return doublon;
	}

	public void setDoublon(int doublon) {
		this.doublon = doublon;
	}

	// méthode to String
	@Override
	public String toString() {
		return "Noeud [stagiaire=" + stagiaire + ", gauche=" + gauche + ", droit=" + droit + ", doublon=" + doublon
				+ "]";
	}

	// methode Ecriture d'un stagiaire à la fin du fichier binaire
	public void ecritureStagiaire(Stagiaire stagiaire) {
		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// place le pointeur à la fin du fichier
			raf.seek(raf.length());
			// ecrit le stagiaire dans le fichier
			raf.writeChars(stagiaire.getNomLong());
			raf.writeChars(stagiaire.getPrenomLong());
			raf.writeChars(stagiaire.getDepartementLong());
			raf.writeChars(stagiaire.getFormationLong());
			raf.writeChars(stagiaire.getAnneeLong());
			raf.writeInt(-1);
			raf.writeInt(-1);
			raf.writeInt(-1);

			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// methode pour lire un noeud à partir d'un index dans le fichier
	public Noeud lireUnNoeud(int index) {

		Noeud noeudLu;
		Stagiaire stagiaire;
		String nom = "";
		String prenom = "";
		String departement = "";
		String formation = "";
		String annee = "";
		int gauche = -1;
		int droit = -1;
		int doublon = -1;

		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// on se place à l'index ou on veut lire le stagiaire
			index = index * TAILLE_NOEUD_OCTET;
			raf.seek(index);
			// on lit chaque info du stagiaire
			for (int j = 0; j < Stagiaire.TAILLE_NOM_MAX; j++) {
				nom += raf.readChar();
			}
			for (int j = 0; j < Stagiaire.TAILLE_PRENOM_MAX; j++) {
				prenom += raf.readChar();
			}
			for (int j = 0; j < Stagiaire.TAILLE_DEPARTEMENT_MAX; j++) {
				departement += raf.readChar();
			}
			for (int j = 0; j < Stagiaire.TAILLE_FORMATION_MAX; j++) {
				formation += raf.readChar();
			}
			for (int j = 0; j < Stagiaire.TAILLE_ANNEE_MAX; j++) {
				annee += raf.readChar();
			}
			gauche = raf.readInt();
			droit = raf.readInt();
			doublon = raf.readInt();
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stagiaire = new Stagiaire(nom, prenom, departement, formation, annee);
		noeudLu = new Noeud(stagiaire, gauche, droit, doublon);
		return noeudLu;
	}

	// Methode pour insérer un stagiaire dans l'arbre
	public void inserer(Stagiaire nouveauStagiaire, int indexCompare) {

		// Lecture du Stagiaire à comparer
		Noeud noeudCompare = lireUnNoeud(indexCompare);
		// calcul de l'index à comparer en octets
		int indexCompareOctet = calculIndexOctet(indexCompare);
		// récupération index du nouveau stagiaire
		int index = calculIndexFin();

		int comparaison = nouveauStagiaire.getNomLong().compareTo(noeudCompare.stagiaire.getNom());

		// comparaison des noms des stagiaires
		if (comparaison < 0) {

			if (noeudCompare.gauche == -1) {
				noeudCompare.gauche = index;
				try {
					RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
					// on place le curseur au niveau du fils gauche
					raf.seek(indexCompareOctet + Stagiaire.TAILLE_STAGIAIRE_OCTET);
					// On écrit l'index du nouveau stagiaire
					raf.writeInt(index);
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// On écrit le nouveau stagiaire à la fin du fichier
				ecritureStagiaire(nouveauStagiaire);
			} else {
				inserer(nouveauStagiaire, noeudCompare.gauche);
			}

		} else if (comparaison > 0) {

			if (noeudCompare.droit == -1) {

				noeudCompare.droit = index;
				try {
					RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
					// on place le curseur au niveau du fils droit
					raf.seek(indexCompareOctet + Stagiaire.TAILLE_STAGIAIRE_OCTET + 4);
					// On écrit l'index du nouveau stagiaire
					raf.writeInt(index);
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ecritureStagiaire(nouveauStagiaire);

			} else {

				inserer(nouveauStagiaire, noeudCompare.droit);
			}
		} else {
			if (noeudCompare.doublon == -1) {

				noeudCompare.doublon = index;
				try {
					RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
					// on place le curseur au niveau du doublon
					raf.seek(indexCompareOctet + Stagiaire.TAILLE_STAGIAIRE_OCTET + 4 + 4);
					// On écrit l'index du nouveau stagiaire
					raf.writeInt(index);
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ecritureStagiaire(nouveauStagiaire);

			} else {

				inserer(nouveauStagiaire, noeudCompare.doublon);
			}
		}

	}

	// méthode pour calculer l'index quand on se place à la fin du fichier
	public int calculIndexFin() {
		int index = 0;
		try {
			InputStream inputStream = new FileInputStream("src/fichiers/stagiaires.bin");
			// lecture du nombre de bytes dans le fichier
			index = inputStream.available();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index / TAILLE_NOEUD_OCTET;

	}
	
	//méthode pour calcul l'index en octet
	public int calculIndexOctet (int index) {
		return index * TAILLE_NOEUD_OCTET;
	}

	// methode supprimer les astérisques dans les noms et prénoms
	public static Stagiaire supprimerAsterisques(Stagiaire stagiaire) {
		String nom = stagiaire.getNom().replaceAll("\\*", "");
		String prenom = stagiaire.getPrenom().replaceAll("\\*", "");
		String departement = stagiaire.getDepartement().replaceAll("\\*", "");
		String formation = stagiaire.getFormation().replaceAll("\\*", "");
		String annee = stagiaire.getAnnee().replaceAll("\\*", "");

		return new Stagiaire(nom, prenom, departement, formation, annee);
	}

	//methode pour afficher les stagiaires dans l'ordre alphabétique
	public ListeStagiaire affichageInfixeNoeud(int index, ListeStagiaire resultat) {
		Noeud noeud = lireUnNoeud(index);
		if (noeud.getGauche() != -1) {
			affichageInfixeNoeud(noeud.getGauche(), resultat);
		}
		resultat.ajouterStagiaire(Noeud.supprimerAsterisques(noeud.stagiaire));
		if (noeud.getDoublon() != -1) {
			affichageInfixeNoeud(noeud.getDoublon(),resultat);
		}
		if (noeud.getDroit() != -1) {
			affichageInfixeNoeud(noeud.getDroit(),resultat);
		}
		return resultat;
	}

	public void modifierNoeud(int index, String nouveauNom, String nouveauPrenom, String nouveauDepartement,
								String nouveauFormation, String nouveauAnnee) {
		Noeud noeudAModifier = lireUnNoeud(index);
		if(nouveauNom!=null) {
			noeudAModifier.stagiaire.setNom(nouveauNom);
			String nomLong = noeudAModifier.stagiaire.getNomLong();
			try {
				RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
				// on place le curseur au niveau du nom a modifier 
				raf.seek(calculIndexOctet(index));
				// On écrit le nouveau nom stagiaire
				raf.writeChars(nomLong);
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(nouveauPrenom!=null) {
			noeudAModifier.stagiaire.setPrenom(nouveauPrenom);
			String prenomLong = noeudAModifier.stagiaire.getPrenomLong();
			try {
				RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
				// on place le curseur au niveau du prénom a modifier 
				raf.seek(calculIndexOctet(index)+ Stagiaire.TAILLE_NOM_MAX);
				// On écrit le nouveau prenom stagiaire
				raf.writeChars(prenomLong);
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(nouveauDepartement!=null) {
			noeudAModifier.stagiaire.setPrenom(nouveauDepartement);
			String departementLong = noeudAModifier.stagiaire.getDepartementLong();
			try {
				RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
				// on place le curseur au niveau du departement a modifier 
				raf.seek(calculIndexOctet(index)+ Stagiaire.TAILLE_NOM_MAX + Stagiaire.TAILLE_PRENOM_MAX);
				// On écrit le nouveau departement stagiaire
				raf.writeChars(departementLong);
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(nouveauFormation!=null) {
			noeudAModifier.stagiaire.setPrenom(nouveauFormation);
			String formationLong = noeudAModifier.stagiaire.getFormationLong();
			try {
				RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
				// on place le curseur au niveau du formation a modifier 
				raf.seek(calculIndexOctet(index)+ Stagiaire.TAILLE_NOM_MAX + Stagiaire.TAILLE_PRENOM_MAX+ Stagiaire.TAILLE_DEPARTEMENT_MAX);
				// On écrit la nouvelle formation stagiaire
				raf.writeChars(formationLong);
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(nouveauAnnee!=null) {
			noeudAModifier.stagiaire.setPrenom(nouveauAnnee);
			String anneeLong = noeudAModifier.stagiaire.getAnneeLong();
			try {
				RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
				// on place le curseur au niveau du annee a modifier 
				raf.seek(calculIndexOctet(index)+ Stagiaire.TAILLE_NOM_MAX+ Stagiaire.TAILLE_PRENOM_MAX+ Stagiaire.TAILLE_DEPARTEMENT_MAX+ Stagiaire.TAILLE_FORMATION_MAX);
				// On écrit la nouvelle annee stagiaire
				raf.writeChars(anneeLong);
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

//    public void afficher() {
//        if (gauche != null) {
//            gauche.afficher();
//        }
//        System.out.println(stagiaire.toString());
//        if (droit != null) {
//            droit.afficher();
//        }
//    }
}
