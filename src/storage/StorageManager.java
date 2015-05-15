/**
 * 
 */
package storage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.RetrievalSystem;
import model.Document;

/**
 * @author Jonas Ahlers
 *
 */
public class StorageManager {
	
	private static final String DEFAULT_EXTENSION = ".txt";
	
	public static List<Document> load() {		
		return load(true, true);
	}
	
	public static List<Document> load(boolean eliminateStopwords, boolean useStemming) {
		List<String> files = listFiles(RetrievalSystem.workingDirectory, DEFAULT_EXTENSION);
		List<Document> documents = new ArrayList<>();
		for(String filename : files) {
			String[] parts = filename.split(File.separator);
			Document doc = new Document(parts[parts.length-1], readFile(filename));
			if(eliminateStopwords)
				doc.eliminateStopwords();
			if(useStemming)
				doc.useStemming();
			 
			documents.add(doc);
		}
		return documents;
	}
	
	public static void save(String filename, String content) {
		FileWriter fw = null;
		try {
            File newTextFile = new File(RetrievalSystem.workingDirectory+File.separator+filename+DEFAULT_EXTENSION);

            fw = new FileWriter(newTextFile);
            fw.write(content);
            fw.close();
        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        } 
	}
	
	/**
     * Loads the given file into a String
     * 
     * @param path Filename - with path - to the file to be loaded
     * @return file-content as String
     */
	public static String readFile(String path) {
	   String content = null;
	   File file = new File(path);
	   
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
    
    private static List<String> listFiles(String path, String filetype) {
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