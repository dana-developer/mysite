package mysite.controller;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Controller
public class MainController {
	
	private final SiteService siteService;
	private LocaleResolver localeResolver;
	
	public MainController(SiteService siteService, LocaleResolver localeResolver) {
		this.siteService = siteService;
		this.localeResolver = localeResolver;
	}
	
	@RequestMapping({"/", "/main"})
	public String index(Model model, HttpServletRequest request) {
		SiteVo siteVo = siteService.getSite();
		String lang = localeResolver.resolveLocale(request).getLanguage();
		
		model.addAttribute("siteVo", siteVo);
		model.addAttribute("lang", lang);
		
		return "main/index";
	}
}
