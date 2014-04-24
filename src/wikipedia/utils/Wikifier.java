package wikipedia.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import wikipedia.bot.WikiString;

/**
 * TODO
 * 1. refactore
 * 
 * @author Mir4ik
 * @version 0.1 06.04.2014
 */
public final class Wikifier {
	
	static class Data {
		
		private String data;
		
		private boolean used = false;
		
		public Data(String data) {
			this.data = data;
		}
		
		public String getData() {
			return data;
		}
		
		public boolean isUsed() {
			return used;
		}
		
		public void setUsed() {
			used = true;
		}
	}
	
	private static final Map<String, Data> replacements = new HashMap<String, Data>();

	static {
		replacements.put("древк", new Data("Древко"));
		replacements.put("щит", new Data("Геральдичний щит"));
		replacements.put("хрест", new Data("Хрест"));
		replacements.put("сонц", new Data("Сонце"));
		replacements.put("шабл", new Data("Шабля"));
		replacements.put("пернач", new Data("Пернач"));
		replacements.put("хоругв", new Data("Хоругва"));
		replacements.put("кадуцей", new Data("Кадуцей"));
		replacements.put("сніп", new Data("Сніп (в'язка)"));
		replacements.put("орнамент", new Data("Орнамент"));
		replacements.put("візерун", new Data("Орнамент"));
		replacements.put("ромашк", new Data("Ромашка (рід рослин)"));
		replacements.put("зір", new Data("Зірка"));
		replacements.put("сноп", new Data("Сніп (в'язка)"));
		replacements.put("коза", new Data("Козаки"));
		replacements.put("чапл", new Data("Чапля"));
		replacements.put("булав", new Data("Булава"));
		replacements.put("сувій", new Data("Сувій"));
		replacements.put("колос", new Data("Колосок"));
		replacements.put("пшениц", new Data("Пшениця"));
		replacements.put("соняшн", new Data("Соняшник"));
		replacements.put("якір", new Data("Якір"));
		replacements.put("трикутн", new Data("трикутник"));
		replacements.put("дзвін", new Data("Дзвін"));
		replacements.put("виноград", new Data("Виноград"));
		replacements.put("дуб", new Data("Дуб"));
		replacements.put("жолуд", new Data("Жолудь"));
		replacements.put("ячм", new Data("Ячмінь"));
		replacements.put("тризуб", new Data("Тризуб"));
		replacements.put("калин", new Data("Калина"));
		replacements.put("лавр", new Data("Лавр"));
		replacements.put("землероб", new Data("Землероб"));
		replacements.put("хлібороб", new Data("Хлібороб"));
	}
	
	private Wikifier() {}
	
	public static String wikify(WikiString string) {
		StringTokenizer st = new StringTokenizer(string.toString(), " ");
		StringBuilder sb = new StringBuilder();
		while (st.hasMoreTokens()) {
			String current = st.nextToken();
			Data value = getSimilar(current);
			if (value != null && !value.isUsed()) {
				current = "[[" + value.getData() + "|" + current + "]]";
				value.setUsed();
			}
			sb.append(current);
			sb.append(" ");
		}
		return sb.toString();
	}
	
	private static Data getSimilar(String string) {
		Iterator<Entry<String, Data>> i = replacements.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, Data> current = i.next();
			if (string.contains(current.getKey())) {
				return current.getValue();
			}
		}
		return null;
	}
}