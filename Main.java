import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Main {

	JFrame frame = new JFrame("Baixa Música Dimas & CIA");
	JLabel linkLabel = new JLabel("Link Youtube --> ");
	JTextField textLink = new JTextField();
	JButton downloadButton = new JButton("Baixar");

	JLabel animacao = new JLabel("", SwingConstants.CENTER);
	JLabel nomeMusica = new JLabel("", SwingConstants.CENTER);
    JLabel total = new JLabel("", SwingConstants.CENTER);

	public Main() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(640, 480));
		frame.setLocationRelativeTo(null);

		animacao.setFont(new Font("Arial", Font.BOLD, 22)); animacao.setHorizontalAlignment(JLabel.CENTER);
		total.setFont(new Font("Arial", Font.BOLD, 26));
		nomeMusica.setFont(new Font("Arial", Font.BOLD, 16));
		
		
		Container c = frame.getContentPane();

		JPanel pa = new JPanel(new FlowLayout());
		// pa.setBackground(Color.RED);
		pa.add(linkLabel);
		textLink.setPreferredSize(new Dimension(350,24));
		pa.add(textLink);
		pa.add(downloadButton);

		JPanel pb = new JPanel(); pb.setLayout(new BoxLayout(pb, BoxLayout.PAGE_AXIS));
		// pb.setBackground(Color.GREEN);
		pb.add(animacao);
		pb.add(nomeMusica);
		pb.add(total); total.setForeground(Color.BLUE);

		c.add(pa, BorderLayout.NORTH);
		c.add(pb, BorderLayout.CENTER);

		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				final String url = textLink.getText();
				if ("".equals(url)) {
					JOptionPane.showMessageDialog(null, "Informe o link do Youtube.");		
					return;	
				} 

				new Thread(new Runnable() {
					public void run() {
						baixar(url);
					}
				}).start();
			};
		});	

		frame.setVisible(true);
	}

	void baixar(String url) {
		url = url.substring(0, 43);
		msg("* baixar " + url + "\n");
		downloadButton.setEnabled(false);
		animacao.setText("Baixando arquivo...");
		
		try {
			String line;
			Process p = Runtime.getRuntime().exec(new String[] {"./download.sh", url});
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
       		while ((line = in.readLine()) != null) {
         		System.out.println(line);

				if (line.contains("FILENAME")) {
					nomeMusica.setText(line.split("=")[1]);
				}

				if (line.contains("ETA")) {
					String parts[] = line.split("ETA");
					total.setText(parts[1]);
				}

				if (line.contains("ffmpeg")) {
					animacao.setText("Convertendo arquivo, aguarde...");
					total.setText("");
				}

				if (line.contains("Deleting original file")) {
					JOptionPane.showMessageDialog(null, "Música baixada: " + nomeMusica.getText());
				}
       		}
			int result = p.waitFor();
       		
			in.close();
     	} catch (Exception e) {
			e.printStackTrace();
			// ...
     	}

		downloadButton.setEnabled(true);
		nomeMusica.setText("");
		total.setText("");
		animacao.setText("");
	}

	public static void main(String args[]) {
		System.out.println("El Baixador");
		new Main();
	
		System.out.println("The End.");
	}

	static void msg(String text) {
		System.out.print(text);
	}

}
