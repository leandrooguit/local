package ponto.config;

import nz.net.ultraq.thymeleaf.JodaDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import ponto.controller.TipoConjuntoConverter;

import org.eclipse.jetty.server.Server;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@ComponentScan(basePackages = { "ponto.controller" })
@Configuration
@EnableWebMvc
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/pages/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("LEGACYHTML5");
		// templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(templateResolver());
		springTemplateEngine.addDialect(new LayoutDialect());
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		springTemplateEngine.addDialect(new JodaDialect());
		return springTemplateEngine;
	}

	@Bean
	@Description("Thymeleaf view resolver")
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		JettyEmbeddedServletContainerFactory jetty = new JettyEmbeddedServletContainerFactory();
		jetty.setContextPath("/pontoNovo");
		jetty.setPort(8080);
		jetty.addServerCustomizers(new JettyServerCustomizer() {

			@Override
			public void customize(Server server) {

			}

		});
		return jetty;
	}

	// @Bean
	// public FilterRegistrationBean filterRegistrationBean() {
	// FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	// HiddenHttpMethodFilter hiddenHttpMethodFilter = new
	// HiddenHttpMethodFilter();
	// registrationBean.setFilter(hiddenHttpMethodFilter);
	// registrationBean.addUrlPatterns("/*");
	// return registrationBean;
	// }
	//

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new GzipResourceResolver())
				.addResolver(new PathResourceResolver());
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new TipoConjuntoConverter());
		
		/*NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);*/
		
		return conversionService;
	}

}
