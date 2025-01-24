package mysite.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
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
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		System.out.println(userVo);
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(/*HttpSession session*/ Authentication authentication, Model model) {
		// 1. HttpSession을 사용하는 방법
//		SecurityContext sc = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//		Authentication authentication = sc.getAuthentication();
//		UserVo authUser = (UserVo) authentication.getPrincipal();
//		
		// 2. SecurityContextHolder(Spring Security ThreadLocal Helper Class)
//		SecurityContext sc = SecurityContextHolder.getContext();
//		Authentication authentication = sc.getAuthentication();
//		UserVo authUser = (UserVo) authentication.getPrincipal();
		
		UserVo authUser = (UserVo) authentication.getPrincipal(); // Authentication authentication 파라미터로 해주면 위의 관련 코드가 작성된다.
		
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("vo", userVo);
		
		return "user/update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Authentication authentication, UserVo userVo) {
		UserVo authUser = (UserVo) authentication.getPrincipal();
		
		userVo.setId(authUser.getId());
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
	
	@RequestMapping("/auth")
	public void auth() {
	}

	@RequestMapping("/logout")
	public void logout() {
	}
}
