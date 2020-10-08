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

import local.com.Senser;
import local.com.impl.DummySenser;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Senser senser;

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
		senser = new DummySenser("pch", url);

		this.getContentPane().add(buildMain());
		this.setLocation(500, 200);
		pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Start")) {
			senser.start();
			butStop.setEnabled(true);
			butStart.setEnabled(false);
		} else {
			senser.stop();
			butStop.setEnabled(false);
			butStart.setEnabled(true);
		}

	}



}
