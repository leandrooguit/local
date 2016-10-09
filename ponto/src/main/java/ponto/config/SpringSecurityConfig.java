package ponto.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import ponto.model.service.PontoSecurityAuthenticationProviderService;

@Configuration
@ComponentScan(basePackages = { "ponto.controller", "ponto.model" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PontoSecurityAuthenticationProviderService securityUserDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(securityUserDetailService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
				// .antMatchers("/pages/s/**").authenticated()//
				// hasRole("USER")//
				.antMatchers("/secured/**")
				.hasRole("USER")
				.anyRequest()
				.permitAll()
				//
				.and()
				.formLogin()
				//
				.loginProcessingUrl("/authenticate")
				.successHandler(new AjaxAuthenticationSuccessHandler())
				.usernameParameter("username")
				.passwordParameter("password")
				//
				.loginPage("/usuarios/login")
				.permitAll()
				.defaultSuccessUrl("/", true)
				//
				.and()
				.httpBasic()
				.and()
				//
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll().and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
				.csrf().csrfTokenRepository(csrfTokenRepository()).and()
				.exceptionHandling().accessDeniedPage("/401");
		// @formatter:on
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	private class CsrfHeaderFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest request,
				HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
					.getName());
			if (csrf != null) {
				Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
				String token = csrf.getToken();
				if (cookie == null || token != null
						&& !token.equals(cookie.getValue())) {
					cookie = new Cookie("XSRF-TOKEN", token);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			filterChain.doFilter(request, response);
		}
	}

	private class AjaxAuthenticationSuccessHandler extends
			SimpleUrlAuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request,
				HttpServletResponse response, Authentication authentication)
				throws IOException, ServletException {
			request.getSession().setMaxInactiveInterval(1800); // 30 minutos
		}
	}

}
