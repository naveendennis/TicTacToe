import beans.State;
import core.MinMax;
import core.Player;
import util.StateUtil;

public class Test {
	public static void main(String[] args) {
		State state = new State(3);
		char[][] board = {{'O',' ',' '},{'O', 'X', ' '},{'X', ' ', ' '}};
		char[][] board1 = {{'O',' ','O'},{' ', 'X', ' '},{'X', ' ', 'O'}};
		state.setBoard(board1);
//		System.out.println(StateUtil.stateWonByPlayer(state));
//		System.out.println(StateUtil.getUtility(state, Player.MIN_PLAYER));
		System.out.println(MinMax.getMinMaxValue(state, Player.MAX_PLAYER)); 
	}

}
