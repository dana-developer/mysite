package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control
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
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		if(password == "") {
			new UserDao().update(authUser.getId(), name, gender);
		} else {
			new UserDao().update(authUser.getId(), name, gender, password);
		}
		
		// 변경된 회원정보로 세션 변경
		UserVo vo = new UserDao().findById(authUser.getId());
		session.setAttribute("authUser", vo);
					
		response.sendRedirect(request.getContextPath());
	}

}
