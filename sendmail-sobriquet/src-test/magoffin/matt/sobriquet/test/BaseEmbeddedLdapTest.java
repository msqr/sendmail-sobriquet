/* ===================================================================
 * BaseEmbeddedLdapTest.java
 * 
 * Created 7/08/2015 10:30:04 am
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

import org.junit.Rule;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base case for tests requiring an embedded LDAP server.
 *
 * @author matt
 * @version 1.0
 */
@ContextConfiguration(classes = LdapConfig.class)
@DirectoryServerConfiguration(ldifFiles = "magoffin/matt/sobriquet/test/embedded-ldap-test.ldif", schemaFiles = {
		"magoffin/matt/sobriquet/test/misc.schema", "magoffin/matt/sobriquet/test/sendmail.schema" }, authDN = LdapConfig.LDAP_USER_DN, authPassword = LdapConfig.LDAP_PASSWORD, baseDN = LdapConfig.LDAP_BASE_DN, port = LdapConfig.LDAP_PORT)
public abstract class BaseEmbeddedLdapTest extends BaseTest {

	@Rule
	public DirectoryServerRule directoryServerRule = new DirectoryServerRule();

}
