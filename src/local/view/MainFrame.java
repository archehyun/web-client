package local.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import local.com.Main;

public class MainFrame extends JFrame implements ActionListener {

	Main main;

	String url;

	private JButton butStart;

	private JButton butStop;
	private JComponent buildMain() {
		JPanel pnMain = new JPanel(new BorderLayout());

		JPanel pnContorl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		butStart = new JButton("Start");
		butStop = new JButton("Stop");
		butStop.setEnabled(false);
		butStart.addActionListener(this);
		butStop.addActionListener(this);
		pnContorl.add(butStart);
		pnContorl.add(butStop);

		JPanel pnUrl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnUrl.add(new JLabel("URL:"));
		pnUrl.add(new JLabel(url));

		pnMain.add(pnUrl);
		pnMain.add(pnContorl, BorderLayout.SOUTH);
		return pnMain;

	}

	public MainFrame(String url) {

		this.url = url;
		main = new Main(url);

		this.getContentPane().add(buildMain());
		//		this.setSize(new Dimension(200, 100));
		this.setLocation(500, 200);
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Start")) {
			main.start();
			butStop.setEnabled(true);
			butStart.setEnabled(false);
		} else {
			main.stop();
			butStop.setEnabled(false);
			butStart.setEnabled(true);
		}

	}

	public static void main(String[] args) {
		new MainFrame(args[0]);
	}

}
