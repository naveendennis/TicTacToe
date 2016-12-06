package servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TicTacToeController
 */
@WebServlet("/TicTacToeController")
public class TicTacToeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicTacToeController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String values = request.getParameter("grid");
    	if(values == null || values.isEmpty()){
    		values = "<null>";
    	}
        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
//        response.getWriter().write("Hello World"+values.length());
        Map<String, String[]> rMap = request.getParameterMap();
        Set<Entry<String, String[]>> set = rMap.entrySet();
        for(Entry<String, String[]> obj : set){
        	response.getWriter().write(obj.getKey()+":"+obj.getValue());
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
