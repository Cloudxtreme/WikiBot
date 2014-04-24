package wikipedia.bot;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Complex modifiable string realization with parameters.
 * <p>
 * It is based on {@link StringBuilder}, so is modifiable. Aggregation was used
 * because of {@code final} modifier in {@link StringBuilder} class. Also it
 * provides replacing parameters, marked by special symbol '%' at beginning and
 * ending, by specified in {@link Map} storage values.
 * 	
 * TODO
 * 1. add extra methods from Map, StringBuilder for usability
 * 
 * @author Mir4ik
 * @version 0.1 26.01.2014
 * @see {@link String}
 */
public class WikiString implements Appendable, CharSequence, Serializable,
	Comparable<WikiString> {
	
	/**
	 * Enumeration containing strategies for replacing parameters, which
	 * have no values in specified {@link Map} storage or if storage is 
	 * {@code null}. 
	 * 
	 * @author Mir4ik
	 * @version 0.1 26.01.2014
	 */
	private enum CompilingStrategy {
		
		/**
		 * Do not replace anything.
		 */
		NONE,
		
		/**
		 * Replace by "null" text {@link String}.
		 */
		NULL,
		
		/**
		 * Replace by "" text {@link String}.
		 */
		BLANK
	}

	private static final long serialVersionUID = 3762650720656885337L;

	private StringBuilder data = new StringBuilder();
	
	private Map<String, String> params;
	
	private CompilingStrategy strategy = CompilingStrategy.NONE;

	public WikiString() {}
	
	public WikiString(CharSequence cs, Map<String, String> params) {
		data.append(cs);
		setParams(params);
	}
	
	public WikiString(String orignal, Map<String, String> params) {
		data.append(orignal);
		setParams(params);
	}
	
	public WikiString(WikiString original, boolean allocate) {
		if (allocate) {
			data = new StringBuilder(original.data);
			params = new HashMap<String, String>();
			addParams(original.params);
		} else {
			data = original.data;
			params = original.params;
		}
		strategy = original.strategy;
	}
	
	public WikiString(StringBuilder builder, Map<String, String> params) {
		data.append(builder.toString());
		setParams(params);
	}
	
	public WikiString(StringBuffer buffer, Map<String, String> params) {
		data.append(buffer.toString());
		setParams(params);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int length() {
		return data.length();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public char charAt(int index) {
		return data.charAt(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return data.subSequence(start, end);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Appendable append(CharSequence csq) throws IOException {
		return data.append(csq);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Appendable append(CharSequence c, int s, int e) throws IOException {
		return data.append(c, s, e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Appendable append(char c) throws IOException {
		return data.append(c);
	}
	
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	private void createParams() {
		if (params == null) {
			params = new HashMap<String, String>();
		}
	}
	
	public void addParam(String key, String value) {
		createParams();
		params.put(key, value);
	}
	
	public void addParams(Map<String, String> map) {
		createParams();
		params.putAll(map);
	}
	
	public void removeParam(String key) {
		if (params != null) {
			params.remove(key);
		}
	}
	
	public void removeParams() {
		if (params != null) {
			params.clear();
		}
	}
	
	public boolean haveParam(String key) {
		return params.containsKey(key);
	}
	
	public int paramSize() {
		return (params == null) ? 0 : params.size();
	}
	
	public void setNONEStrategy() {
		strategy = CompilingStrategy.NONE;
	}
	
	public void setNULLStrategy() {
		strategy = CompilingStrategy.NULL;
	}
	
	public void setBLANKStrategy() {
		strategy = CompilingStrategy.BLANK;
	}
	
	/**
	 * Method creates {@link String} representation of this string, inserting 
	 * specified values on parameters places.
	 *  
	 * @return {@link String} representation of this string
	 */
	public String compileString() {
		String delims = "%";
		StringTokenizer st = new StringTokenizer(data.toString(), delims, true);
		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		while (st.hasMoreTokens()) {
			String current = st.nextToken();
			if (current.charAt(0) == '%') {
				// flag == true when parameter is found
				if (!flag) {
					flag = true;
				} else {
					flag = false;		
				}
				continue;
			}
			if (flag) {
				// if current is parameter
				if (params != null && !params.isEmpty()) {
					if (params.containsKey(current)) {
						// if value was found in storage
						builder.append(params.get(current));
					} else {
						// if there is no value for these parameter
						switch (strategy) {
						case NONE:
							builder.append(current);
							break;						
						case NULL:
							builder.append("null");
							break;
						case BLANK:
							break;
						}
					}
				} else {
					// if storage is null or empty
					switch (strategy) {
					case NONE:
						builder.append(current);
						break;						
					case NULL:
						builder.append("null");
						break;
					case BLANK:
						break;
					}
				}
			} else {
				// if current is simple text
				builder.append(current);
			}
		}
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(WikiString string) {
		return toString().compareTo(string.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else {
			if (object instanceof WikiString) {
				WikiString another = (WikiString) object;
				boolean dataCondition = data.equals(another.data);
				boolean strategyCondition = strategy.equals(another.strategy);
				boolean paramsCondition = params.equals(another.params);
				if (dataCondition && strategyCondition && paramsCondition) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return 37 + data.hashCode() + params.hashCode() + strategy.hashCode();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return compileString();
	}
}