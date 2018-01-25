package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import encryption.AES;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Main {
	private JFrame frame;
	private JTextField fileChoiceText;
	private JTextField txtSelectAKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 729, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		fileChoiceText = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, fileChoiceText, 34, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, fileChoiceText, 137, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, fileChoiceText, -250, SpringLayout.EAST, frame.getContentPane());
		fileChoiceText.setFont(new Font("Courier New", Font.ITALIC, 14));
		fileChoiceText.setText("select an image");
		fileChoiceText.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(fileChoiceText);
		fileChoiceText.setColumns(10);
		
		JButton btnSelect = new JButton("...");
		springLayout.putConstraint(SpringLayout.NORTH, btnSelect, 0, SpringLayout.NORTH, fileChoiceText);
		springLayout.putConstraint(SpringLayout.WEST, btnSelect, 22, SpringLayout.EAST, fileChoiceText);
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(".");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();				
				fileChoiceText.setText(file.getAbsolutePath());
			}
		});
		frame.getContentPane().add(btnSelect);
		
		JButton btnEncrypt = new JButton("Encrypt");
		springLayout.putConstraint(SpringLayout.NORTH, btnEncrypt, 182, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnEncrypt, 137, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, fileChoiceText, -119, SpringLayout.NORTH, btnEncrypt);
		
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File imageFile = new File(fileChoiceText.getText());
					AES.encrypt(imageFile);
				}
				catch (Exception ex) {
					System.out.println("The file was not found or image extension doesn't exist...");
				}
				
			}
		});
		frame.getContentPane().add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File encryptedimageFile = new File(fileChoiceText.getText());
					File keyFile = new File (txtSelectAKey.getText());
					AES.decrypt(keyFile, encryptedimageFile);
					
				}
				catch (Exception ex) {
					
				}
				
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnDecrypt, 0, SpringLayout.NORTH, btnEncrypt);
		springLayout.putConstraint(SpringLayout.EAST, btnDecrypt, 0, SpringLayout.EAST, btnSelect);
		frame.getContentPane().add(btnDecrypt);
		
		txtSelectAKey = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtSelectAKey, 47, SpringLayout.SOUTH, fileChoiceText);
		springLayout.putConstraint(SpringLayout.WEST, txtSelectAKey, 0, SpringLayout.WEST, fileChoiceText);
		springLayout.putConstraint(SpringLayout.EAST, txtSelectAKey, 318, SpringLayout.WEST, fileChoiceText);
		txtSelectAKey.setText("select a key");
		txtSelectAKey.setHorizontalAlignment(SwingConstants.CENTER);
		txtSelectAKey.setFont(new Font("Courier New", Font.ITALIC, 14));
		txtSelectAKey.setColumns(10);
		frame.getContentPane().add(txtSelectAKey);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(".");
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();				
				txtSelectAKey.setText(file.getAbsolutePath());
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, txtSelectAKey);
		springLayout.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, btnSelect);
		frame.getContentPane().add(button);
	}
}
