import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main3 {
	public static void main(String[] args){

		JFrame framee = new JFrame("currency converter");
		framee.setSize(300,300);

		JPanel panell = new JPanel();
		panell.setLayout(null);

		JLabel label1 = new JLabel("source currency");
		label1.setBounds(10,20,100,20);

		JLabel label2 = new JLabel("target currency");
		label2.setBounds(10,50,100,20);

		JLabel label3 = new JLabel("amount");
		label3.setBounds(10,80,100,20);

		JTextField t1 = new JTextField();
		t1.setBounds(150,20,60,20);

		JTextField t2 = new JTextField();
		t2.setBounds(150,50,60,20);

		JTextField t3 = new JTextField();
		t3.setBounds(150,80,60,20);

		JButton button = new JButton("convert");
		button.setBounds(120,120,80,30);

		JTextArea resultArea = new JTextArea();
		resultArea.setBounds(120,180,80,40);

		panell.add(label1);
		panell.add(label2);
		panell.add(label3);
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

					resultArea.setText(String.valueOf(convertedRate));
				}
				else{
					resultArea.setText("failed to retrieve data");
				}
			}
		});

		framee.setVisible(true);
	}
}