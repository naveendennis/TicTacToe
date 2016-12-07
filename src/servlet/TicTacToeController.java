package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.State;
import core.MinMax;
import core.Player;
import util.StateUtil;

/**
 * Servlet implementation class TicTacToeController
 */
@WebServlet("/TicTacToeController")
public class TicTacToeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private State gameState;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TicTacToeController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		if (request.getSession().getAttribute("board") == null) {
			gameState = new State(3);
			gameState.setBoard(gameState.getBoard());
			request.getSession().setAttribute("board", gameState.getBoard());
		}
		gameState.setBoard((char[][]) request.getSession().getAttribute("board"));
		int moveIndex = Integer.parseInt(request.getParameter("playerMove"));
		String boardIndex = MinMax.getMove(moveIndex);
		String[] positions = boardIndex.split(",");
		gameState.setBoardValue('X', Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		Player winner = StateUtil.stateWonByPlayer(gameState);
		if (!StateUtil.isTerminalState(gameState)) {
			State nextState = MinMax.minMaxDecision(gameState, Player.MIN_PLAYER);
			int nextPosition = MinMax.getNextMove(gameState, nextState);
			request.getSession().setAttribute("board", nextState.getBoard());
			response.getWriter().write("Pos:" + nextPosition);
			winner = StateUtil.stateWonByPlayer(nextState);
			if(StateUtil.isTerminalState(nextState)){
				String user = (Player.MAX_PLAYER.equals(winner))?"Player":Player.MIN_PLAYER.equals(winner)? "Computer" : "No One"; 
				response.getWriter().write("Game won by "+user);
				request.getSession().setAttribute("board", null);
			}
			
		}else{
			String user = (Player.MAX_PLAYER.equals(winner))?"Player":Player.MIN_PLAYER.equals(winner)? "Computer" : "No One"; 
			response.getWriter().write("Game won by "+user);
			request.getSession().setAttribute("board", null);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
