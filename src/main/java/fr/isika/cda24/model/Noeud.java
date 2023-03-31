package fr.isika.cda24.model;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Noeud {
	
	public final static int TAILLE_NOEUD_OCTET = Stagiaire.TAILLE_STAGIAIRE_OCTET + 4*3;
	
	private Stagiaire stagiaire;
	private int gauche;
	private int droit;
	private int doublon;

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

	// Ecriture d'un stagiaire à la fin du fichier binaire
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

	// methode pour lire un stagiaire à partir d'un index dans le fichier
	public Noeud lireUnStagiaire(int index) {
		
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

	// Methode insérer stagiaire dans arbre
	public void inserer(Stagiaire nouveauStagiaire, int indexCompare) {
		int index;
		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// calcule l'index du nouveau stagiaire
			index = (int) (raf.length() / Stagiaire.TAILLE_STAGIAIRE_OCTET);
			
			if (nouveauStagiaire.getNom().compareTo(this.stagiaire.getNom()) < 0) {
				if (this.gauche == -1) {
					this.gauche = index; 
					//on place le curseur à l'endroit où on veut modifier l'index
					raf.seek(indexCompare + Stagiaire.TAILLE_STAGIAIRE_OCTET);
					//On supprime l'index déjà présent
					raf.skipBytes(4);
					//On écrit l'index du nouveau stagiaire
					raf.writeInt(index);
					//On écrit le nouveau stagiaire à la fin du fichier
					ecritureStagiaire(nouveauStagiaire);
				} else {
					raf.seek(this.gauche);
					inserer(nouveauStagiaire, this.gauche);
				}
			} else {
				if (this.droit == -1) {
					this.droit = index;
					raf.seek(indexCompare + Stagiaire.TAILLE_STAGIAIRE_OCTET + 4);
					raf.skipBytes(4);
					raf.writeInt(index);
					ecritureStagiaire(nouveauStagiaire);
				} else {
					raf.seek(this.droit);
					inserer(nouveauStagiaire, this.droit);
				}
			}
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public String toString() {
		return "Noeud [stagiaire=" + stagiaire + ", gauche=" + gauche
				+ ", droit=" + droit + ", doublon=" + doublon+ "]";
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
