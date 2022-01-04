package vue;

import modele.Agent;
import modele.Environnement;
import modele.Module;


public class Main {

	public static void main(String[] args) {
		// Initialisation 
		Environnement e = new Environnement(20, 45, 70, 20, 50);
		
		//Module without conflicts, adds or deletes
		Module hand_is_empty = new Module((float) 0,"hand_is_empty");
		Module sander_somewhere = new Module((float) 5,"sander_somewhere");
		Module sprayer_somewhere = new Module((float) 0,"sprayer_somewhere");
		Module operationnal = new Module((float) 0,"operationnal");
		Module board_somewhere = new Module((float) 0,"board_somewhere");
		
		//State Goals
		Module board_sanded = new Module((float) 0,"board_sanded");
		Module self_painted = new Module((float) 0,"self_painted");
		
		//State State
		Module sprayer_in_hand = new Module((float) 0,"sprayer_in_hand");
		Module sander_in_hand = new Module((float) 0,"sander_in_hand");
		Module board_in_hand = new Module((float) 0,"board_in_hand");
		Module board_in_vise = new Module((float) 0,"board_in_vise");
		
		Module pick_up_sprayer = new Module((float) 0,"pick_up_sprayer");
		pick_up_sprayer.addCondition(sprayer_somewhere.toString(), true);
		pick_up_sprayer.addCondition(hand_is_empty.toString(), true);
		pick_up_sprayer.addAjoutes(sprayer_in_hand.toString(), true);
		pick_up_sprayer.addDetruits(sprayer_somewhere.toString(), true);
		pick_up_sprayer.addDetruits(hand_is_empty.toString(), true);
		
		Module pick_up_sander = new Module((float) 0,"pick_up_sander");
		pick_up_sander.addCondition(sander_somewhere.toString(), true);
		pick_up_sander.addCondition(hand_is_empty.toString(), true);
		pick_up_sander.addAjoutes(sander_in_hand.toString(), true);
		pick_up_sander.addDetruits(sander_somewhere.toString(), true);
		pick_up_sander.addDetruits(hand_is_empty.toString(), true);
		
		Module pick_up_board = new Module((float) 0,"pick_up_board");
		pick_up_board.addCondition(board_somewhere.toString(), true);
		pick_up_board.addCondition(hand_is_empty.toString(), true);
		pick_up_board.addAjoutes(board_in_hand.toString(), true);
		pick_up_board.addDetruits(board_somewhere.toString(), true);
		pick_up_board.addDetruits(hand_is_empty.toString(), true);
		
		Module put_down_sprayer = new Module((float) 0,"put_down_sprayer");
		put_down_sprayer.addCondition(sprayer_in_hand.toString(), true);
		put_down_sprayer.addAjoutes(sprayer_somewhere.toString(), true);
		put_down_sprayer.addAjoutes(hand_is_empty.toString(), true);
		put_down_sprayer.addDetruits(sprayer_in_hand.toString(), true);
		
		Module put_down_sander = new Module((float) 0,"put_down_sander");
		put_down_sander.addCondition(sander_in_hand.toString(), true);
		put_down_sander.addAjoutes(sander_somewhere.toString(), true);
		put_down_sander.addAjoutes(hand_is_empty.toString(), true);
		put_down_sander.addDetruits(sander_in_hand.toString(), true);
		
		Module put_down_board = new Module((float) 0,"put_down_board");
		put_down_board.addCondition(board_in_hand.toString(), true);
		put_down_board.addAjoutes(board_somewhere.toString(), true);
		put_down_board.addAjoutes(hand_is_empty.toString(), true);
		put_down_board.addDetruits(board_in_hand.toString(), true);
		
		//deux fois le module pour check avec les deux mains, dans l'ex de pattie maes
		Module sand_board_in_hand = new Module((float) 0,"sand_board_in_hand");
		sand_board_in_hand.addCondition(operationnal.toString(), true);
		sand_board_in_hand.addCondition(board_in_hand.toString(), true);
		sand_board_in_hand.addCondition(sander_in_hand.toString(), true);
		sand_board_in_hand.addAjoutes(board_sanded.toString(), true);
		
		Module sand_board_in_vise = new Module((float) 0,"sand_board_in_vise");
		sand_board_in_vise.addCondition(operationnal.toString(), true);
		sand_board_in_vise.addCondition(board_in_vise.toString(), true);
		sand_board_in_vise.addCondition(sander_in_hand.toString(), true);
		sand_board_in_vise.addAjoutes(board_sanded.toString(), true);
		
		Module spray_paint_self = new Module((float) 0,"spray_paint_self");
		spray_paint_self.addCondition(operationnal.toString(), true);
		spray_paint_self.addCondition(sprayer_in_hand.toString(), true);
		spray_paint_self.addAjoutes(self_painted.toString(), true);
		spray_paint_self.addDetruits(operationnal.toString(), true);
		
		Module place_board_in_vise = new Module((float) 0,"place_board_in_vise");
		place_board_in_vise.addCondition(board_in_hand.toString(), true);
		place_board_in_vise.addAjoutes(hand_is_empty.toString(), true);
		place_board_in_vise.addAjoutes(board_in_vise.toString(), true);
		place_board_in_vise.addDetruits(board_in_hand.toString(), true);
		
		Agent a = new Agent();
		a.addPropositionInitiale(hand_is_empty.toString(), true);
		a.addPropositionInitiale(sander_somewhere.toString(), true);
		a.addPropositionInitiale(sprayer_somewhere.toString(), true);
		a.addPropositionInitiale(operationnal.toString(), true);
		a.addPropositionInitiale(board_somewhere.toString(), true);
		
		a.addBut(board_sanded.toString(), true);
		a.addBut(self_painted.toString(), true);
		
		//D�but de la simulation
		int time = 1;
		while(time < 2) {
			System.out.println("TIME : " + time);
			System.out.println(a.printState());
			//Calcul des activations des modules
			e.executable();
			// Diffusion d'�nergie d'activation
			e.updateEnergyV2();
			//Si executable, Execution d'un module
			Module executableModule = e.getModuleToExecute();
			if(executableModule != null) {
				executableModule.activateModule();
			}
			//Sinon diminution du seuil d'activation
			e.updateTheta();
			time++;
			System.out.println("");
		}
		
	}

}
