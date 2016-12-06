package util;

import java.util.ArrayList;
import java.util.List;

import beans.State;
import core.Player;

public class StateUtil {
	public static int getUtility(State state, Player player) {
		char searchItem = (Player.MAX_PLAYER.equals(player)) ? 'X' : 'O';
		char antiItem = (searchItem == 'X') ? 'O' : 'X';
		int searchCount = 0;
		int antiItemCount = 0;
		char[][] board = state.getBoard();
		int size = board.length;
		int utility = 0;
		if (stateWonByPlayer(state) != null) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j]== ' ') {
						utility += 200;
					}
				}
			}
		}
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[column][row] == searchItem) {
					searchCount++;
				} else if (board[column][row] == antiItem) {
					antiItemCount++;
				}
			}
			if (searchCount == size) {
				utility += 1000 * searchCount;
			} else if (searchCount >= 2) {
				utility += 100;
			}
			if (antiItemCount == size) {
				utility -= 800 * antiItemCount;
			} else if (antiItemCount >= 2) {
				utility -= 100 * antiItemCount;
			}
			searchCount = 0;
			antiItemCount = 0;

		}
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[row][column] == searchItem) {
					searchCount++;
				} else if (board[row][column] == antiItem) {
					antiItemCount++;
				}
			}
			if (searchCount == size) {
				utility += 1000 * searchCount;
			} else if (searchCount >= 2) {
				utility += 100 * searchCount;
			}
			if (antiItemCount == size) {
				utility -= 800 * antiItemCount;
			} else if (antiItemCount >= 2) {
				utility -= 100 * antiItemCount;
			}
			searchCount = 0;
			antiItemCount = 0;

		}
		int incrementor = 1;
		boolean changeCond = false;
		for (int iteration = 0, row = 0, column = 0; iteration < 2; iteration++) {
			for (; true; row += incrementor, column += incrementor) {
				if (row == size) {
					changeCond = true;
					break;
				} else if (board[row][column] == searchItem) {
					searchCount++;
				} else if (board[row][column] == antiItem) {
					antiItemCount++;
				}
				if (changeCond && row == 0) {
					break;
				}
			}
			if (searchCount == size) {
				utility += searchCount * 1000;
			}else if(searchCount >= 2){
				utility += searchCount *100;
			}
			if (antiItemCount == size) {
				utility -= antiItemCount * 1000;
			}else if(antiItemCount >= 2){
				utility += antiItemCount * 100;
			}
			incrementor = -1;
			row += incrementor;
			column += incrementor;
		}
		return utility;
	}

	public static List<State> getSuccessors(State state, Player player) {
		List<State> successors = new ArrayList<State>();
		char[][] board = state.getBoard();
		int size = board.length;
		char insertItem = (Player.MAX_PLAYER.equals(player)) ? 'X' : 'O';
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
		return StateUtil.getSuccessors(state, player).size() == 0 || StateUtil.stateWonByPlayer(state) != null;
	}

	public static boolean isGoalState(State state, Player player) {
		boolean result = false;
		char searchItem = (player.equals(Player.MAX_PLAYER)) ? 'X' : 'O';
		char[][] board = state.getBoard();
		int size = board.length;
		int searchCount = 0;
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[column][row] == searchItem) {
					searchCount++;
				}
			}
			if (searchCount == size) {
				result = true;
				break;
			}
			searchCount = 0;

		}
		for (int row = 0; !result && row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (board[row][column] == searchItem) {
					searchCount++;
				}
			}
			if (searchCount == size) {
				result = true;
				break;
			}
			searchCount = 0;

		}
		int incrementor = 1;
		boolean changeCond = false;
		for (int iteration = 0, row = 0, column = 0; !result && iteration < 2; iteration++) {
			for (; true; row += incrementor, column += incrementor) {
				if (row == size) {
					changeCond = true;
					break;
				} else if (board[row][column] == searchItem) {
					searchCount++;
				}
				if (changeCond && row == 0) {
					break;
				}
			}
			if (searchCount == size) {
				result = true;
				break;
			}
			incrementor = -1;
			row += incrementor;
			column += incrementor;
		}
		return result;
	}

	public static Player stateWonByPlayer(State state) {
		return (isGoalState(state, Player.MIN_PLAYER) ? Player.MIN_PLAYER
				: isGoalState(state, Player.MAX_PLAYER) ? Player.MAX_PLAYER : null);
	}

}
