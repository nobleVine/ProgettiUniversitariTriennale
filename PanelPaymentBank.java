import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelPaymentBank extends PanelPaymentMethod {
	
	public PanelPaymentBank() {
		super();	
	}

	protected void different() {
		lblCard = new JLabel("Inserire il numero del conto");
		lblCard.setBounds(150, 100, 200, 20);
		add(lblCard);
		txtCard = new JTextField();
		txtCard.setBounds(370, 100, 200, 30);
		add(txtCard);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNoPay.setVisible(false);
				Bank bank = Bank.getRef();
				ClientBank client = bank.verifyAccountPayment(Integer.parseInt(txtCard.getText()));
				if (client != null) {
					lblPay.setVisible(true);
					lblInfoCash.setText("Soldi rimanenti: " + client.getCash());
					lblInfoCash.setVisible(true);
				}				
				else {
					lblNoPay.setVisible(true);
					lblPay.setVisible(false);
					lblInfoCash.setVisible(false);
				}
			}
		});
	}
	
}
