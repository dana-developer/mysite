package mysite.controller;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.ModifyAction;
import mysite.controller.action.board.ModifyFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

import java.util.Map;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"list", new ListAction(),
			"view", new ViewAction(),
			"modifyform", new ModifyFormAction(),
			"modify", new ModifyAction(),
			"writeform", new WriteFormAction(),
			"write", new WriteAction()
	);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}
