package util;

import java.util.ArrayList;
import java.util.List;

import beans.State;
import core.Player;

public class StateUtil {
	public static int getUtility(State state, Player player) {
		char searchItem = (Player.MAX_PLAYER == player) ? 'X' : 'O';
		char antiItem = (searchItem == 'X') ? 'O' : 'X';
		int searchCount = 0;
		int antiItemCount = 0;
		char[][] board = state.getBoard();
		int size = board.length;
		int utility = 0;
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[row][column] == searchItem) {
					searchCount++;
				} else if (board[row][column] == antiItem) {
					antiItemCount++;
				}
			}
			if (searchCount >= 2) {
				utility += searchCount * 100;
			}
			if (antiItemCount >= 2) {
				utility -= antiItemCount * 100;
			}
			searchCount = 0;
			antiItemCount = 0;

		}
		int incrementor = 1;
		boolean changeCond = false;
		for (int iteration = 0, row = 0, column =0; iteration < 2; iteration++) {
			for (; true; row+= incrementor, column+= incrementor) {
				if (board[row][column] == searchItem) {
					searchCount++;
				} else if (board[row][column] == antiItem) {
					antiItemCount++;
				}
				if(changeCond && row < 0){
					break;
				}
				else if(row < size){
					changeCond = true;
					break;
				}
			}
			if (searchCount == size) {
				utility += searchCount * 1000;
			}
			if (antiItemCount == size) {
				utility -= antiItemCount * 1000;
			}
			incrementor=-1;
			row+= incrementor; 
			column+= incrementor;
		}
		return utility;
	}

	public static List<State> getSuccessors(State state, Player player) {
		List<State> successors = new ArrayList<State>();
		char[][] board = state.getBoard();
		int size = board.length;
		char insertItem = (Player.MAX_PLAYER == player) ? 'X' : 'O';
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[row][column] == ' ') {
					State newState = new State(state);
					newState.setBoardValue(insertItem, row, column);
					successors.add(newState);
				}
			}
		}
		return successors;
	}

	public static boolean isTerminalState(State state, Player player) {
		return getSuccessors(state, player).size() == 0;
	}

	public static boolean isGoalState(State state, Player player) {
		return false;
	}

}
