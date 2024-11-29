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
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author breno
 */
public class InterfaceC extends JFrame {
    private Conexao conexao = new Conexao("bancoconta", "root", "root");
    private JLabel texto;
    private JTextField numT;
    private JButton ent, back;
    private JPanel panelBack;
    
    public InterfaceC(){
        criarJanela();
        criarComponentes();
        adicionarActionListener();
    }
    
    private void criarJanela(){
        setTitle("Tela Cliente");
        setSize(450, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }
    
    private void criarComponentes(){
        panelBack = new JPanel();
        panelBack.setBounds(0, 0, 450, 250);
        panelBack.setBackground(Color.BLACK);
        
        texto = new JLabel("NÚMERO DA CONTA:");
        texto.setFont(new Font("Arial", Font.BOLD, 14));
        texto.setForeground(Color.WHITE);
        texto.setBounds(20, 20, 150, 85);
        
        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img1);
        back.setFocusPainted(false);
        back.setBackground(new Color(204, 0, 204));
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);
        
        numT = new JTextField();
        numT.setBounds(165, 52, 240, 20);
        numT.setFont(new Font("Arial", Font.PLAIN, 14));
        numT.setForeground(Color.black);
        numT.setBackground(Color.white);
        numT.setBorder(new LineBorder(new Color(204, 0, 204)));
        
        ent = new JButton("ENTRAR");
        ent.setFocusPainted(false);
        ent.setFont(new Font("Arial", Font.BOLD, 14));
        ent.setBackground(new Color(204, 0, 204));
        ent.setForeground(Color.WHITE);
        ent.setBounds(200, 100, 100, 35);
        
        
        this.add(ent);
        this.add(numT);
        this.add(back);
        this.add(texto);
        this.add(panelBack);
    }
    
    private void adicionarActionListener(){
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarTela();
            }
        });
        ent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrarConta();
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

        ent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ent.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ent.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }
    
    private void voltarTela(){
        InterfaceF tela = new InterfaceF();
        tela.setVisible(true);
        dispose();
    }
    
    private void entrarConta() {
    String numeroContaTexto = numT.getText();

    if (numeroContaTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, insira o número da conta.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        int numeroConta = Integer.parseInt(numeroContaTexto);
        
        java.sql.Connection con = conexao.obterConexao();
        
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT nome, tipoConta, saldo FROM Conta WHERE numeroConta = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, numeroConta);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String nomeConta = rs.getString("nome");
                    String tipoConta = rs.getString("tipoConta");
                    double saldoConta = rs.getDouble("saldo");

                    
                    InterfaceConta tela = new InterfaceConta(nomeConta, tipoConta, saldoConta);
                    tela.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } finally {
            con.close();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Número da conta inválido. Insira apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao buscar a conta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    
}
