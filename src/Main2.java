import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.lang.*;
import java.text.DecimalFormat;
import javax.swing.border.*;

public class Main2 {
	public static void main(String[] args){

		JFrame framee = new JFrame("currency converter");
		framee.setSize(260,380);
		framee.setIconImage(new ImageIcon("C:/Users/ashug/OneDrive/Pictures/symbol_currency4.jpg").getImage());

		JPanel panell = new JPanel();
		panell.setBackground(Color.WHITE);
		panell.setLayout(null);

		JLabel label4 = new JLabel("currency converter");
		label4.setBounds(20,5,180,45);
		label4.setFont(new Font("Arial", Font.BOLD,19));
		Color customColor = new Color(0,128,0);
		label4.setForeground(customColor);

		JLabel label3 = new JLabel("Amount:");
		label3.setBounds(25,60,100,20);
		RoundTextField t3 = new RoundTextField(12);
		t3.setBounds(25,80,150,22);
		t3.setMargin(new Insets(2,7,2,7));


		JLabel label1 = new JLabel("From currency:");
		label1.setBounds(25,107,100,20);
		RoundTextField t1 = new RoundTextField(9);
		t1.setBounds(25,130,120,22);
		t1.setMargin(new Insets(2,7,2,7 ) );

		JLabel label5 = new JLabel(new ImageIcon("C:/Users/ashug/OneDrive/Pictures/symbol_currency3.jpg"));
		label5.setBounds(50,154,50,20);

		JLabel label2 = new JLabel("To currency:");
		label2.setBounds(25,174,100,24);
		RoundTextField t2 = new RoundTextField(9);
		t2.setBounds(25,196,120,24);
		t2.setMargin(new Insets(2,7,2,7));

		RoundButton button = new RoundButton(10,"convert");
		button.setBounds(25,238,200,35);
		Color co = new Color(0,123,255);
		button.setBackground(co);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel result = new JLabel("Result:");
		result.setBounds(25,300,60,24);
		result.setFont(new Font("Arial", Font.BOLD, 13));

		JTextArea resultArea = new JTextArea();
		Border blackBorder = BorderFactory.createLineBorder(Color.black);
		resultArea.setBorder(blackBorder);
		resultArea.setBounds(75,303,150,25);

		panell.add(label3);
		panell.add(label1);
		panell.add(label2);
		panell.add(label4);
		panell.add(label5);
		panell.add(result);
		panell.add(t1);
		panell.add(t2);
		panell.add(t3);
		panell.add(button);
		panell.add(resultArea);

		framee.add(panell);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sc = t1.getText().toUpperCase().trim();
				String tc = t2.getText().toUpperCase().trim();
				String amountTxt = t3.getText().trim();

				if(sc.isEmpty() || tc.isEmpty() || amountTxt.isEmpty()){
					resultArea.setText("fill all fields");
					return;
				}
				double amountF = Double.parseDouble(amountTxt);

				String response = Main.getdata(sc,tc);

				if(response != null){
					double exchangeRate = Main.parsedata(response,tc);
					double convertedRate = amountF * exchangeRate;
					DecimalFormat df = new DecimalFormat("#.##");
					String result = df.format(convertedRate);

					resultArea.setText(result);
				}
			}
		});

		framee.setVisible(true);
	}
}

// make round corners of textField
class RoundTextField extends JTextField{
	private int radius;

	public RoundTextField(int radii){
		this.radius = radii;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Draw rounded rectangle background
			g2d.setColor(getBackground());
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

			// Draw the text field's content
			super.paintComponent(g);
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Draw rounded rectangle border
			g2d.setColor(getForeground());
			g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

			g2d.dispose();
	}
}

// make round corners of button
class RoundButton extends JButton{
	private int radius;

	public RoundButton(int radii, String label){
		super(label);
		this.radius = radii;
		setContentAreaFilled(false);
		setFocusPainted(false);
	}

	@Override
	protected void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw rounded rectangle background
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0,0,getWidth(),getHeight(), radius, radius);

		super.paintComponent(g2d);
		g2d.dispose();
	}

	@Override
	protected void paintBorder(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw rounded rectangle border
		g2d.setColor(getForeground());
		g2d.drawRoundRect(0,0, getWidth(), getHeight(), radius, radius);

		g2d.dispose();
	}

	@Override
	public boolean contains(int x, int y){
		Shape shape = new RoundRectangle2D.Double(0,0, getWidth(), getHeight(), radius, radius);
		return shape.contains(x, y);
	}
}