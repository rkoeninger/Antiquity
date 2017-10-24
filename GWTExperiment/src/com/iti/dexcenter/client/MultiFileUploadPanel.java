package com.iti.dexcenter.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * A <code>DeckPanel</code> which displays a stack of <code>FileUpload</code>
 * widgets. Each time a file is selected by the top widget, a new upload field
 * is added on top of it. This gives the illusion that the field is being
 * cleared and the file choice stored elsewhere.
 * 
 * @version 0.2
 * @author Robert Koeninger
 */
public class MultiFileUploadPanel extends DeckPanel
{
	
	/**
	 * Event listener to call once a file has been selected.
	 */
	private FileSelectListener listener;
		
	/**
	 * Creates a new <code>MulitFileUploadPanel</code> with the given
	 * <code>ListBox</code> to display selected files in.
	 * 
	 * @param outputList Display for selected files
	 */
	public MultiFileUploadPanel()
	{
		
		final FileUpload upload = new EventfulFileUpload();
		final FlowPanel container = new FlowPanel();
		upload.setName("file[]");
		container.add(upload);
		add(container);
		showWidget(0);
		
	}
	
	/**
	 * Gets a list of all the <code>FileUpload</code> elements in the stack.
	 * This method has been given the <code>public</code> level of visibility
	 * up from the <code>protected</code> level it had in it's superclass.
	 * 
	 * Widgets <code>0</code> through <code>n - 2</code> should already have
	 * files selected for them. The widget at index <code>n - 1</code> should
	 * be clear and be the only one visible to the user.
	 * 
	 * @return A collection of <code>FileUpload</code> fields.
	 */
	public WidgetCollection getChildren()
	{
		
		return super.getChildren();
		
	}
	
	/**
	 * Tests to see if this <code>DeckPanel</code> has a
	 * <code>FileUpload</code> field with the given file path selected.
	 * 
	 * @param path The path of the file in question
	 * @return <code>true</code> if the file has already been selected 
	 */
	public boolean containsFile(String path)
	{	
		
		final WidgetCollection children = getChildren();
		
		for (int x = 0; x < children.size(); ++x)
		{
			
			final FileUpload upload = (FileUpload) children.get(x);
			
			if (path.equals(upload.getFilename()))
				return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Sets the event listener for selections from the currently visible
	 * <code>FileUpload</code>. Replaces previous listener if one was set.
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
	 * Creates a new <code>FileUpload</code> which reports
	 * <code>ONCHANGE</code> events to this <code>MultiFileUploadPanel</code>'s
	 * <code>FileSelectListener</code>. 
	 * 
	 * @return An eventful <code>FileUpload</code> widget
	 */
	public FileUpload newEventfulFileUpload()
	{
		
		return new EventfulFileUpload();
		
	}
	
	/**
	 * A specialized subclass of <code>FileUpload</code> which has an event
	 * listener to automatically add another, empty <code>FileUpload</code>
	 * on top of itself in the <code>DeckPanel</code>.
	 */
	private class EventfulFileUpload extends FileUpload
	{

		/**
		 * Creates a new <code>FileUpload</code> with event listener enabled.
		 */
		public EventfulFileUpload()
		{
			
			sinkEvents(Event.ONCHANGE);
			
			
		}
		
		/**
		 * Redirects browser events to this <code>DeckPanel</code>'s listener.
		 */
		public void onBrowserEvent(Event event)
		{

			if ((DOM.eventGetType(event) == Event.ONCHANGE) &&
			(listener != null))
			{
			
				// Alert other components of file addition
				listener.onFileSelect();
				
			}

			// Propagate event to superclass
			super.onBrowserEvent(event);
			
		}
		
	}	

}