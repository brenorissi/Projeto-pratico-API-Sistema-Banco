/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import connection.Conexao;
import java.sql.PreparedStatement;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.*;
import java.sql.ResultSet;

/**
 *
 * @author breno
 */
public class InterfaceAddConta extends JFrame {

    private Conexao conexao = new Conexao("bancoconta", "root", "root");
    private JLabel tipoC, nome, atributo;
    private JTextField textNome, textAtr;
    private JComboBox selectTC;
    private JPanel panelBack;
    private JButton criarC, back;

    public InterfaceAddConta() {
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

        criarC = new JButton("CRIAR CONTA");
        criarC.setFocusPainted(false);
        criarC.setBackground(new Color(204, 0, 204));
        criarC.setForeground(Color.WHITE);
        criarC.setBounds(155, 300, 160, 35);

        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img1);
        back.setFocusPainted(false);
        back.setBackground(new Color(204, 0, 204));
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);

        this.add(back);
        this.add(criarC);
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

        criarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarConta();
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

        criarC.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                criarC.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                criarC.setBorder(new LineBorder(new Color(204, 0, 204), 2));
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

    private void criarConta() {
        java.sql.Connection con = conexao.obterConexao();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String sqlId = "SELECT COALESCE(MAX(id), 0) + 1 AS ProximoID FROM Conta";
            String sqlNumConta = "SELECT COALESCE(MAX(numeroConta), 0) + 1 AS ProximoNumero FROM Conta";

            long proximoId = 0;
            int proximoNumeroConta = 0;

            try (Statement stmt = con.createStatement()) {
                ResultSet rsId = stmt.executeQuery(sqlId);
                if (rsId.next()) {
                    proximoId = rsId.getLong("ProximoID");
                }

                ResultSet rsNum = stmt.executeQuery(sqlNumConta);
                if (rsNum.next()) {
                    proximoNumeroConta = rsNum.getInt("ProximoNumero");
                }
            }

            String tipoConta = (String) selectTC.getSelectedItem();
            String nomeCliente = textNome.getText().trim();
            double atributoEspecifico = Double.parseDouble(textAtr.getText().trim());

            if (nomeCliente.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do cliente não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sqlInsertConta = "INSERT INTO Conta (id, nome, tipoConta, numeroConta, saldo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psConta = con.prepareStatement(sqlInsertConta)) {
                psConta.setLong(1, proximoId);
                psConta.setString(2, nomeCliente);
                psConta.setString(3, tipoConta);
                psConta.setInt(4, proximoNumeroConta);
                psConta.setDouble(5, 0.0);
                psConta.executeUpdate();
            }

            String sqlInsertEspecifico;
            switch (tipoConta) {
                case "Conta Corrente":
                    sqlInsertEspecifico = "INSERT INTO ContaCorrente (id, taxa) VALUES (?, ?)";
                    break;
                case "Conta Poupança":
                    sqlInsertEspecifico = "INSERT INTO ContaPoupanca (id, rendimento) VALUES (?, ?)";
                    break;
                case "Conta Especial":
                    sqlInsertEspecifico = "INSERT INTO ContaEspecial (id, limite) VALUES (?, ?)";
                    break;
                default:
                    throw new IllegalStateException("Tipo de conta inválido: " + tipoConta);
            }

            try (PreparedStatement psEspecifico = con.prepareStatement(sqlInsertEspecifico)) {
                psEspecifico.setLong(1, proximoId);
                psEspecifico.setDouble(2, atributoEspecifico);
                psEspecifico.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Conta criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            textNome.setText("");
            textAtr.setText("");
            selectTC.setSelectedIndex(0);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao criar a conta.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido no campo de atributo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void voltarTela(){
        InterfaceFunJT tela = new InterfaceFunJT();
        tela.setVisible(true);
        dispose();
    }
}
