/**
* @file JFrameApp.java
* @Author Jorge Hdez. Batista
* @date 4/12/21
* @brief Window that contains the Graphic User Interface
*/

package gui;

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
	
	/**
	 * The constructor, here it is created the window
	 * @param name
	 */
	public JFrameApp(String name) {
		super();
		
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
		text = new JLabel("Upload a photo of yourselt and find out which celebrity you look like");
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
	}
	
	public void ActionPerformed() {
		
	}

	/**
	 * The method that is called when we interact with the interface
	 * @param event
	 */
	public void actionPerformed(ActionEvent event) {
		Object eventObject = event.getSource();
		if (eventObject == UploadButton || eventObject == ChangeButton) {
			try {
				/*results.setVisible(false);
				result1.setVisible(false);
				result2.setVisible(false);
				result3.setVisible(false);*/
				
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "gif", "png", "jpeg");
				fileChooser.setFileFilter(filter);
				
				int selection = fileChooser.showOpenDialog(this);
				if (selection == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					//imageUploaded.setIcon(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()));
					
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
		} if (eventObject == ConfirmButton) {
			calculating.setVisible(true);
			
			/*try {
				agent.sendMessage(file);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}*/
		}
		
	}

}