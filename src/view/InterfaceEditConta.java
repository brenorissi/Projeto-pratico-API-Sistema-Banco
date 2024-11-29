/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import connection.Conexao;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author breno
 */
public class InterfaceEditConta extends JFrame {

    private Conexao conexao = new Conexao("bancoconta", "root", "root");
    private JLabel tipoC, nome, atributo;
    private JTextField textNome, textAtr;
    private JComboBox selectTC;
    private JPanel panelBack;
    private JButton editarC, back;

    private String tipoConta;
    private String nomeConta;
    private String atributoConta;
    private long idConta;

    public InterfaceEditConta(long idConta, String tipoConta, String nomeConta, String atributoConta) {
        this.idConta = idConta;
        this.tipoConta = tipoConta;
        this.nomeConta = nomeConta;
        this.atributoConta = atributoConta;
        criarJanela();
        criarComp();
        adicionarActionListeners();
    }

    private void criarJanela() {
        setTitle("Tela Funcionario");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComp() {
        tipoC = new JLabel("TIPO DE CONTA:");
        tipoC.setFont(new Font("Arial", Font.BOLD, 14));
        tipoC.setForeground(Color.WHITE);
        tipoC.setBounds(20, 25, 150, 85);

        nome = new JLabel("NOME DA CONTA:");
        nome.setFont(new Font("Arial", Font.BOLD, 14));
        nome.setForeground(Color.WHITE);
        nome.setBounds(20, 80, 150, 85);

        atributo = new JLabel("TAXA:");
        atributo.setFont(new Font("Arial", Font.BOLD, 14));
        atributo.setForeground(Color.WHITE);
        atributo.setBounds(20, 150, 150, 85);

        selectTC = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança", "Conta Especial"});
        selectTC.setBounds(140, 55, 250, 20);
        selectTC.setForeground(Color.black);
        selectTC.setBackground(Color.white);
        selectTC.setFocusable(false);
        selectTC.setFont(new Font("Arial", Font.PLAIN, 14));
        selectTC.setBorder(new LineBorder(new Color(204, 0, 204)));

        textNome = new JTextField();
        textNome.setBounds(150, 110, 240, 20);
        textNome.setFont(new Font("Arial", Font.PLAIN, 14));
        textNome.setForeground(Color.black);
        textNome.setBackground(Color.white);
        textNome.setBorder(new LineBorder(new Color(204, 0, 204)));

        textAtr = new JTextField();
        textAtr.setBounds(95, 180, 293, 20);
        textAtr.setFont(new Font("Arial", Font.PLAIN, 14));
        textAtr.setForeground(Color.black);
        textAtr.setBackground(Color.white);
        textAtr.setBorder(new LineBorder(new Color(204, 0, 204)));

        panelBack = new JPanel();
        panelBack.setBounds(0, 0, 450, 400);
        panelBack.setBackground(Color.BLACK);

        editarC = new JButton("EDITAR CONTA");
        editarC.setFocusPainted(false);
        editarC.setBackground(new Color(204, 0, 204));
        editarC.setForeground(Color.WHITE);
        editarC.setBounds(155, 300, 160, 35);

        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img1);
        back.setFocusPainted(false);
        back.setBackground(new Color(204, 0, 204));
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);

        this.add(back);
        this.add(editarC);
        this.add(tipoC);
        this.add(selectTC);
        this.add(textNome);
        this.add(nome);
        this.add(atributo);
        this.add(textAtr);
        this.add(panelBack);
    }

    private void adicionarActionListeners() {
        selectTC.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                attAtributo();
                textFieldSize();
            }
        });

        editarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarConta();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarTela();
            }
        });

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                back.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                back.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });

        editarC.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editarC.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editarC.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }

    private void attAtributo() {
        int index = selectTC.getSelectedIndex();
        switch (index) {
            case 0:
                atributo.setText("TAXA:");
                break;
            case 1:
                atributo.setText("RENDIMENTO:");
                break;
            case 2:
                atributo.setText("LIMITE:");
                break;
            default:
                atributo.setText("TAXA:");
                break;
        }
    }

    private void textFieldSize() {
        int index = selectTC.getSelectedIndex();
        switch (index) {
            case 0:
                textAtr.setBounds(95, 180, 293, 20);
                break;
            case 1:
                textAtr.setBounds(130, 180, 260, 20);
                break;
            case 2:
                textAtr.setBounds(80, 180, 310, 20);
                break;
            default:
                textAtr.setBounds(95, 180, 293, 20);
                break;
        }
    }

    private void editarConta() {
        java.sql.Connection con = conexao.obterConexao();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        nomeConta = textNome.getText();
        atributoConta = textAtr.getText();
        tipoConta = (String) selectTC.getSelectedItem();

        if (nomeConta.isEmpty() || atributoConta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idConta == -1) {
            JOptionPane.showMessageDialog(this, "Erro ao obter o ID da conta.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            String sqlConta = "UPDATE Conta SET nome = ?, tipoConta = ? WHERE id = ?";
            try (PreparedStatement pstConta = con.prepareStatement(sqlConta)) {
                pstConta.setString(1, nomeConta);
                pstConta.setString(2, tipoConta);
                pstConta.setLong(3, idConta);
                pstConta.executeUpdate();
            }

            String sqlDelete = "";
            switch (tipoConta) {
                case "Conta Corrente":
                    sqlDelete = "DELETE FROM ContaPoupanca WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }

                    sqlDelete = "DELETE FROM ContaEspecial WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }
                    break;
                case "Conta Poupança":
                    sqlDelete = "DELETE FROM ContaCorrente WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }

                    sqlDelete = "DELETE FROM ContaEspecial WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }
                    break;
                case "Conta Especial":
                    sqlDelete = "DELETE FROM ContaCorrente WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }

                    sqlDelete = "DELETE FROM ContaPoupanca WHERE id = ?";
                    try (PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {
                        pstDelete.setLong(1, idConta);
                        pstDelete.executeUpdate();
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de conta inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            String sqlInsert = "";
            switch (tipoConta) {
                case "Conta Corrente":
                    sqlInsert = "INSERT INTO ContaCorrente (id, taxa) VALUES (?, ?)";
                    try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {
                        pstInsert.setLong(1, idConta);
                        pstInsert.setDouble(2, Double.parseDouble(atributoConta));
                        pstInsert.executeUpdate();
                    }
                    break;
                case "Conta Poupança":
                    sqlInsert = "INSERT INTO ContaPoupanca (id, rendimento) VALUES (?, ?)";
                    try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {
                        pstInsert.setLong(1, idConta);
                        pstInsert.setDouble(2, Double.parseDouble(atributoConta));
                        pstInsert.executeUpdate();
                    }
                    break;
                case "Conta Especial":
                    sqlInsert = "INSERT INTO ContaEspecial (id, limite) VALUES (?, ?)";
                    try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {
                        pstInsert.setLong(1, idConta);
                        pstInsert.setDouble(2, Double.parseDouble(atributoConta));
                        pstInsert.executeUpdate();
                    }
                    break;
            }

            JOptionPane.showMessageDialog(this, "Conta atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar os dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void voltarTela() {
        InterfaceFunJT tela = new InterfaceFunJT();
        tela.setVisible(true);
        dispose();
    }
}
