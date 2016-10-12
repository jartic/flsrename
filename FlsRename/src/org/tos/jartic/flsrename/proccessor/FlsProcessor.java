/**
 * 
 */
package org.tos.jartic.flsrename.proccessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ora
 *
 */
public class FlsProcessor implements FlsProcessorInterface {
	private Logger logger = Logger.getLogger(FlsProcessor.class.getName());
	public int cntTotal = 0;
	public int cntComplete = 0;
	ArrayList<String> listOfOutFileNames = new ArrayList<>();
	ArrayList<String> listOfFileNames = new ArrayList<>();

	public FlsProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTotalComplete() {
		return cntComplete * 100 / cntTotal;
	}

	private String getFilePath(String fileName) {
		File temp = new File(fileName);
		String absolutePath = temp.getAbsolutePath();
		String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
		return filePath;
	}

	private String getFullDstName(String srcFileName, String dstFileName) {
		String resultFileName = getFilePath(srcFileName) + "\\" + dstFileName;
		return resultFileName;
	}

	private boolean renameFile(String inFullFileName, String outShortFileName) {
		if (inFullFileName == null || outShortFileName == null) {
			return false;
		}
		File inF = new File(inFullFileName);
		File ouF = new File(getFullDstName(inFullFileName, outShortFileName));
		inF.renameTo(ouF);
		return true;
	}

	public boolean rebuild(String inputFileName, String path) {
		cntTotal = 0;
		cntComplete = 0;
		// TODO Auto-generated method stub
		if (inputFileName == null || path == null) {
			logger.log(Level.INFO, "Bad input parameters");
			return false;
		}
		logger.log(Level.INFO, "inputFileName = " + inputFileName + " " + "path = " + path);
		// Load file from text file
		try {
			Scanner inFile = new Scanner(new File(inputFileName)).useDelimiter("\r\n");
			while (inFile.hasNext()) {
				listOfOutFileNames.add(inFile.next());
			}
			for (int i = 0; i < listOfOutFileNames.size(); i++) {
				logger.log(Level.INFO, "OUT File name is" + " " + listOfOutFileNames.get(i));
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.toString());
			return false;
		}
		//
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fullFileName = path + "\\" + listOfFiles[i].getName();
				logger.log(Level.INFO, "File " + fullFileName);
				listOfFileNames.add(fullFileName);
			} else if (listOfFiles[i].isDirectory()) {
				logger.log(Level.INFO, "Directory " + listOfFiles[i].getName());
			}
		}
		//
		cntTotal = listOfFileNames.size();
		//
		return true;
	}

	public boolean process(int index) {
		if (listOfOutFileNames == null) {
			return false;
		}
		if (index < listOfOutFileNames.size()) {
			if (renameFile(listOfFileNames.get(index), listOfOutFileNames.get(index))) {
				logger.log(Level.INFO,
						"Renamed " + listOfFileNames.get(index) + " to " + listOfOutFileNames.get(index));
			} else {
				logger.log(Level.INFO,
						"Can not rename " + listOfFileNames.get(index) + " to " + listOfOutFileNames.get(index));
			}
		} else {
			logger.log(Level.INFO, "New file name not found for " + listOfFileNames.get(index));
		}
		cntComplete++;
		return true;
	}

}
