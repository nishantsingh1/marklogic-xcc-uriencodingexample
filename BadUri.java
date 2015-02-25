import com.marklogic.xcc.*;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

/**
 * Example showing XCC with a URI which has not been encoded
 *
 */
public class BadUri {

	public static void main(String[] args) throws XccConfigException,
			URISyntaxException {

		String badUri = "&.xml"; 

		URI uri = new URI("xcc://[user]:[pass]@[host]:[port]/[database]");
		ContentSource cs = ContentSourceFactory.newContentSource(uri);
		Session s = cs.newSession();

		String adHocQueryString = String.format(
				"xdmp:document-insert(\"%s\", <test/>)", badUri);
		System.out.println(MessageFormat.format(
				"Full adHocQuery being executed: {0}", adHocQueryString));

		Request r = s.newAdhocQuery(adHocQueryString);

		try {
			s.submitRequest(r);
		} catch (RequestException e) {
			System.err.println(e.toString());
		}

		s.close();
	}
}
