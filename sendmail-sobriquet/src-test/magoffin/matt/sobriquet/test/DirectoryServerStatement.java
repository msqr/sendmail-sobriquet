/* ===================================================================
 * DirectoryServerStatement.java
 * 
 * Created 7/08/2015 12:27:57 pm
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.Statement;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import com.btmatthews.ldapunit.DirectoryServerUtils;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.ldif.LDIFException;

/**
 * Adaptation of {@code com.btmatthews.ldapunit.DirectoryServerStatement} that
 * supports custom LDAP schema registration.
 *
 * @author matt
 * @version 1.0
 */
public class DirectoryServerStatement extends Statement {

	/**
	 * The wrapped statement {@link Statement}.
	 */
	private final Statement base;
	/**
	 * The annotation that defines the directory server configuration.
	 */
	private final DirectoryServerConfiguration annotation;

	/**
	 * Initialise the wrapper statement that starts an embedded LDAP directory
	 * server and shuts it down before and after executing the wrapped
	 * statement.
	 *
	 * @param stmt
	 *        The wrapped statement.
	 * @param cfg
	 *        The directory server configuration.
	 */
	public DirectoryServerStatement(final Statement stmt, final DirectoryServerConfiguration cfg) {
		base = stmt;
		annotation = cfg;
	}

	/**
	 * Start an embedded LDAP directory server before executing the wrapped
	 * statement and shutdown the LDAP directory server after the wrapped
	 * statement completes.
	 *
	 * @throws Throwable
	 *         If there was an error starting the server, executing the wrapped
	 *         statement or shutting the wrapped server.
	 */
	@Override
	public void evaluate() throws Throwable {
		final InMemoryDirectoryServer server;
		server = startServer(annotation.port(), annotation.baseDN(), annotation.authDN(),
				annotation.authPassword(), annotation.ldifFiles(), annotation.schemaFiles());
		try {
			base.evaluate();
		} finally {
			DirectoryServerUtils.stopServer(server);
		}
	}

	public static InMemoryDirectoryServer startServer(final int port, final String baseDN,
			final String authDN, final String authPassword, final String[] ldifFiles,
			final String[] schemaFiles) throws LDIFException, LDAPException, IOException {
		final InMemoryListenerConfig listenerConfig = InMemoryListenerConfig.createLDAPConfig("default",
				port);
		final InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(new DN(baseDN));
		config.setListenerConfigs(listenerConfig);
		config.addAdditionalBindCredentials(authDN, authPassword);
		if ( schemaFiles != null && schemaFiles.length > 0 ) {
			// merge custom schemas with default standard schemas
			List<File> files = new ArrayList<File>(schemaFiles.length);
			for ( String name : schemaFiles ) {
				// try fs first
				Resource r = new FileSystemResource(name);
				if ( r.exists() ) {
					files.add(r.getFile());
				} else {
					r = new ClassPathResource(name);
					if ( r.exists() ) {
						files.add(r.getFile());
					}
				}
			}
			if ( files.size() > 0 ) {
				config.setSchema(Schema.mergeSchemas(Schema.getDefaultStandardSchema(),
						Schema.getSchema(files)));
			}
		}
		final InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
		server.add(new Entry(baseDN, new Attribute("objectclass", "domain", "top")));
		server.startListening();
		if ( ldifFiles != null ) {
			for ( final String ldifFile : ldifFiles ) {
				DirectoryServerUtils.loadData(server, ldifFile);
			}
		}
		return server;
	}
}
