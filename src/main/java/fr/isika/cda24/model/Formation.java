package fr.isika.cda24.model;

public abstract class Formation {
	
	//1 attribut 
	protected String nomFormation;

	//constructeur
	public Formation(String nomFormation) {
		this.nomFormation = nomFormation;
	}

	// getters & setters
	public String getNomFormation() {
		return nomFormation;
	}

	public void setNomFormation(String nomFormation) {
		this.nomFormation = nomFormation;
	}

	//méthode toString
	@Override
	public String toString() {
		return "Formation [nomFormation=" + nomFormation + "]";
	}
	
	
	
	
	

}
