/**
* @file JFrameApp.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief Window that contains the Graphic User Interface
*/

package gui;
import agents.PerceptionAgent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*! The class which define the features of the upcoming window*/
public class JFrameApp extends JFrame implements ActionListener {
	
public static final long serialVersionUID = 1L;
	
	/** Elements of the window
 	*  These are all the objects we will use in our graphic interface
 	*/	
    private JPanel contentPane;
	private JLabel imageUploaded;
	private JButton UploadButton;
	private File file;
	private JButton ConfirmButton;
	private JButton ChangeButton;
	private JLabel title;
	private JLabel text;
	public JLabel calculating;
	public JLabel result1, result2, result3;
	public JLabel results;
	
	protected PerceptionAgent agent;
	
	/**
	 * The constructor, here it is created the window
	 * @param name of the upcoming window
	 * @param agent that have created the GUI (perceptionAgent)
	 */
	public JFrameApp(String name, PerceptionAgent agent) {
		super();
		this.agent = agent;
		
		/** These instructions create the window with the dimensions
		 * and borders that we specify.
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/** These instructions shown a welcome text
		 * to the user which is entering the application
		 * but also the instructions to use it.
		 */
		title = new JLabel("Welcome to Celebrity FaceMatch!");
		title.setFont(new Font("Tahoma", Font.BOLD, 26));
		title.setBounds(120, 15, 550, 62);
		contentPane.add(title);
		text = new JLabel("Upload a photo of yourselfand find out which celebrity you look like");
		text.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text.setBounds(60, 84, 638, 30);
		contentPane.add(text);
		
		/**These instructions are the one we choose to store
		 * the image the user uploads in the GUI, and then 
		 * show to him/her so the person can decide 
		 * if it is correct or it needs to be changed.
		 */
		imageUploaded = new JLabel((String) null);
		imageUploaded.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		imageUploaded.setBounds(59, 150, 245, 266);
		contentPane.add(imageUploaded);
		
		/**These instructions are the one that creates the 
		 * "Upload image" button, and we add this button to
		 * the place the Action Listener needs to take an eye on
		 * @see actionPerformed(ActionEvent event)
		 */
		UploadButton = new JButton("Upload image");
		UploadButton.addActionListener(this);
		UploadButton.setBounds(59, 427, 245, 23);
		contentPane.add(UploadButton);
		
		/**These instructions are the one that creates the 
		 * "Confirm image" and "Change image" button, and 
		 * we add these buttons to the place the 
		 * Action Listener needs to take an eye on
		 * @see actionPerformed(ActionEvent event)
		 */		
		ConfirmButton = new JButton("Confirm");
		ConfirmButton.addActionListener(this);
		ConfirmButton.setBounds(183, 427, 121, 23);
		contentPane.add(ConfirmButton);	
		ChangeButton = new JButton("Change");
		ChangeButton.addActionListener(this);
		ChangeButton.setBounds(59, 427, 121, 23);
		contentPane.add(ChangeButton);
		
		/**These instructions show to the user a gif
		 * of loading, so the user knows the pc is working
		 * and it is not in a state that is blocked
		 */	
		calculating = new JLabel("");
		calculating.setIcon(new ImageIcon("images/gif_carga.gif"));
		calculating.setBounds(370, 190, 320, 215);
		calculating.setFont(new Font("Tahoma", Font.BOLD, 15));
		calculating.setVisible(false);
		calculating.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(calculating);
        
        
        /**These instructions configure where the results will appear
         * when the answer from the computation behaviour is ready
		 */	
        results = new JLabel("");
		results.setBounds(338, 175, 350, 50);
		results.setFont(new Font("Tahoma", Font.BOLD, 15));
		results.setVisible(true);
		results.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(results);
		
        
        /**These following instructions prepare the text that will show
         * the celebrities in the Graphic Interface
		 */	
		result1 = new JLabel("");
		result1.setBounds(350, 225, 300, 50);
		result1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		result1.setVisible(true);
		result1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(result1);
        
		result2 = new JLabel("");
		result2.setBounds(350, 275, 300, 50);
		result2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		result2.setVisible(true);
		result2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(result2);
        
		result3 = new JLabel("");
		result3.setBounds(350, 325, 300, 50);
		result3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		result3.setVisible(true);
		result3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(result3);
	}
	
	/**
	 * The method that is called when we interact with the interface
	 * @param event
	 */
	public void actionPerformed(ActionEvent event) {
		Object eventObject = event.getSource();
		/**
		 * Here we create the environment where the user clicks on "Upload Image" or "Change Image"
		 */
		if (eventObject == UploadButton || eventObject == ChangeButton) {
			try {
				results.setVisible(false);
				result1.setVisible(false);
				result2.setVisible(false);
				result3.setVisible(false);
				
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png", "jpeg");
				fileChooser.setFileFilter(filter);
				
				int selection = fileChooser.showOpenDialog(this);
				if (selection == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					
					ImageIcon i = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
					Image img = i.getImage();
					Image imgScaled = img.getScaledInstance(imageUploaded.getWidth(), imageUploaded.getHeight(), Image.SCALE_SMOOTH);
		            ImageIcon scaledIcon = new ImageIcon(imgScaled);
		            imageUploaded.setIcon(scaledIcon);
					
					UploadButton.setVisible(false);
					ConfirmButton.setVisible(true);
					ChangeButton.setVisible(true);
				}

			} catch (Exception exception) {
				System.err.println("Error al adjuntar la imagen");
			}
		/**
		* If the button clicked is the "Confirm Image"
		*/
		} if (eventObject == ConfirmButton) {
			calculating.setVisible(true);
			
			try {
				agent.sendMessage(file);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	/**
	 * Where the results are ready to be shown this method it's called, showing the information to the user
	 * @param name1 the celebrity the person looks like the most
	 * @param name2 the second celebrity the person looks like the most
	 * @param name3 the celebrity the person looks like the less
	 */
	public void ShowResults(String name1, String name2, String name3) {
    	calculating.setVisible(false);
				
    	results.setText("The celebrities you look like the most are:");
    	result1.setText("1.- " + name1);
		result2.setText("2.- " + name2);
		result3.setText("3.- " + name3);
		
		results.setVisible(true);
		result1.setVisible(true);
		result2.setVisible(true);
		result3.setVisible(true);
	}

}