package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class Filtrar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JToolBar toolBar;
	private JButton btnTornar;
	private JButton btnExportarCsv;
	private JLabel lblSeleccionaTaula;
	private JLabel lblSeleccionaElsCamps;
	private JPanel pnlPopulation;
	private JCheckBox chkCountry;
	private JCheckBox chkPopulation;
	private JCheckBox chkDensity;
	private JCheckBox chkArea;
	private JCheckBox chkFertility;
	private JCheckBox chkAge;
	private JCheckBox chkUrban;
	private JCheckBox chkShare;
	private JPanel pnlUsuari;
	private JCheckBox chkId;
	private JCheckBox chkLogin;
	private JCheckBox chkPassword;
	private JCheckBox chkType;
	private JButton btnMostrarTaula;
	private JRadioButton rdbtnPopulation;
	private JRadioButton rdbtnUsers;
	private JScrollPane scrollPane;
	private ButtonGroup taulas;
	private JButton btnMarcarTot;
	private JButton btnDesmarcar;
	private JButton btnFiltreAvancat;

	public JTable getTable() {
		return table;
	}

	public JButton getBtnTornar() {
		return btnTornar;
	}

	public JButton getBtnExportarCsv() {
		return btnExportarCsv;
	}

	public JPanel getPnlPopulation() {
		return pnlPopulation;
	}

	public JCheckBox getChkCountry() {
		return chkCountry;
	}

	public JCheckBox getChkPopulation() {
		return chkPopulation;
	}

	public JCheckBox getChkDensity() {
		return chkDensity;
	}

	public JCheckBox getChkArea() {
		return chkArea;
	}

	public JCheckBox getChkFertility() {
		return chkFertility;
	}

	public JCheckBox getChkAge() {
		return chkAge;
	}

	public JCheckBox getChkUrban() {
		return chkUrban;
	}

	public JCheckBox getChkShare() {
		return chkShare;
	}

	public JPanel getPnlUsuari() {
		return pnlUsuari;
	}

	public JCheckBox getChkId() {
		return chkId;
	}

	public JCheckBox getChkLogin() {
		return chkLogin;
	}

	public JCheckBox getChkPassword() {
		return chkPassword;
	}

	public JCheckBox getChkType() {
		return chkType;
	}

	public JButton getBtnMostrarTaula() {
		return btnMostrarTaula;
	}

	public JRadioButton getRdbtnPopulation() {
		return rdbtnPopulation;
	}

	public JRadioButton getRdbtnUsers() {
		return rdbtnUsers;
	}

	public ButtonGroup getTaulasRbts() {
		return taulas;
	}

	public JButton getBtnMarcarTot() {
		return btnMarcarTot;
	}

	public JButton getBtnDesmarcar() {
		return btnDesmarcar;
	}

	public JButton getBtnFiltreAvancat() {
		return btnFiltreAvancat;
	}

	/**
	 * Create the frame.
	 */
	public Filtrar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 992, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 976, 27);
		contentPane.add(toolBar);

		btnTornar = new JButton("Tornar");
		btnTornar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnTornar);

		btnFiltreAvancat = new JButton("Filtre Avançat");
		btnFiltreAvancat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnFiltreAvancat);

		btnExportarCsv = new JButton("Exportar a CSV");
		btnExportarCsv.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnExportarCsv);

		lblSeleccionaTaula = new JLabel("Selecciona la taula:");
		lblSeleccionaTaula.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSeleccionaTaula.setBounds(27, 38, 139, 27);
		contentPane.add(lblSeleccionaTaula);

		lblSeleccionaElsCamps = new JLabel("Selecciona els camps a mostrar:");
		lblSeleccionaElsCamps.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSeleccionaElsCamps.setBounds(27, 96, 279, 27);
		contentPane.add(lblSeleccionaElsCamps);

		pnlPopulation = new JPanel();
		pnlPopulation.setBounds(27, 134, 127, 276);
		contentPane.add(pnlPopulation);
		pnlPopulation.setLayout(null);

		chkCountry = new JCheckBox("country");
		chkCountry.setEnabled(false);
		chkCountry.setSelected(true);
		chkCountry.setBounds(18, 5, 79, 29);
		chkCountry.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkCountry);

		chkPopulation = new JCheckBox("population");
		chkPopulation.setBounds(18, 39, 101, 29);
		chkPopulation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkPopulation);

		chkDensity = new JCheckBox("density");
		chkDensity.setBounds(18, 73, 75, 29);
		chkDensity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkDensity);

		chkArea = new JCheckBox("area");
		chkArea.setBounds(18, 107, 55, 29);
		chkArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkArea);

		chkFertility = new JCheckBox("fertility");
		chkFertility.setBounds(18, 141, 75, 29);
		chkFertility.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkFertility);

		chkAge = new JCheckBox("age");
		chkAge.setBounds(18, 175, 51, 29);
		chkAge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkAge);

		chkUrban = new JCheckBox("urban");
		chkUrban.setBounds(18, 209, 67, 29);
		chkUrban.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkUrban);

		chkShare = new JCheckBox("share");
		chkShare.setBounds(18, 243, 63, 29);
		chkShare.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlPopulation.add(chkShare);

		pnlUsuari = new JPanel();
		pnlUsuari.setBounds(26, 134, 140, 159);
		contentPane.add(pnlUsuari);
		pnlUsuari.setLayout(null);

		chkId = new JCheckBox("id");
		chkId.setEnabled(false);
		chkId.setSelected(true);
		chkId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chkId.setBounds(24, 7, 79, 29);
		pnlUsuari.add(chkId);

		chkLogin = new JCheckBox("login");
		chkLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chkLogin.setBounds(24, 39, 79, 29);
		pnlUsuari.add(chkLogin);

		chkPassword = new JCheckBox("password");
		chkPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chkPassword.setBounds(24, 71, 110, 29);
		pnlUsuari.add(chkPassword);

		chkType = new JCheckBox("type");
		chkType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chkType.setBounds(24, 103, 79, 29);
		pnlUsuari.add(chkType);

		btnMostrarTaula = new JButton("Mostrar Taula");
		btnMostrarTaula.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMostrarTaula.setBounds(27, 436, 279, 33);
		contentPane.add(btnMostrarTaula);

		rdbtnPopulation = new JRadioButton("population");
		rdbtnPopulation.setSelected(true);
		rdbtnPopulation.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnPopulation.setBounds(27, 66, 109, 23);
		contentPane.add(rdbtnPopulation);

		rdbtnUsers = new JRadioButton("users");
		rdbtnUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnUsers.setBounds(142, 66, 109, 23);
		contentPane.add(rdbtnUsers);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 38, 608, 431);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		taulas = new ButtonGroup();
		taulas.add(rdbtnPopulation);
		taulas.add(rdbtnUsers);

		btnDesmarcar = new JButton("Desmarcar");
		btnDesmarcar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDesmarcar.setBounds(164, 378, 139, 33);
		contentPane.add(btnDesmarcar);

		btnMarcarTot = new JButton("Marcar tot");
		btnMarcarTot.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMarcarTot.setBounds(164, 332, 139, 33);
		contentPane.add(btnMarcarTot);
	}
}
