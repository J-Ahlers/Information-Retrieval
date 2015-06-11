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
	
	/**
	 * loads and returns all the documents which are available for the search
	 * 
	 * @param eliminateStopwords should stopwords get eliminated or not
	 * @param useStemming use stemming or not
	 * @return loaded (and maybe edited) documents
	 */
	public static List<Document> load(boolean eliminateStopwords, boolean useStemming) {
		boolean saveDocuments = false;
		int status = getType(eliminateStopwords, useStemming);
		String subfolder = getFoldername(status);
		
		ensureFolderExists(RetrievalSystem.workingDirectory, subfolder);
		
		List<String> files = listFiles(RetrievalSystem.workingDirectory+File.separator+subfolder, DEFAULT_EXTENSION);
		saveDocuments = files.size() == 0;
		
		// if there are no edited documents to load use original documents
		if(saveDocuments) {
			subfolder = FOLDER_ORIGINAL;
			ensureFolderExists(RetrievalSystem.workingDirectory, subfolder);
			status = Document.TYPE_ORIGINAL;
			files = listFiles(RetrievalSystem.workingDirectory+File.separator+subfolder, DEFAULT_EXTENSION);
			if(files.size() == 0) {
				FileSplitter fs = new FileSplitter();
				fs.createDocumentCollection();
				load(eliminateStopwords, useStemming);
			}
		}
		
		/**
		 * do stopword elimination and/or stemming for each document
		 * depending on parameters
		 */
		List<Document> documents = new ArrayList<>();
		for(String filename : files) {
			String[] parts = filename.split("\\"+File.separator);
			parts = parts[parts.length-1].split("#");
			int id = Integer.valueOf(parts[0]);
			String name = parts[1];
			Document doc = new Document(id, name, readFile(filename), status);
			if(eliminateStopwords)
				doc.eliminateStopwords();
			if(useStemming)
				doc.useStemming();
			 
			documents.add(doc);
			
			// only save documents when they didn't existed till now
			if(saveDocuments)
				doc.save();
		}
		return documents;
	}
	
	public static List<Document> load(List<Integer> ids, boolean eliminateStopwords, boolean useStemming) {
		List<Document> result = new ArrayList<>();
		for(Integer i : ids)
			result.add(load(i, eliminateStopwords, useStemming));
		return result;
	}
	
	/**
	 * loads and returns all the documents which are available for the search
	 * 
	 * @param eliminateStopwords should stopwords get eliminated or not
	 * @param useStemming use stemming or not
	 * @return loaded (and maybe edited) documents
	 */
	public static Document load(int id, boolean eliminateStopwords, boolean useStemming) {
		int status = getType(eliminateStopwords, useStemming);
		String subfolder = getFoldername(status);
		
		List<String> files = listFiles(RetrievalSystem.workingDirectory+File.separator+subfolder, DEFAULT_EXTENSION);

		Document document = null;
		for(String filename : files) {
			String[] parts = filename.split("\\"+File.separator);
			parts = parts[parts.length-1].split("#");
			int file_id = Integer.valueOf(parts[0]);
			
			if(file_id != id)
				continue;
			
			String name = parts[1].replace(DEFAULT_EXTENSION, "");
			document = new Document(id, name, readFile(filename), status);
			if(eliminateStopwords)
				document.eliminateStopwords();
			if(useStemming)
				document.useStemming();
		}
		return document;
	}
	
	/**
	 * saving a document on the file system
	 * 
	 * @param filename name of the file
	 * @param content content of the file
	 * @param type defines the folder to be saved in
	 */
	public static void saveDocument(String filename, String content, int type) {
		String folder = getFoldername(type);
		filename = filename.replace(DEFAULT_EXTENSION, "");
		ensureFolderExists(RetrievalSystem.workingDirectory, folder);
		save(RetrievalSystem.workingDirectory+File.separator+folder+File.separator+filename+DEFAULT_EXTENSION, content);
	}
	
	/**
	 * properly saving a string on the given path
	 * 
	 * @param path defines the location to save
	 * @param content defines the content to save
	 */
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
    
	/**
	 * returns a list of strings containing all file-paths 
	 * with the desired type
	 * 
	 * @param path the path to find the files
	 * @param filetype the type of the files to be returned
	 * @return a list of strings which contains the paths of the files
	 */
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
    
    /**
     * function to ensure that specific folders exists
     * if not, create the folder
     * 
     * @param path the path where the folder should be
     * @param foldername the name of the folder
     */
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
        	(new File(path+File.separator+foldername)).mkdirs();
    }
    
    /**
     * returns the type of the document depending on the parameters
     * 
     * @param stopwords true if stopwords are deleted
     * @param stemming true if stemming happened
     * @return type of document
     */
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
    
    /**
     * returns the name of the folder, where a document should
     * be saved, depending on the documents type
     * 
     * @param type
     * @return
     */
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