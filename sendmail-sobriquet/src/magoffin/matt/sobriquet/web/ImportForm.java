/* ===================================================================
 * ImportForm.java
 * 
 * Created 9/08/2015 11:13:10 am
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

package magoffin.matt.sobriquet.web;

import java.util.List;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.util.TemporaryFile;

/**
 * Import alias form, supporting either a file attachment or direct content.
 *
 * @author matt
 * @version 1.0
 */
public class ImportForm {

	private TemporaryFile file;
	private String aliasData;
	private List<Alias> aliases;

	public TemporaryFile getFile() {
		return file;
	}

	public void setFile(TemporaryFile temporaryFile) {
		this.file = temporaryFile;
	}

	public String getAliasData() {
		return aliasData;
	}

	public void setAliasData(String aliasData) {
		this.aliasData = aliasData;
	}

	public List<Alias> getAliases() {
		return aliases;
	}

	public void setAliases(List<Alias> aliases) {
		this.aliases = aliases;
	}

}
