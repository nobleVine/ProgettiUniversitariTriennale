import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelPayment extends JPanel {
	
	private JLabel lblImageCard, lblUnicredit, lblIntesa, lblFirenze;
	private JButton card, account;
	private PanelPaymentCard ppc;
	private PanelPaymentBank ppb;
	
	public PanelPayment(PanelPaymentCard ppc, PanelPaymentBank ppb) {
		setLayout(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();	
		setBounds(0, 0, (int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		this.ppc = ppc;
		this.ppb = ppb;
		method();		
	}

	private void method() {
		setBackground(Color.WHITE);	
		ImageIcon imageCard = new ImageIcon("img/creditCard.gif");
		lblImageCard = new JLabel(imageCard);
		lblImageCard.setBounds(170, 115, 168, 40);
		add(lblImageCard);
		ImageIcon imageUnicredit = new ImageIcon("img/unicredit.png");
		lblUnicredit = new JLabel(imageUnicredit);
		lblUnicredit.setBounds(385, 120, 40, 40);
		add(lblUnicredit);
		ImageIcon imageIntesa = new ImageIcon("img/intesa.jpg");
		lblIntesa = new JLabel(imageIntesa);
		lblIntesa.setBounds(435, 120, 40, 40);
		add(lblIntesa);
		ImageIcon imageFirenze = new ImageIcon("img/firenze.gif");
		lblFirenze = new JLabel(imageFirenze);
		lblFirenze.setBounds(485, 120, 40, 40);
		add(lblFirenze);
		card = new JButton("Carta di Credito");
		card.setBounds(180, 168, 150, 40);
		add(card);
		account = new JButton("Banca");
		account.setBounds(380, 168, 150, 40);
		add(account);
		card.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
				ppc.setVisible(true);
				setVisible(false);
			}
		});
		account.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
				ppb.setVisible(true);
				setVisible(false);
			}
		});
	}

}
