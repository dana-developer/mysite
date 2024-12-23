package mysite.controller;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.ModifyAction;
import mysite.controller.action.board.ModifyFormAction;
import mysite.controller.action.board.ViewAction;

import java.util.Map;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"list", new ListAction(),
			"view", new ViewAction(),
			"modifyform", new ModifyFormAction(),
			"modify", new ModifyAction()
	);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}
