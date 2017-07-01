import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JList;

public class ListListener implements MouseListener {
	
	@SuppressWarnings("rawtypes")
	private JList list;
	private Cart cart;
	private JLabel lblPrice, lblInsert, lblDescription;
	
	@SuppressWarnings("rawtypes")
	public ListListener(JList list, JLabel price, JLabel insert, JLabel description) {
		this.list = list;
		this.lblPrice = price;
		this.lblInsert = insert;
		this.lblDescription = description;
	}
	
	private Item searchItem(String p) {
		Warehouse w = Warehouse.getRef();
		Iterator<Item> i = w.getIterator();
		Item temp = null;
		while (i.hasNext()) {
			temp = i.next();
			if (temp.getNameItem() == p)
				return temp;
		}
		return null;		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		String selected = (String)list.getSelectedValue();
		Item i = searchItem(selected);
		lblDescription.setText("<html>" + i.getNameItem() + "<br>" + i.getPrice() + " €" + "<br>" + i.getDescription() + "</html>");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			String selected = (String)list.getSelectedValue();
			cart = Cart.getRef();
			Item i = searchItem(selected);
			cart.add(i);
			lblPrice.setText("€ " + Cart.getRef().getValue());
			lblInsert.setText(i.getNameItem());
		}	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}