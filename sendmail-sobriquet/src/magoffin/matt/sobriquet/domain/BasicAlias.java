/* ===================================================================
 * BasicAlias.java
 * 
 * Created 7/08/2015 10:02:28 am
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

import java.util.Collections;
import java.util.Set;
import magoffin.matt.dao.Identity;

/**
 * Basic implementation of {@link Alias} and {@link AliasSearchResult}.
 *
 * @author matt
 * @version 1.0
 */
public class BasicAlias implements Alias, AliasSearchResult {

	private String alias;
	private Set<String> actuals;

	/**
	 * Default constructor.
	 */
	public BasicAlias() {
		super();
	}

	/**
	 * Construct with a single alias and actual value.
	 * 
	 * @param alias
	 *        The alias.
	 * @param actual
	 *        The actual value.
	 */
	public BasicAlias(String alias, String actual) {
		super();
		setAlias(alias);
		setActual(actual);
	}

	@Override
	public String getId() {
		return getAlias();
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public String getActual() {
		Set<String> vals = getActuals();
		return (vals == null || vals.isEmpty() ? null : vals.iterator().next());
	}

	@Override
	public Set<String> getActuals() {
		return actuals;
	}

	@Override
	public int compareTo(Identity<String> o) {
		return alias.compareToIgnoreCase(o.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( !(obj instanceof Alias) ) {
			return false;
		}
		Alias other = (Alias) obj;
		if ( alias == null ) {
			if ( other.getId() != null ) {
				return false;
			}
		} else if ( !alias.equals(other.getId()) ) {
			return false;
		}
		return true;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setActuals(Set<String> actuals) {
		this.actuals = actuals;
	}

	public void setActual(String actual) {
		setActuals(actual == null ? null : Collections.singleton(actual));
	}

}
