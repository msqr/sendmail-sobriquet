/* ===================================================================
 * SendmailAliasFileParserTests.java
 * 
 * Created 7/08/2015 4:17:22 pm
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

package magoffin.matt.sobriquet.sendmail.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.sendmail.SendmailAliasFileParser;
import magoffin.matt.sobriquet.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * Test cases for the {@link SendmailAliasFileParser} class.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasFileParserTests extends BaseTest {

	@Test
	public void testParseEmptyReader() {
		ByteArrayInputStream byos = new ByteArrayInputStream(new byte[0]);
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(byos);
			SendmailAliasFileParser parser = new SendmailAliasFileParser(in);
			for ( @SuppressWarnings("unused")
			Alias a : parser ) {
				Assert.fail("Should not parse anything");
			}
		} finally {
			if ( in != null ) {
				try {
					in.close();
				} catch ( IOException e ) {
					// ignore
				}
			}
		}
	}

	private final String[][] SAMPLE_ALIASES = new String[][] {
			new String[] { "MAILER-DAEMON", "postmaster" }, new String[] { "postmaster", "root" },
			new String[] { "_dhcp", "root" }, new String[] { "_pflogd", "root" },
			new String[] { "auditdistd", "root" }, new String[] { "bin", "root" },
			new String[] { "bind", "root" }, new String[] { "daemon", "root" },
			new String[] { "games", "root" }, new String[] { "hast", "root" },
			new String[] { "kmem", "root" }, new String[] { "root", "dude" },
			new String[] { "pgsql", "dude" }, new String[] { "munin", "dude" },
			new String[] { "web-admin", "dude" }, new String[] { "sysadmin", "dude" },
			new String[] { "admin", "dude" }, new String[] { "foo.bar.com", "dude" }, };

	@Test
	public void testParseSampleFile() throws IOException {
		ClassPathResource r = new ClassPathResource("aliases.txt", getClass());
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(r.getInputStream());
			SendmailAliasFileParser parser = new SendmailAliasFileParser(in);
			int count = 0;
			for ( Alias a : parser ) {
				Assert.assertEquals("Alias " + count, SAMPLE_ALIASES[count][0], a.getAlias());
				Assert.assertEquals("Actual " + count, SAMPLE_ALIASES[count][1], a.getActual());
				Assert.assertEquals("Acutal count " + count, 1, a.getActuals().size());
				count++;
			}
			Assert.assertEquals("Parsed count", SAMPLE_ALIASES.length, count);
		} finally {
			if ( in != null ) {
				try {
					in.close();
				} catch ( IOException e ) {
					// ignore
				}
			}
		}
	}

}
