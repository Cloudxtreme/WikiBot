package wikipedia.bot;

import java.io.File;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Mir4ik
 * @version 0.1 13.03.2014
 */
public class Target {
	
	private String[] params;
	
	private Target() {}
	
	public static Target create(File file) throws IOException {
		return init(file);
	}
	
	public static Target create(String file) throws IOException {
		return init(new File(file));
	}
	
	private static Target init(File file) throws IOException {
		XStream xs = new XStream();
		Object o = xs.fromXML(file);
		return (Target) o;
	}
	
	public String getTaskParam(int i) {
		return params[i].trim();
	}
}