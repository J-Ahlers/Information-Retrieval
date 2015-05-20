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
	
	private static final String FOLDER_ORIGINAL = "original";
	private static final String FOLDER_STOPWORDS_ELIMINATED = "stopwords";
	private static final String FOLDER_STEMMING = "stemming";
	private static final String FOLDER_BOTH = "stopwords+stemming";
	
	private static final String DEFAULT_EXTENSION = ".txt";
	
	public static List<Document> load() {		
		return load(true, true);
	}
	
	public static List<Document> load(boolean eliminateStopwords, boolean useStemming) {
		boolean saveDocuments = false;
		int status = getType(eliminateStopwords, useStemming);
		String subfolder = getFoldername(status);
		
		ensureFolderExists(RetrievalSystem.workingDirectory, subfolder);
		
		List<String> files = listFiles(RetrievalSystem.workingDirectory+File.separator+subfolder, DEFAULT_EXTENSION);
		saveDocuments = files.size() == 0;
		if(saveDocuments) {
			subfolder = FOLDER_ORIGINAL;
			status = Document.TYPE_ORIGINAL;
			files = listFiles(RetrievalSystem.workingDirectory+File.separator+subfolder, DEFAULT_EXTENSION);
		}
			
		List<Document> documents = new ArrayList<>();
		for(String filename : files) {
			String[] parts = filename.split("\\"+File.separator);
			Document doc = new Document(parts[parts.length-1], readFile(filename), status);
			if(eliminateStopwords)
				doc.eliminateStopwords();
			if(useStemming)
				doc.useStemming();
			 
			documents.add(doc);
			
			if(saveDocuments)
				doc.save();
		}
		return documents;
	}
	
	public static void saveDocument(String filename, String content, int type) {
		String folder = getFoldername(type);	
		ensureFolderExists(RetrievalSystem.workingDirectory, folder);
		save(RetrievalSystem.workingDirectory+File.separator+folder+File.separator+filename+DEFAULT_EXTENSION, content);
	}
	
	public static void save(String path, String content) {		
		FileWriter fw = null;
		try {
            File newTextFile = new File(path);

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
    
    private static void ensureFolderExists(String path, String foldername) {
    	File root = new File( path );
        File[] list = root.listFiles();
        boolean exists = false;

        if(list == null) {
			return;
		}

        for(File f : list) {
            if(f.isDirectory() && f.getAbsolutePath().equals(path+foldername)) {
                exists = true;
                break;
            }
        }
        if(!exists)
        	(new File(path+foldername)).mkdirs();
    }
    
    private static int getType(boolean stopwords, boolean stemming) {
    	if(!stopwords && !stemming)
    		return Document.TYPE_ORIGINAL;
    	else if(stopwords && !stemming)
    		return Document.TYPE_STOPWORDS_ELIMINATED;
    	else if(!stopwords && stemming)
    		return Document.TYPE_STEMMING;
    	else
    		return Document.TYPE_BOTH;
    }
    
    private static String getFoldername(int type) {
    	switch(type) {
    	case Document.TYPE_BOTH:
    		return FOLDER_BOTH;
    	case Document.TYPE_STEMMING:
    		return FOLDER_STEMMING;
    	case Document.TYPE_STOPWORDS_ELIMINATED:
    		return FOLDER_STOPWORDS_ELIMINATED;
    	default:
    		return FOLDER_ORIGINAL;
    	}
    }
}