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

/**
 * Servlet implementation class TicTacToeController
 */
@WebServlet("/TicTacToeController")
public class TicTacToeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static State gameState = null;

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
		if (request.getSession().getAttribute("board") == null) {
			char[][] board = new char[3][3];
			gameState.setBoard(board);
			request.getSession().setAttribute("board", gameState.getBoard());
		}
		gameState.setBoard((char[][]) request.getSession().getAttribute("board"));
		int moveIndex = Integer.parseInt(request.getParameter("board"));
		String boardIndex = MinMax.getMove(moveIndex);
		String[] positions = boardIndex.split(",");
		gameState.setBoardValue('X', Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		State nextState = MinMax.minMaxDecision(gameState, Player.MIN_PLAYER);
		int nextPosition = MinMax.getNextMove(gameState, nextState);
		request.getSession().setAttribute("board", nextState.getBoard());
		response.getWriter().write(nextPosition);
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
