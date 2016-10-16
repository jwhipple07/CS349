package photoViewer;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PhotoViewer extends PhotoViewerLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2324440975258995695L;
	private Photo photo = new Photo();
	private DBHelper DB;
	private Integer total;
	private List<Integer> order;
	private Integer index = 0;

	public PhotoViewer(String name) {
		super(name);
		init();
	}

	public void init() {
		DB = new DBHelper();
		order = DB.getOrder();
		if (!order.isEmpty()) {
			Integer index = DB.getActivePhotoIndex(); //get last active photo
			new nextPrev(order.get(index)).start();
		} else {
			showPicture(photo);
		}
		addListeners();

	}

	public static void main(String[] args) {
		PhotoViewerLayout frame = new PhotoViewer("PhotoViewer");
		frame.pack();
		frame.setMinimumSize(new Dimension(750, 750));
		frame.setVisible(true);
	}

	public void addListeners() {

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		add.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				loading();
				photo = DB.putImage(openImageFromFile(fileChooser));
				if (index == order.size()) {
					order.add(photo.id);
				} else {
					order.add(index, photo.id);
				}
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						showPicture(photo);						
					}
				});
			}

		});
		
		delete.addActionListener(e -> {
			if(order.isEmpty()){
				return;
			}
			int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this photo?", "Warning",
					JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION) {
				if (DB.deletePhoto(photo)) {
					index = order.indexOf(photo.id);
					if (index == order.size() - 1) { //last photo was deleted
						index = index - 1 < 0 ? 0 : index - 1;
					}
					Integer id = 0;
					order.remove(photo.id);
					if(!order.isEmpty()){
						id = order.get(index);
					}
					photo = DB.getPhoto(id);
					SwingUtilities.invokeLater(new Runnable(){
						public void run(){
							showPicture(photo);						
						}
					});
				} else {
					JOptionPane.showMessageDialog(null, "There was an error deleting this photo.");
				}
			}
		});
		
		save.addActionListener(e -> {
			if (photo.validateDate(dateTextField.getText())) {
				photo.description = descriptionTextArea.getText();
				DB.updatePhoto(photo);
				save.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid date Format. Expecting yyyy-MM-dd.");
			}
		});

		maintainMenu.addActionListener(e -> {
			new editVisibility(true).start();
		});

		browse.addActionListener(e -> {
			new editVisibility(false).start();
		});

		nextButton.addActionListener(e -> {
			loading();
			index = order.indexOf(photo.id);
			new nextPrev(order.get(index + 1)).start();

		});
		
		prevButton.addActionListener(e -> {
			loading();
			index = order.indexOf(photo.id);
			new nextPrev(order.get(index - 1)).start();
		});
		
		pictureNumberCombobox.addItemListener(i -> {
			if (i.getStateChange() == ItemEvent.SELECTED) {
				loading();
				new nextPrev(order.get(Integer.parseInt(i.getItem().toString()) - 1)).start();
			}
		});
		
		descriptionTextArea.getDocument().addDocumentListener(new showSave());
		dateTextField.getDocument().addDocumentListener(new showSave());

		close.addActionListener(e -> exit());

	}

	public void createNavigator() {
		total = DB.getCount();
		Vector<Integer> numList = new Vector<Integer>();
		for (int i = 1; i <= total; i++) {
			numList.add(i);
		}
		if (numList.size() != pictureNumberCombobox.getItemCount()) {
			pictureNumberCombobox.setModel(new DefaultComboBoxModel<Integer>(numList));
			pictureCountLabel.setText(" of " + total);
		}
		pictureNumberCombobox.setSelectedIndex(order.indexOf(photo.id));
		index = order.indexOf(photo.id) + 1;
		if (total <= 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
		} else if (index == 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		} else if (index == total) {
			prevButton.setEnabled(true);
			nextButton.setEnabled(false);
		} else {
			prevButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
	}

	public void exit() {
		DB.saveOrder(order);
		DB.setActivePhoto(photo);
		System.exit(0);
	}

	public void loading() {
		imageLabel.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\photoViewer\\loadingTwo.gif"));
	}

	public Photo openImageFromFile(JFileChooser fileChooser) {
		File selectedFile = fileChooser.getSelectedFile();
		Photo newPhoto = new Photo();
		newPhoto.image = new ImageIcon(selectedFile.getAbsolutePath());
		try {
			BufferedImage image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			newPhoto.imageByte = baos.toByteArray();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String temp = newPhoto.image.getDescription();
		newPhoto.description = temp.substring(temp.lastIndexOf("\\") + 1, temp.lastIndexOf("."));
		newPhoto.date = new Date(Calendar.getInstance().getTimeInMillis());
		return newPhoto;
	}

	public void showPicture(Photo photo) {
		descriptionTextArea.setText(photo.description);
		dateTextField.setText(photo.date.toString());
		imageLabel.setIcon(new ImageIcon((photo.image).getImage().getScaledInstance(700, 500, Image.SCALE_DEFAULT)));
		createNavigator();

	}

	class editVisibility extends Thread {
		private boolean editable;

		public editVisibility(boolean editable) {
			this.editable = editable;
		}

		public void run() {
			browse.setEnabled(editable);
			maintainMenu.setEnabled(!editable);
			buttonPane.setVisible(editable);
			descriptionTextArea.setEditable(editable);
			descriptionTextArea.setOpaque(editable);
			dateTextField.setEditable(editable);
		}
	}

	class nextPrev extends Thread {
		Integer id;

		public nextPrev(Integer id) {
			this.id = id;
		}

		@Override
		public void run() {
			photo = DB.getPhoto(id);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					showPicture(photo);
					save.setVisible(false);
				}
			});
		}
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

		public void showSaveButton() {
			save.setVisible(true);
		}
	}

}
