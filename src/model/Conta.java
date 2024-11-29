/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author breno
 */
public abstract class Conta{
    
    private int id;

    private String nome;
    private String tipoConta;
    int numeroConta;
    private double saldo;
    
    public Conta(){
        
    }
    public Conta(String nome,String tipoConta, double saldo){
        this.nome = nome;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        
    }
    
    
    public Conta(String nome, int numeroConta,String tipoConta, double saldo){
        this.nome = nome;
        this.tipoConta = tipoConta;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        
    }
    
    public abstract String sacar(double valorS) throws Exception;
    
    public abstract String depositar(double valorD) throws Exception;
    
    public void setNumConta(int numeroConta){
        this.numeroConta = numeroConta;
    }
        
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
    
    public int getNumConta(){
        return this.numeroConta;
    }
        
    public double getSaldo(){
        return this.saldo;
    }
    
    public String getNome(){
        return this.nome;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
