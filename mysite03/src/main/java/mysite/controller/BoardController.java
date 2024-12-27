package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = true, defaultValue = "1") int currentPage,
			@RequestParam(value = "kwd", required = false) String keyword,
			Model model) {
		
		model.addAttribute("result", boardService.getContentsList(currentPage, keyword));
		return "board/list";
	}
	
	@RequestMapping(value = {"/view"}, method = RequestMethod.GET)
	public String view(@RequestParam(value = "boardId") Long boardId, Model model) {
		model.addAttribute("board", boardService.getContents(boardId));
		return "board/view";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
	public String delete(HttpSession session, @RequestParam(value = "boardId") Long boardId) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		boardService.deleteContents(boardId, authUser.getId());
		return "redirect:/board";
	}
	
	@RequestMapping(value = {"/write"}, method = RequestMethod.GET)
	public String write(HttpSession session, 
			Model model, 
			@RequestParam(value = "type") String type, 
			@RequestParam(value = "id", required = false) Long id) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		System.out.println(id);
		return "board/write";
	}
	
	@RequestMapping(value = {"/write"}, method = RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		boardService.addContents(vo, authUser.getId());
		return "redirect:/board";
	}
}
