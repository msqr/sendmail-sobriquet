/* ===================================================================
 * LdapConfig.java
 * 
 * Created 7/08/2015 10:16:44 am
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

package magoffin.matt.sobriquet.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * Configuration for LDAP integration tests.
 *
 * @author matt
 * @version 1.0
 */
@Configuration
@Profile("test")
public class LdapConfig {

	public static final String LDAP_USER_DN = "cn=Test,ou=Integration";
	public static final String LDAP_PASSWORD = "secret";
	public static final String LDAP_BASE_DN = "dc=example,dc=com";
	public static final int LDAP_PORT = 10389;

	@Bean
	public ContextSource contextSource() {
		LdapContextSource lcs = new LdapContextSource();
		lcs.setUserDn(LDAP_USER_DN);
		lcs.setPassword(LDAP_PASSWORD);
		lcs.setBase(LDAP_BASE_DN);
		lcs.setUrl("ldap://localhost:" + LDAP_PORT);
		return lcs;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		LdapTemplate l = new LdapTemplate(contextSource());

		return l;
	}

}
