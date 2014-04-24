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
		replacements.put("�����", new Data("������"));
		replacements.put("���", new Data("������������ ���"));
		replacements.put("�����", new Data("�����"));
		replacements.put("����", new Data("�����"));
		replacements.put("����", new Data("�����"));
		replacements.put("������", new Data("������"));
		replacements.put("������", new Data("�������"));
		replacements.put("�������", new Data("�������"));
		replacements.put("���", new Data("��� (�'����)"));
		replacements.put("��������", new Data("��������"));
		replacements.put("������", new Data("��������"));
		replacements.put("������", new Data("������� (�� ������)"));
		replacements.put("��", new Data("ǳ���"));
		replacements.put("����", new Data("��� (�'����)"));
		replacements.put("����", new Data("������"));
		replacements.put("����", new Data("�����"));
		replacements.put("�����", new Data("������"));
		replacements.put("����", new Data("����"));
		replacements.put("�����", new Data("�������"));
		replacements.put("������", new Data("�������"));
		replacements.put("������", new Data("��������"));
		replacements.put("���", new Data("���"));
		replacements.put("�������", new Data("���������"));
		replacements.put("����", new Data("����"));
		replacements.put("��������", new Data("��������"));
		replacements.put("���", new Data("���"));
		replacements.put("�����", new Data("������"));
		replacements.put("���", new Data("�����"));
		replacements.put("������", new Data("������"));
		replacements.put("�����", new Data("������"));
		replacements.put("����", new Data("����"));
		replacements.put("��������", new Data("��������"));
		replacements.put("�������", new Data("�������"));
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