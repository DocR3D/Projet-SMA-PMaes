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
		
		
		
	}

	public static void main(String[] args) {
		Maes();

	}

}
