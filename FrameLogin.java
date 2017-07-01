import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameLogin extends JFrame {
	
	public FrameLogin() {	
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();		
		setLocation((int)dimension.getWidth()/4, (int)dimension.getHeight()/4);
		setSize((int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		getContentPane().add(new PanelLogin(this));
		setTitle("Entra e fai goal con noi");
	}
}
