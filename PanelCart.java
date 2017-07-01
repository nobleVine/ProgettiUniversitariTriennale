import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelCart extends JPanel {
	
	@SuppressWarnings("rawtypes")
	private JList list;
	private JScrollPane scroll;
	private JButton delete, close, removeAll;
	private JLabel info;
	
	public PanelCart(final JFrame frame) {
		setLayout(null);
		setBounds(966, 318, 400, 400);
		
		info = new JLabel("Chiudere e riaprire il carrello per visualizzare le modifiche");
		info.setBounds(10, 325, 350, 20);
		add(info);
		
		fillList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroll = new JScrollPane(list);
		scroll.setBounds(10, 10, 200, 300);
		add(scroll);
		
		delete = new JButton("Delete");
		delete.setBounds(230, 50, 90, 30);
		add(delete);
		close = new JButton("Close");
		close.setBounds(230, 100, 90, 30);
		add(close);
		removeAll = new JButton("Delete All");
		removeAll.setBounds(230, 150, 90, 30);
		add(removeAll);
		removeAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cart.getRef().removeAll();
				PanelList.getLblPrice().setText("€ 0.00");
			}
		});
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		delete.addActionListener(new DeleteCartListener(list));	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillList() {
		Cart cart = Cart.getRef();
		Iterator<Item> i = cart.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		String [] listCart = new String [j];
		i = cart.getIterator();
		j = 0;
		while (i.hasNext()) {
			listCart[j] = i.next().getNameItem();
			j++;
		}
		list = new JList(listCart);
	}

}
