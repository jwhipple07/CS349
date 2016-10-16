package photoViewer;

import java.awt.*;
import javax.swing.*;

public class PhotoViewerLayout extends JFrame {
	
	private static final long serialVersionUID = 8973438971186075837L;
	protected JTextArea descriptionTextArea;
	protected JTextField dateTextField;
	protected JLabel imageLabel;
	protected JLabel pictureCountLabel;
	protected JComboBox<Integer> pictureNumberCombobox;
	protected JButton prevButton;
	protected JButton nextButton;
	protected JButton delete;
	protected JButton save;
	protected JButton add;
	protected JPanel buttonPane;
	protected JMenuItem browse;
	protected JMenuItem maintainMenu;
	protected JMenuItem close;

	public PhotoViewerLayout(String name) {
		super(name);
		
		Container contentPane = getContentPane();

		contentPane.add(createMenuBar(), BorderLayout.NORTH);

		imageLabel = new JLabel("", SwingConstants.CENTER);
		JScrollPane scrollPane = new JScrollPane(imageLabel);
		scrollPane.setPreferredSize(new Dimension(700, 500));
		ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "\\src\\photoViewer\\loadingTwo.gif");
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
		save.setVisible(false);
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
	}
	

	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		close = new JMenuItem("Close");
		JMenu view = new JMenu("View");
		browse = new JMenuItem("Browse");
		maintainMenu = new JMenuItem("Maintain");
		browse.setEnabled(false); //default to not enabled
		
		menu.add(close);
		view.add(browse);
		view.add(maintainMenu);
		menuBar.add(menu);
		menuBar.add(view);
		return menuBar;
	}


}
