package vue;

import modele.Agent;
import modele.Environnement;
import modele.Module;
import modele.Proposition;


public class Main {
	
	public static void Maes() {
		// Initialisation
				Agent unAgent = new Agent();

				float niveauActivationPI = 100, seuilActivationTHETA=20,  energieInjecteeSousButGAMMA = 80,  energieInjecteePropositionVraiePHI = 5, energiePriseButProtegeDELTA = 50;
				Environnement e = new Environnement(niveauActivationPI, seuilActivationTHETA, energieInjecteeSousButGAMMA, energieInjecteePropositionVraiePHI, energiePriseButProtegeDELTA,unAgent);

				Proposition sprayer_somewhere = new Proposition("sprayer_somewhere", true);
				Proposition hand_is_empty = new Proposition("hand_is_empty", true,2);
				Proposition sprayer_in_hand = new Proposition("sprayer_in_hand", false);
				Proposition sander_somewhere = new Proposition("sander_somewhere", true);
				Proposition sander_in_hand = new Proposition("sander_in_hand", false);
				Proposition board_somewhere = new Proposition("board_somewhere", true);
				Proposition board_in_hand = new Proposition("board_in_hand", false);
				Proposition operationnal = new Proposition("operationnal", true);
				Proposition board_in_vise = new Proposition("board_in_vise", false);
				Proposition self_painted = new Proposition("self_painted", false);
				Proposition board_sanded = new Proposition("board_sanded", false);


				Module pick_up_sprayer = new Module(0,"pick_up_sprayer");
				pick_up_sprayer.addCondition(sprayer_somewhere);
				pick_up_sprayer.addCondition(hand_is_empty);
				pick_up_sprayer.addAjoutes(sprayer_in_hand);
				pick_up_sprayer.addDetruits(sprayer_somewhere);
				pick_up_sprayer.addDetruits(hand_is_empty);

				Module pick_up_sander = new Module(0,"pick_up_sander");
				pick_up_sander.addCondition(sander_somewhere);
				pick_up_sander.addCondition(hand_is_empty);
				pick_up_sander.addAjoutes(sander_in_hand);
				pick_up_sander.addDetruits(sander_somewhere);
				pick_up_sander.addDetruits(hand_is_empty);

				Module pick_up_board = new Module(0,"pick_up_board");
				pick_up_board.addCondition(board_somewhere);
				pick_up_board.addCondition(hand_is_empty);
				pick_up_board.addAjoutes(board_in_hand);
				pick_up_board.addDetruits(board_somewhere);
				pick_up_board.addDetruits(hand_is_empty);

				Module put_down_sprayer = new Module(0,"put_down_sprayer");
				put_down_sprayer.addCondition(sprayer_in_hand);
				put_down_sprayer.addAjoutes(sprayer_somewhere);
				put_down_sprayer.addAjoutes(hand_is_empty);
				put_down_sprayer.addDetruits(sprayer_in_hand);

				Module put_down_sander = new Module(0,"put_down_sander");
				put_down_sander.addCondition(sander_in_hand);
				put_down_sander.addAjoutes(sander_somewhere);
				put_down_sander.addAjoutes(hand_is_empty);
				put_down_sander.addDetruits(sander_in_hand);

				Module put_down_board = new Module(0,"put_down_board");
				put_down_board.addCondition(board_in_hand);
				put_down_board.addAjoutes(board_somewhere);
				put_down_board.addAjoutes(hand_is_empty);
				put_down_board.addDetruits(board_in_hand);

				Module sand_board_in_hand = new Module(0,"sand_board_in_hand");
				sand_board_in_hand.addCondition(operationnal);
				sand_board_in_hand.addCondition(board_in_hand);
				sand_board_in_hand.addCondition(sander_in_hand);
				sand_board_in_hand.addAjoutes(board_sanded);

				Module sand_board_in_vise = new Module(0,"sand_board_in_vise");
				sand_board_in_vise.addCondition(operationnal);
				sand_board_in_vise.addCondition(board_in_vise);
				sand_board_in_vise.addCondition(sander_in_hand);
				sand_board_in_vise.addAjoutes(board_sanded);

				Module spray_paint_self = new Module(0,"spray_paint_self");
				spray_paint_self.addCondition(operationnal);
				spray_paint_self.addCondition(sprayer_in_hand);
				spray_paint_self.addAjoutes(self_painted);
				spray_paint_self.addDetruits(operationnal);

				Module place_board_in_vise = new Module(0,"place_board_in_vise");
				place_board_in_vise.addCondition(board_in_hand);
				place_board_in_vise.addAjoutes(hand_is_empty);
				place_board_in_vise.addAjoutes(board_in_vise);
				place_board_in_vise.addDetruits(board_in_hand);

				unAgent.addPropositionInitiale(hand_is_empty);

				unAgent.addPropositionInitiale(sander_somewhere);
				unAgent.addPropositionInitiale(sprayer_somewhere);
				unAgent.addPropositionInitiale(operationnal);
				unAgent.addPropositionInitiale(board_somewhere);

				unAgent.addBut(board_sanded);
				unAgent.addBut(self_painted);

				e.calculLinkBetweenModules();
				//D�but de la simulation
				StatsCreator sc = new StatsCreator(e.getAllModules(),e.getAllTheta());

				while(!unAgent.isDone()) {
					System.out.println("\n\nTIME : " + e.time);
					//unAgent.printState();
					//Calcul des activations des modules
					e.executable();
					// Diffusion d'énergie d'activation

					e.updateEnergy(false);
					
					//e.afficherEtatActivation();

					//Si executable, Execution d'un module
					e.execute();
					e.updateTheta();
					e.time++;
				}
				System.out.println("\nLes missions ont été terminés en " + e.time );
				sc.exportToExcel("test1.txt");
				sc.exportToPng("Seuils4");
	}
	
	public void scenario() {
		float niveauActivationPI = 100, seuilActivationTHETA=20,  energieInjecteeSousButGAMMA = 80,  energieInjecteePropositionVraiePHI = 5, energiePriseButProtegeDELTA = 50;
		Agent unAgent = new Agent();

		Environnement e = new Environnement(niveauActivationPI, seuilActivationTHETA, energieInjecteeSousButGAMMA, energieInjecteePropositionVraiePHI, energiePriseButProtegeDELTA,unAgent);
		StatsCreator sc = new StatsCreator(e.getAllModules(),e.getAllTheta());
		
		Proposition clef_quelquepart = new Proposition("clef_quelquepart", true);
		Proposition clef_dans_la_main = new Proposition("clef_dans_la_main", false,2);
		Proposition etre_proche_de_la_clef = new Proposition("etre_proche_de_la_clef", false);
		Proposition porte_est_ferme = new Proposition("porte_est_ferme", true);
		Proposition porte_est_ouverte = new Proposition("porte_est_ouverte", false);
		Proposition meuble_non_assemble = new Proposition("meuble_non_assemble", true);
		Proposition meuble_est_assemble = new Proposition("meuble_est_assemble", false);
		Proposition partie1_du_meuble_quelquepart = new Proposition("partie1_du_meuble_quelquepart", true);
		Proposition partie1_du_meuble_dans_la_main = new Proposition("partie1_du_meuble_dans_la_main", false);
		Proposition etre_proche_de_partie1 = new Proposition("etre_proche_de_partie1", false);
		Proposition etre_proche_de_partie2 = new Proposition("etre_proche_de_partie2", false);
		Proposition partie2_du_meuble_quelquepart = new Proposition("partie2_du_meuble_quelquepart", true);
		Proposition partie2_du_meuble_dans_la_main = new Proposition("partie2_du_meuble_dans_la_main", false);
		Proposition A_Cherche_Clef = new Proposition("A_Cherche_Clef", false);
		Proposition A_Cherche_partie1 = new Proposition("A_Cherche_partie1", false);
		Proposition A_Cherche_partie2 = new Proposition("A_Cherche_partie2", false);
		
		Module ouvrir_porte = new Module(0,"pick_up_sprayer");
		ouvrir_porte.addCondition(clef_dans_la_main);
		ouvrir_porte.addCondition(porte_est_ferme);
		ouvrir_porte.addAjoutes(porte_est_ouverte);
		ouvrir_porte.addDetruits(porte_est_ferme);
		
		Module chercher_clef = new Module(0,"pick_up_sprayer");
		chercher_clef.addCondition(sprayer_somewhere);
		chercher_clef.addCondition(hand_is_empty);
		chercher_clef.addAjoutes(sprayer_in_hand);
		chercher_clef.addDetruits(sprayer_somewhere);
		chercher_clef.addDetruits(hand_is_empty);
		
		Module chercher_partie1 = new Module(0,"pick_up_sprayer");
		chercher_partie1.addCondition(sprayer_somewhere);
		chercher_partie1.addCondition(hand_is_empty);
		chercher_partie1.addAjoutes(sprayer_in_hand);
		chercher_partie1.addDetruits(sprayer_somewhere);
		chercher_partie1.addDetruits(hand_is_empty);
		
		Module chercher_partie2 = new Module(0,"pick_up_sprayer");
		chercher_partie2.addCondition(sprayer_somewhere);
		chercher_partie2.addCondition(hand_is_empty);
		chercher_partie2.addAjoutes(sprayer_in_hand);
		chercher_partie2.addDetruits(sprayer_somewhere);
		chercher_clef.addDetruits(hand_is_empty);
		
		Module prendre_clef = new Module(0,"pick_up_sprayer");
		prendre_clef.addCondition(sprayer_somewhere);
		prendre_clef.addCondition(hand_is_empty);
		prendre_clef.addAjoutes(sprayer_in_hand);
		prendre_clef.addDetruits(sprayer_somewhere);
		prendre_clef.addDetruits(hand_is_empty);
		
		Module prendre_partie1 = new Module(0,"pick_up_sprayer");
		prendre_clef.addCondition(sprayer_somewhere);
		prendre_clef.addCondition(hand_is_empty);
		prendre_clef.addAjoutes(sprayer_in_hand);
		prendre_clef.addDetruits(sprayer_somewhere);
		prendre_clef.addDetruits(hand_is_empty);
		
		Module prendre_partie2 = new Module(0,"pick_up_sprayer");
		prendre_clef.addCondition(sprayer_somewhere);
		prendre_clef.addCondition(hand_is_empty);
		prendre_clef.addAjoutes(sprayer_in_hand);
		prendre_clef.addDetruits(sprayer_somewhere);
		prendre_clef.addDetruits(hand_is_empty);
		
		Module aller_vers_clef = new Module(0,"pick_up_sprayer");
		aller_vers_clef.addCondition(sprayer_somewhere);
		aller_vers_clef.addCondition(hand_is_empty);
		aller_vers_clef.addAjoutes(sprayer_in_hand);
		aller_vers_clef.addDetruits(sprayer_somewhere);
		aller_vers_clef.addDetruits(hand_is_empty);
		
		Module aller_vers_partie1 = new Module(0,"pick_up_sprayer");
		aller_vers_partie1.addCondition(sprayer_somewhere);
		aller_vers_partie1.addCondition(hand_is_empty);
		aller_vers_partie1.addAjoutes(sprayer_in_hand);
		aller_vers_partie1.addDetruits(sprayer_somewhere);
		aller_vers_partie1.addDetruits(hand_is_empty);
		
		Module aller_vers_partie2 = new Module(0,"pick_up_sprayer");
		aller_vers_partie2.addCondition(sprayer_somewhere);
		aller_vers_partie2.addCondition(hand_is_empty);
		aller_vers_partie2.addAjoutes(sprayer_in_hand);
		aller_vers_partie2.addDetruits(sprayer_somewhere);
		aller_vers_partie2.addDetruits(hand_is_empty);
		
		Module assembler_meuble = new Module(0,"pick_up_sprayer");
		assembler_meuble.addCondition(sprayer_somewhere);
		assembler_meuble.addCondition(hand_is_empty);
		assembler_meuble.addAjoutes(sprayer_in_hand);
		assembler_meuble.addDetruits(sprayer_somewhere);
		assembler_meuble.addDetruits(hand_is_empty);


		

		while(!unAgent.isDone()) {
			System.out.println("\n\nTIME : " + e.time);
			//unAgent.printState();
			//Calcul des activations des modules
			e.executable();
			// Diffusion d'énergie d'activation

			e.updateEnergy(false);
			
			//e.afficherEtatActivation();

			//Si executable, Execution d'un module
			e.execute();
			e.updateTheta();
			e.time++;
		}
		System.out.println("\nLes missions ont été terminés en " + e.time );
		sc.exportToExcel("test1.txt");
		sc.exportToPng("Seuils4");
		
		
		
	}

	public static void main(String[] args) {
		Maes();

	}

}
