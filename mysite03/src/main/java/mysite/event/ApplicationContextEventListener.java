package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import jakarta.servlet.ServletContext;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
    private ServletContext servletContext;
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		   SiteService siteService = applicationContext.getBean(SiteService.class);
		   
		   // 1. sitevo를 빈으로 등록하기
		   SiteVo siteVo = siteService.getSite();
		   servletContext.setAttribute("siteVo", siteVo);		   
	}
}
