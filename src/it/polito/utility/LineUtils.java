package it.polito.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a few utilities that can be used to
 * load the lines from a local text file or from a remote URL.
 * 
 * <h3>Example</h3>
 * The lines can be load from a remote URL
 * using the {@link #loadLinesUrl(String) loadLinesUrl()}
 * 
 *   <pre>
 *   {@code
 *      String url = "http://softeng.polito.it/courses/09CBI/schools.csv";
 *      List<String> lines = LineUtils.loadLinesUrl(url);
 *   }
 *   </pre>
 *  
 * @author Marco Torchiano
 * @version 1.0.0
 *
 */
public class LineUtils {

	/**
	 * Load the lines from a remote URL into a list of strings.
	 * 
	 * The remove URL id first downloaded to a local cache file
	 * and then read through {@link #loadLines(String) loadLines()}.
	 * The local cache copy is used in subsequent operation to
	 * avoid multiple download. The cache is updated if the
	 * remote file has changed after the file was last downloaded.
	 * 
	 * @param url URL of the resource whose line must be loaded
	 * @return the list of lines
	 */
	public static List<String> loadLinesUrl(String url){		
		try {
			URL resource = new URL(url);
			String filename = resource.getFile().replaceAll("/", "_");
			URLConnection conn = resource.openConnection();
			File file = new File(filename);
			if(!file.exists() || 
				file.lastModified() < conn.getLastModified() ){
				byte buffer[] = new byte[4096];
				int n;
				try(
					InputStream in = conn.getInputStream();
					OutputStream out = new FileOutputStream(file)){
					while( (n=in.read(buffer)) != -1){
						out.write(buffer, 0, n);
					}
				}
			}
			return loadLines(filename);
		} catch (IOException e) {
			System.err.println("Detected and error while downloading url: " + url);
			return null;
		}
	}
	
	/**
	 * Load the lines from a local text file into a list of strings.
	 * 
	 * @param file path of the file whose line must be loaded
	 * @return the list of lines
	 */
	public static List<String> loadLines(String file){
		ArrayList<String> rows = new ArrayList<>();
		try(
		BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line;
			while( ( line = reader.readLine() ) != null ){
				rows.add(line);
			}
		}catch(IOException e){
			System.err.println("Detected and error while reading file: " + file);
			return null;
		}
		return rows;
	}
}
