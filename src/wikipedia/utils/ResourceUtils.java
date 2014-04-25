package wikipedia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author Mir4ik
 * @version 0.1 26.01.2014
 */
public final class ResourceUtils {
	
	private ResourceUtils() {}

	public static String read(InputStream is, String encoding) 
		throws IOException, UnsupportedEncodingException {
			InputStreamReader isr = new InputStreamReader(is, encoding);
			StringBuilder buffer = new StringBuilder();
			int i;
			do {
				i = isr.read();
				if (i != -1) {
					buffer.append((char) i);
				}
			} while (i != -1);
			isr.close();
			return buffer.toString();
	}
	
	public static String readFile(String file, String encoding)
		throws IOException, UnsupportedEncodingException {
			return read(new FileInputStream(file), encoding);
	}
	
	public static String readFile(File file, String encoding)
		throws IOException, UnsupportedEncodingException {
			return read(new FileInputStream(file), encoding);
	}
	
	public static void write(OutputStream os, String data, String encoding)
		throws IOException, UnsupportedEncodingException {
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			osw.write(data);
			osw.flush();
			osw.close();
	}
	
	public static void writeFile(String file, String data, String encoding) 
		throws IOException, UnsupportedEncodingException {
			write(new FileOutputStream(file), data, encoding);
	}
	
	public static void writeFile(File file, String data, String encoding) 
		throws IOException, UnsupportedEncodingException {
			write(new FileOutputStream(file), data, encoding);
	}
	
	public static String readPage(URL url, String encoding) 
		throws IOException, UnsupportedEncodingException {
			return read(url.openStream(), encoding);
	}
	
	public static String readPage(String url, String encoding)
		throws IOException, MalformedURLException, UnsupportedEncodingException {
			return read(new URL(url).openStream(), encoding);
	}
}