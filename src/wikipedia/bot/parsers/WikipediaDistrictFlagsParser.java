package wikipedia.bot.parsers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import wikipedia.bot.ApplicationException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 28.04.2014
 */
public class WikipediaDistrictFlagsParser extends SiteParser {

	/**
	 * {@inheritDoc}
	 * @param text
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected Map<String, String> getParams(String text, String[] selectors)
		throws ApplicationException {
			Map<String, String> map = new HashMap<String, String>();
			int index = text.indexOf("ий райо́н — ");
			if (index != -1) {
				StringBuilder sb = new StringBuilder();
				char current = text.charAt(index--);
				// store all chars while new line symbol will be find
				while (current != '\n') {
					current = text.charAt(index--);
					sb.append(current);
				}
				String name = sb.reverse().substring(1);
				map.put("НАЗВА_НАГОЛОС", name);
			}
			return map;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * @param url
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected String extract(URL url, String[] selectors) 
		throws ApplicationException {
			return exctractAllText(url);
	}

	/**
	 * {@inheritDoc}
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected String getURL(String[] selectors) throws ApplicationException {
		if (selectors == null || selectors.length != 4 ||
			selectors[0] == null || selectors[0].isEmpty()) {
				throw new ApplicationException(
					"Wrong selectors in Wikipedia parser!");
		}
		return "https://uk.wikipedia.org/wiki/" + selectors[0] + "ий район";
	}
}