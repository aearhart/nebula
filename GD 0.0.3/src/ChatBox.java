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
	
	public ChatBox(ClientController clientController) {
		// TODO Auto-generated constructor stub
		control = clientController;
		this.setPreferredSize(new Dimension (100, 300));
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Chat History");
		title.setFont(Globals.f);
		
		
		// chat history
		chatHistory = new JTextArea();
		chatHistory.setEditable(false);
		chatHistory.setLineWrap(true);
		chatHistory.setWrapStyleWord(true);
		chatHistory.setFont(Globals.f);
		chatHistory.setText(currentHistory);
		JScrollPane chatScrollPane = new JScrollPane(chatHistory);
		chatScrollPane.setPreferredSize(new Dimension(100, 300));
	
		
		// input line
		inputField = new JTextField(15);
		inputField.addActionListener(this);

		//inputField.setForeground(Globals.textColor);
		inputField.setFont(Globals.f);
		inputField.setDocument(new JTextFieldLimit(40));
		inputField.setText(">>");
		
		this.add(title, BorderLayout.NORTH);
		this.add(chatScrollPane, BorderLayout.CENTER);
		this.add(inputField, BorderLayout.SOUTH);
	}
	
	public Boolean illegal(String t) {
		return t.equals("");
	}
	
	public void sendChatMessage(String t) {
		control.currentState = "MESSAGE@@" + t;
		control.sendMessage();
	}
	
	public void updateHistory(String t) {
		currentHistory += t + "\n";
		chatHistory.setText(currentHistory);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		String text = inputField.getText();
		if (illegal(text)) {
			return;
		}
		else {
			text = control.getPlayer().getName() + ":" + text.substring(2, text.length());
			updateHistory(text);
			inputField.setText(">>");
			if (! control.testing)
				sendChatMessage(text);
		}

		
	}

}
