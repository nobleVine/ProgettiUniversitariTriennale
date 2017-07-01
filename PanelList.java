import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelList extends JPanel {
	
	@SuppressWarnings("rawtypes")
	private JList list;
	private JButton cartButton, next;
	private static JLabel lblPrice;
	private JLabel lblLastInsert, lblDescription;
	private Image img;
	
	public PanelList(final JFrame frame) {
		setLayout(null);
		setBackground("img/sfondo3.jpg");
		setSize(frame.getSize());
		method();	
	}
	
	private void method() {
		ImageIcon cart = new ImageIcon("img/cart.jpg");
		cartButton = new JButton(cart);
		cartButton.setBounds(1250, 30, 70, 70);
		cartButton.setBackground(Color.WHITE);
		cartButton.setBorder(null);
		add(cartButton);
		
		next = new JButton("Fine");
		next.setBounds(210, 600, 70, 30);
		add(next);
		
		lblPrice = new JLabel("€ 0.00");
		lblPrice.setBounds(1250, 100, 63, 20);
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPrice);
		
		lblDescription = new JLabel();
		lblDescription.setBounds(500, 150, 300, 300);
		add(lblDescription);
		
		fillList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(100, 70, 300, 500);		
		add(scroll);
				
		cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameCart();
			}
		});
		cartButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {			
				cartButton.setBackground(Color.RED);
			}
			public void mouseExited(MouseEvent e) {			
				cartButton.setBackground(Color.WHITE);
			}
		});	
		list.addMouseListener(new ListListener(list, lblPrice, lblLastInsert, lblDescription));
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FramePayment();
			}
		});
	}
	
	public void setBackground(String FileName) {
		img = Toolkit.getDefaultToolkit().createImage(FileName);
		loadImage(img);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fillList() {
		Warehouse w = Warehouse.getRef();
		Iterator<Item> i = w.getIterator();
		int j = 0;
		while (i.hasNext()) {
			i.next();
			j++;
		}
		String [] listW = new String [j];
		i = w.getIterator();
		j = 0;
		while (i.hasNext()) {
			listW[j] = i.next().getNameItem();
			j++;
		}
		list = new JList(listW);
	}
	
	public static JLabel getLblPrice() {
		return lblPrice;
	}
	
	private void loadImage(Image img) {
		try {
			MediaTracker track = new MediaTracker(this);
			track.addImage(img, 0);
			track.waitForID(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(img, 0, 0, null);
		super.paintComponent(g);
	}

}