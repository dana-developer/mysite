package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ListAction implements Action {
	final int OFFSET = 5;
	
	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));
		List<BoardVo> list = new BoardDao().findPageAll((currentPage - 1) * OFFSET, OFFSET);
		int cntPages = new BoardDao().countPages();
		int endPage = Math.ceilDiv(cntPages, OFFSET);
		
		request.setAttribute("list", list);
		request.setAttribute("prevPage", currentPage > 1);
		request.setAttribute("nextPage", currentPage < endPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("beginPage", 1);
		request.setAttribute("endPage", endPage);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
