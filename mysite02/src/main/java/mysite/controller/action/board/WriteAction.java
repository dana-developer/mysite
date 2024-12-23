package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
				
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		String boardId = request.getParameter("boardId");
				
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserId(authUser.getId());
		
		if(type.equals("reply") && boardId != null) {
			BoardVo originVo = new BoardDao().findById(Long.parseLong(boardId));
			vo.setgNo(originVo.getgNo());
			vo.setoNo(originVo.getoNo());
			vo.setDepth(originVo.getDepth());
			
			new BoardDao().updateONo(vo);
			new BoardDao().insertReply(vo);
		} else {
			int gNo = new BoardDao().findMaxGroupId();
			new BoardDao().insertOrigin(vo, gNo+1);
		}
		
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
