package vue;

import modele.Agent;
import modele.Environnement;
import modele.Module;


public class Main {

	public static void main(String[] args) {
		// Initialisation 
		Agent a = new Agent();
		Environnement e = new Environnement(0, 0, 0, 0, 0);
		Module arriverAuBout = new Module((float) 5,"arriverAuBout");
		Module ouvrirPorte = new Module((float) 5,"ouvrirPorte");
		Module trouverClef = new Module((float) 5,"trouverClef");
		Module trouverBatiment = new Module((float) 5,"trouverBatiment");
		
		arriverAuBout.addCondition(ouvrirPorte.toString(), true);
		ouvrirPorte.addCondition(trouverClef.toString(), true);
		trouverClef.addCondition(trouverBatiment.toString(), true);
		
		//Début de la simulation
		int time = 1;
		//while(true) {
			System.out.println("TIME : " + time);
			System.out.println(a.printState());
			//Calcul des activations des modules
			
			// Diffusion d'énergie d'activation
			
			//Si executable, Execution d'un module
			Module executableModule = e.getModuleToExecute();
			if(executableModule != null) {
				
			}
			//Sinon diminution du seuil d'activation
			e.updateTheta();
		//}
		
		
		



	}

}
