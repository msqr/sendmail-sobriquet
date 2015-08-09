/* ===================================================================
 * AliasSearchCriteria.java
 * 
 * Created 7/08/2015 2:55:33 pm
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

package magoffin.matt.sobriquet.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magoffin.matt.dao.SearchCriteria;
import magoffin.matt.dao.SortDescriptor;
import magoffin.matt.sobriquet.domain.Alias;

/**
 * Search criteria for {@link Alias} entities.
 *
 * @author matt
 * @version 1.0
 */
public class AliasSearchCriteria implements SearchCriteria {

	private static final long serialVersionUID = -6984129948839127446L;

	private String alias;
	private String actual;
	private List<SortDescriptor> sortDescriptors;
	private Integer startingOffset;
	private Integer maximumResultCount;

	/**
	 * Default constructor.
	 */
	public AliasSearchCriteria() {
		super();
	}

	/**
	 * Construct with an alias criteria.
	 * 
	 * @param alias
	 *        The alias to search for.
	 */
	public AliasSearchCriteria(String alias) {
		super();
		setAlias(alias);
	}

	@Override
	public Map<String, ?> getSearchFilters() {
		LinkedHashMap<String, Object> f = new LinkedHashMap<String, Object>(2);
		if ( alias != null ) {
			f.put(AliasDao.SEARCH_FILTER_ALIAS, alias);
		}
		if ( actual != null ) {
			f.put(AliasDao.SEARCH_FILTER_ACTUAL, actual);
		}
		return f;
	}

	@Override
	public List<SortDescriptor> getSortDescriptors() {
		return sortDescriptors;
	}

	@Override
	public Integer getStartingOffset() {
		return startingOffset;
	}

	@Override
	public Integer getMaximumResultCount() {
		return maximumResultCount;
	}

	/**
	 * Get the alias to search for.
	 * 
	 * @return The alias.
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Set an alias to search for.
	 * 
	 * @param alias
	 *        The alias to search for.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Get an actual value to search for.
	 * 
	 * @return The actual value to search for.
	 */
	public String getActual() {
		return actual;
	}

	/**
	 * Set an actual value to search for.
	 * 
	 * @param actual
	 *        The actual value to search for.
	 */
	public void setActual(String actual) {
		this.actual = actual;
	}

	/**
	 * Set sort descriptors for the search results.
	 * 
	 * @param sortDescriptors
	 *        Sorting criteria.
	 */
	public void setSortDescriptors(List<SortDescriptor> sortDescriptors) {
		this.sortDescriptors = sortDescriptors;
	}

	/**
	 * Set a starting offset for the returned search results.
	 * 
	 * @param startingOffset
	 *        The starting offset.
	 */
	public void setStartingOffset(Integer startingOffset) {
		this.startingOffset = startingOffset;
	}

	/**
	 * Set a maximum result count.
	 * 
	 * @param maximumResultCount
	 *        The maximum result count.
	 */
	public void setMaximumResultCount(Integer maximumResultCount) {
		this.maximumResultCount = maximumResultCount;
	}

}
