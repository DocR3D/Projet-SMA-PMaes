package modele;

public class Proposition {

	private String nom;
	private boolean valeur;
	
	public Proposition(String nom, boolean valeur) {
		super();
		this.nom = nom;
		this.valeur = valeur;
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public boolean isValeur() {
		return valeur;
	}
	public void setValeur(boolean valeur) {
		this.valeur = valeur;
	}
	
	@Override
	public boolean equals(Object unObjet) {
		if(!(unObjet instanceof Proposition)) return false;
		Proposition uneProposition = (Proposition) unObjet;
		if(this.nom != uneProposition.nom ) return false; // Si deux proposition ont le même nom mais pas la même valeur ils sont considéré comme identique
		return true;
	}
	
	public String toString() {
		return this.getNom();
	}
	
	
}
