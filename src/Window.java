
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Window extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField readDir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Window dialog = new Window();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("deprecation")
	public Window() {
		Methods methods = new Methods();
		setBounds(100, 100, 956, 574);
		getContentPane().setLayout(null);

		JButton btnPasirinktiDirektorija = new JButton("<html>Pasirinkti<br />nuskaitymo direktoriją</html>");

		btnPasirinktiDirektorija.setBounds(22, 24, 178, 44);
		getContentPane().add(btnPasirinktiDirektorija);

		readDir = new JTextField();
		readDir.setBounds(210, 34, 699, 20);
		getContentPane().add(readDir);
		readDir.setColumns(10);
		readDir.disable();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		DefaultListModel<String> dlm2 = new DefaultListModel<String>();

		JLabel lblFailSuKomentarais = new JLabel("Failų su komentarais sąrašas:");
		lblFailSuKomentarais.setBounds(22, 79, 419, 14);
		getContentPane().add(lblFailSuKomentarais);

		JLabel lblPasirinktoFailoKomentar = new JLabel("Pasirinkto failo komentarų sąrašas");
		lblPasirinktoFailoKomentar.setBounds(473, 79, 446, 14);
		getContentPane().add(lblPasirinktoFailoKomentar);

		JButton saveFile = new JButton("<html>Išsaugoti visus<br />komentarus faile...</html>");
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setDialogTitle("Iš saugoti komentarus..");
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						methods.saveAllComments(readDir.getText(), String.valueOf(fileChooser.getSelectedFile()));

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		saveFile.setBounds(708, 472, 201, 41);
		saveFile.setEnabled(false);
		getContentPane().add(saveFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 95, 436, 366);
		getContentPane().add(scrollPane);

		JList<String> list = new JList<String>(dlm);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				List<String> allComments = methods.findAllComments(list.getSelectedValue());
				dlm2.clear();
				for (String tempComment : allComments) {
					dlm2.addElement(tempComment);
				}
			}
		});
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(473, 95, 436, 366);
		getContentPane().add(scrollPane_1);

		JList<String> list_1 = new JList<String>(dlm2);
		scrollPane_1.setViewportView(list_1);

		btnPasirinktiDirektorija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Pasirinkite direktoriją");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					readDir.setText(String.valueOf(chooser.getSelectedFile()));
					List<String> failai = methods.getAllFiles(readDir.getText());
					dlm.clear();
					saveFile.setEnabled(true);
					for (String tempFile : failai) {
						if (methods.findAllComments(tempFile).size() > 0) {
							dlm.addElement(tempFile);
						}
					}
				} 

			}
		});
	}
}
