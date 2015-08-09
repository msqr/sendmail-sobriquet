/* ===================================================================
 * SendmailAlias.java
 * 
 * Created 7/08/2015 8:38:05 am
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

import java.util.Set;
import javax.naming.Name;
import magoffin.matt.dao.Identity;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.sobriquet.domain.AliasSearchResult;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Sendmail alias implementation.
 *
 * @author matt
 * @version 1.0
 */
@Entry(objectClasses = { "sendmailMTAAliasObject", "sendmailMTAAlias", "sendmailMTA", "top" }, base = "ou=Sendmail")
public final class SendmailAlias implements Alias, AliasSearchResult {

	/** The Sendmail objectClass attribute values. */
	public static final String[] SENDMAIL_OBJECT_CLASS = new String[] { "sendmailMTAAliasObject",
			"sendmailMTAAlias", "sendmailMTA", "top" };

	/** The Sendmail key attribute, also used as a DN attribute. */
	public static final String SENDMAIL_MTA_KEY_ATTR = "sendmailMTAKey";

	/** The Sendmail alias value attribute. */
	public static final String SENDMAIL_MTA_ALIAS_VALUE_ATTR = "sendmailMTAAliasValue";

	/** The Sendmail grouping value attribute. */
	public static final String SENDMAIL_MTA_ALIAS_GROUPING_ATTR = "sendmailMTAAliasGrouping";

	/** The Sendmail cluster value attribute. */
	public static final String SENDMAIL_MTA_CLUSTER_ATTR = "sendmailMTACluster";

	@Id
	private Name dn;

	@DnAttribute(value = SENDMAIL_MTA_KEY_ATTR, index = 1)
	@Attribute(name = SENDMAIL_MTA_KEY_ATTR)
	private String key;

	@Attribute(name = SENDMAIL_MTA_ALIAS_VALUE_ATTR)
	private Set<String> values;

	@Attribute(name = SENDMAIL_MTA_ALIAS_GROUPING_ATTR)
	private String grouping;

	@Attribute(name = SENDMAIL_MTA_CLUSTER_ATTR)
	private String cluster;

	/**
	 * Create a new instance.
	 */
	public SendmailAlias() {
		super();
	}

	/**
	 * Create a copy of an alias.
	 * 
	 * @param other
	 *        The other alias to copy.
	 */
	public SendmailAlias(Alias other) {
		super();
		setKey(other.getAlias());
		setValues(other.getActuals());
		if ( other instanceof SendmailAlias ) {
			SendmailAlias o = (SendmailAlias) other;
			setGrouping(o.getGrouping());
			setCluster(o.getCluster());
			dn = o.dn;
		}
	}

	@JsonIgnore
	@Override
	public String getId() {
		return key;
	}

	@Override
	public int compareTo(Identity<String> o) {
		return key.compareToIgnoreCase(o.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		if ( getClass() != obj.getClass() ) {
			return false;
		}
		SendmailAlias other = (SendmailAlias) obj;
		if ( key == null ) {
			if ( other.key != null ) {
				return false;
			}
		} else if ( !key.equals(other.key) ) {
			return false;
		}
		return true;
	}

	/**
	 * Get the distinguished name of this object.
	 * 
	 * @return The distinguished name.
	 */
	@JsonIgnore
	public Name getDn() {
		return dn;
	}

	@Override
	public String getAlias() {
		return key;
	}

	@JsonIgnore
	@Override
	public String getActual() {
		Set<String> vals = getActuals();
		return (vals == null || vals.isEmpty() ? null : vals.iterator().next());
	}

	@Override
	public Set<String> getActuals() {
		return values;
	}

	@JsonIgnore
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@JsonIgnore
	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

}
