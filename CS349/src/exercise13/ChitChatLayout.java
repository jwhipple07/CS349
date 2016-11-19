package exercise13;

import java.awt.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.Border;

public class ChitChatLayout extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6714202191042947261L;
	private static final int LARGE_FONT_SIZE = 28;
	private static final int MEDIUM_FONT_SIZE = 20;

	protected JButton sendButton;
	protected JTextArea messageTextArea;
	protected JTextField ipTextField;
	protected JTextField senderNameTextField;
	protected String localAddress = "";
	// defined at the class level so addAMessage()
	//   can update it with incoming messages.
	protected JPanel messagesReceivedPanel;

	public ChitChatLayout(String name) {
		super(name);
		setTitle("Chit-Chat");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// The default layout for the content pane
		// of a JFrame is BorderLayout
		Container contentPane = getContentPane();

		// Note, to find our IP address under Windows
		//   execute ipconfig from cmd line.
		try {
			localAddress = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel ipLabel = new JLabel(localAddress);
		ipLabel.setFont(new Font("SansSerif", Font.PLAIN, LARGE_FONT_SIZE));
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(ipLabel, BorderLayout.NORTH);

		JComponent scrollableMessagesReceived = buildMessagesReceivedPanel();
		contentPane.add(scrollableMessagesReceived, BorderLayout.CENTER);

		JComponent sendMessagePanel = buildSendMessagePanel();
		contentPane.add(sendMessagePanel, BorderLayout.SOUTH);
	}

	private JComponent buildMessagesReceivedPanel() {
		messagesReceivedPanel = new JPanel();

		messagesReceivedPanel.setLayout(new GridBagLayout());

		JScrollPane scrollPane = new JScrollPane(messagesReceivedPanel);

		Border titledBorder = BorderFactory.createTitledBorder("Receive");
		Border compoundBorder = BorderFactory.createCompoundBorder(titledBorder, scrollPane.getBorder());
		scrollPane.setBorder(compoundBorder);

		return scrollPane;
	}

	protected void addAMessage(String msg) {
		GridBagConstraints constraints = new GridBagConstraints();

		// Defaults
		constraints.gridx = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;

		JTextArea messageTextArea = new JTextArea(msg, 4, 20);
		messageTextArea.setEditable(false);
		messageTextArea.setLineWrap(true);
		messageTextArea.setWrapStyleWord(true);

		messageTextArea.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		JScrollPane sourceScrollPane = new JScrollPane(messageTextArea);
		messagesReceivedPanel.add(sourceScrollPane, constraints);
		this.validate();
	}

	private JComponent buildSendMessagePanel() {
		JPanel messagePanel = new JPanel();

		messagePanel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();

		// Defaults
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.anchor = GridBagConstraints.NORTHWEST;

		JLabel ipLabel = new JLabel("Receiver's IP:");
		ipLabel.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		messagePanel.add(ipLabel, constraints);

		constraints.gridy = 1;
		JLabel senderNameLabel = new JLabel("Sender's Name:");
		senderNameLabel.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		messagePanel.add(senderNameLabel, constraints);

		constraints.weighty = 1;
		constraints.gridy = 2;
		JLabel messageLabel = new JLabel("Message:");
		messageLabel.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		messagePanel.add(messageLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		ipTextField = new JTextField(20);
		ipTextField.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		messagePanel.add(ipTextField, constraints);

		constraints.gridy = 1;
		senderNameTextField = new JTextField(20);
		senderNameTextField.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		messagePanel.add(senderNameTextField, constraints);

		constraints.gridy = 2;
		constraints.weighty = 1;
		messageTextArea = new JTextArea(5, 20);
		messageTextArea.setLineWrap(true);
		messageTextArea.setWrapStyleWord(true);

		messageTextArea.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		JScrollPane sourceScrollPane = new JScrollPane(messageTextArea);
		messagePanel.add(sourceScrollPane, constraints);

		constraints.weighty = 0;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.NONE;
		sendButton = new JButton("Send");

		sendButton.setFont(new Font("SansSerif", Font.PLAIN, MEDIUM_FONT_SIZE));
		messagePanel.add(sendButton, constraints);

		Border titledBorder = BorderFactory.createTitledBorder("Send");
		messagePanel.setBorder(titledBorder);
		return messagePanel;
	}

}