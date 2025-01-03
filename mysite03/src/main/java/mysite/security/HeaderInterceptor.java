package mysite.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;

public class HeaderInterceptor implements HandlerInterceptor {
	
	private final SiteService siteService;
	
	public HeaderInterceptor(SiteService siteService) {
		this.siteService = siteService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String title = siteService.getSite().getTitle();
		request.setAttribute("headerTitle", title);
		
		return true;
	}
}
