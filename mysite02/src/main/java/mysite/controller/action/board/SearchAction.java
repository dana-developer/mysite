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

public class SearchAction implements Action {
	final int OFFSET = 5;
	final int PAGER_OFFSET = 5;

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("kwd");
		int currentPage = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));
		List<BoardVo> list = new BoardDao().findPageAllByKeyword((currentPage - 1) * OFFSET, OFFSET, keyword);
		
		int cntPages = new BoardDao().countPagesByKeyword(keyword);
		int totalPages = Math.ceilDiv(cntPages, OFFSET);
		int pageGroupNum = (currentPage / PAGER_OFFSET) + 1;
		
		if(currentPage % PAGER_OFFSET == 0) {
			pageGroupNum = (currentPage / PAGER_OFFSET);
		}
		int beginPage = (pageGroupNum-1) * PAGER_OFFSET + 1;
		
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", beginPage+(PAGER_OFFSET-1));
		request.setAttribute("startPage", 1);
		request.setAttribute("totalPage", totalPages);
		request.setAttribute("cntPages", cntPages);
		request.setAttribute("keyword", keyword);
				
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
