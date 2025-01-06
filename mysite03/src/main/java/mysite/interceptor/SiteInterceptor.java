package mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;

public class SiteInterceptor implements HandlerInterceptor {

	private SiteService siteService;
	private LocaleResolver localeResolver;

	public SiteInterceptor(SiteService siteService, LocaleResolver localeResolver) {
		this.siteService = siteService;
		this.localeResolver = localeResolver;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String title = siteService.getSite().getTitle();
		request.setAttribute("headerTitle", title);
		
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		return true;
	}
	
}
