/* ===================================================================
 * DirectoryServerRule.java
 * 
 * Created 7/08/2015 11:25:48 am
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

package magoffin.matt.sobriquet.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import com.btmatthews.ldapunit.DirectoryTester;

/**
 * Adaptation of {@link com.btmatthews.ldapunit.DirectoryServerRule} that allows
 * searching test super-classes for annotations.
 *
 * @author matt
 * @version 1.0
 */
public class DirectoryServerRule implements TestRule {

	/**
	 * The current configuration for the in-memory LDAP directory server.
	 * 
	 * @since 1.0.2
	 */
	private DirectoryServerConfiguration annotation;

	/**
	 * Modifies the method-running {@link Statement} to implement this
	 * test-running rule. The configuration for the embedded LDAP directory
	 * server is obtained from the {@link DirectoryServerConfiguration}
	 * annotation that was applied to either the test class or test method.
	 *
	 * @param base
	 *        The {@link Statement} to be modified
	 * @param description
	 *        A {@link Description} of the test implemented in {@code base}
	 * @return If no configuration was found then {@code base} is returned.
	 *         Otherwise, a new {@link DirectoryServerStatement} that wraps
	 *         {@code base} is returned.
	 */
	@Override
	public Statement apply(final Statement base, final Description description) {
		annotation = findAnnotation(description);
		if ( annotation != null ) {
			return new DirectoryServerStatement(base, annotation);
		}
		return base;
	}

	/**
	 * Verify that an entry identified by {@code dn} exists.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @return {@code true} if an entry identified by {@code dn} exists.
	 *         Otherwise, {@code false} is returned.
	 * @since 1.0.2
	 */
	public boolean verifyDNExists(final String dn) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			return directoryTester.verifyDNExists(dn);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Verify that the entry identified by {@code dn} is of type
	 * {@code objectclass}.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @param objectclass
	 *        The type name.
	 * @return {@code true} if an entry identified by {@code dn} exists and has
	 *         attribute named {@code objectclass}. Otherwise, {@code false} is
	 *         returned.
	 * @since 1.0.2
	 */
	public boolean verifyDNIsA(final String dn, final String objectclass) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			return directoryTester.verifyDNIsA(dn, objectclass);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Verify that the entry identified by {@code dn} has an attribute named
	 * {@code attributeName}.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @param attributeName
	 *        The attribute name.
	 * @return {@code true} if an entry identified by {@code dn} exists and has
	 *         an attributed named {@code attributeName}. Otherwise,
	 *         {@code false} is returned.
	 * @since 1.0.2
	 */
	public boolean verifyDNHasAttribute(final String dn, final String attributeName) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			return directoryTester.verifyDNHasAttribute(dn, attributeName);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Verify that the entry identified by {@code dn} has an attribute named
	 * {@code attributeName} with the attribute value(s) {@code attributeName}.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @param attributeName
	 *        The attribute name.
	 * @param attributeValue
	 *        The attribute value(s).
	 * @return {@code true} if an antry identified by {@code dn} exists with an
	 *         an attribute named {@code attributeName} that has value(s)
	 *         {@code attributeValue}. Otherwise, {@code false} is returned.
	 * @since 1.0.2
	 */
	public boolean verifyDNHasAttributeValue(final String dn, final String attributeName,
			final String... attributeValue) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			return directoryTester.verifyDNHasAttributeValue(dn, attributeName, attributeValue);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Assert that an entry identified by {@code dn} exists.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @since 1.0.2
	 */
	public void assertDNExists(final String dn) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			directoryTester.assertDNExists(dn);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Assert that the entry identified by {@code dn} is of type
	 * {@code objectclass}.
	 *
	 * @param dn
	 *        The distinguished name.
	 * @param objectclass
	 *        The type name.
	 * @since 1.0.2
	 */
	public void assertDNIsA(final String dn, final String objectclass) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			directoryTester.assertDNIsA(dn, objectclass);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Assert that the entry identified by {@code dn} has an attribute named
	 * {@code attributeName}.
	 *
	 * @param dn
	 *        The distinguished name
	 * @param attributeName
	 *        The attribute name
	 * @since 1.0.2
	 */
	public void assertDNHasAttribute(final String dn, final String attributeName) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			directoryTester.assertDNHasAttribute(dn, attributeName);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Assert that the entry identified by {@code dn} has an attribute named
	 * {@code attributeName} with the attribute value(s) {@code attributeName}.
	 *
	 * @param dn
	 *        The distinguished name
	 * @param attributeName
	 *        The attribute name
	 * @param attributeValue
	 *        The attribute value(s)
	 * @since 1.0.2
	 */
	public void assertDNHasAttributeValue(final String dn, final String attributeName,
			final String... attributeValue) {
		final DirectoryTester directoryTester = getDirectoryTester();
		try {
			directoryTester.assertDNHasAttributeValue(dn, attributeName, attributeValue);

		} finally {
			directoryTester.disconnect();
		}
	}

	/**
	 * Find the DirectoryServerConfiguration annotation for a description.
	 * 
	 * @param description
	 *        The test description.
	 * @return The annotation, or <em>null</em> if not found.
	 */
	protected DirectoryServerConfiguration findAnnotation(final Description description) {
		DirectoryServerConfiguration annot = description
				.getAnnotation(DirectoryServerConfiguration.class);
		if ( annot == null ) {
			Class<?> testClass = description.getTestClass();
			while ( testClass != null && annot == null ) {
				annot = testClass.getAnnotation(DirectoryServerConfiguration.class);
				testClass = testClass.getSuperclass();
			}
		}
		return annot;
	}

	/**
	 * Create a {@link DirectoryTester} that connects to the in-memory LDAP
	 * directory server created by this rule.
	 *
	 * @return The {@link DirectoryTester}.
	 * @since 1.0.2
	 */
	private DirectoryTester getDirectoryTester() {
		return new DirectoryTester("localhost", annotation.port(), annotation.authDN(),
				annotation.authPassword());
	}

}
