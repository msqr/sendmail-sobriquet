/* ===================================================================
 * SendmailAliasFileParser.java
 * 
 * Created 7/08/2015 3:57:09 pm
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

package magoffin.matt.sobriquet.sendmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.regex.Pattern;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.BasicAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parse Sendmail compatible alias text file into {@link Alias} instances.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasFileParser implements Iterable<Alias> {

	private static Logger LOG = LoggerFactory.getLogger(SendmailAliasFileParser.class);

	private final BufferedReader in;

	/**
	 * Construct from a reader.
	 * 
	 * @param reader
	 *        The reader to parse the data from.
	 */
	public SendmailAliasFileParser(Reader reader) {
		super();
		in = new BufferedReader(reader);
	}

	private static final Pattern COMMENT_PAT = Pattern.compile("^\\s*#");

	@Override
	public Iterator<Alias> iterator() {
		return new Iterator<Alias>() {

			private Alias next = null;

			private Alias parseAlias() throws IOException {
				String line = null;
				while ( true ) {
					line = in.readLine();
					if ( line == null ) {
						// EOF
						return null;
					}
					if ( COMMENT_PAT.matcher(line).find() ) {
						// comment
						continue;
					}

					// looking for <key>:<value>; whitespace allowed around either
					String[] kv = line.split("\\s*:\\s*", 2);
					if ( kv.length == 2 ) {
						kv[0] = kv[0].trim();
						kv[1] = kv[1].trim();
						if ( kv[0].length() > 0 && kv[1].length() > 0 ) {
							return new BasicAlias(kv[0], kv[1]);
						}
					}
				}
			}

			@Override
			public boolean hasNext() {
				if ( next != null ) {
					return true;
				}
				try {
					next = parseAlias();
				} catch ( IOException e ) {
					LOG.warn("Error reading alias data: " + e.getMessage());
				}
				return (next != null);
			}

			@Override
			public Alias next() {
				Alias n = (hasNext() ? next : null);
				next = null;
				return n;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
