package exercise13;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.swing.SwingUtilities;

public class ChitChat extends ChitChatLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6883528146760768296L;
	private static ChitChatLayout frame;
	private ChitChatClientSwingWorker swingWorker;
	
	public ChitChat(String name) {
		super(name);
		init();
	}

	public void init() {		
		addListeners();
		swingWorker = new ChitChatClientSwingWorker(messagesReceivedPanel,
				messageTextArea, localAddress, "Jon", ipTextField);

		swingWorker.execute();
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
		        frame = new ChitChat("Chit-Chat");
		        frame.pack();
		        frame.setVisible(true);	
            }
		});
		new Thread(){
			public void run(){
				ChitChatServer.main(args);				
			}
		}.start();
		
    }
	public void addListeners(){

		sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

		        swingWorker.setFrame(frame);
		        swingWorker.setSocket(ipTextField.getText());
		        //encode into JSON
		        String packet = JSONConverter(messageTextArea.getText(),senderNameTextField.getText(), ipTextField.getText(), localAddress);
        		try {
					swingWorker.sendMessage(packet);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		ipTextField.setText("");
        		messageTextArea.setText("");            
    		}
        });
	}
	
	public String JSONConverter(String message, String name, String toIP, String fromIP){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);

		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		JsonObject value = factory.createObjectBuilder()
				.add("message", message)
				.add("name", name)
				.add("toIP", toIP)
				.add("fromIP", fromIP)
			.build();
		return value.toString();
	}
}