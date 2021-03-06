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
		int blanks = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == ' ') {
					blanks++;
					;
				}
			}
		}
		if (stateWonByPlayer(state) != null) {
			if (stateWonByPlayer(state).equals(player)) {
				utility += blanks * 1000;
			}
			Player opposition = (Player.MAX_PLAYER.equals(player) ? Player.MIN_PLAYER : Player.MAX_PLAYER);
			if (stateWonByPlayer(state).equals(opposition)) {
				utility -= blanks * 1000;
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
			if (searchCount >= size) {
				utility += 1000 * searchCount;
			}
			if (antiItemCount >= size) {
				utility -= 800 * antiItemCount;
			}
			if (searchCount == 0 && antiItemCount == size -1) {
				utility -= antiItemCount * 500;
			}
			if (searchCount == size -1 && antiItemCount == 0) {
				utility += searchCount * 500;
			}
			if (searchCount == 1 && antiItemCount == 0) {
				utility += searchCount * 300;
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
			if (searchCount >= size) {
				utility += 1000 * searchCount;
			}
			if (antiItemCount >= size) {
				utility -= 800 * antiItemCount;
			}
			if (searchCount == 0 && antiItemCount == size -1) {
				utility -= antiItemCount * 500;
			}
			if (searchCount == size -1 && antiItemCount == 0) {
				utility += searchCount * 500;
			}
			if (searchCount == 1 && antiItemCount == 0) {
				utility += searchCount * 300;
			}
			searchCount = 0;
			antiItemCount = 0;

		}
		int xincrementor = 1, yincrementor = 1;
		for (int iteration = 0, row = 0, column = 0; iteration < size -1; iteration++) {
			for (; true; row += xincrementor, column += yincrementor) {
				if (row == size) {
					break;
				} else if (board[row][column] == searchItem) {
					searchCount++;
				} else if (board[row][column] == antiItem) {
					antiItemCount++;
				}
			}
			if (searchCount == size) {
				utility += searchCount * 1000;
			}
			if (antiItemCount == size) {
				utility -= antiItemCount * 1000;
			}
			if (searchCount == 0 && antiItemCount == size -1) {
				utility -= antiItemCount * 500;
			}
			if (searchCount == size -1 && antiItemCount == 0) {
				utility += searchCount * 500;
			}
			if (searchCount == 1 && antiItemCount == 0) {
				utility += searchCount * 500;
			}
			xincrementor = 1;
			yincrementor = -1;
			row = 0;
			column = size - 1;
			searchCount = 0;
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

	public static boolean isTerminalState(State state) {
		return StateUtil.getSuccessors(state, Player.MAX_PLAYER).size() == 0
				|| StateUtil.stateWonByPlayer(state) != null;
	}

	public static boolean isDraw(State state) {
		return StateUtil.getSuccessors(state, Player.MAX_PLAYER).size() == 0;
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
		int xincrementor = 1;
		int yincrementor = 1;
		for (int iteration = 0, row = 0, column = 0; !result && iteration < 2; iteration++) {
			for (; true; row += xincrementor, column += yincrementor) {
				if (row == size) {
					break;
				} else if (board[row][column] == searchItem) {
					searchCount++;
				}
			}
			if (searchCount == size) {
				result = true;
				break;
			}
			xincrementor = 1;
			yincrementor = -1;
			row = 0;
			column = size - 1;
			searchCount = 0;
		}
		return result;
	}

	public static Player stateWonByPlayer(State state) {
		return (isGoalState(state, Player.MIN_PLAYER) ? Player.MIN_PLAYER
				: isGoalState(state, Player.MAX_PLAYER) ? Player.MAX_PLAYER : null);
	}

}
