package vue;

import modele.Agent;
import modele.Environnement;
import modele.Module;


public class Main {

	public static void main(String[] args) {
		Agent a = new Agent();
		Environnement e = new Environnement(0, 0, 0, 0, 0);
		Module arriverAuBout = new Module((float) 5);
		Module ouvrirPorte = new Module((float) 5);
		Module trouverClef = new Module((float) 5);
		Module trouverBatiment = new Module((float) 5);
		
		arriverAuBout.addCondition(ouvrirPorte.toString(), true);
		ouvrirPorte.addCondition(trouverClef.toString(), true);
		trouverClef.addCondition(trouverBatiment.toString(), true);
		
		
		



	}

}
