package wikipedia.bot.parsers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import wikipedia.bot.ApplicationException;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;

/**
 * 
 * @author Mir4ik
 * @version 0.1 09.04.2014
 */
public abstract class SiteParser {
	
	public Map<String, String> parse(String[] selectors)
		throws ApplicationException {
			URL url = null;
			try {
				url = new URL(getURL(selectors));
			} catch (MalformedURLException e) {
				throw new ApplicationException(e);
			}
			String text = extract(url, selectors);
			return getParams(text, selectors);
	}
	
	protected abstract Map<String, String> getParams(
		String text, String[] selectors) throws ApplicationException;
	
	protected String extract(URL url, String[] selectors) 
		throws ApplicationException {
			try {
				return ArticleExtractor.INSTANCE.getText(url);
			} catch (BoilerpipeProcessingException e) {
				throw new ApplicationException(e);
			}
	}
	
	protected String exctractAllText(URL url) throws ApplicationException {
		try {
			return KeepEverythingExtractor.INSTANCE.getText(url);
		} catch (BoilerpipeProcessingException e) {
			throw new ApplicationException(e);
		}
	}
	
	protected abstract String getURL(String[] selectors) 
		throws ApplicationException;
}