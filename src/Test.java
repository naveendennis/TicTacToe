import beans.State;
import core.MinMax;
import core.Player;
import util.StateUtil;

public class Test {
	public static void main(String[] args) {
		State state = new State(3);
		char[][] board = {{'X','O','X'},{'O', 'O', 'X'},{' ', 'X', ' '}};
		
		state.setBoard(board);
//		System.out.println(StateUtil.stateWonByPlayer(state));
//		System.out.println(StateUtil.getUtility(state, Player.MIN_PLAYER));
		State nextState = MinMax.minMaxDecision(state, Player.MIN_PLAYER);
	}

}
