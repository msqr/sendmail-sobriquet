/* ===================================================================
 * SendmailAliasDaoTests.java
 * 
 * Created 7/08/2015 10:15:25 am
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

import magoffin.matt.dao.SearchResults;
import magoffin.matt.sobriquet.api.AliasSearchCriteria;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.AliasSearchResult;
import magoffin.matt.sobriquet.domain.BasicAlias;
import magoffin.matt.sobriquet.sendmail.SendmailAlias;
import magoffin.matt.sobriquet.sendmail.SendmailAliasDao;
import magoffin.matt.sobriquet.test.BaseEmbeddedLdapTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Test cases for the {@link SendmailAliasDao} class.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasDaoTests extends BaseEmbeddedLdapTest {

	private static final String TEST_ALIAS_GROUPING = "test-grouping";
	private static final String TEST_CLUSTER = "test-cluster";

	private SendmailAliasDao dao;

	@Autowired
	private LdapTemplate ldapTemplate;

	@Before
	public void setup() {
		dao = new SendmailAliasDao(ldapTemplate);
		dao.setDefaultAliasGrouping(TEST_ALIAS_GROUPING);
		dao.setDefaultCluster(TEST_CLUSTER);
	}

	@Test
	public void createNew() {
		BasicAlias a = new BasicAlias("foo", "bar");
		String key = dao.store(a);
		Assert.assertEquals("Returned key", "foo", key);
	}

	@Test
	public void findNonExistent() {
		Alias result = dao.get("nope");
		Assert.assertNull("Result not found", result);
	}

	@Test
	public void findByKey() {
		BasicAlias a = new BasicAlias("foo", "bar");
		String key = dao.store(a);
		Assert.assertEquals("Returned key", "foo", key);

		Alias found = dao.get(key);
		Assert.assertTrue("SendmailAlias instance", found instanceof SendmailAlias);
		SendmailAlias smAlias = (SendmailAlias) found;
		Assert.assertEquals("Alias", a.getAlias(), smAlias.getAlias());
		Assert.assertEquals("Actuals", a.getActuals(), smAlias.getActuals());
		Assert.assertEquals("Alias grouping", TEST_ALIAS_GROUPING, smAlias.getGrouping());
		Assert.assertEquals("Alias cluster", TEST_CLUSTER, smAlias.getCluster());
	}

	@Test
	public void delete() {
		BasicAlias a = new BasicAlias("foo", "bar");
		String key = dao.store(a);
		Alias found = dao.get(key);
		Assert.assertTrue("SendmailAlias instance", found instanceof SendmailAlias);

		// now delete, and try to find again
		dao.delete(found);

		found = dao.get(key);
		Assert.assertNull("Deleted", found);
	}

	@Test
	public void searchForAliasNoResults() {
		AliasSearchCriteria crit = new AliasSearchCriteria("foo");
		SearchResults<AliasSearchResult> results = dao.findByCriteria(crit);
		Assert.assertNotNull("Returned results", results);
		Assert.assertEquals("Returned count", 0, results.getReturnedResultCount());
		Assert.assertEquals("Total results", Integer.valueOf(0), results.getTotalResultCount());
	}

	@Test
	public void searchForAliasNoMatch() {
		BasicAlias a = new BasicAlias("foo", "bar");
		dao.store(a);
		AliasSearchCriteria crit = new AliasSearchCriteria("bar");
		SearchResults<AliasSearchResult> results = dao.findByCriteria(crit);
		Assert.assertNotNull("Returned results", results);
		Assert.assertEquals("Returned count", 0, results.getReturnedResultCount());
		Assert.assertEquals("Total results", Integer.valueOf(0), results.getTotalResultCount());
	}

	@Test
	public void searchForAliasOneResultExactAlias() {
		BasicAlias a = new BasicAlias("foo", "bar");
		dao.store(a);

		AliasSearchCriteria crit = new AliasSearchCriteria("foo");
		SearchResults<AliasSearchResult> results = dao.findByCriteria(crit);
		Assert.assertNotNull("Returned results", results);
		Assert.assertEquals("Returned count", 1, results.getReturnedResultCount());
		Assert.assertEquals("Total results", Integer.valueOf(1), results.getTotalResultCount());

		AliasSearchResult r = results.getResults().iterator().next();
		Assert.assertNotNull("Returned result", r);
		Assert.assertEquals("Alias", a.getAlias(), r.getAlias());
		Assert.assertEquals("Actuals", a.getActuals(), r.getActuals());
	}

	@Test
	public void searchForAliasOneResultSubstringAlias() {
		BasicAlias a = new BasicAlias("foo.bam.doh", "bar");
		dao.store(a);

		AliasSearchCriteria crit = new AliasSearchCriteria("bam");
		SearchResults<AliasSearchResult> results = dao.findByCriteria(crit);
		Assert.assertNotNull("Returned results", results);
		Assert.assertEquals("Returned count", 1, results.getReturnedResultCount());
		Assert.assertEquals("Total results", Integer.valueOf(1), results.getTotalResultCount());

		AliasSearchResult r = results.getResults().iterator().next();
		Assert.assertNotNull("Returned result", r);
		Assert.assertEquals("Alias", a.getAlias(), r.getAlias());
		Assert.assertEquals("Actuals", a.getActuals(), r.getActuals());
	}

	@Test
	public void searchForAliasMultiResultSubstringAlias() {
		BasicAlias a1 = new BasicAlias("foo.bam.doh", "bar");
		dao.store(a1);
		BasicAlias a2 = new BasicAlias("foo.bam.dope", "bar");
		dao.store(a2);

		AliasSearchCriteria crit = new AliasSearchCriteria("bam");
		SearchResults<AliasSearchResult> results = dao.findByCriteria(crit);
		Assert.assertNotNull("Returned results", results);
		Assert.assertEquals("Returned count", 2, results.getReturnedResultCount());
		Assert.assertEquals("Total results", Integer.valueOf(2), results.getTotalResultCount());
	}

}
