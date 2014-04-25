package wikipedia.bot.parsers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import wikipedia.bot.ApplicationException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 14.04.2014
 */
public class CommonsDistrictFlagsParser extends SiteParser {

	private static final Map<String, String> links = new HashMap<String, String>();
	
	private List<String> images = new ArrayList<String>();
	
	private static final String URL = "https://commons.wikimedia.org/w/api.php?"
		+ "action=query&"
		+ "list=categorymembers&"
		+ "format=xml&"
		+ "cmlimit=50&"
		+ "cmtitle=";
	
	private static final String SEPARATOR = System.getProperty("line.separator");
	
	static {
		links.put("АРК", "Crimea");
		links.put("Черкась", "Cherkasy_Oblast");
		links.put("Чернігівсь", "Chernihiv_Oblast");
		links.put("Чернівець", "Chernivtsi_Oblast");
		links.put("Дніпропетровсь", "Dnipropetrovsk_Oblast");
		links.put("Донець", "Donetsk_Oblast");
		links.put("Івано-Франківсь", "Ivano-Frankivsk_Oblast");
		links.put("Харківсь", "Kharkiv_Oblast");
		links.put("Херсонсь", "Kherson_Oblast");
		links.put("Хмельниць", "Khmelnytskyi_Oblast");
		links.put("Київсь", "Kiev_Oblast");
		links.put("Кіровоградсь", "Kirovograd_Oblast");
		links.put("Лугансь", "Luhansk_Oblast");
		links.put("Львівсь", "Lviv_Oblast");
		links.put("Миколаївсь", "Mykolaiv_Oblast");
		links.put("Одесь", "Odessa_Oblast");
		links.put("Полтавсь", "Poltava_Oblast");
		links.put("Рівненсь", "Rivne_Oblast");
		links.put("Сумсь", "Sumy_Oblast");
		links.put("Тернопільсь", "Ternopil_Oblast");
		links.put("Вінниць", "Vinnytsia_Oblast");
		links.put("Волинсь", "Volyn_Oblast");
		links.put("Запорізь", "Zaporizhzhya_Oblast");
		links.put("Житомирсь", "Zhytomyr_Oblast");
		links.put("Закарпатсь", "Zakarpattia_Oblast");
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * @param url
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected String extract(URL url, String[] selectors) 
		throws ApplicationException {
			Objects.requireNonNull(url, "URL can not be null!");
			processCategory(url);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < images.size(); i++) {
				sb.append(images.get(i));
				sb.append(SEPARATOR);
			}
			// form list of extracted files
			return sb.toString();
	}
	
	private void processCategory(URL url) throws ApplicationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		Object result = null;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			XPathFactory pathFactory = XPathFactory.newInstance();
			XPath xpath = pathFactory.newXPath();
			// get all "cm" XML elements
			XPathExpression expr = xpath.compile("//cm");
			result = expr.evaluate(doc, XPathConstants.NODESET);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			// get attribute "title" value
			String current = nodes.item(i).getAttributes().getNamedItem(
				"title").getNodeValue();
			// process file
			if (current.startsWith("File:")) {
				images.add(current);
			} else {
				try {
					// process category recursively
					String newCurrent = current.replace(' ', '_');
					processCategory(new URL(URL + newCurrent));
				} catch (MalformedURLException e) {
					throw new ApplicationException(e);
				}
			}
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected String getURL(String[] selectors) throws ApplicationException {
		if (selectors == null || selectors.length != 4 ||
			selectors[2] == null || selectors[2].isEmpty()) {
				throw new ApplicationException(
					"Wrong selectors in Commons parser!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(URL);
		sb.append("Category:Flags_of_raions_in_");
		sb.append(links.get(selectors[2]));
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * @param text
	 * @param selectors
	 * @return
	 */
	@Override
	protected Map<String, String> getParams(String text, String[] selectors) 
		throws ApplicationException {
			if (selectors == null || selectors.length != 4 ||
				selectors[0] == null || selectors[0].isEmpty() ||
				selectors[1] == null || selectors[1].isEmpty() ||
				selectors[2] == null || selectors[2].isEmpty()) {
					throw new ApplicationException(
						"Wrong selectors in Commons parser!");
			}
			if (text == null || text.isEmpty()) {
				throw new ApplicationException("Empty images list!");
			}
			Map<String, String> map = new HashMap<String, String>();
			String picture = null;
			String[] divided = text.split(SEPARATOR);
			// form all enabled pictures
			List<String> marked = new LinkedList<String>();
			for (String current: divided) {
				if (current.contains(selectors[0]) || 
					current.contains(selectors[1])) {
						marked.add(current);
				}
			}
			if (marked.isEmpty()) {
				throw new ApplicationException("Can not find params!");
			} else {
				// take first picture
				picture = marked.get(0);
				for (String current: marked) {
					// if we have pictures in better quality, take them
					if (current.contains(".png") || 
						current.contains(".PNG") ||
						current.contains(".svg") || 
						current.contains(".SVG")) {
							picture = current;
					}
				}
			}
			// delete "File:" before
			map.put("МАЛЮНОК", picture.substring(5, picture.length()));
			return map;
	}
}