package com.iti.dexcenter.client;

import java.util.EventListener;

/**
 * An interface to unify the behavior of the <code>FileUpload</code> fields,
 * the <code>ListBox</code> where the files are listed and any other buttons
 * or components that may be added later.
 * 
 * @version 0.2
 * @author Robert Koeninger
 */
public interface FileSelectListener extends EventListener
{

	/**
	 * Called when a file has been selected and needs to be added to the UI.
	 */
	public void onFileSelect();
	
	/**
	 * Called when files have been chosen for removal.
	 */
	public void onFileDeselect();
	
	/**
	 * Called to remove all files from the selection list.
	 */
	public void onRemoveAll();
	
}
