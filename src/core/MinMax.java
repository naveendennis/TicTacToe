package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.State;
import util.StateUtil;

public class MinMax {

	public static State minMaxDecision(State currentState, Player player) {
		State chosenAction = null;

		List<State> successors = StateUtil.getSuccessors(currentState, player);
		Map<Integer, List<State>> utilityMap = new HashMap<Integer, List<State>>();
		List<State> states = new ArrayList<State>();
		int bestUtilityValue = -2147483648;
		for (State eachSuccessor : successors) {
			Player opposition = (Player.MAX_PLAYER.equals(player) ? Player.MIN_PLAYER : Player.MAX_PLAYER);
			int utilityValue = getMinMaxValue(eachSuccessor, opposition, StateUtil.getUtility(eachSuccessor, player));
			if (utilityMap.containsKey(utilityValue)) {
				states = utilityMap.get(utilityValue);
			} else {
				states = new ArrayList<State>();
			}
			states.add(eachSuccessor);
			utilityMap.put(utilityValue, states);
			bestUtilityValue = (bestUtilityValue < utilityValue) ? utilityValue : bestUtilityValue;

		}
		List<State> listStates = utilityMap.get(bestUtilityValue);
		for (State eachState : listStates) {
			int searchCount = 0;
			int antiSearchCount = 0;
			int size = eachState.getBoard().length;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (eachState.getBoard()[i][j] == 'X') {
						antiSearchCount++;
					} else if (eachState.getBoard()[i][j] == 'O') {
						searchCount++;
					}
				}
			}
			if (searchCount == 0 && antiSearchCount == size -1) {
				continue;
			}
			searchCount = 0;
			antiSearchCount = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (eachState.getBoard()[j][i] == 'X') {
						antiSearchCount++;
					} else if (eachState.getBoard()[j][i] == 'O') {
						searchCount++;
					}
				}
			}
			if (searchCount == 0 && antiSearchCount == size -1) {
				continue;
			}
			searchCount = 0;
			antiSearchCount = 0;
			for (int i = 0, j = size - 1; i < size; i++, j--) {
				if (eachState.getBoard()[i][j] == 'X') {
					antiSearchCount++;
				} else if (eachState.getBoard()[i][j] == 'O') {
					searchCount++;
				}
			}
			if (searchCount == 0 && antiSearchCount == size -1) {
				continue;
			}
			searchCount = 0;
			antiSearchCount = 0;
			for (int i = 0, j = 0; i < size; i++, j++) {
				if (eachState.getBoard()[i][j] == 'X') {
					antiSearchCount++;
				} else if (eachState.getBoard()[i][j] == 'O') {
					searchCount++;
				}
			}
			if (searchCount == 0 && antiSearchCount == size -1) {
				continue;
			}
			chosenAction = eachState;
			break;
		}

		return (chosenAction == null) ? utilityMap.get(bestUtilityValue).get(0): chosenAction;

	}

	public static List<Integer> getEmptyPositions(State state) {
		List<Integer> positions = new ArrayList<Integer>();
		int index = 0;
		for (int i = 0; i < state.getBoard().length; i++) {
			for (int j = 0; j < state.getBoard().length; j++) {
				if (state.getBoard()[i][j] == ' ') {
					positions.add(index);
				}
				index++;
			}
		}
		return positions;
	}

	public static int getMinMaxValue(State currentState, Player player, int baseUtilityValue) {
		int utilityValue = -1;
		if (StateUtil.isTerminalState(currentState)) {
			utilityValue = baseUtilityValue + StateUtil.getUtility(currentState, Player.MIN_PLAYER);
			return utilityValue;
		} else {
			utilityValue = (player.equals(Player.MAX_PLAYER)) ? -2147483648 : 2147483647;
			for (State eachSuccessor : StateUtil.getSuccessors(currentState, player)) {
				if (Player.MAX_PLAYER.equals(player)) {
					utilityValue = Math.max(utilityValue,
							getMinMaxValue(eachSuccessor, Player.MIN_PLAYER, baseUtilityValue));
				} else {
					utilityValue = Math.min(utilityValue,
							getMinMaxValue(eachSuccessor, Player.MAX_PLAYER, baseUtilityValue));
				}
			}
		}
		return utilityValue;
	}

	public static String getMove(int moveIndex) {
		int cursor = 0;
		int i, j;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (moveIndex == cursor)
					return ((Integer) i).toString() + "," + ((Integer) j).toString();
				cursor++;
			}
		}
		return null;
	}

	public static int getNextMove(State currentState, State newState) {
		char[][] currentBoard = currentState.getBoard();
		char[][] newBoard = newState.getBoard();
		int position = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (currentBoard[i][j] != newBoard[i][j]) {
					return ++position;
				}
				position++;
			}
		}
		return position;
	}

}
