package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long pageId = Long.parseLong(request.getParameter("pageId") == null ? "1" : request.getParameter("pageId"));
		new BoardDao().updateViewCnt(pageId);
		
		BoardVo vo = new BoardDao().findById(pageId);
		request.setAttribute("board", vo);
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
		rd.forward(request, response);
	}

}
