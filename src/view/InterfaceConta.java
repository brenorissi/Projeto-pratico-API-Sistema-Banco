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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author breno
 */
public class InterfaceConta extends JFrame {

    private Conexao conexao = new Conexao("bancoconta", "root", "root");
    private JLabel foto, nome, saldo, tipoC;
    private JButton back, deposito, saque;
    private JPanel panelBack1, panelBack2;

    private String nomeC, tipoConta;
    private double saldoC;

    public InterfaceConta(String nomeC, String tipoConta, double saldoC) {
        this.nomeC = nomeC;
        this.tipoConta = tipoConta;
        this.saldoC = saldoC;
        criarJanela();
        criarComponentes();
        adicionarActionListenes();
    }

    private void criarJanela() {
        setTitle("Tela Cliente");
        setSize(475, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        panelBack1 = new JPanel();
        panelBack1.setBounds(0, 150, 475, 720);
        panelBack1.setBackground(Color.BLACK);

        panelBack2 = new JPanel();
        panelBack2.setBounds(0, 0, 475, 720);
        panelBack2.setBackground(new Color(204, 0, 204));

        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/User.png"));
        foto = new JLabel(img1);
        foto.setBounds(10, 20, 100, 100);

        nome = new JLabel("Olá, " + nomeC);
        nome.setFont(new Font("Arial", Font.BOLD, 14));
        nome.setForeground(Color.WHITE);
        nome.setBounds(40, 80, 150, 85);

        saldo = new JLabel("R$ " + saldoC);
        saldo.setFont(new Font("Arial", Font.BOLD, 20));
        saldo.setForeground(Color.WHITE);
        saldo.setBounds(340, 30, 200, 85);

        tipoC = new JLabel(tipoConta);
        tipoC.setFont(new Font("Arial", Font.BOLD, 14));
        tipoC.setForeground(Color.WHITE);
        tipoC.setBounds(340, 75, 150, 85);

        deposito = new JButton("DEPOSITO");
        deposito.setFocusPainted(false);
        deposito.setFont(new Font("Arial", Font.BOLD, 14));
        deposito.setBackground(new Color(204, 0, 204));
        deposito.setForeground(Color.WHITE);
        deposito.setBounds(50, 200, 150, 50);

        saque = new JButton("SAQUE");
        saque.setFocusPainted(false);
        saque.setFont(new Font("Arial", Font.BOLD, 14));
        saque.setBackground(new Color(204, 0, 204));
        saque.setForeground(Color.WHITE);
        saque.setBounds(250, 200, 150, 50);

        ImageIcon img2 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img2);
        back.setFocusPainted(false);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);

        this.add(deposito);
        this.add(saque);
        this.add(back);
        this.add(tipoC);
        this.add(saldo);
        this.add(nome);
        this.add(foto);
        this.add(panelBack1);
        this.add(panelBack2);
    }

    private void adicionarActionListenes() {
        deposito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositar();
            }
        });

        saque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacar();
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

        deposito.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deposito.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deposito.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });

        saque.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saque.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saque.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }

    private void voltarTela() {
        InterfaceC tela = new InterfaceC();
        tela.setVisible(true);
        dispose();
    }

    private void depositar() {
        String valorDepositoStr = JOptionPane.showInputDialog(this, "Digite o valor para depositar:", "Depósito", JOptionPane.PLAIN_MESSAGE);

        if (valorDepositoStr == null || valorDepositoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.", "Depósito", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            double valorDeposito = Double.parseDouble(valorDepositoStr);

            if (valorDeposito <= 0) {
                throw new Exception("O valor do depósito deve ser maior que zero.");
            }

            saldoC += valorDeposito;

            String sql = "UPDATE Conta SET saldo = ? WHERE nome = ?";
            try (PreparedStatement pst = conexao.obterConexao().prepareStatement(sql)) {
                pst.setDouble(1, saldoC);
                pst.setString(2, nomeC);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new Exception("Nenhuma conta foi encontrada para atualização.");
                }
            }

            saldo.setText("R$ " + saldoC);

            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!", "Depósito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sacar() {
        String valorSaqueStr = JOptionPane.showInputDialog(this, "Digite o valor para sacar:", "Saque", JOptionPane.PLAIN_MESSAGE);

        if (valorSaqueStr == null || valorSaqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.", "Saque", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            double valorSaque = Double.parseDouble(valorSaqueStr);

            if (valorSaque <= 0) {
                throw new Exception("O valor do saque deve ser maior que zero.");
            }

            if (tipoConta.equals("Conta Corrente")) {
                double taxa = 0.05;
                double valorComTaxa = valorSaque * taxa;
                if (valorSaque + valorComTaxa > saldoC) {
                    throw new Exception("Saldo insuficiente para realizar o saque.");
                }
                saldoC -= (valorSaque + valorComTaxa);

            } else if (tipoConta.equals("Conta Especial")) {
                double limite = 500;
                if (valorSaque > (saldoC + limite)) {
                    throw new Exception("Limite de saque insuficiente.");
                }
                saldoC -= valorSaque;

            } else{
                saldoC -= valorSaque;
            }

            String sql = "UPDATE Conta SET saldo = ? WHERE nome = ?";
            try (PreparedStatement pst = conexao.obterConexao().prepareStatement(sql)) {
                pst.setDouble(1, saldoC);
                pst.setString(2, nomeC);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new Exception("Nenhuma conta foi encontrada para atualização.");
                }
            }

            saldo.setText("R$ " + saldoC);

            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!", "Saque", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
