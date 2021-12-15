/**
* @file MainGui.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief Main controller of the graphic interface 
*/
package gui;

import agents.PerceptionAgent;


/*! The class which will interact with the upcoming windows (graphic interface)*/
public class MainGui extends Thread {
	/** A string type
	 *  It will represent the title of the window that we'll see
	 */
	String title;
	/** A JFrameApp type
	 * 	This attribute contains the graphic interface
	 */
	JFrameApp frame;

	/**
	 * The constructor, here it is created the interface
	 * @param title the name of the upcoming window
	 * @param agent agent that have created the GUI (perceptionAgent)
	 * @see run()
	 */
	public MainGui(String title, PerceptionAgent agent) {
		
		
		this.title = title;
		this.frame = new JFrameApp(title, agent);
	}
	/**
	 * The method which runs the interface and makes it visible
	 */
	public void run() {
		
		/**
		 * We set the title to our graphic interface
		 * and also make visible the window, so we
		 * can start interacting with it.
		 */
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setVisible(true);	
	}

}