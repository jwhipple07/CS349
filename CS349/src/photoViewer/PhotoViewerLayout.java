package photoViewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PhotoViewerLayout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8973438971186075837L;
	private static final String serializedFilePath = "C:/CS349/photoViewer.ser";

	private photoAlbum album;

	private JTextArea descriptionTextArea;
	private JTextField dateTextField;
	private JLabel imageLabel;
	private JLabel pictureCountLabel;
	private JComboBox<Integer> pictureNumberCombobox;
	private JButton prevButton;
	private JButton nextButton;
	private JButton delete;
	private JButton save;
	private JButton add;
	private JPanel buttonPane;

	public PhotoViewerLayout() {
		loadAlbum();
		Container contentPane = getContentPane();

		contentPane.add(createMenuBar(), BorderLayout.NORTH);

		imageLabel = new JLabel("", SwingConstants.CENTER);
		JScrollPane scrollPane = new JScrollPane(imageLabel);
		scrollPane.setPreferredSize(new Dimension(700, 500));
		ImageIcon image = new ImageIcon();
		imageLabel.setIcon(image);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel controlPane = new JPanel();
		controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.PAGE_AXIS));

		JPanel descriptionPane = new JPanel();
		descriptionPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionTextArea = new JTextArea(4, 50);
		descriptionPane.add(descriptionLabel);
		descriptionPane.add(descriptionTextArea);

		JPanel datePane = new JPanel();
		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setPreferredSize(
				new Dimension(descriptionLabel.getPreferredSize().width, dateLabel.getPreferredSize().height));
		dateTextField = new JTextField("", 10);
		datePane.add(dateLabel);
		datePane.add(dateTextField);

		descriptionTextArea.setEditable(false);
		descriptionTextArea.setOpaque(false);
		dateTextField.setEditable(false);

		buttonPane = new JPanel();
		delete = new JButton("Delete");
		save = new JButton("Save Changes");
		add = new JButton("Add Photo");

		buttonPane.add(save);
		buttonPane.add(delete);
		buttonPane.add(add);
		buttonPane.setVisible(false);//default

		JPanel leftRightPane = new JPanel();
		leftRightPane.setLayout(new BorderLayout());
		leftRightPane.add(datePane, BorderLayout.WEST);
		leftRightPane.add(buttonPane, BorderLayout.EAST);

		JPanel southButtonPanel = new JPanel();
		pictureNumberCombobox = new JComboBox<Integer>();
		pictureCountLabel = new JLabel("");
		prevButton = new JButton("<prev");
		nextButton = new JButton("next>");

		southButtonPanel.add(prevButton);
		southButtonPanel.add(pictureNumberCombobox);
		southButtonPanel.add(pictureCountLabel);
		southButtonPanel.add(nextButton);
		FlowLayout flowLayout = (FlowLayout) southButtonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);

		controlPane.add(descriptionPane);
		controlPane.add(leftRightPane);
		controlPane.add(southButtonPanel);

		contentPane.add(controlPane, BorderLayout.SOUTH); 
		updatePanels();
		addActionListeners();
	}

	public static void main(String[] args) {
		JFrame frame = new PhotoViewerLayout();
		frame.pack();
		frame.setMinimumSize(new Dimension(750, 750));
		frame.setVisible(true);
	}

	class showSave implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
			showSaveButton();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			showSaveButton();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			showSaveButton();
		}
	}

	public void addActionListeners() {
		descriptionTextArea.getDocument().addDocumentListener(new showSave());
		dateTextField.getDocument().addDocumentListener(new showSave());

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				album.nextPhoto();
				updatePanels();
			}

		});
		prevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				album.prevPhoto();
				updatePanels();
			}

		});
		pictureNumberCombobox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object item = event.getItem();
					album.photoNum = Integer.parseInt(item.toString());
					album.setCurrentPhoto();
					updatePanels();
				}
			}

		});
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (album.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There is nothing to delete.");
					return;
				}
				int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this photo?", "Warning",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					album.deletePhoto();
					updatePanels();
				}
			}

		});
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					Photo newPhoto = new Photo();
					newPhoto.image = new ImageIcon(selectedFile.getAbsolutePath());
					String temp = newPhoto.image.getDescription();
					newPhoto.description = temp.substring(temp.lastIndexOf("\\") + 1, temp.lastIndexOf("."));
					album.addPhoto(newPhoto);
					updatePanels();
				}

			}

		});
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (album.isEmpty()) {
					JOptionPane.showMessageDialog(null, "There is nothing to save.");
					return;
				}
				//validate date
				if(album.getCurrentPhoto().validateDate(dateTextField.getText())){
					dateTextField.setText(album.getCurrentPhoto().dateText);
					album.getCurrentPhoto().description = descriptionTextArea.getText();
					album.getCurrentPhoto().index = album.getIndex();
					save.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid date Format. Expecting MM/dd/yyyy.");
				}
			}

		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}

		});

		JMenu view = new JMenu("View");
		JMenuItem browse = new JMenuItem("Browse");

		JMenuItem maintainMenu = new JMenuItem("Maintain");
		browse.setEnabled(false); //default to not enabled
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				browse.setEnabled(false);
				maintainMenu.setEnabled(true);
				buttonPane.setVisible(false);
				descriptionTextArea.setEditable(false);
				descriptionTextArea.setOpaque(false);
				dateTextField.setEditable(false);
			}

		});
		maintainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browse.setEnabled(true);
				maintainMenu.setEnabled(false);
				buttonPane.setVisible(true);
				descriptionTextArea.setEditable(true);
				descriptionTextArea.setOpaque(true);
				dateTextField.setEditable(true);
			}

		});
		menu.add(close);
		view.add(browse);
		view.add(maintainMenu);
		menuBar.add(menu);
		menuBar.add(view);
		return menuBar;
	}

	public void exit() {
		try {
			FileOutputStream fileOut = new FileOutputStream(serializedFilePath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(album);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in: " + serializedFilePath);
		} catch (IOException i) {
			i.printStackTrace();
		}

		System.exit(0);
	}

	public void loadAlbum() {
		album = new photoAlbum();
		try {
			FileInputStream fileIn = new FileInputStream(serializedFilePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			album = (photoAlbum) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			album = new photoAlbum();
			return;
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Photo Album class not found");
			c.printStackTrace();
			return;
		}
	}

	public void showSaveButton() {
		save.setVisible(true);
	}

	public void updatePanels() {
		Photo picture;
		if (album == null || album.getTotalSize() == 0) {
			picture = new Photo();
		} else {
			picture = album.getCurrentPhoto();
		}
		Vector<Integer> numList = new Vector<Integer>();
		for (int i = 1; i <= album.getTotalSize(); i++) {
			numList.add(i);
		}
		if (numList.size() != pictureNumberCombobox.getItemCount()) {
			pictureNumberCombobox.setModel(new DefaultComboBoxModel<Integer>(numList));

			pictureCountLabel.setText(" of " + album.getTotalSize());
		}
		if (album.photoNum > 0) {
			pictureNumberCombobox.setSelectedIndex(album.photoNum - 1);
		}
		imageLabel.setIcon(new ImageIcon(picture.image.getImage().getScaledInstance(700, 500, Image.SCALE_DEFAULT)));
		dateTextField.setText(picture.dateText);
		descriptionTextArea.setText(picture.description);

		if (album.isEmpty() || album.getTotalSize() == 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
		} else if (album.photoNum == 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		} else if (album.photoNum.equals(album.getTotalSize())) {
			prevButton.setEnabled(true);
			nextButton.setEnabled(false);
		} else {
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);

		}
		save.setVisible(false);
	}

}
