/* ===================================================================
 * AliasSearchResults.java
 * 
 * Created 7/08/2015 3:32:45 pm
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

import java.util.Collections;
import java.util.List;
import magoffin.matt.dao.SearchCriteria;
import magoffin.matt.dao.SearchResults;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.AliasSearchResult;

/**
 * Search results object for {@link Alias} searches.
 *
 * @author matt
 * @version 1.0
 */
public class AliasSearchResults implements SearchResults<AliasSearchResult> {

	private static final long serialVersionUID = -4664209937982372820L;

	private final List<AliasSearchResult> results;
	private final Integer totalResultCount;
	private final Integer startingOffset;
	private final Integer maximumResultCount;

	/**
	 * Construct for a full set of results.
	 * 
	 * @param results
	 *        The results.
	 * @param criteria
	 *        The search criteria, or <em>null</em> if not used.
	 */
	public AliasSearchResults(List<AliasSearchResult> results, SearchCriteria criteria) {
		super();
		this.results = (results == null ? Collections.<AliasSearchResult> emptyList() : results);
		this.totalResultCount = results.size();
		this.startingOffset = (criteria != null ? criteria.getStartingOffset() : Integer.valueOf(0));
		this.maximumResultCount = (criteria != null ? criteria.getMaximumResultCount() : null);
	}

	@Override
	public Integer getTotalResultCount() {
		return totalResultCount;
	}

	@Override
	public int getReturnedResultCount() {
		return results.size();
	}

	@Override
	public Integer getStartingOffset() {
		return startingOffset;
	}

	@Override
	public Integer getMaximumResultCount() {
		return maximumResultCount;
	}

	@Override
	public Iterable<AliasSearchResult> getResults() {
		return results;
	}

}
