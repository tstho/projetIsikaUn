package fr.isika.cda24.model;

public abstract class Promo extends Formation {
	
	//attributs
	protected int numPromo;
	protected int anneeEntree;
	
	//constructeurs
	public Promo(String nomFormation, int numPromo, int anneeEntree) {
		super(nomFormation);
		this.numPromo = numPromo;
		this.anneeEntree = anneeEntree;
	}
	
	//getters & setters
	public int getNumPromo() {
		return numPromo;
	}

	public void setNumPromo(int numPromo) {
		this.numPromo = numPromo;
	}

	public int getAnneeEntree() {
		return anneeEntree;
	}

	public void setAnneeEntree(int anneeEntree) {
		this.anneeEntree = anneeEntree;
	}

	
	//méthode toString
	@Override
	public String toString() {
		return "Promo [numPromo=" + numPromo + ", anneeEntree=" + anneeEntree + "] " + super.toString();
	}
	

}
