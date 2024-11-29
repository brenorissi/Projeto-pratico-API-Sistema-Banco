/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author breno
 */
public class ContaCorrente extends Conta {
    private double taxa;
    
    public ContaCorrente(){
        
    }
    public ContaCorrente(String nome, String tipoConta, double saldo, double taxa){
        super(nome, tipoConta, saldo);
        this.taxa = taxa;
    }
    
    public ContaCorrente(String nome, int numeroConta,String tipoConta, double saldo, double taxa){
        super(nome, numeroConta, tipoConta, saldo);
        this.taxa = taxa;
    }
    
    @Override
    public String sacar(double valorS) throws Exception {
        if(valorS <= 0){
            throw new Exception("Digite um novo valor para sacar.");
        }else if(valorS > this.getSaldo()){
            throw new Exception("Saldo insuficiente pra realizar o saque.");
        }else {
            double valorComTaxa = valorS * getTaxa();
            setSaldo(getSaldo() - (valorS + valorComTaxa));
            return("Saque realizado!");
        }
    }
    
    @Override
    public String depositar(double valorD) throws Exception {
         if(valorD <= 0){
            throw new Exception("Digite um novo valor para depositar.");
         }else{
            valorD = this.getSaldo() + valorD;
            this.setSaldo(valorD); 
            return("Deposito realizado com sucesso!");
         }
        
    }
    
    public void setTaxa(double valorT){
        this.taxa = valorT;
    }
    
    public double getTaxa(){
        return this.taxa;
    }
    
    public String getConsulta(){
        return("Nome: " + getNome() + "\n"
                +"Tipo de conta: Conta Corrente" + "\n"
                + "Número da conta: " + getNumConta() + "\n"
                + "Saldo atual: " + getSaldo() + "\n"
                + "Taxa da conta: " + getTaxa());
    }
}
