package org.tos.jartic.flsrename.proccessor;

public interface FlsProcessorInterface {
	public boolean rebuild(String inputFileName, String path);

	public boolean process(int index);
}
