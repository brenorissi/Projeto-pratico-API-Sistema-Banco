/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

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
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.LineBorder;

/**
 *
 * @author breno
 */
public class InterfaceFun extends JFrame {

    private JLabel texto;
    private JButton consulta, back;
    private JPanel panelBack;

    public InterfaceFun() {
        criarJanela();
        criarComponentes();
        adicionarActionListeners();
    }

    private void criarJanela() {
        setTitle("Tela Funcionario");
        setSize(560, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        
        texto = new JLabel("CONSULTAR");
        texto.setFont(new Font("Arial", Font.BOLD, 14));
        texto.setForeground(Color.WHITE);
        texto.setBounds(220, 200, 130, 70);

        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img1);
        back.setFocusPainted(false);
        back.setBackground(new Color(204, 0, 204));
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);
        
        ImageIcon img2 = new ImageIcon(getClass().getResource("/img/Search.png"));
        consulta = new JButton(img2);
        consulta.setFocusPainted(false);
        consulta.setBackground(new Color(204, 0, 204));
        consulta.setForeground(Color.WHITE);
        consulta.setBounds(200, 145, 130, 70);

        panelBack = new JPanel();
        panelBack.setBounds(0, 0, 560, 400);
        panelBack.setBackground(Color.BLACK);

        this.add(back);
        this.add(texto);
        this.add(consulta);
        this.add(panelBack);
    }

    private void adicionarActionListeners() {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarTela();
            }

        });
        consulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaJT();
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
        consulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                consulta.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                consulta.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }
    
    private void voltarTela(){
        InterfaceF tela1 = new InterfaceF();
        tela1.setVisible(true);
        dispose();
    }
    
    private void abrirTelaJT(){
        InterfaceFunJT tela1 = new InterfaceFunJT();
        tela1.setVisible(true);
        dispose();
    }
}
