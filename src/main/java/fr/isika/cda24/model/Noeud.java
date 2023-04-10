package fr.isika.cda24.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * La classe Noeud représente les nœuds de l'arbre binaire de recherche qui
 * stocke les instances de la classe Stagiaire. Chaque nœud est associé à un ou
 * plusieurs éléments (clés) et possède des liens vers ses nœuds enfants via les
 * indices "gauche" et "droit" ainsi que vers son nœud jumeau (doublon) s'il
 * existe. La taille du nœud en octets est égale à la taille d'un objet
 * Stagiaire en octets plus 4 * 3 octets (pour stocker les références aux nœuds
 * enfants et jumeau).
 */
public class Noeud {

	// attributs
	/**
	 * Constante Taille d'un nœud en octets. Utilisé en écriture et lecture du
	 * fichier binaire.
	 */
	public final static int TAILLE_NOEUD_OCTET = Stagiaire.TAILLE_STAGIAIRE_OCTET + 4 * 3;
	/**
	 * L'instance de la classe Stagiaire stockée dans ce nœud.
	 */
	private Stagiaire stagiaire;
	/**
	 * L'indice du nœud associé au fils gauche.
	 */
	private int gauche;

	/**
	 * L'indice du nœud associé au fils droit.
	 */
	private int droit;
	/**
	 * L'indice du noeud associé au nœud(s) jumeau(x).
	 */
	private int doublon;
	

	public Noeud() {
		super();
	}

	/**
	 * Constructeur d'un nœud avec une instance de la classe Stagiaire et des
	 * valeurs par défaut pour les liens vers les nœuds enfants et jumeau(x).
	 *
	 * @param stagiaire L'instance de la classe Stagiaire à stocker dans le nœud.
	 */
	public Noeud(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
		this.gauche = -1;
		this.droit = -1;
		this.doublon = -1;
	}

	/**
	 * Constructeur d'un nœud avec une instance de la classe Stagiaire et des liens
	 * vers des nœuds enfants et jumeau spécifiés.
	 *
	 * @param stagiaire L'instance de la classe Stagiaire à stocker dans le nœud.
	 * @param gauche    Le numéro de nœud associé au fils gauche.
	 * @param droit     Le numéro de nœud associé au fils droit.
	 * @param doublon   Le numéro de nœud associé au nœud jumeau.
	 */
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

	/**
	 * Override de la méthode toString()
	 */
	public String toString() {
		return "Noeud [stagiaire=" + stagiaire + ", gauche=" + gauche + ", droit=" + droit + ", doublon=" + doublon
				+ "]";
	}

	/**
	 * Écrit un objet Stagiaire à la fin du fichier binaire "stagiaires.bin". Cette
	 * méthode est utilisée pour ajouter un nouveau stagiaire à la fin du fichier
	 * binaire
	 *
	 * @param stagiaire L'objet Stagiaire à écrire dans le fichier binaire
	 *                  "stagiaires.bin".
	 */
	public void ecritureStagiaire(Stagiaire stagiaire) {
		try {
			File dossier = new File("src/fichiers");
			dossier.mkdir();
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

	/**
	 * Méthode pour calculer l'index quand on se place à la fin du fichier.
	 * 
	 * @return L'index correspondant à la position à la fin du fichier.
	 */
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

	/**
	 * Méthode pour calculer l'index en octet.
	 * 
	 * @param index L'index en nombre entier.
	 * @return L'index en octet.
	 */
	public int calculIndexOctet(int index) {
		return index * TAILLE_NOEUD_OCTET;
	}

	/**
	 * Lit un nœud à partir de l'index spécifié dans le fichier binaire
	 * "stagiaires.bin". Cette méthode est utilisée pour récupérer un nœud
	 * spécifique de l'arbre binaire de recherche à partir de son numéro son index.
	 *
	 * @param index L'index du nœud à lire.
	 * @return Le nœud lu depuis le fichier binaire "stagiaires.bin".
	 */
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
			// on se place à l'index où on veut lire le stagiaire
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

	/**
	 * Insère un objet Stagiaire dans l'arbre binaire de recherche. Cette méthode
	 * ajoute un nouveau nœud à l'arbre binaire de recherche à partir d'un objet
	 * Stagiaire, en le comparant avec les autres nœuds de l'arbre. Si l'objet
	 * Stagiaire est inférieur à un autre nœud, il sera placé comme fils gauche de
	 * ce nœud, si l'objet Stagiaire est supérieur, il sera placé comme fils droit,
	 * et s'il est égal, il sera placé comme doublon. Cette méthode est utilisée
	 * pour ajouter un nouveau stagiaire à l'arbre binaire de recherche.
	 *
	 * @param nouveauStagiaire L'objet Stagiaire à insérer dans l'arbre binaire de
	 *                         recherche.
	 * @param indexCompare     L'index du nœud à partir duquel comparer le
	 *                         nouveauStagiaire dans le fichier binaire
	 *                         "stagiaires.bin".
	 */
	public void inserer(Stagiaire nouveauStagiaire, int indexCompare) {

		// Lecture du Stagiaire à comparer
		Noeud noeudCompare = lireUnNoeud(indexCompare);
		// récupération index du nouveau stagiaire (fin du fichier)
		int index = calculIndexFin();

		// comparaison des noms des stagiaires
		int comparaison = nouveauStagiaire.getNomLong().compareTo(noeudCompare.stagiaire.getNom());

		if (comparaison < 0) {

			if (noeudCompare.gauche == -1) {
				// Si le noeud n'a pas de fils gauche on l'insert en fils gauche
				modifierFilsGauche(index, indexCompare);
				// On écrit le nouveau stagiaire à la fin du fichier
				ecritureStagiaire(nouveauStagiaire);
			} else {
				// Sinon on continue la recherche vers la gauche
				inserer(nouveauStagiaire, noeudCompare.gauche);
			}

		} else if (comparaison > 0) {

			if (noeudCompare.droit == -1) {
				modifierFilsDroit(index, indexCompare);
				ecritureStagiaire(nouveauStagiaire);

			} else {
				inserer(nouveauStagiaire, noeudCompare.droit);
			}
		} else {
			if (noeudCompare.doublon == -1) {

				modifierDoublon(index, indexCompare);
				ecritureStagiaire(nouveauStagiaire);

			} else {

				inserer(nouveauStagiaire, noeudCompare.doublon);
			}
		}

	}

	/**
	 * Méthode pour modifier le fils Gauche d'un noeud
	 * 
	 * @param indexAInserer l'index à insérer en fils gauche
	 * @param indexNoeud    le noeud dans lequel on veut insérer l'index
	 */
	public void modifierFilsGauche(int indexAInserer, int indexNoeud) {
		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// on place le curseur au niveau du fils gauche
			raf.seek(calculIndexOctet(indexNoeud) + Stagiaire.TAILLE_STAGIAIRE_OCTET);
			// On écrit l'index du nouveau stagiaire
			raf.writeInt(indexAInserer);
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour modifier le fils Droit d'un noeud
	 * 
	 * @param indexAInserer l'index à insérer en fils droit
	 * @param indexNoeud    le noeud dans lequel on veut insérer l'index
	 */
	public void modifierFilsDroit(int indexAInserer, int indexNoeud) {

		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// on place le curseur au niveau du fils droit
			raf.seek(calculIndexOctet(indexNoeud) + Stagiaire.TAILLE_STAGIAIRE_OCTET + 4);
			// On écrit l'index du nouveau stagiaire
			raf.writeInt(indexAInserer);
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Méthode pour modifier le doublon d'un noeud
	 * 
	 * @param indexAInserer l'index à insérer en doublon
	 * @param indexNoeud    le noeud dans lequel on veut insérer l'index
	 */
	public void modifierDoublon(int indexAInserer, int indexNoeud) {

		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// on place le curseur au niveau du doublon
			raf.seek(calculIndexOctet(indexNoeud) + Stagiaire.TAILLE_STAGIAIRE_OCTET + 4 + 4);
			// On écrit l'index du nouveau stagiaire
			raf.writeInt(indexAInserer);
			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Méthode pour supprimer les astérisques des arguments d'un stagiaire.
	 * 
	 * @param stagiaire Le stagiaire dont les astérisques doivent être supprimés.
	 * @return Un nouveau stagiaire avec les astérisques supprimés.
	 */
	public static Stagiaire supprimerAsterisques(Stagiaire stagiaire) {
		String nom = stagiaire.getNom().replaceAll("\\*", "");
		String prenom = stagiaire.getPrenom().replaceAll("\\*", "");
		String departement = stagiaire.getDepartement().replaceAll("\\*", "");
		String formation = stagiaire.getFormation().replaceAll("\\*", "");
		String annee = stagiaire.getAnnee().replaceAll("\\*", "");

		return new Stagiaire(nom, prenom, departement, formation, annee);
	}

	/**
	 * Methode pour afficher les stagiaires dans l'ordre alphabétique
	 *
	 * @param index    l'index du noeud à partir duquel on doit afficher les
	 *                 stagiaires
	 * @param resultat la liste des stagiaires trouvés jusqu'à présent
	 * @return la liste des stagiaires trouvés
	 */

	public ListeStagiaire affichageInfixeNoeud(int index, ListeStagiaire resultat) {
		// Si le fils gauche existe, on parcourt récursivement cet arbre
		Noeud noeud = lireUnNoeud(index);
		if (noeud.getGauche() != -1) {
			affichageInfixeNoeud(noeud.getGauche(), resultat);
		}
		// Ajout du stagiaire actuel dans la liste de résultats
		resultat.ajouterStagiaire(supprimerAsterisques(noeud.stagiaire));
		// Si le noeud doublon existe, on parcourt récursivement cet arbre
		if (noeud.getDoublon() != -1) {
			affichageInfixeNoeud(noeud.getDoublon(), resultat);
		}
		if (noeud.getDroit() != -1) {
			affichageInfixeNoeud(noeud.getDroit(), resultat);
		}
		return resultat;
	}

	/**
	 * Methode pour rechercher un Noeud
	 *
	 * @param attribut l'attribut sur lequel on doit effectuer la recherche
	 * @param valeur   la valeur recherchée
	 * @param index    l'index du noeud à partir duquel on doit effectuer la
	 *                 recherche
	 * @param resultat la liste des stagiaires trouvés jusqu'à présent
	 * @return la liste des stagiaires trouvés
	 */
	public ListeStagiaire rechercherNoeud(String attribut, String valeur, int index, ListeStagiaire resultat) {
		// Lire le noeud à l'indice spécifié et récupérer le stagiaire
		Noeud noeud = lireUnNoeud(index);
		Stagiaire stagiaire = noeud.getStagiaire();
		// Supprimer les astérisques stagiaire pour faciliter la comparaison
		stagiaire = Noeud.supprimerAsterisques(stagiaire);
		String valeurAttribut = "";
		// On récupère la valeur de l'attribut pour le stagiaire courant
		switch (attribut) {

		case "Tout":
			if (stagiaire.getNom().equalsIgnoreCase(valeur) || stagiaire.getPrenom().equalsIgnoreCase(valeur)
					|| stagiaire.getDepartement().equalsIgnoreCase(valeur)
					|| stagiaire.getFormation().equalsIgnoreCase(valeur)
					|| stagiaire.getAnnee().equalsIgnoreCase(valeur)) {
				resultat.ajouterStagiaire(stagiaire);
			}
			break;

		case "Nom":
			valeurAttribut = stagiaire.getNom();
			break;
		case "Prénom":
			valeurAttribut = stagiaire.getPrenom();
			break;
		case "Département":
			valeurAttribut = stagiaire.getDepartement();
			break;
		case "Formation":
			valeurAttribut = stagiaire.getFormation();
			break;
		case "Année":
			valeurAttribut = stagiaire.getAnnee();
			break;
		}
		int comparaison = valeur.compareToIgnoreCase(valeurAttribut);

		if (comparaison == 0) {
			resultat.ajouterStagiaire(stagiaire);
		}
		if (noeud.getDoublon() != -1) {
			resultat = rechercherNoeud(attribut, valeur, noeud.getDoublon(), resultat);
		}
		if (noeud.getGauche() != -1) {
			resultat = rechercherNoeud(attribut, valeur, noeud.getGauche(), resultat);
		}

		if (noeud.getDroit() != -1) {
			resultat = rechercherNoeud(attribut, valeur, noeud.getDroit(), resultat);
		}
		return resultat;
	}

	/**
	 * Cette méthode permet de rechercher des stagiaires dans une liste
	 * multi-critères en fonction des critères spécifiés.
	 *
	 * @param nom           le nom du stagiaire recherché.
	 * @param prenom        le prénom du stagiaire recherché.
	 * @param departement   le département du stagiaire recherché.
	 * @param formation     la formation du stagiaire recherché.
	 * @param annee         l'année de la formation du stagiaire recherché.
	 * @param index         l'indice du noeud dans lequel on doit commencer la
	 *                      recherche.
	 * @param resultatMulti la liste de stagiaires correspondant aux critères de
	 *                      recherche.
	 * @return la liste de stagiaires correspondant aux critères de recherche.
	 */
	public ListeStagiaire rechercherMultiCritere(String nom, String prenom, String departement, String formation,
			String annee, int index, ListeStagiaire resultatMulti) {
		// Lire le noeud à l'indice spécifié et récupérer le stagiaire
		Noeud noeudMulti = lireUnNoeud(index);
		Stagiaire stagiaire = noeudMulti.getStagiaire();
		// Supprimer les astérisques stagiaire pour faciliter la comparaison
		stagiaire = Noeud.supprimerAsterisques(stagiaire);
		// Vérifier si le stagiaire correspond aux critères de recherche
		boolean verification = true;
		if (nom != null && !stagiaire.getNom().equalsIgnoreCase(nom)) {
			verification = false;
		}
		if (prenom != null && !stagiaire.getPrenom().equalsIgnoreCase(prenom)) {
			verification = false;
		}
		if (departement != null && !stagiaire.getDepartement().equalsIgnoreCase(departement)) {
			verification = false;
		}
		if (formation != null && !stagiaire.getFormation().equalsIgnoreCase(formation)) {
			verification = false;
		}
		if (annee != null && !stagiaire.getAnnee().equalsIgnoreCase(annee)) {
			verification = false;
		}
		// Si le stagiaire correspond aux critères, l'ajouter à la liste de résultats
		if (verification) {
			resultatMulti.ajouterStagiaire(stagiaire);
		}
		// Récursivement rechercher les stagiaires correspondant aux critères dans les
		// noeuds fils et jumeaux
		if (noeudMulti.getDoublon() != -1) {
			resultatMulti = rechercherMultiCritere(nom, prenom, departement, formation, annee, noeudMulti.getDoublon(),
					resultatMulti);
		}
		if (noeudMulti.getGauche() != -1) {
			resultatMulti = rechercherMultiCritere(nom, prenom, departement, formation, annee, noeudMulti.getGauche(),
					resultatMulti);
		}
		if (noeudMulti.getDroit() != -1) {
			resultatMulti = rechercherMultiCritere(nom, prenom, departement, formation, annee, noeudMulti.getDroit(),
					resultatMulti);
		}

		return resultatMulti;
	}

	/**
	 * Methode récursive pour rechercher l'index d'un Noeud à partir d'un Stagiaire.
	 * On commence la recherche au noeud 0 et on continue la recherche suivant la
	 * comparaison effectué.
	 * 
	 * @param stagiaireRecherche le stagiaire recherché
	 * @param index              l'index où on va lire le stagiaire à comparer
	 * @return l'index du noeud ou se trouve le stagiaire
	 */
	public int rechercherIndexNoeud(Stagiaire stagiaireRecherche, int index) {
		Noeud noeud = lireUnNoeud(index);
		Stagiaire stagiaire = noeud.getStagiaire();
		stagiaire = Noeud.supprimerAsterisques(stagiaire);

		int comparaisonNom = stagiaireRecherche.getNom().compareTo(stagiaire.getNom());

		if (comparaisonNom == 0) {
			int comparaisonPrenom = stagiaireRecherche.getPrenom().compareTo(stagiaire.getPrenom());
			int comparaisonDept = stagiaireRecherche.getDepartement().compareTo(stagiaire.getDepartement());
			int comparaisonFormation = stagiaireRecherche.getFormation().compareToIgnoreCase(stagiaire.getFormation());
			int comparaisonAnnee = stagiaireRecherche.getAnnee().compareTo(stagiaire.getAnnee());

			if (comparaisonPrenom == 0 && comparaisonDept == 0 && comparaisonFormation == 0 && comparaisonAnnee == 0) {
				return index;
			} else if (noeud.getDoublon() != -1) {
				return rechercherIndexNoeud(stagiaireRecherche, noeud.getDoublon());
			}
		} else if (comparaisonNom < 0 && noeud.getGauche() != -1) {
			return rechercherIndexNoeud(stagiaireRecherche, noeud.getGauche());
		} else if (comparaisonNom > 0 && noeud.getDroit() != -1) {
			return rechercherIndexNoeud(stagiaireRecherche, noeud.getDroit());
		}
		return -1;

	}

	/**
	 * Méthode récursive pour rechercher le parent d'un noeud à partir de l'index
	 * enfant. On commence la recherche au noeud 0. Si l'index n'est pas trouvé dans
	 * les fils ou le doublon, on la rappelle sur le noeud n+1. Sinon on renvoie
	 * l'index du noeud.
	 * 
	 * @param indexRecherche index enfant recherché
	 * @param indexParent    index utilisé pour comparer
	 * @return on renvoi l'index parent ou se trouve l'index de l'enfant
	 */
	public int rechercherIndexParent(int indexRecherche, int indexParent) {

		Noeud noeud = lireUnNoeud(indexParent);

		if (noeud.getGauche() == indexRecherche || noeud.getDroit() == indexRecherche
				|| noeud.getDoublon() == indexRecherche) {
			return indexParent;
		} else {
			return rechercherIndexParent(indexRecherche, indexParent + 1);
		}
	}

	/**
	 * Methode pour supprimer un noeud
	 * 
	 * @param indexASupprimer index du noeud à supprimer
	 */
	public void supprimerNoeud(int indexASupprimer) {
		Noeud noeudASupprimer = lireUnNoeud(indexASupprimer);

		if (noeudASupprimer.getGauche() == -1 && noeudASupprimer.getDroit() == -1
				&& noeudASupprimer.getDoublon() == -1) {
			// On efface le noeud dans le parent
			modifierUnFilsNoeudParent(indexASupprimer, -1);
		} else if (noeudASupprimer.getGauche() >= 0 && noeudASupprimer.getDroit() == -1
				&& noeudASupprimer.getDoublon() == -1) {
			// On remonte le fils gauche
			modifierUnFilsNoeudParent(indexASupprimer, noeudASupprimer.getGauche());
		} else if (noeudASupprimer.getGauche() == -1 && noeudASupprimer.getDroit() >= 0
				&& noeudASupprimer.getDoublon() == -1) {
			// On remonte le fils droit
			modifierUnFilsNoeudParent(indexASupprimer, noeudASupprimer.getDroit());

		} else if (noeudASupprimer.getGauche() == -1 && noeudASupprimer.getDroit() == -1
				&& noeudASupprimer.getDoublon() >= 0) {
			// On remonte le doublon
			modifierUnFilsNoeudParent(indexASupprimer, noeudASupprimer.getDoublon());

		} else {

			supprimerRacine(indexASupprimer);
		}

	}

	/**
	 * Méthode pour supprimer un noeud lorsque celui-ci est une racine de l'arbre
	 * 
	 * @param indexASupprimer index du noeud à supprimer
	 */
	public void supprimerRacine(int indexASupprimer) {
		Noeud noeudASupprimer = lireUnNoeud(indexASupprimer);

		if (noeudASupprimer.getDoublon() >= 0) {

			// Si le noeud à un doublon, c'est lui qu'on remonte
			Noeud noeudDoublon = lireUnNoeud(noeudASupprimer.getDoublon());

			// On réécrit le doublon à la place du noeud supprimé
			reecrireNoeud(noeudDoublon, indexASupprimer);

			// On replace les fils du noeud supprimé en fils du doublon remonté (qui est
			// maintenant à l'index du noeud supprimé)
			modifierFilsGauche(noeudASupprimer.getGauche(), indexASupprimer);
			modifierFilsDroit(noeudASupprimer.getDroit(), indexASupprimer);

		} else {
			// Sinon on recherche alors le noeud le plus à gauche du sous arbre droit
			int indexARemonter = plusPetitDescendant(noeudASupprimer.getDroit());

			// on réécrit le noeud à la place du noeud supprimé
			Noeud noeudRemonte = lireUnNoeud(indexARemonter);
			reecrireNoeud(noeudRemonte, indexASupprimer);

			// on replace le noeud gauche du noeud supprimé en fils gauche du noeud remonté
			modifierFilsGauche(noeudASupprimer.getGauche(), indexASupprimer);

			// on replace le noeud droit du noeud supprimé en fils droit du noeud remonté
			modifierFilsDroit(noeudASupprimer.getDroit(), indexASupprimer);

			// on supprime le noeud remonté
			supprimerNoeud(indexARemonter);
		}
	}

	/**
	 * Méthode récursive pour rechercher le plus petit descendant dans le sous arbre
	 * droit. Si le noeud n'a pas de fils gauche, c'est le plus petit descendant.
	 * 
	 * @param index index du noeud où on effectue la recherche (mettre le fils droit
	 *              lors de l'appel de la méthode)
	 * @return l'index du plus petit descendant
	 */
	public int plusPetitDescendant(int index) {
		Noeud noeud = lireUnNoeud(index);
		if (noeud.getGauche() == -1) {
			return index;
		} else {
			return plusPetitDescendant(noeud.getGauche());
		}
	}

	/**
	 * 
	 * Réécrit un noeud dans le fichier binaire "stagiaires.bin". Cette méthode est
	 * utilisée pour remonter un noeud à la place de la racine supprimé.
	 * 
	 * @param noeud : Noeud que l'on veut écrire
	 * @param index : index où l'on veut réécrire le noeud (index de la racine
	 *              supprimé)
	 */
	public void reecrireNoeud(Noeud noeud, int index) {

		try {
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// place le pointeur au niveau de l'index fourni
			raf.seek(calculIndexOctet(index));
			// ecrit le stagiaire dans le fichier
			raf.writeChars(noeud.stagiaire.getNomLong());
			raf.writeChars(noeud.stagiaire.getPrenomLong());
			raf.writeChars(noeud.stagiaire.getDepartementLong());
			raf.writeChars(noeud.stagiaire.getFormationLong());
			raf.writeChars(noeud.stagiaire.getAnneeLong());
			raf.writeInt(noeud.getGauche());
			raf.writeInt(noeud.getDroit());
			raf.writeInt(noeud.getDoublon());

			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour modifier un fils non identifié du Noeud parent. La méthode va
	 * rechercher ou se trouve le fils que l'on veut modifier en fonction de l'index
	 * à supprimer et faire appel à la méthode permettant de le modifier par le
	 * nouveau fils.
	 * 
	 * @param indexASupprimer index recherché et écrasé par le nouveau fils
	 * @param indexAInserer   index à insérer
	 */
	public void modifierUnFilsNoeudParent(int indexASupprimer, int indexAInserer) {
		//On cherche le noeud Parent
		int indexParent = rechercherIndexParent(indexASupprimer, 0);
		Noeud noeudParent = lireUnNoeud(indexParent);

		if (noeudParent.getGauche() == indexASupprimer) {
			// si le noeud à supprimer est le fils gauche du noeud Parent, on le modifie avec l'index à insérer
			modifierFilsGauche(indexAInserer, indexParent);

		} else if (noeudParent.getDroit() == indexASupprimer) {
			// si le noeud à supprimer est le fils droit du noeud Parent, on le modifie avec l'index à insérer
			modifierFilsDroit(indexAInserer, indexParent);

		} else if (noeudParent.getDoublon() == indexASupprimer) {
			// si le noeud à supprimer est le doublon du noeud Parent, on le modifie avec l'index à insérer
			modifierDoublon(indexAInserer, indexParent);
		}

	}

	/**
	 * Méthode pour écraser le prénom, le département, la formation et l'année dans
	 * le fichier binaire, avec les attributs modifiés.
	 * 
	 * @param index            index du Noeud à modifier
	 * @param stagiaireModifie stagiaire avec les nouvelles informations
	 */
	public void modifierNoeud(int index, Stagiaire stagiaireModifie) {
		try {
			// on ouvre le fichier
			RandomAccessFile raf = new RandomAccessFile("src/fichiers/stagiaires.bin", "rw");
			// on place le curseur au niveau du prenom du stagiaire à modifier
			raf.seek(calculIndexOctet(index) + Stagiaire.TAILLE_NOM_OCTET);

			// on réécrit tout ces attributs
			raf.writeChars(stagiaireModifie.getPrenomLong());
			raf.writeChars(stagiaireModifie.getDepartementLong());
			raf.writeChars(stagiaireModifie.getFormationLong());
			raf.writeChars(stagiaireModifie.getAnneeLong());

			raf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
