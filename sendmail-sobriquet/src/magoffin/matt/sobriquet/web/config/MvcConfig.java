/* ===================================================================
 * MvcConfig.java
 * 
 * Created 7/08/2015 6:52:55 am
 * 
 * Copyright (c) 2015 Matt Magoffin.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 * 02111-1307 USA
 * ===================================================================
 */

package magoffin.matt.sobriquet.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * MVC configuration.
 * 
 * @author matt
 * @version 1.0
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "magoffin.matt.sobriquet.web" })
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Value("${i18n.cacheSecs}")
	private final int i18nCacheSecs = -1;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		// this turns off path variable truncation, to avoid treating a request like
		// PUT /alias/example.com
		// as /alias/example with a requested content type of "com" and instead use the default or Accept header
		matcher.setUseSuffixPatternMatch(false);
	}

	@Override
	public void configureContentNegotiation(
			org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer configurer) {
		// support rendering arbitrary path extensions always as JSON responses, e.g. PUT /alias/example.com
		configurer.favorPathExtension(false).useJaf(false)
				.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/jsp/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean(name = "messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("/WEB-INF/messages");
		resource.setDefaultEncoding("UTF-8");
		resource.setCacheSeconds(i18nCacheSecs);
		return resource;
	}

}
