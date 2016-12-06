package beans;
public class State {
	char [][] board ;
	int size;
	
	public State(int size){
		this.board = new char[size][size];
		this.size = size;
	}
	
	public State(State state){
		this.size = state.board.length;
		this.board = new char[this.size][this.size];
		for(int row = 0 ; row < this.size ; row++){
			for(int column = 0 ; column < this.size ; column ++){
				board[row][column] = state.board[row][column];
			}
		}
	}
	
	public char[][] getBoard(){
		return board;
	}
	
	public void setBoardValue(char value, int row, int column){
		this.board[row][column] = value;
	}
	
}
