package com.iti.dexcenter.client;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;//DOM,Event,Window
import com.google.gwt.user.client.ui.*;

public class GWTExperiment implements EntryPoint{

	private VerticalPanel vPanel;
	private FileListBox list;
	private MultiFileUploadPanel deck;
	private FlowPanel listContainer;
	private Button removeAll;
	private Button removeSelected;
	private TabPanel tabPanel;
	private HorizontalPanel hPanel;
	
	public void onModuleLoad(){
		vPanel = new VerticalPanel();
		list = new FileListBox();
		deck = new MultiFileUploadPanel();
		listContainer = new FlowPanel();
		removeAll = new Button("Remove All");
		removeSelected = new Button("Remove Selected");
		listContainer.add(list);
		tabPanel = new TabPanel();
		HorizontalPanel t1 = new HorizontalPanel();
		t1.add(new Image("format-justify-left.png"));
		t1.add(new HTML("&nbsp;"));
		t1.add(new Label("File List",false));
		HorizontalPanel t2 = new HorizontalPanel();
		t2.add(new Image("edit-find.png"));
		t2.add(new HTML("&nbsp;"));
		t2.add(new Label("File Details",false));
		
		tabPanel.add(listContainer,t1);
		tabPanel.add(new SimplePanel(),t2);
		
		final FormPanel form = new FormPanel();
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);
		form.setAction("/fileselect/catchfiles.php");
		
		final Button submit = new Button("Submit");
		
		//form.setWidget(deck);
		//vPanel.add(form);
		
		
		//hiden FU hack-----------------------------------------//
		final Button addfiles = new Button("Add Files");
		addfiles.addStyleName("gwt-iti-AddFilesButton");
		vPanel.add(addfiles);
		//hiden fu hack(button should be visible only-----------//
		
		vPanel.add(deck);//add if not nested in form field
		
		//add to slot1 instead, wrapped with <form>
		//RootPanel.get("slot1").add(deck);

		FlowPanel somepanel = new FlowPanel();
		somepanel.add(tabPanel);
		vPanel.add(somepanel);
		hPanel = new HorizontalPanel();
		hPanel.add(removeAll);
		hPanel.add(removeSelected);
		
		hPanel.add(submit);
		
		vPanel.add(hPanel);
		vPanel.setWidth("700px");
		somepanel.setWidth("100%");
		tabPanel.setWidth("100%");
		listContainer.setWidth("100%");
		list.setWidth("100%");
		//list.setVisibleItemCount(8);
		list.setHeight("100%");
		tabPanel.selectTab(0);
		
		FixedWidthGrid header = new FixedWidthGrid();
		FixedWidthGrid body = new FixedWidthGrid();
		header.resize(1,5);
		header.setText(0,0,"Filename");
		header.setText(0,1,"Datatype");
		header.setText(0,2,"CAD System");
		header.setText(0,3,"CAD Format");
		header.setText(0,4,"Location");
		body.resize(10,5);
		for (int x=0;x<10;++x){
			body.setText(x,0,"cadfile.prt");
			body.setText(x,1,"3D Part");
			body.setText(x,2,"SomeSystem");
			body.setText(x,3,"ItsFormat");
			body.setText(x,4,"/the/full/path");
		}
		final ScrollTable dlist = new ScrollTable(body,header);
		final SimplePanel extraBorder = new SimplePanel();
		extraBorder.addStyleName("gwt-ExtraBorder");
		extraBorder.setWidget(dlist);
		extraBorder.setSize("100%","100%");
		((SimplePanel)tabPanel.getWidget(1)).setWidget(dlist);
		tabPanel.getDeckPanel().setSize("100%","150px");
		dlist.setSize("100%","100%");
		
		final FileSelectListener listener = new FileSelectListenerImpl();
		list.setFileSelectListener(listener);
		deck.setFileSelectListener(listener);

		submit.addClickListener(new ClickListener(){
			public void onClick(Widget sender){
				form.submit();
			}
		});
		
		removeAll.addClickListener(new ClickListener(){
			public void onClick(Widget sender){
				listener.onRemoveAll();
			}
		});
		
		removeSelected.addClickListener(new ClickListener(){
			public void onClick(Widget sender){
				listener.onFileDeselect();
			}
		});

	    form.addFormHandler(new FormHandler() {
	        public void onSubmit(FormSubmitEvent event) {
	            if (deck.getChildren().size() == 1) {
	                Window.alert("No files specified");
	                event.setCancelled(true);
	            }
	        }
	        public void onSubmitComplete(FormSubmitCompleteEvent event) {
	            //Window.alert(event.getResults());
	        }
	    });

		//RootPanel.get("slot1").add(vPanel);
		form.setWidget(vPanel);
		RootPanel.get("slot1").add(form);
		
	}
	
	private class FileSelectListenerImpl implements FileSelectListener
	{
		
		public void onFileSelect(){
			final FlowPanel container = (FlowPanel) deck.getWidget(0);
			final FileUpload upload = (FileUpload) container.getWidget(0);
			final String path = upload.getFilename();			
			boolean pathInList = false;

			/*Checking for duplicate entries in list*/
			for (int x = 0; x < list.getItemCount(); ++x){
				if (path.equals(list.getValue(x))){
					pathInList = true;
					break;	
				}
			}

			if (pathInList){
				// Path already in list, replace field with a blank one
				deck.getChildren().remove(0);
				
			}
			else{	
				// Show the new selection in the file list
				list.addItem(getShortFilename(path),path);
				
			}
			
			final FileUpload newUpload = deck.newEventfulFileUpload();
			newUpload.setName("file[]");
			
			//*******************//
			// hack to remove text field on FileUpload (works in all browsers?)
			//*******************//
			//newUpload.setWidth("50px");
			
			// FileUpload needs to be wrapped in FlowPanel
			// to be sized correctly
			final FlowPanel newContainer = new FlowPanel();
			newContainer.add(newUpload);

			// Insert a blank FileUpload on top of the current one
			deck.insert(newContainer, 0);
			deck.showWidget(0);
		}
		
		public void onFileDeselect()
		{
			// Remove FileUpload and list entry for each path that is selected
			for (int x = 0; x < list.getItemCount();)
				if (list.isItemSelected(x)){
					// Remove item from list and FileUpload stack
					deck.getChildren().remove(x + 1);
					list.removeItem(x);	
				}
				else{
					// Increment index to skip over unselected item
					x++;	
				}
		}

		public void onRemoveAll(){
			// Keep removing items until there are none left
			while (list.getItemCount() > 0){
				list.removeItem(0);
				
				// The file upload at index 0 is the only empty one,
				// the next one for the user to enter a path in
				deck.getChildren().remove(1);	
			}
		}
	}

	private static String getShortFilename(String filename){
		
		// No path should contain a '/' or a '\\'
		final int lastSlashIndex = filename.lastIndexOf('/');
		final int lastBackslashIndex = filename.lastIndexOf('\\');
		
		if (lastSlashIndex > 0)
			return filename.substring(lastSlashIndex + 1);
		if (lastBackslashIndex > 0)
			return filename.substring(lastBackslashIndex + 1);
		
		return filename;
	}
}
