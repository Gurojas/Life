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
public class Reglas {
    
    public static final int VIVO = 1;
    public static final int MUERTO = 0;
    
    private int copia[][];
    private Tablero tablero;
    private int nuevoTablero[][];
    
    public Reglas(Tablero tablero){
        this.tablero = tablero;
        
    }
    
    public void iniciarTablero(){
        this.copia = this.tablero.getTablero();
        this.nuevoTablero = new int [this.copia.length][this.copia.length];
    }
    
    public Tablero getTablero(){
        return this.tablero;
    }
    
    public int[][] getNuevoTablero(){
        return this.nuevoTablero;
    }
    
    public void aplicarReglas(int i, int j){
        
        int vecinos = this.numeroVecinos(i, j);
        
        if (this.condicionReglaNac(i,j,vecinos)){
            this.reglaNacimiento(i, j);
        }
        else if (this.condicionReglaSup(i,j,vecinos)){
            this.reglaSupervivencia(i,j);
        }
        else if (this.condicionReglaSuppob(i,j,vecinos)){
            this.reglaSuperpoblacion(i,j);
        }
        else if (this.condicionReglaAis(i,j,vecinos)){
            this.reglaAislamiento(i,j);
        }
    }
    
    
    
    private void reglaNacimiento(int i0, int j0){
        this.nuevoTablero[i0][j0] = Reglas.VIVO;
    }
    
    private void reglaSupervivencia(int i0, int j0){
        this.nuevoTablero[i0][j0] = Reglas.VIVO;
    }
    
    private void reglaSuperpoblacion(int i0, int j0){
        this.nuevoTablero[i0][j0] = Reglas.MUERTO;
    }
    
    private void reglaAislamiento(int i0, int j0){
        this.nuevoTablero[i0][j0] = Reglas.MUERTO;
    }
    
    private boolean condicionReglaNac(int i0, int j0, int vecinos){
        /* Para aplicar la regla de nacimiento se debe cumplir que:
        1. La celula en la posicion (i,j) esta muerta
        2. Si esa celula muerta tiene 3 vecinas vivas entonces nace*/
        return this.copia[i0][j0] == Reglas.MUERTO && vecinos == 3;
    }
    
    private boolean condicionReglaSup(int i0, int j0 ,int vecinos){
        /* Para que se cumpla la regla de supervivencia se debe cumplir que:
        1. La celula tiene 2 0 3 vecinas vivas
        */
        return this.copia[i0][j0] == Reglas.VIVO && (vecinos == 2 || vecinos == 3);
    }
    
    /* Para que se cumpla la regla de la superpoblacion se debe cumplir que:
    1. Una celula muere si tiene 4 o mas vecinas vivas*/
    private boolean condicionReglaSuppob(int i0, int j0 ,int vecinos){
        return this.copia[i0][j0] == Reglas.VIVO && vecinos >= 4;
    }
    /* Para que se cumpla la regla del aislamiento se debe cumplir que:
    1. Una celula muere o permanece meurta si tiene menos de 2 vecinos vivos*/
    private boolean condicionReglaAis(int i0, int j0 ,int vecinos){
        return (this.copia[i0][j0] == Reglas.VIVO || this.copia[i0][j0] == Reglas.MUERTO) && vecinos < 2;
    }
    
    public int numeroVecinos(int i0, int j0){
        int n = this.copia.length;
        int count = 0;
        if (i0 - 1 < 0){
            if (j0 - 1 < 0){
                count = this.buscarEsqSupIzq(i0, j0);
            }
            else if (j0 + 1 == n){
                count = this.buscarEsqSupDer(i0, j0);
            }         
            else if (j0 - 1 >= 0 && j0 + 1 <= n){
                count = this.buscarArriba(i0, j0);
            }
                  
        }
        else if (i0 + 1 == n){
            if (j0 - 1 < 0){
                count = this.buscarEsqInfIzq(i0, j0);
            }
            else if (j0 + 1 == n){
                count = this.buscarEsqInfDer(i0, j0);
            }
            else if (j0 + 1 <= n){
                count = this.buscarAbajo(i0, j0);
            }
        }
        else if (i0 - 1 >= 0 && j0 - 1 < 0){
            count = this.buscarIzq(i0, j0);
        }
        else if (i0 + 1 <= n && j0 + 1 == n){
            count = this.buscarDer(i0, j0);
        }
        else{
            count = this.buscarCentro(i0, j0);
        }
        
        return count;
    }
        
    private int buscarEsqSupDer(int i0, int j0){
        int count = 0;
        if (this.copia[i0][j0-1] == Reglas.VIVO){
                count++;
        }
        if (this.copia[i0+1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        }    
        return count;
    }
    
    private int buscarEsqInfDer(int i0, int j0){
        int count = 0;
        if (this.copia[i0-1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0-1] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    
    private int buscarEsqSupIzq(int i0, int j0){
        int count = 0;
        if (this.copia[i0][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    
    private int buscarEsqInfIzq(int i0, int j0){
        int count = 0;
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0+1] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    
    private int buscarArriba(int i0, int j0){
        int count = 0;
        if (this.copia[i0][j0+1] == Reglas.VIVO){
                count++;
        }
        if (this.copia[i0+1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0-1] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    
    private int buscarAbajo(int i0, int j0){
        int count = 0;
        if (this.copia[i0][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0+1] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    
    private int buscarDer(int i0, int j0){
        int count = 0;
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    private int buscarIzq(int i0, int j0){
        int count = 0;
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        }
        return count;
    }
    private int buscarCentro(int i0, int j0){
        int count = 0;
    if (this.copia[i0-1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0-1][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0][j0+1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0-1] == Reglas.VIVO){
            count++;
        }
        if (this.copia[i0+1][j0] == Reglas.VIVO){
            count++;
        } 
        if (this.copia[i0+1][j0+1] == Reglas.VIVO){
            count++;
        }
        return count;
    }   
            
                 
    
}
