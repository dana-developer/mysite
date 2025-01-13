package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}
	
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", guestbookService.getContentsList());
		return "guestbook/list";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.GET)
	public String delete(@RequestParam("id") Long id) {
		return "guestbook/delete";
	}
	
	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public String delete(GuestbookVo vo) {
		guestbookService.deleteContents(vo.getId(), vo.getPassword());
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value = {"/add"}, method = RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook/list";
	}
}
