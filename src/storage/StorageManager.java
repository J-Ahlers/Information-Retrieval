/**
 * 
 */
package storage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Document;

/**
 * @author Jonas Ahlers
 *
 */
public class StorageManager {
	
	public List<Document> load() {
		return null;
	}
	
	public void save(File file) {
		
	}
	
	public void save(String filename, String content) {

	}
	
	/**
     * Loads the given file into a String
     * 
     * @param filename Filename - with path - to the file to be loaded
     * @return file-content as String
     */
    public static String readFile(String filename) {
	   String content = null;
	   File file = new File(filename);
	   
	   try {
		   FileReader reader = new FileReader(file);
		   char[] chars = new char[(int) file.length()];
		   reader.read(chars);
		   content = new String(chars);
		   reader.close();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   
	   return content;
	}
	
	public static List<String> listFiles(String path) {
		return listFiles(path, "");
	}
    
    public static List<String> listFiles(String path, String filetype) {
		List<String> result = new ArrayList<String>();
		
		File root = new File( path );
        File[] list = root.listFiles();

        if(list == null) {
			return result;
		}

        for(File f : list) {
			String current = f.getAbsolutePath();
            if(f.isDirectory()) {
                result.addAll(listFiles(current, filetype));
            }
            else if(current.endsWith(filetype) || filetype.equals("")) {
                result.add(current);
            }
        }
		
		return result;
    }
}
