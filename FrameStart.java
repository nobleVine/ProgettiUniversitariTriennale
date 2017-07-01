import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameStart extends JFrame {
	
	public FrameStart() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(new PanelStart());
		setTitle("Benvenuto Nello Store Online Della Tua Squadra ");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();		
		setLocation((int)dimension.getWidth()/4, (int)dimension.getHeight()/4);
		setSize((int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
	}
}
