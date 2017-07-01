
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class FrameList extends JFrame {
	
	public FrameList() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Nuovo Carrello");		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();		
		setSize((int)dimension.getWidth(), (int)(dimension.getHeight()-50));
		getContentPane().add(new PanelList(this));
	}

}
