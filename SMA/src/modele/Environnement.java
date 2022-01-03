package modele;

import java.util.ArrayList;

public class Environnement {
	
	private float niveauActivationPI;
	private float seuilActivationTHETA;
	private float energieInjecteeSousButGAMMA;
	private float energieInjecteePropositionVraiePHI;
	private float energiePriseButProtegeDELTA;
	
	private Agent agent;
	private Boolean but = false;
	private ArrayList<Module> listeModule;
	
	public Environnement(float niveauActivationPI, float seuilActivationTHETA, float energieInjecteeSousButGAMMA,
			float energieInjecteePropositionVraiePHI, float energiePriseButProtegeDELTA) {
		super();
		this.niveauActivationPI = niveauActivationPI;
		this.seuilActivationTHETA = seuilActivationTHETA;
		this.energieInjecteeSousButGAMMA = energieInjecteeSousButGAMMA;
		this.energieInjecteePropositionVraiePHI = energieInjecteePropositionVraiePHI;
		this.energiePriseButProtegeDELTA = energiePriseButProtegeDELTA;
		
		this.agent = new Agent();
		
		//Modules - lambda à 0 pour le moment, TO IMPLEMENT
		
		this.listeModule = new ArrayList<Module>();
		Module objet_cle = new Module(0);
		objet_cle.addCondition("presence_salle_1", true);
		this.listeModule.add(objet_cle);
		Module objet_outil = new Module(0);
		objet_outil.addCondition("presence_salle_2", true);
		this.listeModule.add(objet_outil);
		Module objet_meuble_1 = new Module(0);
		objet_meuble_1.addCondition("presence_salle_2", true);
		this.listeModule.add(objet_meuble_1);
		Module objet_meuble_2 = new Module(0);
		objet_meuble_2.addCondition("presence_salle_2", true);
		this.listeModule.add(objet_meuble_2);
		Module objet_porte = new Module(0);
		objet_porte.addAjoutes("presence_salle_2", true);
		this.listeModule.add(objet_porte);
	} 
}
