package modele;

import java.util.ArrayList;

public class Agent {

	//Liste contenant toute les propositions vraies à l'instant T aussi nommé S dans le sujet
	private  ArrayList<Proposition> propositionVraies;
	//Liste contenant toute les proposition étant définit comme objectif à l'instant T aussi nommé G
	private  ArrayList<Proposition> propositionButs;
	//Liste contenant toute les propositions étant définit comme objectif atteint à l'instant T aussi appelé R
	private  ArrayList<Proposition> propositionButTerminees;

	public Agent() {
		super();

		propositionVraies = new ArrayList<>();
		propositionButs = new ArrayList<>();
		propositionButTerminees = new ArrayList<>();
	}

	//Met à jours et renvoie la liste S
	public   ArrayList<Proposition> S(){
		propositionVraies = new ArrayList<>();
		for(Proposition uneProposition : Environnement.listeDesProposition) {
			if(uneProposition.isTrue()) propositionVraies.add(uneProposition);
		}
		return propositionVraies;
	}

	//Met à jours et renvoie la liste G
	public   ArrayList<Proposition> G(){
		ArrayList<Proposition> toDelete = new ArrayList<Proposition>();

		for(Proposition uneProposition : propositionButs) {
			if(uneProposition.isTrue()) {
				toDelete.add(uneProposition);
			}
		}
		for(Proposition uneProposition : toDelete) {
			terminerBut(uneProposition);
		}
		return propositionButs;
	}

	//Met à jours et renvoie la liste R
	public   ArrayList<Proposition> R(){
		ArrayList<Proposition> toDelete = new ArrayList<Proposition>();
		for(Proposition uneProposition : propositionButTerminees) {
			if(!uneProposition.isTrue()) {
				toDelete.add(uneProposition);
			}
		}
		for(Proposition uneProposition : toDelete) {
			resetBut(uneProposition);
		}
		return propositionButTerminees;
	}

	//Vérifie si une proposition se trouve dans liste G
	public boolean isInPropositionButs(Proposition uneProposition) {
		return propositionButs.contains(uneProposition);
	}

	//Vérifie si une proposition se trouve dans liste S
	public boolean isInPropositionVraies(Proposition uneProposition) {
		return propositionButs.contains(uneProposition);
	}

	//Vérifie si une proposition se trouve dans liste R
	public boolean isInPropositionButTerminees(Proposition uneProposition) {
		return propositionButTerminees.contains(uneProposition);
	}

	//Déplace une proposition de G à R
	public  void terminerBut(Proposition uneProposition) {
		propositionButs.remove(uneProposition);
		propositionButTerminees.add(uneProposition);
	}

	//Déplace une proposition de R à G
	public  void resetBut(Proposition uneProposition) {
		propositionButs.add(uneProposition);
		propositionButTerminees.remove(uneProposition);
	}


	//Ajoutes une proposition à S
	public void addPropositionInitiale(Proposition key) {
		propositionVraies.add(key);
	}

	//Ajoutes une proposition à G
	public void addBut(Proposition key) {
		propositionButs.add(key);
	}

	//Ajoutes une proposition à R
	public void addButTermine(Proposition key) {
		propositionButTerminees.add(key);
	}

	//Affiches l'ensembles des propositions
	public void printState() {
		String result = "State of the environnement : (";
		for(Proposition uneProposition : S()) for(int i = 0; i < uneProposition.nbOccurence ; i++) result = result + uneProposition + " ";
		result = result + ")\n goals of the environnement : (";
		for(Proposition uneProposition : G()) result = result + uneProposition + " ";
		result = result + ")\n protected goals of the environment : (";
		for(Proposition uneProposition : R()) result = result + uneProposition + " ";
		result = result + ")\n";
		System.out.println(result);
	}

	//Vérifies si toutes les propositions objectif ont été accompli
	public boolean isDone() {
		for(Proposition uneProposition : propositionButs) if(!uneProposition.isTrue()) return false;
		return true;
	}


}
