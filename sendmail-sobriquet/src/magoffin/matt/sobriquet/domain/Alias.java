/* ===================================================================
 * Alias.java
 * 
 * Created 7/08/2015 8:32:36 am
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

package magoffin.matt.sobriquet.domain;

import java.util.Set;
import magoffin.matt.dao.Entity;

/**
 * An individual alias value.
 *
 * @author matt
 * @version 1.0
 */
public interface Alias extends Entity<String> {

	/**
	 * Get the alias.
	 * 
	 * @return The alias value.
	 */
	String getAlias();

	/**
	 * Get the first actual value the alias maps to.
	 * 
	 * This is just a convenience method for getting the first value from
	 * {@link #getActuals()}.
	 * 
	 * @return The first actual value.
	 */
	String getActual();

	/**
	 * Get a set of acutal values the alias maps to.
	 * 
	 * @return A set of values the alias map to.
	 */
	Set<String> getActuals();

}
