/* ===================================================================
 * DirectoryServerConfiguration.java
 * 
 * Created 7/08/2015 12:30:13 pm
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Extension of {@code com.btmatthews.ldapunit.DirectoryServerConfiguration} to
 * support schema registration.
 *
 * @author matt
 * @version 1.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DirectoryServerConfiguration {

	/**
	 * The TCP port that the LDAP directory server will be configured to listen
	 * on.
	 *
	 * @return The TCP port.
	 */
	int port() default 10389;

	/**
	 * The DN that will be configured as the root of the LDAP directory.
	 *
	 * @return The DN.
	 */
	String baseDN() default "dc=btmatthews,dc=com";

	/**
	 * The DN that will be configured as the administrator account identifier.
	 *
	 * @return The DN.
	 */
	String authDN() default "uid=admin,ou=system";

	/**
	 * The password that will be configured as the authentication credentials
	 * for the administrator account.
	 *
	 * @return The password.
	 */
	String authPassword() default "secret";

	/**
	 * The locations of optional LDIF files that can be used to see the LDAP
	 * directory with an initial data set. The files may localed on the file
	 * system or the classpath. The classpath is checked first and then falls
	 * back to che file system if it was not found on the class path.
	 *
	 * @return An array of LDIF file paths.
	 */
	String[] ldifFiles() default {};

	/**
	 * The locations of optional LDAP schema files that can be used to configure
	 * the LDAP server with. The files may localed on the file system or the
	 * classpath. The classpath is checked first and then falls back to che file
	 * system if it was not found on the class path.
	 *
	 * @return An array of LDAP schema file paths.
	 */
	String[] schemaFiles() default {};

}
