/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import connection.Conexao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.border.LineBorder;

/**
 *
 * @author breno
 */
public class InterfaceFunJT extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JPanel panelBack;
    private JScrollPane scrollPane;
    private JButton adicionar, editar, deletar, back;
    private Conexao conexao = new Conexao("bancoconta", "root", "root");

    public InterfaceFunJT() {
        criarJanela();
        criarComponentes();
        carregarDados();
        adicionarActionListeners();
    }

    private void criarJanela() {
        setTitle("Tela Funcionario");
        setSize(720, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Tipo Conta", "Número Conta", "Saldo", "Atributo Específico"}, 0);
        tabela = new JTable(modeloTabela);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(25);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setDefaultEditor(Object.class, null);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));

        scrollPane = new JScrollPane(tabela);
        scrollPane.add(tabela.getTableHeader());
        scrollPane.setBounds(20, 200, 660, 170);

        ImageIcon img1 = new ImageIcon(getClass().getResource("/img/Back.png"));
        back = new JButton(img1);
        back.setFocusPainted(false);
        back.setBackground(new Color(204, 0, 204));
        back.setForeground(Color.WHITE);
        back.setBounds(5, 5, 80, 35);

        adicionar = new JButton("ADICIONAR");
        adicionar.setFont(new Font("Arial", Font.BOLD, 14));
        adicionar.setFocusPainted(false);
        adicionar.setBackground(new Color(204, 0, 204));
        adicionar.setForeground(Color.WHITE);
        adicionar.setBounds(20, 160, 150, 35);

        editar = new JButton("EDITAR");
        editar.setFont(new Font("Arial", Font.BOLD, 14));
        editar.setFocusPainted(false);
        editar.setBackground(new Color(204, 0, 204));
        editar.setForeground(Color.WHITE);
        editar.setBounds(272, 160, 150, 35);

        deletar = new JButton("DELETAR");
        deletar.setFont(new Font("Arial", Font.BOLD, 14));
        deletar.setFocusPainted(false);
        deletar.setBackground(new Color(204, 0, 204));
        deletar.setForeground(Color.WHITE);
        deletar.setBounds(530, 160, 150, 35);

        panelBack = new JPanel();
        panelBack.setBounds(0, 0, 720, 500);
        panelBack.setBackground(Color.BLACK);

        this.add(back);
        this.add(adicionar);
        this.add(editar);
        this.add(deletar);
        this.add(scrollPane);
        this.add(panelBack);
    }

    private void carregarDados() {
        java.sql.Connection con = conexao.obterConexao();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = """
        SELECT 
            c.id AS ContaID,
            c.nome AS NomeCliente,
            c.tipoConta AS TipoConta,
            c.numeroConta AS NumeroConta,
            c.saldo AS Saldo,
            COALESCE(cc.taxa, cp.rendimento, ce.limite) AS AtributoEspecifico
        FROM 
            Conta c
        LEFT JOIN 
            ContaCorrente cc ON c.id = cc.id
        LEFT JOIN 
            ContaPoupanca cp ON c.id = cp.id
        LEFT JOIN 
            ContaEspecial ce ON c.id = ce.id;
    """;

        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            modeloTabela.setRowCount(0);

            while (rs.next()) {
                Object[] linha = {
                    rs.getLong("ContaID"),
                    rs.getString("NomeCliente"),
                    rs.getString("TipoConta"),
                    rs.getInt("NumeroConta"),
                    rs.getDouble("Saldo"),
                    rs.getObject("AtributoEspecifico")
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados do banco!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarActionListeners() {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarTela();
            }
        });
        adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaAdd();
            }
        });

        deletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarConta();
            }
        });

        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma conta.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long idConta = obterIdDaConta(linhaSelecionada);
                if (idConta == -1) {
                    return;
                }

                String tipoConta = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
                String nomeConta = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
                String atributoConta = String.valueOf(modeloTabela.getValueAt(linhaSelecionada, 5));

                InterfaceEditConta telaEdicao = new InterfaceEditConta(idConta, tipoConta, nomeConta, atributoConta);
                telaEdicao.setVisible(true);
                dispose();
            }

            private long obterIdDaConta(int linhaSelecionada) {
                return (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
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
        adicionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                adicionar.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                adicionar.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
        editar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editar.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editar.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
        deletar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deletar.setBorder(new LineBorder(new Color(255, 0, 204), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deletar.setBorder(new LineBorder(new Color(204, 0, 204), 2));
            }
        });
    }

    private void voltarTela() {
        InterfaceFun tela = new InterfaceFun();
        tela.setVisible(true);
        dispose();
    }

    private void abrirTelaAdd() {
        InterfaceAddConta tela = new InterfaceAddConta();
        tela.setVisible(true);
        dispose();
    }

    private void deletarConta() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma conta para deletar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long idConta = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);

        java.sql.Connection con = conexao.obterConexao();
        if (con == null) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            String sqlDeleteCorrente = "DELETE FROM ContaCorrente WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteCorrente)) {
                ps.setLong(1, idConta);
                ps.executeUpdate();
            }

            String sqlDeletePoupanca = "DELETE FROM ContaPoupanca WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDeletePoupanca)) {
                ps.setLong(1, idConta);
                ps.executeUpdate();
            }

            String sqlDeleteEspecial = "DELETE FROM ContaEspecial WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteEspecial)) {
                ps.setLong(1, idConta);
                ps.executeUpdate();
            }

            String sqlDeleteConta = "DELETE FROM Conta WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteConta)) {
                ps.setLong(1, idConta);
                ps.executeUpdate();
            }

            modeloTabela.removeRow(linhaSelecionada);

            JOptionPane.showMessageDialog(this, "Conta deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao deletar a conta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
