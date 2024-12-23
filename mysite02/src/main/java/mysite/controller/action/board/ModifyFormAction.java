package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ModifyFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageId = Integer.parseInt(request.getParameter("pageId") == null ? "1" : request.getParameter("pageId"));
		BoardVo vo = new BoardDao().findById(pageId);
		request.setAttribute("board", vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp");
		rd.forward(request, response);
	}

}
