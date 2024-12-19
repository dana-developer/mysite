package mysite.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.action.main.MainAction;
import mysite.controller.action.user.JoinAction;
import mysite.controller.action.user.JoinFormAction;
import mysite.controller.action.user.JoinSuccessAction;
import mysite.controller.action.user.LoginAction;
import mysite.controller.action.user.LoginFormAction;
import mysite.controller.action.user.LogoutAction;
import mysite.controller.action.user.UpdateAction;
import mysite.controller.action.user.UpdateFormAction;

import java.io.IOException;
import java.util.Map;


@WebServlet("/user")
public class UserServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"joinform", new JoinFormAction(),
			"join", new JoinAction(),
			"joinsuccess", new JoinSuccessAction(),
			"loginform", new LoginFormAction(),
			"login", new LoginAction(),
			"logout", new LogoutAction(),
			"updateform", new UpdateFormAction(),
			"update", new UpdateAction()
	);
			
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new MainAction());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
