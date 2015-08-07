/* ===================================================================
 * ServiceConfig.java
 * 
 * Created 7/08/2015 6:51:07 am
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

import magoffin.matt.sobriquet.api.AliasDao;
import magoffin.matt.sobriquet.sendmail.SendmailAliasDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Configuration for app services.
 *
 * @author matt
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:env.properties")
public class ServiceConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer pspc() {
		PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
		return result;
	}

	@Value("${ldapUrl}")
	private String ldapUrl;

	@Value("${ldapUserDn}")
	private String ldapUserDn;

	@Value("${ldapPassword}")
	private String ldapPassword;

	@Value("${ldapBaseDn}")
	private String ldapBaseDn;

	@Bean
	public ContextSource contextSource() {
		LdapContextSource lcs = new LdapContextSource();
		lcs.setUserDn(ldapUserDn);
		lcs.setPassword(ldapPassword);
		lcs.setBase(ldapBaseDn);
		lcs.setUrl(ldapUrl);
		return lcs;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(contextSource());
	}

	@Bean
	public AliasDao aliasDao() {
		SendmailAliasDao dao = new SendmailAliasDao(ldapTemplate());

		return dao;
	}

}
