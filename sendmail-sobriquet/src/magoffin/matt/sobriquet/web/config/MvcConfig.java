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

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
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
		return resource;
	}

}
