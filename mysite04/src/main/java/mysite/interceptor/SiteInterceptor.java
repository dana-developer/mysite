package mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {

	private SiteService siteService;
	private LocaleResolver localeResolver;

	public SiteInterceptor(
			SiteService siteService, 
			LocaleResolver localeResolver) {
		this.siteService = siteService;
		this.localeResolver = localeResolver;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("siteVo");
		
		if(siteVo == null) { // siteVo가 ServletContext's Attribute Map이 없는 경우 세팅
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		return true;
	}
	
}
