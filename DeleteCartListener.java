import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JList;

public class DeleteCartListener implements ActionListener {
	
	@SuppressWarnings("rawtypes")
	private JList list;
	
	@SuppressWarnings("rawtypes")
	public DeleteCartListener(JList list) {
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selected = (String)list.getSelectedValue();
		Item i = searchItem(selected);
		Cart cart = Cart.getRef();
		cart.remove(i);
		PanelList.getLblPrice().setText("€ " + cart.getValue());
	}
	
	private Item searchItem(String p) {
		Cart cart = Cart.getRef();
		Iterator<Item> i = cart.getIterator();
		Item temp = null;
		while (i.hasNext()) {
			temp = i.next();
			if (temp.getNameItem() == p)
				return temp;
		}
		return null;		
	}
	
}
