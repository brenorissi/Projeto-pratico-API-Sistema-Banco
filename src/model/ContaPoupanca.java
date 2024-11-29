/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author breno
 */
public class ContaPoupanca extends Conta {
    private double rendimento;
    
    public ContaPoupanca(){
        
    }
    public ContaPoupanca(String nome,String tipoConta, double saldo, double rendimento){
        super(nome, tipoConta, saldo);
        this.rendimento = rendimento;
    }
    
    public ContaPoupanca(String nome, int numeroConta,String tipoConta, double saldo, double rendimento){
        super(nome, numeroConta, tipoConta, saldo);
        this.rendimento = rendimento;
    }
    
    public void setRendimento(double valorR){
        this.rendimento = valorR; 
    }
    
    public double getRendimento(){
        return(getSaldo() + (getSaldo() * rendimento));
    }
    
    @Override
    public String sacar(double valorS) throws Exception{
        if(valorS <= 0){
            throw new Exception("Digite um novo valor para sacar.");
        }else if (this.getSaldo() >= valorS){
            this.setSaldo(this.getSaldo() - valorS);
            return("Saque de: " + valorS + " realizado.");
        }else{
            return ("Saldo indisponivel para saque.");
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
    
    public String getConsulta(){
        return("Nome: " + getNome() + "\n"
                +"Tipo de conta: Conta Poupança" + "\n"
                + "Número da conta: " + getNumConta() + "\n"
                + "Saldo atual: " + getSaldo()+ "\n"
                + "Rendimento da conta: " + getRendimento());
    }
    
}
