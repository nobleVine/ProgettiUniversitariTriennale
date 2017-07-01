import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameCart extends JFrame {
	
	public FrameCart() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(966, 318, 400, 400);
		setTitle("Carrello");
		getContentPane().add(new PanelCart(this));
	}

}
