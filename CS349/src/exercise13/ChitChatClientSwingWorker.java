package exercise13;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

public class ChitChatClientSwingWorker extends SwingWorker<Integer,String> {
	private static final int MEDIUM_FONT_SIZE = 20;
	private static final int PORT = 8086;

    PrintWriter output;
    BufferedReader input;
    String usernamee, address;
    JTextPane textPane;
    JTextArea messageTextArea;
    String serverMessage;
    Socket socket;
    JPanel messagesReceivedPanel;
    ChitChatLayout frame;
    JTextField ipTextField;

    public ChitChatClientSwingWorker(JPanel messagesReceivedPanel, JTextArea messageTextArea,
                                     String address, String username, JTextField ipTextField){
    	this.messagesReceivedPanel = messagesReceivedPanel;
        this.usernamee = username;
        this.messageTextArea = messageTextArea;
        this.address = address;
        this.ipTextField = ipTextField;
        try {
            // while server is waiting for a call,
            // client instantiates socket object with specified server address and port
            socket = new Socket(address, PORT);
            // client gets the server's output stream
            output = new PrintWriter(socket.getOutputStream(),true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSocket(String address){
    	try {
    		socket = new Socket();
    		socket.connect(new InetSocketAddress(address, PORT), 1000);//add timeout
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Override
    protected Integer doInBackground() throws Exception{

        try {
            // Create a buffered reader to the socket
            // in order to get the input from the server
            // Buffered reader reads the bytes and converts them into chars.
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));


            while(true){
                // receive client input
                serverMessage = input.readLine();

                // write the message to the Text Pane
                publish();
                // end loop
                if (serverMessage.equals("Bye.")) {
                    break;
                }

            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("There is an I/O problem or Connection has been reset");
        }

        return 1;
    }
	
	public void setFrame(ChitChatLayout frame){
	    this.frame = frame;		
	}

    public void setUsernamee(String name){
        this.usernamee = name;
    }

    protected void sendMessage(String message) throws Exception{
            output.println(message);
    }
    public void messageReceived(String message) {
    	//0 = name, 1 = fromIP, 2 = message, 3 toIP
    		String[] parsed = JSONParser(message);  
    		updateReceiverIP(parsed[1]);
    		String template = "FROM: %s @ %s \n%s";
        	addAMessage(String.format(template, parsed[0], parsed[1], parsed[2]));
    }

    @Override
    protected void process(java.util.List<String> chunks) {
    	messageReceived(serverMessage);    	
    }
    
    protected void updateReceiverIP(String ip){
    	ipTextField.setText(ip);
    	//frame.validate();
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
		frame.validate();
	}
    public String[] JSONParser(String json){
		try {
			JsonReader rdr = Json.createReader(new StringReader(json));
			
			JsonObject obj = rdr.readObject();
			String name = obj.getString("name");
			String message = obj.getString("message");
			String toIP = obj.getString("toIP");
			String fromIP = obj.getString("fromIP");
			return new String[] {name, fromIP, message, toIP};
		} catch (Exception e) {
			System.out.println("ERROR READING");
		}
		return new String[] {"","","", ""};
    	
    }
}
