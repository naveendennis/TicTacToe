package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.State;
import util.StateUtil;

public class MinMax {

	public State minMaxDecision(State currentState, Player player) {
		State chosenAction = null;
		
		List<State> successors = StateUtil.getSuccessors(currentState, player);
		Map<Integer, List<State>> utilityMap = new HashMap<Integer, List<State>>();
		List<State> states = new ArrayList<State>();
		int bestUtilityValue = (player == Player.MAX_PLAYER) ?-2147483648: 2147483647;
		for(State eachSuccessor: successors){
			int utilityValue = getMinMaxValue(eachSuccessor, player);
			states.clear();
			if (utilityMap.containsKey(utilityValue)){
				states = utilityMap.get(utilityValue);
			}else{
				states = new ArrayList<State>();
			}
			states.add(eachSuccessor);
			utilityMap.put(utilityValue, states);
			if(player == Player.MAX_PLAYER){
				bestUtilityValue = (bestUtilityValue < utilityValue)? utilityValue: bestUtilityValue;
			}else{
				bestUtilityValue = (bestUtilityValue > utilityValue)? utilityValue: bestUtilityValue;
			}
				
		}
		chosenAction = utilityMap.get(bestUtilityValue).get(0);
		return chosenAction;
	}

	public int getMinMaxValue(State currentState, Player player) {
		if (StateUtil.isTerminalState(currentState, player)) {
			return StateUtil.getUtility(currentState, player);
		}
		int utilityValue = (player == Player.MAX_PLAYER) ?-2147483648: 2147483647;
		for (State eachSuccessor: 
			StateUtil.getSuccessors(currentState, player)){
			if(Player.MAX_PLAYER == player){
				utilityValue = Math.max(utilityValue, getMinMaxValue(eachSuccessor, Player.MIN_PLAYER));
			}else{
				utilityValue = Math.min(utilityValue, getMinMaxValue(eachSuccessor, Player.MAX_PLAYER));
			}
		}
		return utilityValue;
		
		
	}

}