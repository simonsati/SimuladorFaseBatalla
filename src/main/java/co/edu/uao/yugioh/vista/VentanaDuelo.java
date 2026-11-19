package co.edu.uao.yugioh.vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Vista del patrón MVC: ventana principal del simulador.
 * <p>
 * Toda la interfaz se construyó con el diseñador visual de NetBeans
 * (JFrame Form): los componentes, colores, bordes e iconos están definidos en
 * {@code VentanaDuelo.form} y NetBeans genera el método {@code initComponents()}.
 * Esta clase no contiene lógica de negocio: {@code ControladorDuelo} registra
 * los eventos de los botones y actualiza los componentes a través de los
 * métodos de acceso que se exponen aquí.
 *
 * @author simonsati
 */
public class VentanaDuelo extends javax.swing.JFrame {

    private static final String RUTA_ICONO_VENTANA = "/co/edu/uao/yugioh/vista/iconos/logo.png";

    /**
     * Crea la ventana con los componentes definidos en el diseñador.
     */
    public VentanaDuelo() {
        initComponents();
        java.net.URL urlIcono = getClass().getResource(RUTA_ICONO_VENTANA);
        if (urlIcono != null) {
            setIconImage(new javax.swing.ImageIcon(urlIcono).getImage());
        }
    }

    // ------------------------------------------------------------------
    // Mensajes al usuario (los invoca el controlador)
    // ------------------------------------------------------------------

    public void mostrarInformacion(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarAdvertencia(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public void mostrarError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmar(String titulo, String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // ------------------------------------------------------------------
    // Acceso a los componentes que usa el controlador
    // ------------------------------------------------------------------

    public JLabel getLblTurno() {
        return lblTurno;
    }

    public JTabbedPane getTabsPrincipal() {
        return tabsPrincipal;
    }

    public JTextField getTxtJugador1() {
        return txtJugador1;
    }

    public JTextField getTxtJugador2() {
        return txtJugador2;
    }

    public JTextField getTxtLpIniciales() {
        return txtLpIniciales;
    }

    public JButton getBtnDueloEjemplo() {
        return btnDueloEjemplo;
    }

    public JButton getBtnIniciarDuelo() {
        return btnIniciarDuelo;
    }

    public JComboBox<String> getCmbDueno() {
        return cmbDueno;
    }

    public JTextField getTxtNombreMonstruo() {
        return txtNombreMonstruo;
    }

    public JTextField getTxtAtaque() {
        return txtAtaque;
    }

    public JTextField getTxtDefensa() {
        return txtDefensa;
    }

    public JComboBox<String> getCmbPosicion() {
        return cmbPosicion;
    }

    public JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public JTextField getTxtDetalle() {
        return txtDetalle;
    }

    public JButton getBtnLimpiarMonstruo() {
        return btnLimpiarMonstruo;
    }

    public JButton getBtnInvocar() {
        return btnInvocar;
    }

    public JTable getTblMonstruos() {
        return tblMonstruos;
    }

    public JLabel getLblNombreJugador1() {
        return lblNombreJugador1;
    }

    public JProgressBar getBarLpJugador1() {
        return barLpJugador1;
    }

    public JLabel getLblInfoJugador1() {
        return lblInfoJugador1;
    }

    public JTable getTblCampoJugador1() {
        return tblCampoJugador1;
    }

    public JLabel getLblCementerioJugador1() {
        return lblCementerioJugador1;
    }

    public JLabel getLblEnTurno() {
        return lblEnTurno;
    }

    public JComboBox<String> getCmbAtacante() {
        return cmbAtacante;
    }

    public JComboBox<String> getCmbObjetivo() {
        return cmbObjetivo;
    }

    public JButton getBtnAtacar() {
        return btnAtacar;
    }

    public JButton getBtnCambiarPosicion() {
        return btnCambiarPosicion;
    }

    public JButton getBtnTerminarTurno() {
        return btnTerminarTurno;
    }

    public JTextArea getTxtResultado() {
        return txtResultado;
    }

    public JLabel getLblNombreJugador2() {
        return lblNombreJugador2;
    }

    public JProgressBar getBarLpJugador2() {
        return barLpJugador2;
    }

    public JLabel getLblInfoJugador2() {
        return lblInfoJugador2;
    }

    public JTable getTblCampoJugador2() {
        return tblCampoJugador2;
    }

    public JLabel getLblCementerioJugador2() {
        return lblCementerioJugador2;
    }

    public JLabel getLblTituloHistorial() {
        return lblTituloHistorial;
    }

    public JButton getBtnRecargarHistorial() {
        return btnRecargarHistorial;
    }

    public JButton getBtnVaciarHistorial() {
        return btnVaciarHistorial;
    }

    public JTable getTblHistorial() {
        return tblHistorial;
    }

    public JLabel getLblResumenHistorial() {
        return lblResumenHistorial;
    }

    public JLabel getLblEstado() {
        return lblEstado;
    }

    public JLabel getLblArchivo() {
        return lblArchivo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlEncabezado = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlTitulos = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        tabsPrincipal = new javax.swing.JTabbedPane();
        pnlDuelistas = new javax.swing.JPanel();
        pnlTarjetaDuelistas = new javax.swing.JPanel();
        lblTituloDuelistas = new javax.swing.JLabel();
        lblAyudaDuelistas = new javax.swing.JLabel();
        lblJugador1 = new javax.swing.JLabel();
        txtJugador1 = new javax.swing.JTextField();
        lblJugador2 = new javax.swing.JLabel();
        txtJugador2 = new javax.swing.JTextField();
        lblLpIniciales = new javax.swing.JLabel();
        txtLpIniciales = new javax.swing.JTextField();
        pnlBotonesDuelistas = new javax.swing.JPanel();
        btnDueloEjemplo = new javax.swing.JButton();
        btnIniciarDuelo = new javax.swing.JButton();
        lblNotaDuelistas = new javax.swing.JLabel();
        pnlMonstruos = new javax.swing.JPanel();
        pnlFormMonstruo = new javax.swing.JPanel();
        lblTituloMonstruo = new javax.swing.JLabel();
        lblDueno = new javax.swing.JLabel();
        cmbDueno = new javax.swing.JComboBox<>();
        lblNombreMonstruo = new javax.swing.JLabel();
        txtNombreMonstruo = new javax.swing.JTextField();
        lblAtaque = new javax.swing.JLabel();
        txtAtaque = new javax.swing.JTextField();
        lblDefensa = new javax.swing.JLabel();
        txtDefensa = new javax.swing.JTextField();
        lblPosicion = new javax.swing.JLabel();
        cmbPosicion = new javax.swing.JComboBox<>();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblDetalle = new javax.swing.JLabel();
        txtDetalle = new javax.swing.JTextField();
        pnlBotonesMonstruo = new javax.swing.JPanel();
        btnLimpiarMonstruo = new javax.swing.JButton();
        btnInvocar = new javax.swing.JButton();
        lblAyudaMonstruo = new javax.swing.JLabel();
        pnlListaMonstruos = new javax.swing.JPanel();
        lblTituloListaMonstruos = new javax.swing.JLabel();
        scrMonstruos = new javax.swing.JScrollPane();
        tblMonstruos = new javax.swing.JTable();
        pnlBatalla = new javax.swing.JPanel();
        pnlArena = new javax.swing.JPanel();
        pnlJugador1 = new javax.swing.JPanel();
        pnlInfoJugador1 = new javax.swing.JPanel();
        lblNombreJugador1 = new javax.swing.JLabel();
        barLpJugador1 = new javax.swing.JProgressBar();
        lblInfoJugador1 = new javax.swing.JLabel();
        scrCampoJugador1 = new javax.swing.JScrollPane();
        tblCampoJugador1 = new javax.swing.JTable();
        lblCementerioJugador1 = new javax.swing.JLabel();
        pnlAcciones = new javax.swing.JPanel();
        lblTituloAcciones = new javax.swing.JLabel();
        lblEnTurno = new javax.swing.JLabel();
        lblAtacante = new javax.swing.JLabel();
        cmbAtacante = new javax.swing.JComboBox<>();
        lblObjetivo = new javax.swing.JLabel();
        cmbObjetivo = new javax.swing.JComboBox<>();
        btnAtacar = new javax.swing.JButton();
        pnlBotonesTurno = new javax.swing.JPanel();
        btnCambiarPosicion = new javax.swing.JButton();
        btnTerminarTurno = new javax.swing.JButton();
        lblTituloResultado = new javax.swing.JLabel();
        scrResultado = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextArea();
        pnlJugador2 = new javax.swing.JPanel();
        pnlInfoJugador2 = new javax.swing.JPanel();
        lblNombreJugador2 = new javax.swing.JLabel();
        barLpJugador2 = new javax.swing.JProgressBar();
        lblInfoJugador2 = new javax.swing.JLabel();
        scrCampoJugador2 = new javax.swing.JScrollPane();
        tblCampoJugador2 = new javax.swing.JTable();
        lblCementerioJugador2 = new javax.swing.JLabel();
        lblReglas = new javax.swing.JLabel();
        pnlHistorial = new javax.swing.JPanel();
        pnlBarraHistorial = new javax.swing.JPanel();
        lblTituloHistorial = new javax.swing.JLabel();
        pnlBotonesHistorial = new javax.swing.JPanel();
        btnRecargarHistorial = new javax.swing.JButton();
        btnVaciarHistorial = new javax.swing.JButton();
        scrHistorial = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        lblResumenHistorial = new javax.swing.JLabel();
        pnlEstado = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        lblArchivo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Yu-Gi-Oh! · Simulador de la Fase de Batalla");
        setMinimumSize(new java.awt.Dimension(1040, 660));

        pnlEncabezado.setBackground(new java.awt.Color(15, 18, 38));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(212, 175, 55)), javax.swing.BorderFactory.createEmptyBorder(12, 20, 12, 20)));
        pnlEncabezado.setLayout(new java.awt.BorderLayout(16, 0));

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(236, 239, 244));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/logo.png"))); // NOI18N
        lblLogo.setText("");
        pnlEncabezado.add(lblLogo, java.awt.BorderLayout.LINE_START);

        pnlTitulos.setOpaque(false);
        pnlTitulos.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(212, 175, 55));
        lblTitulo.setText("Simulador de la Fase de Batalla");
        pnlTitulos.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(165, 173, 207));
        lblSubtitulo.setText("Yu-Gi-Oh! · cálculo oficial de daño, Puntos de Vida (LP) e historial de combate");
        pnlTitulos.add(lblSubtitulo);

        pnlEncabezado.add(pnlTitulos, java.awt.BorderLayout.CENTER);

        lblTurno.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblTurno.setForeground(new java.awt.Color(240, 214, 130));
        lblTurno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/reloj.png"))); // NOI18N
        lblTurno.setText("Duelo sin iniciar");
        lblTurno.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(212, 175, 55), 2, true), javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16)));
        lblTurno.setIconTextGap(10);
        pnlEncabezado.add(lblTurno, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.PAGE_START);

        tabsPrincipal.setBackground(new java.awt.Color(24, 28, 54));
        tabsPrincipal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tabsPrincipal.setForeground(new java.awt.Color(236, 239, 244));
        tabsPrincipal.setOpaque(true);
        tabsPrincipal.setPreferredSize(new java.awt.Dimension(1260, 560));

        pnlDuelistas.setBackground(new java.awt.Color(24, 28, 54));
        pnlDuelistas.setLayout(new java.awt.GridBagLayout());

        pnlTarjetaDuelistas.setBackground(new java.awt.Color(32, 37, 70));
        pnlTarjetaDuelistas.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(60, 68, 120), 1, true), javax.swing.BorderFactory.createEmptyBorder(26, 32, 26, 32)));
        pnlTarjetaDuelistas.setLayout(new java.awt.GridBagLayout());

        lblTituloDuelistas.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTituloDuelistas.setForeground(new java.awt.Color(212, 175, 55));
        lblTituloDuelistas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/duelistas.png"))); // NOI18N
        lblTituloDuelistas.setText("Registro de duelistas");
        lblTituloDuelistas.setIconTextGap(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        pnlTarjetaDuelistas.add(lblTituloDuelistas, gridBagConstraints);

        lblAyudaDuelistas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblAyudaDuelistas.setForeground(new java.awt.Color(165, 173, 207));
        lblAyudaDuelistas.setText("<html>Cada duelista empieza con los mismos Puntos de Vida (LP).<br>En un duelo oficial son 8000 LP.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        pnlTarjetaDuelistas.add(lblAyudaDuelistas, gridBagConstraints);

        lblJugador1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblJugador1.setForeground(new java.awt.Color(84, 154, 236));
        lblJugador1.setText("Duelista 1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 16);
        pnlTarjetaDuelistas.add(lblJugador1, gridBagConstraints);

        txtJugador1.setBackground(new java.awt.Color(42, 48, 88));
        txtJugador1.setColumns(18);
        txtJugador1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJugador1.setForeground(new java.awt.Color(236, 239, 244));
        txtJugador1.setText("Yugi");
        txtJugador1.setCaretColor(new java.awt.Color(212, 175, 55));
        txtJugador1.setToolTipText("Nombre del primer duelista (empieza atacando)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        pnlTarjetaDuelistas.add(txtJugador1, gridBagConstraints);

        lblJugador2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblJugador2.setForeground(new java.awt.Color(232, 86, 102));
        lblJugador2.setText("Duelista 2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 16);
        pnlTarjetaDuelistas.add(lblJugador2, gridBagConstraints);

        txtJugador2.setBackground(new java.awt.Color(42, 48, 88));
        txtJugador2.setColumns(18);
        txtJugador2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJugador2.setForeground(new java.awt.Color(236, 239, 244));
        txtJugador2.setText("Kaiba");
        txtJugador2.setCaretColor(new java.awt.Color(212, 175, 55));
        txtJugador2.setToolTipText("Nombre del segundo duelista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        pnlTarjetaDuelistas.add(txtJugador2, gridBagConstraints);

        lblLpIniciales.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLpIniciales.setForeground(new java.awt.Color(236, 239, 244));
        lblLpIniciales.setText("LP iniciales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 16);
        pnlTarjetaDuelistas.add(lblLpIniciales, gridBagConstraints);

        txtLpIniciales.setBackground(new java.awt.Color(42, 48, 88));
        txtLpIniciales.setColumns(18);
        txtLpIniciales.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLpIniciales.setForeground(new java.awt.Color(236, 239, 244));
        txtLpIniciales.setText("8000");
        txtLpIniciales.setCaretColor(new java.awt.Color(212, 175, 55));
        txtLpIniciales.setToolTipText("Solo números, entre 100 y 99999");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        pnlTarjetaDuelistas.add(txtLpIniciales, gridBagConstraints);

        pnlBotonesDuelistas.setOpaque(false);
        pnlBotonesDuelistas.setLayout(new java.awt.GridLayout(1, 0, 12, 0));

        btnDueloEjemplo.setBackground(new java.awt.Color(42, 48, 88));
        btnDueloEjemplo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDueloEjemplo.setForeground(new java.awt.Color(236, 239, 244));
        btnDueloEjemplo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/ejemplo.png"))); // NOI18N
        btnDueloEjemplo.setText("Duelo de ejemplo");
        btnDueloEjemplo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDueloEjemplo.setFocusPainted(false);
        btnDueloEjemplo.setIconTextGap(8);
        btnDueloEjemplo.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnDueloEjemplo.setToolTipText("Carga a Yugi y Kaiba con monstruos clásicos para probar rápido");
        pnlBotonesDuelistas.add(btnDueloEjemplo);

        btnIniciarDuelo.setBackground(new java.awt.Color(212, 175, 55));
        btnIniciarDuelo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIniciarDuelo.setForeground(new java.awt.Color(22, 24, 44));
        btnIniciarDuelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/iniciar.png"))); // NOI18N
        btnIniciarDuelo.setText("Iniciar duelo");
        btnIniciarDuelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarDuelo.setFocusPainted(false);
        btnIniciarDuelo.setIconTextGap(8);
        btnIniciarDuelo.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnIniciarDuelo.setToolTipText("Crea a los duelistas con los LP indicados");
        pnlBotonesDuelistas.add(btnIniciarDuelo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 10, 0);
        pnlTarjetaDuelistas.add(pnlBotonesDuelistas, gridBagConstraints);

        lblNotaDuelistas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblNotaDuelistas.setForeground(new java.awt.Color(165, 173, 207));
        lblNotaDuelistas.setText("<html>Iniciar un duelo nuevo reinicia el campo, los LP y los turnos.<br>El historial guardado en el archivo se conserva.</html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        pnlTarjetaDuelistas.add(lblNotaDuelistas, gridBagConstraints);

        pnlDuelistas.add(pnlTarjetaDuelistas, new java.awt.GridBagConstraints());

        tabsPrincipal.addTab("1 · Duelistas", new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/duelistas.png")), pnlDuelistas, "RF1: registrar a los duelistas y sus LP iniciales"); // NOI18N

        pnlMonstruos.setBackground(new java.awt.Color(24, 28, 54));
        pnlMonstruos.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 16, 16, 16));
        pnlMonstruos.setLayout(new java.awt.BorderLayout(18, 0));

        pnlFormMonstruo.setBackground(new java.awt.Color(32, 37, 70));
        pnlFormMonstruo.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(60, 68, 120), 1, true), javax.swing.BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        pnlFormMonstruo.setLayout(new java.awt.GridBagLayout());

        lblTituloMonstruo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTituloMonstruo.setForeground(new java.awt.Color(212, 175, 55));
        lblTituloMonstruo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/carta.png"))); // NOI18N
        lblTituloMonstruo.setText("Invocar monstruo");
        lblTituloMonstruo.setIconTextGap(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        pnlFormMonstruo.add(lblTituloMonstruo, gridBagConstraints);

        lblDueno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDueno.setForeground(new java.awt.Color(236, 239, 244));
        lblDueno.setText("Duelista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblDueno, gridBagConstraints);

        cmbDueno.setBackground(new java.awt.Color(42, 48, 88));
        cmbDueno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbDueno.setForeground(new java.awt.Color(236, 239, 244));
        cmbDueno.setToolTipText("Campo en el que se invoca el monstruo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(cmbDueno, gridBagConstraints);

        lblNombreMonstruo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombreMonstruo.setForeground(new java.awt.Color(236, 239, 244));
        lblNombreMonstruo.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblNombreMonstruo, gridBagConstraints);

        txtNombreMonstruo.setBackground(new java.awt.Color(42, 48, 88));
        txtNombreMonstruo.setColumns(16);
        txtNombreMonstruo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNombreMonstruo.setForeground(new java.awt.Color(236, 239, 244));
        txtNombreMonstruo.setText("");
        txtNombreMonstruo.setCaretColor(new java.awt.Color(212, 175, 55));
        txtNombreMonstruo.setToolTipText("Nombre de la carta (máximo 40 caracteres)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(txtNombreMonstruo, gridBagConstraints);

        lblAtaque.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAtaque.setForeground(new java.awt.Color(236, 239, 244));
        lblAtaque.setText("ATQ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblAtaque, gridBagConstraints);

        txtAtaque.setBackground(new java.awt.Color(42, 48, 88));
        txtAtaque.setColumns(16);
        txtAtaque.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAtaque.setForeground(new java.awt.Color(236, 239, 244));
        txtAtaque.setText("");
        txtAtaque.setCaretColor(new java.awt.Color(212, 175, 55));
        txtAtaque.setToolTipText("Puntos de ataque: solo números de 0 a 5000");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(txtAtaque, gridBagConstraints);

        lblDefensa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDefensa.setForeground(new java.awt.Color(236, 239, 244));
        lblDefensa.setText("DEF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblDefensa, gridBagConstraints);

        txtDefensa.setBackground(new java.awt.Color(42, 48, 88));
        txtDefensa.setColumns(16);
        txtDefensa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDefensa.setForeground(new java.awt.Color(236, 239, 244));
        txtDefensa.setText("");
        txtDefensa.setCaretColor(new java.awt.Color(212, 175, 55));
        txtDefensa.setToolTipText("Puntos de defensa: solo números de 0 a 5000");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(txtDefensa, gridBagConstraints);

        lblPosicion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPosicion.setForeground(new java.awt.Color(236, 239, 244));
        lblPosicion.setText("Posición");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblPosicion, gridBagConstraints);

        cmbPosicion.setBackground(new java.awt.Color(42, 48, 88));
        cmbPosicion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbPosicion.setForeground(new java.awt.Color(236, 239, 244));
        cmbPosicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ataque", "Defensa" }));
        cmbPosicion.setToolTipText("Posición de batalla del monstruo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(cmbPosicion, gridBagConstraints);

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(236, 239, 244));
        lblTipo.setText("Tipo de carta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblTipo, gridBagConstraints);

        cmbTipo.setBackground(new java.awt.Color(42, 48, 88));
        cmbTipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTipo.setForeground(new java.awt.Color(236, 239, 244));
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Normal", "Efecto" }));
        cmbTipo.setToolTipText("Monstruo Normal o de Efecto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(cmbTipo, gridBagConstraints);

        lblDetalle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDetalle.setForeground(new java.awt.Color(236, 239, 244));
        lblDetalle.setText("Descripción / efecto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 14);
        pnlFormMonstruo.add(lblDetalle, gridBagConstraints);

        txtDetalle.setBackground(new java.awt.Color(42, 48, 88));
        txtDetalle.setColumns(16);
        txtDetalle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDetalle.setForeground(new java.awt.Color(236, 239, 244));
        txtDetalle.setText("");
        txtDetalle.setCaretColor(new java.awt.Color(212, 175, 55));
        txtDetalle.setToolTipText("Texto opcional (máximo 120 caracteres)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        pnlFormMonstruo.add(txtDetalle, gridBagConstraints);

        pnlBotonesMonstruo.setOpaque(false);
        pnlBotonesMonstruo.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnLimpiarMonstruo.setBackground(new java.awt.Color(42, 48, 88));
        btnLimpiarMonstruo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiarMonstruo.setForeground(new java.awt.Color(236, 239, 244));
        btnLimpiarMonstruo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/limpiar.png"))); // NOI18N
        btnLimpiarMonstruo.setText("Limpiar");
        btnLimpiarMonstruo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiarMonstruo.setFocusPainted(false);
        btnLimpiarMonstruo.setIconTextGap(8);
        btnLimpiarMonstruo.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnLimpiarMonstruo.setToolTipText("Vacía el formulario");
        pnlBotonesMonstruo.add(btnLimpiarMonstruo);

        btnInvocar.setBackground(new java.awt.Color(212, 175, 55));
        btnInvocar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInvocar.setForeground(new java.awt.Color(22, 24, 44));
        btnInvocar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/invocar.png"))); // NOI18N
        btnInvocar.setText("Invocar al campo");
        btnInvocar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInvocar.setFocusPainted(false);
        btnInvocar.setIconTextGap(8);
        btnInvocar.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnInvocar.setToolTipText("Crea el monstruo y lo pone en el campo del duelista");
        pnlBotonesMonstruo.add(btnInvocar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 10, 0);
        pnlFormMonstruo.add(pnlBotonesMonstruo, gridBagConstraints);

        lblAyudaMonstruo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblAyudaMonstruo.setForeground(new java.awt.Color(165, 173, 207));
        lblAyudaMonstruo.setText("<html>ATQ y DEF admiten números de 0 a 5000.<br>Cada duelista puede tener máximo 5 monstruos en el campo.</html>");
        lblAyudaMonstruo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        pnlFormMonstruo.add(lblAyudaMonstruo, gridBagConstraints);

        pnlMonstruos.add(pnlFormMonstruo, java.awt.BorderLayout.LINE_START);

        pnlListaMonstruos.setOpaque(false);
        pnlListaMonstruos.setLayout(new java.awt.BorderLayout(0, 10));

        lblTituloListaMonstruos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloListaMonstruos.setForeground(new java.awt.Color(212, 175, 55));
        lblTituloListaMonstruos.setText("Monstruos en el campo");
        pnlListaMonstruos.add(lblTituloListaMonstruos, java.awt.BorderLayout.PAGE_START);

        scrMonstruos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 68, 120)));

        tblMonstruos.setBackground(new java.awt.Color(32, 37, 70));
        tblMonstruos.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblMonstruos.setForeground(new java.awt.Color(236, 239, 244));
        tblMonstruos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Duelista", "Monstruo", "Tipo", "ATQ", "DEF", "Posición", "Descripción / efecto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (tblMonstruos.getColumnModel().getColumnCount() > 0) {
            tblMonstruos.getColumnModel().getColumn(0).setPreferredWidth(90);
            tblMonstruos.getColumnModel().getColumn(1).setPreferredWidth(190);
            tblMonstruos.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblMonstruos.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblMonstruos.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblMonstruos.getColumnModel().getColumn(5).setPreferredWidth(80);
            tblMonstruos.getColumnModel().getColumn(6).setPreferredWidth(280);
        }
        tblMonstruos.setFillsViewportHeight(true);
        tblMonstruos.setGridColor(new java.awt.Color(60, 68, 120));
        tblMonstruos.setRowHeight(28);
        tblMonstruos.setSelectionBackground(new java.awt.Color(90, 76, 30));
        tblMonstruos.setSelectionForeground(new java.awt.Color(240, 214, 130));
        tblMonstruos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMonstruos.setShowVerticalLines(false);
        tblMonstruos.getTableHeader().setReorderingAllowed(false);
        scrMonstruos.setViewportView(tblMonstruos);

        pnlListaMonstruos.add(scrMonstruos, java.awt.BorderLayout.CENTER);

        pnlMonstruos.add(pnlListaMonstruos, java.awt.BorderLayout.CENTER);

        tabsPrincipal.addTab("2 · Monstruos", new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/carta.png")), pnlMonstruos, "RF2: crear monstruos con nombre, ATQ, DEF y posición"); // NOI18N

        pnlBatalla.setBackground(new java.awt.Color(24, 28, 54));
        pnlBatalla.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 12, 14));
        pnlBatalla.setLayout(new java.awt.BorderLayout(0, 10));

        pnlArena.setOpaque(false);
        pnlArena.setLayout(new java.awt.GridBagLayout());

        pnlJugador1.setBackground(new java.awt.Color(32, 37, 70));
        pnlJugador1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(84, 154, 236), 2, true), javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        pnlJugador1.setLayout(new java.awt.BorderLayout(0, 10));

        pnlInfoJugador1.setOpaque(false);
        pnlInfoJugador1.setLayout(new java.awt.GridLayout(3, 1, 0, 6));

        lblNombreJugador1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblNombreJugador1.setForeground(new java.awt.Color(84, 154, 236));
        lblNombreJugador1.setText("Duelista 1");
        pnlInfoJugador1.add(lblNombreJugador1);

        barLpJugador1.setBackground(new java.awt.Color(42, 48, 88));
        barLpJugador1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        barLpJugador1.setForeground(new java.awt.Color(84, 154, 236));
        barLpJugador1.setMaximum(8000);
        barLpJugador1.setString("8000 / 8000 LP");
        barLpJugador1.setValue(8000);
        barLpJugador1.setPreferredSize(new java.awt.Dimension(146, 28));
        barLpJugador1.setStringPainted(true);
        pnlInfoJugador1.add(barLpJugador1);

        lblInfoJugador1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblInfoJugador1.setForeground(new java.awt.Color(165, 173, 207));
        lblInfoJugador1.setText("Campo: 0/5 · Cementerio: 0");
        pnlInfoJugador1.add(lblInfoJugador1);

        pnlJugador1.add(pnlInfoJugador1, java.awt.BorderLayout.PAGE_START);

        scrCampoJugador1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 68, 120)));

        tblCampoJugador1.setBackground(new java.awt.Color(32, 37, 70));
        tblCampoJugador1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCampoJugador1.setForeground(new java.awt.Color(236, 239, 244));
        tblCampoJugador1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Monstruo", "ATQ", "DEF", "Posición", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (tblCampoJugador1.getColumnModel().getColumnCount() > 0) {
            tblCampoJugador1.getColumnModel().getColumn(0).setPreferredWidth(170);
            tblCampoJugador1.getColumnModel().getColumn(1).setPreferredWidth(55);
            tblCampoJugador1.getColumnModel().getColumn(2).setPreferredWidth(55);
            tblCampoJugador1.getColumnModel().getColumn(3).setPreferredWidth(75);
            tblCampoJugador1.getColumnModel().getColumn(4).setPreferredWidth(85);
        }
        tblCampoJugador1.setFillsViewportHeight(true);
        tblCampoJugador1.setGridColor(new java.awt.Color(60, 68, 120));
        tblCampoJugador1.setRowHeight(28);
        tblCampoJugador1.setSelectionBackground(new java.awt.Color(90, 76, 30));
        tblCampoJugador1.setSelectionForeground(new java.awt.Color(240, 214, 130));
        tblCampoJugador1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCampoJugador1.setShowVerticalLines(false);
        tblCampoJugador1.getTableHeader().setReorderingAllowed(false);
        scrCampoJugador1.setViewportView(tblCampoJugador1);

        pnlJugador1.add(scrCampoJugador1, java.awt.BorderLayout.CENTER);

        lblCementerioJugador1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblCementerioJugador1.setForeground(new java.awt.Color(165, 173, 207));
        lblCementerioJugador1.setText("Cementerio: vacío");
        pnlJugador1.add(lblCementerioJugador1, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlArena.add(pnlJugador1, gridBagConstraints);

        pnlAcciones.setBackground(new java.awt.Color(32, 37, 70));
        pnlAcciones.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(212, 175, 55), 2, true), javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        pnlAcciones.setPreferredSize(new java.awt.Dimension(300, 420));
        pnlAcciones.setLayout(new java.awt.GridBagLayout());

        lblTituloAcciones.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        lblTituloAcciones.setForeground(new java.awt.Color(212, 175, 55));
        lblTituloAcciones.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloAcciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/batalla.png"))); // NOI18N
        lblTituloAcciones.setText("Declarar ataque");
        lblTituloAcciones.setIconTextGap(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        pnlAcciones.add(lblTituloAcciones, gridBagConstraints);

        lblEnTurno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEnTurno.setForeground(new java.awt.Color(240, 214, 130));
        lblEnTurno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnTurno.setText("Ataca: —");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlAcciones.add(lblEnTurno, gridBagConstraints);

        lblAtacante.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblAtacante.setForeground(new java.awt.Color(236, 239, 244));
        lblAtacante.setText("Monstruo atacante");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlAcciones.add(lblAtacante, gridBagConstraints);

        cmbAtacante.setBackground(new java.awt.Color(42, 48, 88));
        cmbAtacante.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbAtacante.setForeground(new java.awt.Color(236, 239, 244));
        cmbAtacante.setToolTipText("Solo aparecen los monstruos en Ataque que no han atacado en este turno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        pnlAcciones.add(cmbAtacante, gridBagConstraints);

        lblObjetivo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblObjetivo.setForeground(new java.awt.Color(236, 239, 244));
        lblObjetivo.setText("Objetivo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 4, 0);
        pnlAcciones.add(lblObjetivo, gridBagConstraints);

        cmbObjetivo.setBackground(new java.awt.Color(42, 48, 88));
        cmbObjetivo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbObjetivo.setForeground(new java.awt.Color(236, 239, 244));
        cmbObjetivo.setToolTipText("Monstruos del rival, o ataque directo si su campo está vacío");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        pnlAcciones.add(cmbObjetivo, gridBagConstraints);

        btnAtacar.setBackground(new java.awt.Color(190, 46, 62));
        btnAtacar.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnAtacar.setForeground(new java.awt.Color(236, 239, 244));
        btnAtacar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/atacar.png"))); // NOI18N
        btnAtacar.setText("¡Atacar!");
        btnAtacar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtacar.setFocusPainted(false);
        btnAtacar.setIconTextGap(8);
        btnAtacar.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnAtacar.setToolTipText("Resuelve el ataque según la tabla oficial de daño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 8, 0);
        pnlAcciones.add(btnAtacar, gridBagConstraints);

        pnlBotonesTurno.setOpaque(false);
        pnlBotonesTurno.setLayout(new java.awt.GridLayout(1, 0, 8, 0));

        btnCambiarPosicion.setBackground(new java.awt.Color(42, 48, 88));
        btnCambiarPosicion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCambiarPosicion.setForeground(new java.awt.Color(236, 239, 244));
        btnCambiarPosicion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/posicion.png"))); // NOI18N
        btnCambiarPosicion.setText("Posición");
        btnCambiarPosicion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCambiarPosicion.setFocusPainted(false);
        btnCambiarPosicion.setIconTextGap(8);
        btnCambiarPosicion.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnCambiarPosicion.setToolTipText("Cambia a Ataque/Defensa el monstruo seleccionado en tu tabla (una vez por turno)");
        pnlBotonesTurno.add(btnCambiarPosicion);

        btnTerminarTurno.setBackground(new java.awt.Color(42, 48, 88));
        btnTerminarTurno.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTerminarTurno.setForeground(new java.awt.Color(236, 239, 244));
        btnTerminarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/turno.png"))); // NOI18N
        btnTerminarTurno.setText("Fin de turno");
        btnTerminarTurno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTerminarTurno.setFocusPainted(false);
        btnTerminarTurno.setIconTextGap(8);
        btnTerminarTurno.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnTerminarTurno.setToolTipText("Pasa el turno al rival");
        pnlBotonesTurno.add(btnTerminarTurno);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        pnlAcciones.add(pnlBotonesTurno, gridBagConstraints);

        lblTituloResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTituloResultado.setForeground(new java.awt.Color(165, 173, 207));
        lblTituloResultado.setText("Resultado del último ataque");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        pnlAcciones.add(lblTituloResultado, gridBagConstraints);

        scrResultado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 68, 120)));

        txtResultado.setBackground(new java.awt.Color(42, 48, 88));
        txtResultado.setColumns(20);
        txtResultado.setEditable(false);
        txtResultado.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtResultado.setForeground(new java.awt.Color(236, 239, 244));
        txtResultado.setLineWrap(true);
        txtResultado.setRows(6);
        txtResultado.setText("Aquí verás el cálculo de cada ataque: qué monstruo se destruye y cuánto daño recibe cada duelista.");
        txtResultado.setWrapStyleWord(true);
        txtResultado.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10));
        scrResultado.setViewportView(txtResultado);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        pnlAcciones.add(scrResultado, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 12);
        pnlArena.add(pnlAcciones, gridBagConstraints);

        pnlJugador2.setBackground(new java.awt.Color(32, 37, 70));
        pnlJugador2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(232, 86, 102), 2, true), javax.swing.BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        pnlJugador2.setLayout(new java.awt.BorderLayout(0, 10));

        pnlInfoJugador2.setOpaque(false);
        pnlInfoJugador2.setLayout(new java.awt.GridLayout(3, 1, 0, 6));

        lblNombreJugador2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblNombreJugador2.setForeground(new java.awt.Color(232, 86, 102));
        lblNombreJugador2.setText("Duelista 2");
        pnlInfoJugador2.add(lblNombreJugador2);

        barLpJugador2.setBackground(new java.awt.Color(42, 48, 88));
        barLpJugador2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        barLpJugador2.setForeground(new java.awt.Color(232, 86, 102));
        barLpJugador2.setMaximum(8000);
        barLpJugador2.setString("8000 / 8000 LP");
        barLpJugador2.setValue(8000);
        barLpJugador2.setPreferredSize(new java.awt.Dimension(146, 28));
        barLpJugador2.setStringPainted(true);
        pnlInfoJugador2.add(barLpJugador2);

        lblInfoJugador2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblInfoJugador2.setForeground(new java.awt.Color(165, 173, 207));
        lblInfoJugador2.setText("Campo: 0/5 · Cementerio: 0");
        pnlInfoJugador2.add(lblInfoJugador2);

        pnlJugador2.add(pnlInfoJugador2, java.awt.BorderLayout.PAGE_START);

        scrCampoJugador2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 68, 120)));

        tblCampoJugador2.setBackground(new java.awt.Color(32, 37, 70));
        tblCampoJugador2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCampoJugador2.setForeground(new java.awt.Color(236, 239, 244));
        tblCampoJugador2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Monstruo", "ATQ", "DEF", "Posición", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (tblCampoJugador2.getColumnModel().getColumnCount() > 0) {
            tblCampoJugador2.getColumnModel().getColumn(0).setPreferredWidth(170);
            tblCampoJugador2.getColumnModel().getColumn(1).setPreferredWidth(55);
            tblCampoJugador2.getColumnModel().getColumn(2).setPreferredWidth(55);
            tblCampoJugador2.getColumnModel().getColumn(3).setPreferredWidth(75);
            tblCampoJugador2.getColumnModel().getColumn(4).setPreferredWidth(85);
        }
        tblCampoJugador2.setFillsViewportHeight(true);
        tblCampoJugador2.setGridColor(new java.awt.Color(60, 68, 120));
        tblCampoJugador2.setRowHeight(28);
        tblCampoJugador2.setSelectionBackground(new java.awt.Color(90, 76, 30));
        tblCampoJugador2.setSelectionForeground(new java.awt.Color(240, 214, 130));
        tblCampoJugador2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCampoJugador2.setShowVerticalLines(false);
        tblCampoJugador2.getTableHeader().setReorderingAllowed(false);
        scrCampoJugador2.setViewportView(tblCampoJugador2);

        pnlJugador2.add(scrCampoJugador2, java.awt.BorderLayout.CENTER);

        lblCementerioJugador2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblCementerioJugador2.setForeground(new java.awt.Color(165, 173, 207));
        lblCementerioJugador2.setText("Cementerio: vacío");
        pnlJugador2.add(lblCementerioJugador2, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlArena.add(pnlJugador2, gridBagConstraints);

        pnlBatalla.add(pnlArena, java.awt.BorderLayout.CENTER);

        lblReglas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblReglas.setForeground(new java.awt.Color(165, 173, 207));
        lblReglas.setText("<html><b>Tabla de daño:</b> Ataque vs Ataque: el monstruo con menos ATQ se destruye y su dueño recibe la diferencia (si empatan, se destruyen ambos y nadie recibe daño). &nbsp;Ataque vs Defensa: si el ATQ supera la DEF el defensor se destruye sin daño; si es menor, el atacante recibe la diferencia. &nbsp;Ataque directo: solo si el rival no tiene monstruos; recibe todo el ATQ.</html>");
        lblReglas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 2, 0, 2));
        pnlBatalla.add(lblReglas, java.awt.BorderLayout.PAGE_END);

        tabsPrincipal.addTab("3 · Batalla", new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/batalla.png")), pnlBatalla, "RF3: declarar ataques y calcular el daño"); // NOI18N

        pnlHistorial.setBackground(new java.awt.Color(24, 28, 54));
        pnlHistorial.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 12, 14));
        pnlHistorial.setLayout(new java.awt.BorderLayout(0, 10));

        pnlBarraHistorial.setOpaque(false);
        pnlBarraHistorial.setLayout(new java.awt.BorderLayout(12, 0));

        lblTituloHistorial.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloHistorial.setForeground(new java.awt.Color(212, 175, 55));
        lblTituloHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/historial.png"))); // NOI18N
        lblTituloHistorial.setText("Historial de combate · historial_combate.txt");
        lblTituloHistorial.setIconTextGap(10);
        pnlBarraHistorial.add(lblTituloHistorial, java.awt.BorderLayout.CENTER);

        pnlBotonesHistorial.setOpaque(false);
        pnlBotonesHistorial.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        btnRecargarHistorial.setBackground(new java.awt.Color(42, 48, 88));
        btnRecargarHistorial.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRecargarHistorial.setForeground(new java.awt.Color(236, 239, 244));
        btnRecargarHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/recargar.png"))); // NOI18N
        btnRecargarHistorial.setText("Recargar archivo");
        btnRecargarHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecargarHistorial.setFocusPainted(false);
        btnRecargarHistorial.setIconTextGap(8);
        btnRecargarHistorial.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnRecargarHistorial.setToolTipText("Vuelve a leer historial_combate.txt");
        pnlBotonesHistorial.add(btnRecargarHistorial);

        btnVaciarHistorial.setBackground(new java.awt.Color(42, 48, 88));
        btnVaciarHistorial.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVaciarHistorial.setForeground(new java.awt.Color(236, 239, 244));
        btnVaciarHistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/vaciar.png"))); // NOI18N
        btnVaciarHistorial.setText("Vaciar historial");
        btnVaciarHistorial.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVaciarHistorial.setFocusPainted(false);
        btnVaciarHistorial.setIconTextGap(8);
        btnVaciarHistorial.setMargin(new java.awt.Insets(8, 16, 8, 16));
        btnVaciarHistorial.setToolTipText("Borra todos los registros del archivo");
        pnlBotonesHistorial.add(btnVaciarHistorial);

        pnlBarraHistorial.add(pnlBotonesHistorial, java.awt.BorderLayout.LINE_END);

        pnlHistorial.add(pnlBarraHistorial, java.awt.BorderLayout.PAGE_START);

        scrHistorial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(60, 68, 120)));

        tblHistorial.setBackground(new java.awt.Color(32, 37, 70));
        tblHistorial.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblHistorial.setForeground(new java.awt.Color(236, 239, 244));
        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha y hora", "Turno", "Duelista", "Atacante", "Rival", "Defensor", "Enfrentamiento", "Daño", "Afectado", "Resultado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (tblHistorial.getColumnModel().getColumnCount() > 0) {
            tblHistorial.getColumnModel().getColumn(0).setPreferredWidth(140);
            tblHistorial.getColumnModel().getColumn(1).setPreferredWidth(48);
            tblHistorial.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblHistorial.getColumnModel().getColumn(3).setPreferredWidth(175);
            tblHistorial.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblHistorial.getColumnModel().getColumn(5).setPreferredWidth(175);
            tblHistorial.getColumnModel().getColumn(6).setPreferredWidth(125);
            tblHistorial.getColumnModel().getColumn(7).setPreferredWidth(55);
            tblHistorial.getColumnModel().getColumn(8).setPreferredWidth(70);
            tblHistorial.getColumnModel().getColumn(9).setPreferredWidth(300);
        }
        tblHistorial.setFillsViewportHeight(true);
        tblHistorial.setGridColor(new java.awt.Color(60, 68, 120));
        tblHistorial.setRowHeight(28);
        tblHistorial.setSelectionBackground(new java.awt.Color(90, 76, 30));
        tblHistorial.setSelectionForeground(new java.awt.Color(240, 214, 130));
        tblHistorial.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHistorial.setShowVerticalLines(false);
        tblHistorial.getTableHeader().setReorderingAllowed(false);
        scrHistorial.setViewportView(tblHistorial);

        pnlHistorial.add(scrHistorial, java.awt.BorderLayout.CENTER);

        lblResumenHistorial.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblResumenHistorial.setForeground(new java.awt.Color(165, 173, 207));
        lblResumenHistorial.setText("Sin registros.");
        pnlHistorial.add(lblResumenHistorial, java.awt.BorderLayout.PAGE_END);

        tabsPrincipal.addTab("4 · Historial", new javax.swing.ImageIcon(getClass().getResource("/co/edu/uao/yugioh/vista/iconos/historial.png")), pnlHistorial, "RF4: registro de cada ataque en el archivo de texto"); // NOI18N

        getContentPane().add(tabsPrincipal, java.awt.BorderLayout.CENTER);

        pnlEstado.setBackground(new java.awt.Color(15, 18, 38));
        pnlEstado.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(60, 68, 120)), javax.swing.BorderFactory.createEmptyBorder(7, 18, 7, 18)));
        pnlEstado.setLayout(new java.awt.BorderLayout(16, 0));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(165, 173, 207));
        lblEstado.setText("Listo. Registra a los duelistas para comenzar.");
        pnlEstado.add(lblEstado, java.awt.BorderLayout.CENTER);

        lblArchivo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblArchivo.setForeground(new java.awt.Color(165, 173, 207));
        lblArchivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblArchivo.setText("Historial: historial_combate.txt");
        pnlEstado.add(lblArchivo, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlEstado, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barLpJugador1;
    private javax.swing.JProgressBar barLpJugador2;
    private javax.swing.JButton btnAtacar;
    private javax.swing.JButton btnCambiarPosicion;
    private javax.swing.JButton btnDueloEjemplo;
    private javax.swing.JButton btnIniciarDuelo;
    private javax.swing.JButton btnInvocar;
    private javax.swing.JButton btnLimpiarMonstruo;
    private javax.swing.JButton btnRecargarHistorial;
    private javax.swing.JButton btnTerminarTurno;
    private javax.swing.JButton btnVaciarHistorial;
    private javax.swing.JComboBox<String> cmbAtacante;
    private javax.swing.JComboBox<String> cmbDueno;
    private javax.swing.JComboBox<String> cmbObjetivo;
    private javax.swing.JComboBox<String> cmbPosicion;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblArchivo;
    private javax.swing.JLabel lblAtacante;
    private javax.swing.JLabel lblAtaque;
    private javax.swing.JLabel lblAyudaDuelistas;
    private javax.swing.JLabel lblAyudaMonstruo;
    private javax.swing.JLabel lblCementerioJugador1;
    private javax.swing.JLabel lblCementerioJugador2;
    private javax.swing.JLabel lblDefensa;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblDueno;
    private javax.swing.JLabel lblEnTurno;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblInfoJugador1;
    private javax.swing.JLabel lblInfoJugador2;
    private javax.swing.JLabel lblJugador1;
    private javax.swing.JLabel lblJugador2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLpIniciales;
    private javax.swing.JLabel lblNombreJugador1;
    private javax.swing.JLabel lblNombreJugador2;
    private javax.swing.JLabel lblNombreMonstruo;
    private javax.swing.JLabel lblNotaDuelistas;
    private javax.swing.JLabel lblObjetivo;
    private javax.swing.JLabel lblPosicion;
    private javax.swing.JLabel lblReglas;
    private javax.swing.JLabel lblResumenHistorial;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloAcciones;
    private javax.swing.JLabel lblTituloDuelistas;
    private javax.swing.JLabel lblTituloHistorial;
    private javax.swing.JLabel lblTituloListaMonstruos;
    private javax.swing.JLabel lblTituloMonstruo;
    private javax.swing.JLabel lblTituloResultado;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlArena;
    private javax.swing.JPanel pnlBarraHistorial;
    private javax.swing.JPanel pnlBatalla;
    private javax.swing.JPanel pnlBotonesDuelistas;
    private javax.swing.JPanel pnlBotonesHistorial;
    private javax.swing.JPanel pnlBotonesMonstruo;
    private javax.swing.JPanel pnlBotonesTurno;
    private javax.swing.JPanel pnlDuelistas;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlEstado;
    private javax.swing.JPanel pnlFormMonstruo;
    private javax.swing.JPanel pnlHistorial;
    private javax.swing.JPanel pnlInfoJugador1;
    private javax.swing.JPanel pnlInfoJugador2;
    private javax.swing.JPanel pnlJugador1;
    private javax.swing.JPanel pnlJugador2;
    private javax.swing.JPanel pnlListaMonstruos;
    private javax.swing.JPanel pnlMonstruos;
    private javax.swing.JPanel pnlTarjetaDuelistas;
    private javax.swing.JPanel pnlTitulos;
    private javax.swing.JScrollPane scrCampoJugador1;
    private javax.swing.JScrollPane scrCampoJugador2;
    private javax.swing.JScrollPane scrHistorial;
    private javax.swing.JScrollPane scrMonstruos;
    private javax.swing.JScrollPane scrResultado;
    private javax.swing.JTabbedPane tabsPrincipal;
    private javax.swing.JTable tblCampoJugador1;
    private javax.swing.JTable tblCampoJugador2;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTable tblMonstruos;
    private javax.swing.JTextField txtAtaque;
    private javax.swing.JTextField txtDefensa;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtJugador1;
    private javax.swing.JTextField txtJugador2;
    private javax.swing.JTextField txtLpIniciales;
    private javax.swing.JTextField txtNombreMonstruo;
    private javax.swing.JTextArea txtResultado;
    // End of variables declaration//GEN-END:variables
}
