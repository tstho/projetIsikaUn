package fr.isika.cda24.model;

/**
 * Représente un stagiaire avec ses informations.
 */
public class Stagiaire {
	/**
	 * Constantes représentant la taille maximum de chaque attribut
	 */
	public final static int TAILLE_NOM_MAX = 21;
	public final static int TAILLE_PRENOM_MAX = 20;
	public final static int TAILLE_DEPARTEMENT_MAX = 2;
	public final static int TAILLE_FORMATION_MAX = 21;
	public final static int TAILLE_ANNEE_MAX = 4;
	/**
	 * Constantes représentant la taille maximum en octet de chaque attribut et la
	 * taille total en octet de tous ces attributs
	 */
	public final static int TAILLE_NOM_OCTET = 21 * 2;
	public final static int TAILLE_PRENOM_OCTET = 20 * 2;
	public final static int TAILLE_DEPARTEMENT_OCTET = 2 * 2;
	public final static int TAILLE_FORMATION_OCTET = 21 * 2;
	public final static int TAILLE_ANNEE_OCTET = 4 * 2;
	public final static int TAILLE_STAGIAIRE_OCTET = TAILLE_NOM_OCTET + TAILLE_PRENOM_OCTET + TAILLE_DEPARTEMENT_OCTET
			+ TAILLE_FORMATION_OCTET + TAILLE_ANNEE_OCTET;
	/**
	 * Attributs de la classe stagiaire
	 */
	private String nom;
	private String prenom;
	private String departement;
	private String formation;
	private String annee;

	/**
	 * Crée un nouveau stagiaire avec les informations spécifiées.
	 *
	 * @param nom         le nom du stagiaire.
	 * @param prenom      le prénom du stagiaire.
	 * @param departement le département du stagiaire.
	 * @param formation   la formation suivie par le stagiaire.
	 * @param annee       l'année de formation du stagiaire.
	 */
	public Stagiaire(String nom, String prenom, String departement, String formation, String annee) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.formation = formation;
		this.annee = annee;
	}

	/**
	 * Getters & Setters des attributs
	 *
	 */
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	/**
	 * Méthodes pour retourner les attributs du stagiaire avec leurs tailles
	 * maximum.
	 * 
	 * @return l'attribut avec une suite d'étoiles jusqu'à atteindre la taille max
	 */
	public String getNomLong() {
		String nomLong = this.nom;
		for (int i = this.nom.length(); i < TAILLE_NOM_MAX; i++) {
			nomLong += "*";
		}
		return nomLong;
	}

	public String getPrenomLong() {
		String prenomLong = this.prenom;
		for (int i = this.prenom.length(); i < TAILLE_PRENOM_MAX; i++) {
			prenomLong += "*";
		}
		return prenomLong;
	}

	public String getDepartementLong() {
		String departementLong = this.departement;
		for (int i = this.departement.length(); i < TAILLE_DEPARTEMENT_MAX; i++) {
			departementLong += "*";
		}
		return departementLong;
	}

	public String getFormationLong() {
		String formationLong = this.formation;
		for (int i = this.formation.length(); i < TAILLE_FORMATION_MAX; i++) {
			formationLong += "*";
		}
		return formationLong;
	}

	public String getAnneeLong() {
		String anneeLong = this.annee;
		for (int i = this.annee.length(); i < TAILLE_ANNEE_MAX; i++) {
			anneeLong += "*";
		}
		return anneeLong;
	}
/**
 * Méthode Override toString()
 */
	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + ", formation="
				+ formation + ", annee=" + annee + "]";
	}

}
