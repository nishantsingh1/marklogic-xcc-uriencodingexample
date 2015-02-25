import com.marklogic.xcc.*;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * Example showing XCC with a URI which has been encoded safely
 */
public class GoodUri {
	public static void main(String[] args) throws XccConfigException,
			URISyntaxException, UnsupportedEncodingException {

		String badUri = "&.xml"; 
		String goodUri = URLEncoder.encode(badUri, "UTF-8");
		System.out.println(MessageFormat.format(
				"Filename being used as the URI: {0}", goodUri)); 

		URI uri = new URI("xcc://[user]:[pass]@[host]:[port]/[database]");
		ContentSource cs = ContentSourceFactory.newContentSource(uri);
		Session s = cs.newSession();

		String adHocQueryString = String.format(
				"xdmp:document-insert(\"%s\", <test/>)", goodUri);
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
