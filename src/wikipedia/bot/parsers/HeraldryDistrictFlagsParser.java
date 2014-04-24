package wikipedia.bot.parsers;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import wikipedia.bot.ApplicationException;
import wikipedia.utils.DateUtils;

/**
 * 
 * @author Mir4ik
 * @version 0.1 15.02.2014
 */
public class HeraldryDistrictFlagsParser extends SiteParser {

	/**
	 * 
	 * {@inheritDoc}
	 * @param text
	 * @param selectors
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	protected Map<String, String> getParams(String text, String[] selectors) 
		throws ApplicationException {
			if (text == null || text.isEmpty()) {
				throw new ApplicationException("Empty images list!");
			}
			Map<String, String> map = new HashMap<String, String>();
			String number = null;
			String month = null;
			String year = null;
			String proportion = null;
			String book = null;
			String description = null;
			String color1 = null;
			String color2 = null;
			String color3 = null;
			String[] divided = text.split("\n");
			for (String current: divided) {
				// paragraph with general information
				if (current.contains("�.") &&
					current.contains("����������")) {
						StringTokenizer st = new StringTokenizer(current, " ");
						while (st.hasMoreTokens()) {
							boolean digit = true;
							String token = st.nextToken();
							for (int i = 0; i < token.length(); i++) {
								if (!Character.isDigit(token.charAt(i))) {
									digit = false;
								}
							}
							if (token.contains("�.")) {
								year = token.substring(0, token.length() - 2);
							}
							if (!token.contains("�") && digit) {
								number = token;
							}
							if (DateUtils.month(token)) {
								month = token;
							}
						}
				}
				// paragraph with description
				if (current.contains("������������")) {
					description = current;
					int index = current.indexOf(':');
					proportion = current.substring(index - 1, index + 2);
					StringTokenizer st = new StringTokenizer(current, " ");
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						String result = colour(token);
						if (result != null) {
							if (color1 == null) {
								color1 = result + 
										((result == "���") ? "��" : "��");
							} else {
								if (color2 == null) {
									color2 = result + 
										((result == "���") ? "��" : "��");
								} else {
									if (color3 == null) {
										color3 = result + 
											((result == "���") ? "��" : "��");
									}
								}
							}
						}
					}
				}
				// paragraph with literature
				if (current.contains("������") && 
					current.contains("�����������")) {
						book = "''������: ����� �� �������'' / ���. �������"
							+ " �� ����. : �. �. ������, �. �. �����������. �"
							+ " �. : ������������� ���-��, 2010. � 456 �. : "
							+ "�����. � {{ref-uk}} {{ref-ru}} {{ref-en}}";
						/*
							book = "{{�����"
								+ " |�����=�. �. ������, �. �. �����������"
								+ " |���������=������: ����� �� �������"
								+ " |����=�"
								+ " |�����������=������������� ���-��"
								+ " |��=2010"
								+ " |�������= 456"
								+ "}} � {{ref-uk}} {{ref-ru}} {{ref-en}}";		
						 */			
					}
			}
			map.put("����", description);
			map.put("�����", number);
			map.put("̲����", month);
			map.put("в�", year);
			map.put("������ֲ�", proportion);
			map.put("�����", book);
			map.put("��˲�1", color1);
			map.put("��˲�2", color2);
			map.put("��˲�3", color3);
			return map;
	}

	private static String colour(String colour) {
		String[] colours = {"����", "���", "�������", "��", "����������",
			"�����", "����", "�����", "������", "�������", "��������"};
		for (int i = 0; i < colours.length; i++) {
			if (colour.contains(colours[i])) {
				return colours[i];
			}
		}
		return null;
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
			selectors[3] == null || selectors[3].isEmpty()) {
				throw new ApplicationException(
					"Wrong selectors in Commons parser!");
		}
		return "http://heraldry.com.ua/index.php3?lang=U&context=info&id=" +
			selectors[3] + "#verh";
	}
}