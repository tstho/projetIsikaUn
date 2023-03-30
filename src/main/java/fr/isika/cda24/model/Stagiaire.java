package fr.isika.cda24.model;

public class Stagiaire extends Promo{
	
	//attributs
	private String nom;
	private String prenom;
	private int departement;
	
	//constructeur
	public Stagiaire(String nomFormation, int numPromo, int anneeEntree, String nom, String prenom, int departement) {
		super(nomFormation, numPromo, anneeEntree);
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
	}

	//getters & setters
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


	public int getDepartement() {
		return departement;
	}


	public void setDepartement(int departement) {
		this.departement = departement;
	}


	//methode toString
	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom + ", departement=" + departement + "] " + super.toString();
	}
	


	
	

}
