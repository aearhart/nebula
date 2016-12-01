import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.print.attribute.AttributeSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

class JTextFieldLimit extends PlainDocument {
	private int limit;
	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}
	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
	}
	public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {

		if (str == null)
			return;
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}

public class ChatBox extends JPanel implements ActionListener{
	ClientController control;
	private JTextField inputField;
	private JTextArea chatHistory;
	private String currentHistory = "";
	public String message = "";
	
	private int width = 100;
	private int height = 300;
	
	public ChatBox(ClientController clientController) {
		control = clientController;
		
		if (Globals.winSizeSet) {
			width = Globals.mapSize / 10;
			height = Globals.mapSize / 3;
		}
		
		this.setPreferredSize(new Dimension (width, height)); //100, 300
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Chat History");
		title.setFont(Globals.f);
		
		
		// chat history
		chatHistory = new JTextArea();
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		chatHistory.setFont(Globals.f);
		chatHistory.setText("Chat not currently available");
		JScrollPane chatScrollPane = new JScrollPane(chatHistory);
		chatScrollPane.setPreferredSize(new Dimension(width, height)); //100, 300
	
		
		// input line
		inputField = new JTextField(15);
		inputField.addActionListener(this);

		//inputField.setForeground(Globals.textColor);
		inputField.setFont(Globals.f);
		inputField.setDocument(new JTextFieldLimit(40));
		inputField.setText(">>");
		inputField.setEditable(false);
		
		this.add(title, BorderLayout.NORTH);
		this.add(chatScrollPane, BorderLayout.CENTER);
		this.add(inputField, BorderLayout.SOUTH);
	}
	
	public void turnOn() {
		chatHistory.setText(currentHistory);
		inputField.setEditable(true);
		control.chatEnabled = true;
	}
	
	public void turnOff(String msg) {
		chatHistory.setText(msg);
		inputField.setEditable(false);
		control.chatEnabled = false;
	}
	
	public void emptyMessage() {
		message = "";
	}
	
	public Boolean illegal(String t) {
		return t.equals("");
	}
	
	public void updateHistory(String t) {
		currentHistory += t + "\n";
		chatHistory.setText(currentHistory);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String text = inputField.getText();
		if (illegal(text)) {
			return;
		}
		else {
			if (text.substring(0, 2).equals(">>")) {
				text = text.substring(2, text.length());
			}
			text = control.getPlayer().getName() + ":" + text;
			updateHistory(text);
			inputField.setText(">>");
			message = text;
		}

		
	}

}
