/* ===================================================================
 * SendmailIOService.java
 * 
 * Created 9/08/2015 11:39:32 am
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

import java.io.Reader;
import magoffin.matt.sobriquet.api.IOService;
import magoffin.matt.sobriquet.domain.Alias;

/**
 * Sendmail implementation of {@link IOService}.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailIOService implements IOService {

	@Override
	public Iterable<Alias> parseAliasRecords(Reader reader) {
		return new SendmailAliasFileParser(reader);
	}

}
