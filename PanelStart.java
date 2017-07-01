import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class PanelStart extends JPanel {
	
	private JButton newCart, exit;
	private Image img;
	
	public PanelStart() {
		setLayout(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimension = tk.getScreenSize();	
		setBounds(0, 0, (int)dimension.getWidth()/2, (int)dimension.getHeight()/2);
		setBackground("img/calcio.jpg");
		method();
	}
	
	public void setBackground(String FileName) {
		img = Toolkit.getDefaultToolkit().createImage(FileName);
		loadImage(img);
	}

	protected void method() {
		newCart = new JButton("Nuova Spesa");
		newCart.setBounds(266, 92, 150, 50);
		add(newCart);
		exit = new JButton("Uscita");
		exit.setBounds(266, 200, 150, 50);
		add(exit);		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}		
		});
		newCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameList();
			}
		});
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
