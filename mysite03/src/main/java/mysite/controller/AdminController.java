package mysite.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final SiteService siteService;
	private final FileUploadService fileUploadService;
	
	@Autowired
    private ServletContext servletContext;
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
	}

	@RequestMapping({"", "/main"})
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
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
	
	@RequestMapping("/main/update")
	public String update(
		@RequestParam("title") String title, 
		@RequestParam("welcomeMessage") String welcomeMessage, 
		@RequestParam("description") String description, 
		@RequestParam("file1") MultipartFile file) {
				
		String url = Optional.ofNullable(fileUploadService.restore(file)).orElse("");
		
		SiteVo siteVo = new SiteVo();
		
		siteVo.setDescription(description);
		siteVo.setProfile(url);
		siteVo.setWelcome(welcomeMessage);
		siteVo.setTitle(title);
		
		siteService.updateSite(siteVo);
		servletContext.setAttribute("siteVo", siteVo);
		
		return "redirect:/admin";
	}
}
