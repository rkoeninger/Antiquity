package com.iti.dexcenter.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * A multi-selectable <code>ListBox</code> that reports delete key events to
 * it's <code>FileSelectListener</code> when items are selected (this means
 * they need to be removed).
 * 
 * @version 0.2
 * @author Robert Koeninger
 */
public class FileListBox extends ListBox
{

	/**
	 * Event listener to call when files have been deselected.
	 */
	private FileSelectListener listener;
	
	/**
	 * Creates a new multi-selectable <code>ListBox</code>. This widget is also
	 * setup to receive key events in case a file is removed using the delete
	 * key.
	 */
	public FileListBox()
	{
		
		super(true); // Set multiple selections enabled
		sinkEvents(Event.ONKEYDOWN);
		
	}

	/**
	 * Sets the event listener for deselections from the file list. Files are
	 * removed from the list by selecting them and then pressing the delete
	 * key.
	 * 
	 * @param listener The new event listener
	 */
	public void setFileSelectListener(FileSelectListener listener)
	{
		
		this.listener = listener;
		
	}
	
	/**
	 * Gets the event listener, <code>null</code> if there is none.
	 * 
	 * @return The event listener currently being called
	 */
	public FileSelectListener getFileSelectListener()
	{
		
		return listener;
		
	}
	
	/**
	 * Reports file deselection to the <code>FileSelectListener</code>.
	 */
	public void onBrowserEvent(Event event)
	{

		if ((DOM.eventGetType(event) == Event.ONKEYDOWN) &&
		(DOM.eventGetKeyCode(event) == 46) && (listener != null))
		{

			listener.onFileDeselect();
				
		}
		
		// Propagate event
		super.onBrowserEvent(event);
		
	}
	
}