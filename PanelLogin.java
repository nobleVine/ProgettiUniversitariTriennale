import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel {
	
	private JLabel lblUser, lblPass, lblImage, lblError;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JButton login, cancel;
	private JFrame frame;
	
	public PanelLogin(final JFrame frame) {
		this.frame = frame;
		setLayout(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();	
		setBounds(0, 0, (int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		method();		
	}

	private void method() {
		ImageIcon image = new ImageIcon("img/pallone.png");
		lblImage = new JLabel(image);
		lblImage.setBounds(0, 40, 256, 256);
		add(lblImage);
		lblUser = new JLabel("Username: ");
		lblUser.setBounds(270, 100, 130, 30);
		add(lblUser);
		lblPass = new JLabel("Password: ");
		lblPass.setBounds(270, 150, 130, 30);
		add(lblPass);
		txtUser = new JTextField();
		txtUser.setBounds(430, 100, 150, 30);
		add(txtUser);
		txtPass = new JPasswordField();
		txtPass.setBounds(430, 150, 150, 30);
		add(txtPass);
		login = new JButton("Login");
		login.setBounds(320, 200, 80, 30);
		add(login);
		cancel = new JButton("Canc");
		cancel.setBounds(415, 200, 80, 30);
		add(cancel);
		lblError = new JLabel("Errore nell'immissione dell'username o della password");
		lblError.setBounds(250, 250, 370, 30);
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);
		add(lblError);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				char[] passChar = txtPass.getPassword();
				String passString = new String(passChar);
				Supporter s = DatabaseSupporters.getRef().searchSupporter(txtUser.getText(), passString);
				if (s != null) {
					frame.setVisible(false);
					new FrameStart();
				}				
				else
					lblError.setVisible(true);
			}			
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPass.setText("");
				txtUser.setText("");
				lblError.setVisible(false);
			}
		});
	}

}