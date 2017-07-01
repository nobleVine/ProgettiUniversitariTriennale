import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public abstract class PanelPaymentMethod extends JPanel{
	
	protected JLabel lblInfo, lblCard, lblPay, lblNoPay, lblInfoCash;
	protected JTextField txtCard;
	protected JButton next;
	
	//Template
	
	public PanelPaymentMethod() {
		setLayout(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();	
		setBounds(0, 0, (int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		setBackground(Color.WHITE);
		setVisible(false);
		setElement();
		different();
	}
	
	private void setElement() {
		lblInfo = new JLabel("Inserimento credenziali");
		lblInfo.setBounds(0, 20, getWidth(), 35);
		lblInfo.setHorizontalAlignment(JLabel.CENTER);
		Font font = new Font("Arial", Font.BOLD, 30);
		lblInfo.setFont(font);
		add(lblInfo);
		ImageIcon image = new ImageIcon("img/check.jpg");
		lblPay = new JLabel(image);
		lblPay.setBounds(0, 180, getWidth(), 70);
		lblPay.setVisible(false);
		add(lblPay);
		lblNoPay = new JLabel("Pagamento non accettato: denaro insufficiente");
		lblNoPay.setForeground(Color.RED);
		lblNoPay.setBounds(0, 240, getWidth(), 30);
		lblNoPay.setHorizontalAlignment(JLabel.CENTER);
		lblNoPay.setVisible(false);
		add(lblNoPay);
		lblInfoCash = new JLabel();
		lblInfoCash.setVisible(false);
		lblInfoCash.setBounds(0, 240, getWidth(), 30);
		lblInfoCash.setHorizontalAlignment(JLabel.CENTER);
		add(lblInfoCash);
		next = new JButton("Conferma");
		next.setBounds(300, 300, 100, 30);
		add(next);
	}
	
	protected abstract void different();

}

