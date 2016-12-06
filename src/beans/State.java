package beans;

public class State {
	char[][] board;
	int size;

	public State(int size) {
		this.board = new char[size][size];
		this.size = size;
	}

	public State(State state) {
		this.size = state.board.length;
		this.board = new char[this.size][this.size];
		for (int row = 0; row < this.size; row++) {
			for (int column = 0; column < this.size; column++) {
				board[row][column] = state.board[row][column];
			}
		}
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != '\0') {
					this.board[i][j] = board[i][j];
				} else {
					this.board[i][j] = ' ';
				}
			}
		}
		this.board = board;
	}

	public void setBoardValue(char value, int row, int column) {
		this.board[row][column] = value;
	}

}
