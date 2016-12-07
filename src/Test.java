import beans.State;
import core.MinMax;
import core.Player;
import util.StateUtil;

public class Test {
	public static void main(String[] args) {
		State state = new State(3);
		char[][] board = {{'O',' ',' '},{'O', 'X', ' '},{'X', ' ', ' '}};
		char[][] board1 = {{'X','O',' '},{' ', 'O', ' '},{' ', 'X', 'X'}};
		state.setBoard(board1);
//		System.out.println(StateUtil.stateWonByPlayer(state));
//		System.out.println(StateUtil.getUtility(state, Player.MIN_PLAYER));
		State nextState = MinMax.minMaxDecision(state, Player.MIN_PLAYER);
		for (int i = 0 ; i < 3; i++){
			for(int j = 0 ; j < 3; j++){
				
				System.out.print(nextState.getBoard()[i][j]+"\t");
			}
			System.out.println();
		}
//		System.out.println(StateUtil.getSuccessors(state, Player.MIN_PLAYER));
	}

}
