package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mysite.security.Auth;

@Auth(role = "ADMIN") // 모든 메서드의 명시해야 하므로 
@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping({"", "/main"})
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
