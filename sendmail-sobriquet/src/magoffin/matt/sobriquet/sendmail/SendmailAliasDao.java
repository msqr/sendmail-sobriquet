/* ===================================================================
 * SendmailAliasDao.java
 * 
 * Created 7/08/2015 9:56:46 am
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

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import magoffin.matt.dao.SearchCriteria;
import magoffin.matt.dao.SearchResults;
import magoffin.matt.sobriquet.api.AliasDao;
import magoffin.matt.sobriquet.api.AliasSearchResults;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.AliasSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ConditionCriteria;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQueryBuilder;

/**
 * Sendmail LDAP implementation of {@link AliasDao}.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasDao implements AliasDao {

	private final LdapTemplate ldapTemplate;

	@Value("${sendmail.defaultAliasGrouping}")
	private String defaultAliasGrouping;

	@Value("${sendmail.defaultCluster}")
	private String defaultCluster;

	/**
	 * Constructor.
	 * 
	 * @param ldapTemplate
	 *        The LdapTemplate object to use.
	 */
	@Autowired
	public SendmailAliasDao(LdapTemplate ldapTemplate) {
		super();
		this.ldapTemplate = ldapTemplate;
	}

	private void applyDefaults(SendmailAlias alias) {
		if ( alias == null ) {
			return;
		}
		if ( defaultAliasGrouping != null && alias.getGrouping() == null ) {
			alias.setGrouping(defaultAliasGrouping);
		}
		if ( defaultCluster != null && alias.getCluster() == null ) {
			alias.setCluster(defaultCluster);
		}
	}

	@Override
	public String store(Alias domainObject) {
		SendmailAlias alias = (domainObject instanceof SendmailAlias ? (SendmailAlias) domainObject
				: new SendmailAlias(domainObject));
		applyDefaults(alias);
		SendmailAlias existing = findFirst(alias.getId());
		if ( existing != null ) {
			ldapTemplate.update(alias);
		} else {
			ldapTemplate.create(alias);
		}
		return alias.getId();
	}

	private SendmailAlias findFirst(String id) {
		try {
			return ldapTemplate.findOne(query().where(SendmailAlias.SENDMAIL_MTA_KEY_ATTR).is(id),
					SendmailAlias.class);
		} catch ( EmptyResultDataAccessException e ) {
			// ignore this
			return null;
		}
	}

	@Override
	public Alias get(String id) {
		return findFirst(id);
	}

	@Override
	public void delete(Alias domainObject) {
		SendmailAlias alias = (domainObject instanceof SendmailAlias ? (SendmailAlias) domainObject
				: new SendmailAlias(domainObject));
		ldapTemplate.delete(alias);
	}

	@Override
	public SearchResults<AliasSearchResult> findByCriteria(SearchCriteria criteria) {
		LdapQueryBuilder builder = query();
		if ( criteria.getMaximumResultCount() != null ) {
			builder.countLimit(criteria.getMaximumResultCount());
		}
		Map<String, ?> filter = criteria.getSearchFilters();
		ContainerCriteria or = null;
		if ( filter.containsKey(SEARCH_FILTER_ALIAS) ) {
			or = builder.where(SendmailAlias.SENDMAIL_MTA_KEY_ATTR).like(
					"*" + filter.get(SEARCH_FILTER_ALIAS) + "*");
		}
		if ( filter.containsKey(SEARCH_FILTER_ACTUAL) ) {
			ConditionCriteria cond = null;
			if ( or != null ) {
				cond = or.or(SendmailAlias.SENDMAIL_MTA_ALIAS_VALUE_ATTR);
			} else {
				cond = builder.where(SendmailAlias.SENDMAIL_MTA_ALIAS_VALUE_ATTR);
			}
			cond.is(filter.get(SEARCH_FILTER_ACTUAL).toString());
		}
		List<SendmailAlias> found = null;
		try {
			found = ldapTemplate.find(builder, SendmailAlias.class);
		} catch ( IllegalStateException e ) {
			// find() throws this when no filter conditions have been specified.. ignore and continue
		}
		return new AliasSearchResults((found != null ? new ArrayList<AliasSearchResult>(found) : null),
				criteria);
	}

	/**
	 * Get the default alias grouping to use.
	 * 
	 * @return The default alias grouping.
	 */
	public String getDefaultAliasGrouping() {
		return defaultAliasGrouping;
	}

	/**
	 * Set a default alias grouping to apply when {@link #store(Alias)} is
	 * called and no grouping is defined on that instance.
	 * 
	 * @param defaultAliasGrouping
	 *        The default alias grouping value.
	 */
	public void setDefaultAliasGrouping(String defaultAliasGrouping) {
		this.defaultAliasGrouping = defaultAliasGrouping;
	}

	/**
	 * Get the default cluster to use.
	 * 
	 * @return The default cluster.
	 */
	public String getDefaultCluster() {
		return defaultCluster;
	}

	/**
	 * Set a default cluster to apply when {@link #store(Alias)} is called and
	 * no cluster is defined on that instance.
	 * 
	 * @param defaultCluster
	 *        The default cluster value.
	 */
	public void setDefaultCluster(String defaultCluster) {
		this.defaultCluster = defaultCluster;
	}

}
