import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.binance.api.client.exception.BinanceApiException;
import com.webcerebrium.binance.api.BinanceApi;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WindowsMode extends JFrame {

	private JPanel contentPane;
	private static JLabel priceBTC;
	private static JTextField BTCbuy;
	private static JTextField startAmount;
	private static JLabel BTCpercent;
	private static JLabel finishAmount;


	public static String calcPercent(String currentBuy, String currentPrice) throws com.webcerebrium.binance.api.BinanceApiException
	{

		double buy = 0;
		double price;
		double percent;

		if(currentBuy.equals(""))
		{

		}
		else
		{
			buy = Double.valueOf(currentBuy);
		}

		price = Double.valueOf(currentPrice);

		if(buy > 0)
		{
			percent = ((price - buy) / buy)*100;
		}
		else
		{
			percent = 0;
		}

		DecimalFormat dc = new DecimalFormat("0.00");
		String formattedText = dc.format(percent);


		return (String.valueOf(formattedText) + "%");

	}

	public static String calculateEarnings(String startAmount, String percentDifference)
	{

		
		double total = 0;

		if(startAmount.isEmpty())
		{

		}
		else
		{
			double start = Double.valueOf(startAmount);
			double percent = Double.valueOf(percentDifference);
			
			
			if(start > 0)
			{
				total = start + (start * percent);
			}
			else
			{
				total = 0;
			}
		}

		return "$" + total;



	}

	public static void timer()
	{

		Timer t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					BinanceApi api = new BinanceApi();

					DecimalFormat dc = new DecimalFormat("0.00");

					//BTC
					String formattedText = dc.format(api.pricesMap().get("BTCUSDT"));
					priceBTC.setText("$" + String.valueOf(formattedText));
					BTCpercent.setText(calcPercent(BTCbuy.getText(), formattedText));

					finishAmount.setText(calculateEarnings(startAmount.getText(), BTCpercent.getText()));





				} catch (BinanceApiException d) {
					System.out.println( "ERROR: " + d.getMessage());
				} catch (com.webcerebrium.binance.api.BinanceApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		t.start();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowsMode frame = new WindowsMode();
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);

					timer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WindowsMode() {
		setBackground(new Color(255, 255, 255));
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 324, 252);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		priceBTC = new JLabel("$XXXXX.xx");
		priceBTC.setHorizontalAlignment(SwingConstants.CENTER);
		priceBTC.setFont(new Font("Tahoma", Font.BOLD, 15));
		priceBTC.setBounds(188, 25, 120, 24);
		contentPane.add(priceBTC);

		JLabel lblNewLabel = new JLabel("BTC");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 308, 14);
		contentPane.add(lblNewLabel);

		BTCbuy = new JTextField();
		BTCbuy.setBounds(0, 29, 107, 20);
		contentPane.add(BTCbuy);
		BTCbuy.setColumns(10);

		startAmount = new JTextField();
		startAmount.setColumns(10);
		startAmount.setBounds(0, 60, 107, 20);
		contentPane.add(startAmount);

		BTCpercent = new JLabel("%");
		BTCpercent.setHorizontalAlignment(SwingConstants.CENTER);
		BTCpercent.setBounds(108, 32, 99, 14);
		contentPane.add(BTCpercent);

		finishAmount = new JLabel("");
		finishAmount.setHorizontalAlignment(SwingConstants.CENTER);
		finishAmount.setBounds(108, 63, 99, 14);
		contentPane.add(finishAmount);
	}
}
