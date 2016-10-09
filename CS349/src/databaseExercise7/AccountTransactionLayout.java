package databaseExercise7;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountTransactionLayout extends JFrame {

	private static final long serialVersionUID = -2460931238651018605L;

	private JTable table;

	private String[] columnNames = { "Account ID", "Account Name", "Balance" };
	private Object[][] data = { };
    private AccountManager AM = new AccountManager();
    
    
	public AccountTransactionLayout() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		data = AM.getData();
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		table = new JTable(dtm);
		
		Dimension smallerSize = new Dimension(450, 100);
		table.setPreferredScrollableViewportSize(smallerSize);

		JScrollPane scrollPaneForTable = new JScrollPane(table);

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(scrollPaneForTable, constraints);

		constraints.gridx = 0;
		constraints.weighty = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel toLabel = new JLabel("From:");
		contentPane.add(toLabel, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField fromField = new JTextField("", 8);
		fromField.setMinimumSize(fromField.getPreferredSize());
		contentPane.add(fromField, constraints);

		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel fromLabel = new JLabel("To:");
		contentPane.add(fromLabel, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField toField = new JTextField("", 8);
		toField.setMinimumSize(toField.getPreferredSize());
		contentPane.add(toField, constraints);

		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel amountLabel = new JLabel("Amount:");
		contentPane.add(amountLabel, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JTextField amountField = new JTextField("", 8);
		amountField.setMinimumSize(amountField.getPreferredSize());
		contentPane.add(amountField, constraints);

		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JButton clearButton = new JButton("Clear");
		contentPane.add(clearButton, constraints);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				amountField.setText("0");
				toField.setText("");
				fromField.setText("");
			}
		});

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		JButton transferButton = new JButton("Transfer");
		transferButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Integer to = Integer.parseInt(toField.getText().isEmpty() ? "0" : toField.getText());
				Integer from = Integer.parseInt(fromField.getText().isEmpty() ? "0" : fromField.getText());
				Integer amount = Integer.parseInt(amountField.getText().isEmpty() ? "0" : amountField.getText());
				
				if(AM.transfer(to, from, amount, table)){
					table.setModel(new DefaultTableModel(AM.getData(), columnNames));
				} else {
					JOptionPane.showMessageDialog(null, "This amount cannot be transferred to and from specified accounts.");
				}

			}

		});
		contentPane.add(transferButton, constraints);
	}

	public static void main(String[] args) {
		JFrame frame = new AccountTransactionLayout();
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
