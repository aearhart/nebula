
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WelcomeTab extends JPanel implements ActionListener{
	private ClientController control;
	public String name = "WelcomeTab";
	
	protected JTextField textField;
	protected JTextArea textArea;
	private String areaText = "Welcome to GD 0.0.3!\n\nPlease enter your name in the text field above to play. \n\n";
	private String fieldText = "What is your name? (hit enter to select)";
	private Player p;
	Boolean finished = false;

	
	public WelcomeTab(ClientController clientController) {
		super(new GridBagLayout());
		control = clientController;
		p = control.getPlayer();
		this.setPreferredSize(new Dimension(800, 500));
		Font f1 = new Font("Consolas", Font.PLAIN, 30);
		
		textField = new JTextField(20);
		textField.addActionListener(this);
		textField.setText(fieldText);
		textField.setFont(f1);
		
		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(f1);
		textArea.setText(areaText);
		 
		//JScrollPane scrollPane = new JScrollPane(textArea);
		
		JButton b1 = new JButton("Yes");
		b1.setMnemonic('h');
		b1.setActionCommand("Done");
		b1.setFont(f1);
		b1.addActionListener(this);
		
		GridBagConstraints c= new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		//add(scrollPane, c);
		add(textArea, c);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.anchor = GridBagConstraints.SOUTH;
		//c.gridwidth =1; c.gridheight =1;
		c1.ipadx = 5; c1.ipady = 10;
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.insets = new Insets(5, 5, 5, 5);
		// testing with buttons

		this.add(b1, c1);
	}
	
	public Boolean notFinished() {
		 return (! finished);
	}
	
	public Boolean illegal(String t) {
		if (t.contains("@@")) return true;
		return false;
	}
	
	
	public void update() {
		this.setVisible(true);
	}
	
	public String getName() {
		return name;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("event");
		if ("Done".equals(e.getActionCommand())) {
			if (textField.getText().equals(fieldText)) {
				Random rand = new Random();
				int r = rand.nextInt(5);
				String[] names = {"What's your name again?", "Anonymous", "Me who shall not be named", "[insert name here]", "Lazy the Nameless"};
				textField.setText(names[r]);
			}
			p.setName(textField.getText());
			control.getWindow().rename(p.getName());
			finished = true;
			//System.out.println("done -- " + p.getName() + "   " + notFinished());
			return;
		}
		
		String text = textField.getText();
		if (illegal(text)) {
			textArea.setText(areaText + "That's an illegal name. A name can't have two @@ symbols.");
			textField.setText(fieldText);
		}
		else if (text.equals(fieldText)){
		}
		else {
			textArea.setText(areaText + "\nYour name:\n" + text+ "\n\nIs this okay?\n");
		}
		textField.selectAll();
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
	}
	
}
