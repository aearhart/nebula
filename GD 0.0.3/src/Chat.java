import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Chat extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client control;
	private JTextField inputField;
	private JTextArea chatHistory;
	private String currentHistory = "";
	public String message = "";
	
	public Chat(Client control2) {
		// TODO Auto-generated constructor stub
		control = control2;
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
		inputField.setText(">>");
		
		this.add(title, BorderLayout.NORTH);
		this.add(chatScrollPane, BorderLayout.CENTER);
		this.add(inputField, BorderLayout.SOUTH);
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
		// TODO Auto-generated method stub
		
		String text = inputField.getText();
		if (illegal(text)) {
			return;
		}
		else {
			text = control.clientNum + ":" + text.substring(2, text.length());
			updateHistory(text);
			inputField.setText(">>");
			//sendChatMessage(text);
			message = text;
		}

		
	}

}
