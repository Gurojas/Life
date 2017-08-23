/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package life;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class Life extends Application {
    
    private Stage stage;
    private VBox root;
    private Group paneTablero;
    private GraphicsContext gc;
    private Tablero tablero;
    private Reglas reglas;
    private HBox paneBottom;
    private Button botonNext;
    private Button botonStartStop;
    private Slider velocidad;
    private final int margen = 2;
    private Reloj reloj;
    private double tic = 1;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        this.stage = primaryStage;
        
        this.root = new VBox(10);
        this.paneTablero = new Group();
        Canvas canvas = new Canvas(600,600);
        
        
        this.gc = canvas.getGraphicsContext2D();
        this.paneTablero.getChildren().add(canvas);
        
        this.root.getChildren().add(this.paneTablero);
        this.root.setAlignment(Pos.CENTER);
        this.root.setStyle("-fx-background-color:gray;");
        
        this.tablero = new Tablero(60,10);
        this.bordesTablero();
        
        ImageView imagen = new ImageView("/source/velocidad.png");
        imagen.setFitWidth(35);
        imagen.setFitHeight(35);
        
        this.paneBottom = new HBox(10);
        this.botonNext = new Button("Next");
        this.botonStartStop = new Button("Start");
        this.velocidad = new Slider();
        this.velocidad.setMin(1);
        this.velocidad.setMax(10);
        this.velocidad.setValue(1);
        this.velocidad.setShowTickLabels(true);
        this.velocidad.setBlockIncrement(1);
        
        this.paneBottom.getChildren().addAll(this.botonNext,this.botonStartStop,this.velocidad,imagen);
        this.paneBottom.setAlignment(Pos.CENTER);
        
        this.root.getChildren().add(this.paneBottom);
        
        Scene scene = new Scene(root,650,650);
        
        this.stage.setTitle("Life");
        this.stage.setResizable(false);
        this.stage.setScene(scene);
        this.stage.show();
        
        this.reglas = new Reglas(this.tablero);
        Timer timer = new Timer();
        this.reloj = new Reloj(1,this.reglas,timer);
        
        EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                double xMouse = event.getX();
                double yMouse = event.getY();
                Life.this.pintar(xMouse,yMouse);
            }
        };
        
        
        
        canvas.setOnMouseClicked(mouseEvent);
        
        this.botonStartStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String name = Life.this.botonStartStop.getText();
                if (name.equals("Start")){
                    Life.this.reloj.start();
                    Life.this.botonStartStop.setText("Stop");
                    canvas.setOnMouseClicked(null);
                    
                }
                else{
                    Life.this.reloj.stop();
                    Life.this.botonStartStop.setText("Start");
                    canvas.setOnMouseClicked(mouseEvent);
                }
                
            }
        });
        
        this.botonNext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                reglas.iniciarTablero();
                for (int i = 0; i < tablero.getN(); i++) {
                    for (int j = 0; j < tablero.getN(); j++) {
                        reglas.aplicarReglas(i, j);
                    }
                }
                tablero.setTablero(reglas.getNuevoTablero());
                Life.this.repintar();
            }
            
        });
        
        this.velocidad.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Life.this.reloj.setTic((double)newValue);
            }
        });

         
        
    }
    
    private void bordesTablero(){
        
        int n = this.tablero.getN();
        int dim = this.tablero.getDim();
        
        
        for (int i = 0; i <= n*dim; i += dim) {
            for (int j = 0; j <= n*dim; j += dim) {
                this.gc.setFill(Color.DARKGRAY);
                this.gc.fillRect(j+this.margen, i+this.margen, dim-this.margen, dim-this.margen);
            }
        }
        
        

    }
    
    private void repintar(){
        int dim = this.tablero.getDim();
        int n = this.tablero.getN();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = j*dim;
                int y = i*dim;
                if (this.tablero.getEstado(i, j) == Reglas.VIVO){
                    this.gc.setFill(Color.YELLOW);
                    this.gc.fillRect(x+this.margen,y+this.margen, dim-this.margen, dim-this.margen);
                }
                if (this.tablero.getEstado(i, j) == Reglas.MUERTO){
                    this.gc.setFill(Color.DARKGRAY);
                    this.gc.fillRect(x+this.margen,y+this.margen, dim-this.margen, dim-this.margen);
                }
            }
        }
    }
    
    private void pintar(double xMouse, double yMouse){
           int dim = this.tablero.getDim();
           int x = (int)xMouse;
           int y = (int)yMouse;
           
           int i = y/dim;
           int j = x/dim;
           x = j*dim;
           y = i*dim;
           
           if (this.tablero.getEstado(i, j) == Reglas.MUERTO){
               this.tablero.setEstado(i, j, Reglas.VIVO);
               this.gc.setFill(Color.YELLOW);
               
               this.gc.fillRect(x+this.margen, y+this.margen, dim-this.margen, dim-this.margen);
           }
           
           else{
               this.tablero.setEstado(i, j, Reglas.MUERTO);
               this.gc.setFill(Color.DARKGRAY);
               this.gc.fillRect(x+this.margen, y+this.margen, dim-this.margen, dim-this.margen);
           }
           
    }
    
    private class Timer implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            Life.this.reglas.iniciarTablero();
            for (int i = 0; i < tablero.getN(); i++) {
                for (int j = 0; j < tablero.getN(); j++) {
                    Life.this.reglas.aplicarReglas(i, j);
                }
            }
            tablero.setTablero(reglas.getNuevoTablero());
            Life.this.repintar();
            
            
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
