import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FramePayment extends JFrame {
	
	public FramePayment() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Pagamento");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();		
		setLocation((int)dimension.getWidth()/4, (int)dimension.getHeight()/4);
		setSize((int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		PanelPaymentCard ppc = new PanelPaymentCard();
		getContentPane().add(ppc);
		PanelPaymentBank ppb = new PanelPaymentBank();
		getContentPane().add(ppb);
		getContentPane().add(new PanelPayment(ppc, ppb));						
	}

}
