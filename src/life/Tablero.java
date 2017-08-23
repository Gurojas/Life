/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

/**
 *
 * @author Usuario
 */
public class Tablero {
    
    private int tablero[][];
    private int n;
    private int dim;
    
    public Tablero(int n, int dim){
        this.n = n;
        this.tablero = new int [this.n][this.n];
        this.dim = dim;
    }

    public int getEstado(int i, int j){
        return this.tablero[i][j];
    }
    
    public void setEstado(int i, int j,int estado){
        this.tablero[i][j] = estado;
    }

    public int getN() {
        return n;
    }

    public int getDim() {
        return dim;
    }
    
    public int[][] getTablero(){
        return this.tablero;
    }
    
    public void setTablero(int tablero[][]){
        this.tablero = tablero;
    }
    
    public void mostrarTablero(){
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                System.out.print(this.tablero[i][j]+" ");
            }
            System.out.println("");
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
    
    
    
    
    
}
