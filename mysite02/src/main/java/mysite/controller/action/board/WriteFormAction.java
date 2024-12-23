package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;

public class WriteFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("type", request.getParameter("type"));
		request.setAttribute("boardId", request.getParameter("boardId"));		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/write.jsp");
		rd.forward(request, response);
	}

}
