/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.LineBorder;

/**
 *
 * @author breno
 */
public class InterfaceF extends JFrame {

    private JButton buttonFun, buttonCliente;
    private JPanel panelBack;

    public InterfaceF() {
        criarJanela();
        criarComponentes();
        adicionarActionListeners();
    }

    private void criarJanela() {
        setTitle("Tela Inicial");
        setSize(560, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        
        
        buttonFun = new JButton("FUNCIONARIO");
        buttonFun.setFocusPainted(false);
        buttonFun.setBackground(new Color(204, 0, 204));
        buttonFun.setForeground(Color.WHITE);
        buttonFun.setFont(new Font("Arial", Font.BOLD, 14));
        buttonFun.setBounds(90, 140, 150, 50);
        
        buttonCliente = new JButton("CLIENTE");
        buttonCliente.setFocusPainted(false);
        buttonCliente.setBackground(new Color(204, 0, 204));
        buttonCliente.setForeground(Color.WHITE);
        buttonCliente.setFont(new Font("Arial", Font.BOLD, 14));
        buttonCliente.setBounds(300, 140, 150, 50);
        
        panelBack = new JPanel();
        panelBack.setBounds(0, 0, 560, 400);
        panelBack.setBackground(Color.BLACK);
        
        
        this.add(buttonFun);
        this.add(buttonCliente);
        this.add(panelBack);

    }
    
    private void adicionarActionListeners(){
        buttonCliente.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCliente();
            }
            
        });
        
        buttonFun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFun();
            }

            private void abrirTelaFun() {
                InterfaceFun  tela = new InterfaceFun();
                tela.setVisible(true);
                dispose();
            }
        });
        
        buttonFun.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonFun.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonFun.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
        
        buttonCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonCliente.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonCliente.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }
    
    private void abrirTelaCliente(){
            InterfaceC tela = new InterfaceC();
            tela.setVisible(true);
            dispose();
        }

}
