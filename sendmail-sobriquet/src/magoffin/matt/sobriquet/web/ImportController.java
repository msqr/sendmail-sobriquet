/* ===================================================================
 * ImportController.java
 * 
 * Created 9/08/2015 11:17:49 am
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import magoffin.matt.sobriquet.api.AliasDao;
import magoffin.matt.sobriquet.api.IOService;
import magoffin.matt.sobriquet.domain.Alias;
import magoffin.matt.util.TemporaryFile;
import magoffin.matt.util.TemporaryFileMultipartFileEditor;
import magoffin.matt.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Import alaises.
 *
 * @author matt
 * @version 1.0
 */
@Controller
@SessionAttributes("importForm")
@RequestMapping("/import")
public class ImportController {

	@Autowired
	public IOService ioService;

	@Autowired
	public AliasDao aliasDao;

	@ModelAttribute("importForm")
	public ImportForm getFormBean() {
		return new ImportForm();
	}

	@InitBinder
	protected void initBinderAll(WebDataBinder binder) {
		// register our Multipart TemporaryFile binder and validator
		binder.registerCustomEditor(TemporaryFile.class, new TemporaryFileMultipartFileEditor(true));
	}

	/**
	 * Begin the import process.
	 * 
	 * @param form
	 *        The import data.
	 * @param session
	 *        The HTTP session. This is required so we can store the parsed data
	 *        in memory to verify.
	 * @return The response.
	 * @throws IOException
	 *         If any IO error occurs.
	 */
	@RequestMapping(method = RequestMethod.POST, params = "_to=verify")
	@ResponseBody
	public Response<List<Alias>> verify(@ModelAttribute("importForm") ImportForm form,
			HttpSession session) throws IOException {
		Reader reader = null;
		if ( form.getFile() != null && form.getFile().getSize() > 0 ) {
			reader = new InputStreamReader(form.getFile().getInputStream(), "UTF-8");
		} else {
			reader = new StringReader(form.getAliasData());
		}
		List<Alias> results = new ArrayList<Alias>();
		try {
			for ( Alias alias : ioService.parseAliasRecords(reader) ) {
				results.add(alias);
			}
			form.setAliases(results);
		} finally {
			try {
				reader.close();
			} catch ( IOException e ) {
				// ignore
			}
		}

		//form.setFile(null);
		return Response.response(results);
	}

	/**
	 * Confirm the data previously imported via
	 * {@link #verify(ImportForm, HttpSession)}.
	 * 
	 * @param form
	 *        The imported data.
	 * @return The response.
	 */
	@RequestMapping(method = RequestMethod.POST, params = "_to=save")
	@ResponseBody
	public Response<Object> save(@ModelAttribute("importForm") ImportForm form) {
		if ( form.getAliases() != null ) {
			for ( Alias alias : form.getAliases() ) {
				aliasDao.store(alias);
			}
		}
		return Response.response(null);
	}

}
