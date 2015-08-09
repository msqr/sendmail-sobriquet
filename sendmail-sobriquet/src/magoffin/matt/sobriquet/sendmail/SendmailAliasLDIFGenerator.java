/* ===================================================================
 * SendmailAliasLDIFGenerator.java
 * 
 * Created 7/08/2015 4:38:26 pm
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

import java.io.IOException;
import java.io.OutputStream;
import magoffin.matt.sobriquet.domain.Alias;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldif.LDIFWriter;

/**
 * Generate LDIF data from {@link Alias} objects.
 *
 * @author matt
 * @version 1.0
 */
public class SendmailAliasLDIFGenerator {

	private String baseDN;
	private String defaultAliasGrouping;
	private String defaultCluster;

	/**
	 * Default constructor.
	 */
	public SendmailAliasLDIFGenerator() {
		super();
	}

	/**
	 * Construct with a base distinguished name.
	 * 
	 * @param dn
	 *        The base distinguished name to use.
	 */
	public SendmailAliasLDIFGenerator(String dn) {
		super();
		baseDN = dn;
	}

	/**
	 * Construct with a base distinguished name.
	 * 
	 * @param dn
	 *        The base distinguished name to use.
	 * @param defaultAliasGrouping
	 *        The default alias grouping to apply.
	 * @param defaultCluster
	 *        The default cluster to apply.
	 */
	public SendmailAliasLDIFGenerator(String dn, String defaultAliasGrouping, String defaultCluster) {
		super();
		baseDN = dn;
		this.defaultAliasGrouping = defaultAliasGrouping;
		this.defaultCluster = defaultCluster;
	}

	/**
	 * Generate LDIF from an iterator of {@link Alias} objects.
	 * 
	 * @param input
	 *        The aliases to generate LDIF from. The stream will <b>not</b> be
	 *        closed.
	 * @param out
	 *        The output stream to write the LDIF data to.
	 * @throws IOException
	 *         If any IO error occurss.
	 */
	public void generate(Iterable<Alias> input, OutputStream out) throws IOException {
		final LDIFWriter writer = new LDIFWriter(out);
		try {
			for ( Alias a : input ) {
				Entry e;
				String dn = SendmailAlias.SENDMAIL_MTA_KEY_ATTR + "=" + a.getAlias();
				if ( baseDN != null ) {
					dn += "," + baseDN;
				}
				e = new Entry(dn);
				e.addAttribute("objectClass", SendmailAlias.SENDMAIL_OBJECT_CLASS);
				e.addAttribute(SendmailAlias.SENDMAIL_MTA_KEY_ATTR, a.getAlias());
				e.addAttribute(SendmailAlias.SENDMAIL_MTA_ALIAS_VALUE_ATTR, a.getActuals());

				String grouping = null;
				String cluster = null;
				if ( a instanceof SendmailAlias ) {
					SendmailAlias sma = (SendmailAlias) a;
					grouping = sma.getGrouping();
					cluster = sma.getCluster();
				}
				if ( grouping == null ) {
					grouping = defaultAliasGrouping;
				}
				if ( grouping != null ) {
					e.addAttribute(SendmailAlias.SENDMAIL_MTA_ALIAS_GROUPING_ATTR, grouping);
				}
				if ( cluster == null ) {
					cluster = defaultCluster;
				}
				if ( cluster != null ) {
					e.addAttribute(SendmailAlias.SENDMAIL_MTA_CLUSTER_ATTR, cluster);
				}
				writer.writeEntry(e);
			}
		} finally {
			writer.flush();
		}
	}

	/**
	 * Get a base distinguished name to add to exported LDIF entries.
	 * 
	 * @return The base distinguished name.
	 */
	public String getBaseDN() {
		return baseDN;
	}

	/**
	 * Set a base distinguished name to add to exported LDIF entries.
	 * 
	 * @param baseDN
	 *        The base distinguished name.
	 */
	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
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
	 * Set a default alias grouping to apply when
	 * {@link #generate(Iterable, OutputStream)} is called and no cluster is
	 * defined on the {@link Alias} instances being exported.
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
	 * Set a default cluster to apply when
	 * {@link #generate(Iterable, OutputStream)} is called and no cluster is
	 * defined on the {@link Alias} instances being exported.
	 * 
	 * @param defaultCluster
	 *        The default cluster value.
	 */
	public void setDefaultCluster(String defaultCluster) {
		this.defaultCluster = defaultCluster;
	}

}
