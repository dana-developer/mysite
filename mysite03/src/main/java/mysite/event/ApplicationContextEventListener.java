package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import mysite.service.SiteService;

public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		   SiteService siteService = applicationContext.getBean(SiteService.class);
		   
		   // 1. sitevo를 빈으로 등록하기
			String title = siteService.getSite().getTitle();
			
		   // 2. container에 등록한 빈을 jsp에 보이도록 (view resolver에서 설정)
		   
		   
	}
}
