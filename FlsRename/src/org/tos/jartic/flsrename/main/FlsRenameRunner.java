/**
 * 
 */
package org.tos.jartic.flsrename.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.tos.jartic.flsrename.forms.FlsMainFrame;
import org.tos.jartic.flsrename.proccessor.FlsProcessor;
import org.tos.jartic.flsrename.proccessor.FlsProcessorInterface;

/**
 * @author ora
 *
 */
public class FlsRenameRunner {
	private static Logger logger = Logger.getLogger(FlsRenameRunner.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.log(Level.INFO, "Start application..");
		//
		FlsMainFrame mainFrame = new FlsMainFrame();
		mainFrame.runMainLogic();
		//
		logger.log(Level.INFO, "Finish application..");
	}

}
