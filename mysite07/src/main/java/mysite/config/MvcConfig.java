package mysite.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import mysite.event.ApplicationContextEventListener;
import mysite.interceptor.SiteInterceptor;

@SpringBootConfiguration
public class MvcConfig implements WebMvcConfigurer {
	
	//Locale Resolver
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver("lang");
		localeResolver.setCookieHttpOnly(false);
		
		return localeResolver;
	}
	
//	// View Resolver
//	@Bean
//	public ViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass(JstlView.class);
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jsp");
//		viewResolver.setExposeContextBeansAsAttributes(true);
//		viewResolver.setExposedContextBeanNames("site");
//		
//		return viewResolver;
//	}
	
	// ApplicationContextEventListener
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
	
	// Interceptors
	@Bean
	public HandlerInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/assets/**");
	}
}
