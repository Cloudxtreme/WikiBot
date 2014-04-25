package wikipedia.bot.parsers;

import java.util.HashMap;
import java.util.Map;

import wikipedia.bot.ApplicationException;
import wikipedia.utils.DateUtils;

/**
 * 
 * @author Mir4ik
 * @version 0.1 24.04.2014
 */
public class GeneralInformationDistrictFlagsParser extends SiteParser {

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
			Map<String, String> params = new HashMap<String, String>();
			params.put("ЛІНК1", selectors[3]);
			params.put("СЬОГОДНІ", DateUtils.convertDate(
				System.currentTimeMillis()));
			params.put("ОБЛАСТЬ", selectors[2]);
			params.put("НАЗВА", selectors[0]);
			return params;
	}

	/**
	 * {@inheritDoc}
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected String getURL(String[] selectors) throws ApplicationException {
		return "http://google.com.ua";
	}
}