/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author Usuario
 */
public class Reloj {
    
    private Timeline timeLine;
    private Reglas reglas;
    private Tablero tablero;
    private double tic;
    private KeyFrame frame;
    private EventHandler<ActionEvent> timer;
    
    public Reloj(double tic, Reglas reglas, EventHandler<ActionEvent> timer){
        this.tic = tic;
        this.reglas = reglas;
        this.tablero = this.reglas.getTablero();
        this.timer = timer;
        this.frame = new KeyFrame(Duration.seconds(this.tic),timer);
        this.timeLine = new Timeline(frame);
        
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
        
    }
    
    public void setTic(double tic){
        this.timeLine.setRate(tic);
        
    }
    
    public void start(){
        this.timeLine.play();
    }
    
    public void stop(){
        this.timeLine.stop();
    }
    
    
    
}
