package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, UserVo userVo, Model model) { // 객체로 입력받는 것이 좋다.(validation 유효성 검사하기가 쉬워서)
		// login은 session과 보안 때문에 controller에서 하지 않는 것이 좋다.
		// session처리를 controller 밖으로 빼는 것 = spring security, interceptor
		UserVo authUser = userService.getUser(userVo.getEmail(), userVo.getPassword());
		
		if(authUser == null) {
			model.addAttribute("email", userVo.getEmail()); // 로그인 실패시 로그인의 이메일 입력창에 입력한 이메일을 유지하기 위해서
			model.addAttribute("result", "fail");
			return "user/login";
		}
		
		// login 처리
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// Access Control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("vo", userVo);
		
		return "user/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		// Access Control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		userVo.setId(authUser.getId());
		userService.update(userVo);
		
		return "redirect:/user/update";
	}
}
