/* ===================================================================
 * AliasController.java
 * 
 * Created 7/08/2015 7:31:28 pm
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

import magoffin.matt.dao.SearchResults;
import magoffin.matt.sobriquet.api.AliasDao;
import magoffin.matt.sobriquet.api.AliasSearchCriteria;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.AliasSearchResult;
import magoffin.matt.sobriquet.domain.BasicAlias;
import magoffin.matt.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for {@link Alias} management.
 *
 * @author matt
 * @version 1.0
 */
@Controller
@RequestMapping("/api/v1")
public class AliasController {

	@Autowired
	private AliasDao aliasDao;

	/**
	 * Search for aliases.
	 * 
	 * @param criteria
	 *        The search criteria.
	 * @return The result response.
	 */
	@RequestMapping(method = RequestMethod.GET, path = { "/", "/find" })
	@ResponseBody
	public Response<SearchResults<AliasSearchResult>> search(AliasSearchCriteria criteria) {
		return Response.response(aliasDao.findByCriteria(criteria));
	}

	/**
	 * Get a specific alias by its key.
	 * 
	 * @param key
	 *        The alias to get.
	 * @return The alias response.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{key}")
	@ResponseBody
	public Response<Alias> getByKey(@PathVariable("key") String key) {
		return Response.response(aliasDao.get(key));
	}

	/**
	 * Store an alias.
	 * 
	 * @param key
	 *        The alias.
	 * @param value
	 *        The alias actual value.
	 * @return The response.
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/{key}", consumes = "text/plain")
	public Response<Object> add(@PathVariable("key") String key, @RequestBody String value) {
		BasicAlias alias = new BasicAlias(key, value);
		aliasDao.store(alias);
		return Response.response(null);
	}

	/**
	 * Delete an alias.
	 * 
	 * @param key
	 *        The alias to delete.
	 * @return The response.
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/{key}")
	public Response<Object> delete(@PathVariable("key") String key) {
		Alias alias = aliasDao.get(key);
		if ( alias != null ) {
			aliasDao.delete(alias);
		}
		return Response.response(null);
	}

}
