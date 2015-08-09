/* ===================================================================
 * SendmailAliasLDIFGeneratorTests.java
 * 
 * Created 7/08/2015 5:18:55 pm
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

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.sendmail.SendmailAliasFileParser;
import magoffin.matt.sobriquet.sendmail.SendmailAliasLDIFGenerator;
import magoffin.matt.sobriquet.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 * Test cases for the {@link SendmailAliasLDIFGenerator} class.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasLDIFGeneratorTests extends BaseTest {

	@Test
	public void generateNothing() throws IOException {
		SendmailAliasLDIFGenerator gen = new SendmailAliasLDIFGenerator();
		ByteArrayOutputStream byos = new ByteArrayOutputStream();
		gen.generate(Collections.<Alias> emptyList(), byos);
		Assert.assertEquals("Nothing generated", 0, byos.size());
	}

	@Test
	public void generateSamples() throws IOException {
		ClassPathResource r = new ClassPathResource("aliases.txt", getClass());
		InputStreamReader in = null;
		ByteArrayOutputStream byos = new ByteArrayOutputStream();
		try {
			in = new InputStreamReader(r.getInputStream());
			SendmailAliasFileParser parser = new SendmailAliasFileParser(in);
			SendmailAliasLDIFGenerator gen = new SendmailAliasLDIFGenerator("ou=Test", "aliases",
					"example.com");
			gen.generate(parser, byos);
			String generated = new String(byos.toByteArray(), "US-ASCII");
			log.debug("Generated LDIF:\n{}", generated);
			String expected = FileCopyUtils.copyToString(new FileReader(new ClassPathResource(
					"aliases.ldif", getClass()).getFile()));
			Assert.assertEquals("Generated LDIF", expected, generated);
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
