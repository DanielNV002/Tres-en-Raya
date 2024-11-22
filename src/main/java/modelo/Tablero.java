package modelo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import vista.FormJuego;
import vista.FormResultado;

public class Tablero extends JPanel{
    private int anchoCI;
    private int alturaCI;
    private int margen;
    private Color colorTablero;
    private Color colorCI;
    private TipoImagen jugadorActual;
    private TipoImagen turnoPartida;
    
    private Jugador jugador1;
    private Jugador jugador2;
    
    private ArrayList<Cuadro> cuadros;
    private Cuadro cuadroFrontal;

    public Tablero(){
        init();
    }
    
    private void init(){
        anchoCI = 80;
        alturaCI = 80;
        colorCI = Color.BLUE;
        colorTablero = Color.RED;
        margen = 6;
        jugador1 = new Jugador();
        jugador2 = new Jugador();
        cuadros = new ArrayList();
        jugadorActual = TipoImagen.EQUIS;
        turnoPartida = TipoImagen.EQUIS;
    }
    
    public void crearTablero(){
        setLayout(null);
        setSize(anchoCI*3 + margen*4, alturaCI*3 + margen*4);
        setBackground(colorTablero);  
        cuadroFrontal = new Cuadro(this.getWidth(),this.getHeight(),Color.RED);
        cuadroFrontal.setLocation(0,0);
        cuadroFrontal.setOpaque(false);
        cuadroFrontal.setEnabled(false);
        add(cuadroFrontal);
        
        crearCuadrosInternos();
    }
    
    private void crearCuadrosInternos(){
        int x = margen;
        int y = margen;
        
        for(int i = 0; i < 3; i++){
            x=margen;
            for(int j = 0; j < 3; j++){
                Cuadro cuadro = new Cuadro(anchoCI,alturaCI,colorCI);
                cuadro.setCursor(new Cursor(Cursor.HAND_CURSOR));
                cuadro.setLocation(x,y);
                cuadro.setI(i);
                cuadro.setJ(j);
                add(cuadro);
                cuadros.add(cuadro);
                crearEventosCuadros(cuadro);
                
                x+=(anchoCI+margen);
            }
            y+=(alturaCI+margen);
        }
    }

    public void crearEventosCuadros(Cuadro cuadro){
        MouseListener evento = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
                if(cuadro.isDibujado()) return ;
                
                TipoImagen tipoImagenResultado = null;
                if(jugadorActual == TipoImagen.EQUIS){
                    cuadro.setTipoImagen(TipoImagen.EQUIS);
                    jugador1.getTablero()[cuadro.coordI()][cuadro.coordJ()] = 1;
                    tipoImagenResultado = jugador1.TresEnRaya(jugador2);
                    resultado(tipoImagenResultado,TipoImagen.EQUIS);
                    jugadorActual = TipoImagen.CIRCULO;
                    CambiarEstilos(TipoImagen.CIRCULO);
                }else if(jugadorActual == TipoImagen.CIRCULO){
                    cuadro.setTipoImagen(TipoImagen.CIRCULO);
                    jugador2.getTablero()[cuadro.coordI()][cuadro.coordJ()] = 1;
                    tipoImagenResultado = jugador2.TresEnRaya(jugador1);
                    resultado(tipoImagenResultado,TipoImagen.CIRCULO);
                    jugadorActual = TipoImagen.EQUIS;
                    CambiarEstilos(TipoImagen.EQUIS);
                }
                cuadro.setDibujado(true);
                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        };
        cuadro.addMouseListener(evento);
        
    }
    public void CambiarEstilos(TipoImagen jugadorAct){
        if(jugadorAct == TipoImagen.CIRCULO){
            FormJuego.imgJugadorEquis.setRuta(Ruta.JUGADORAUXILIAR);
            FormJuego.imgJugadorEquis.repaint();
            FormJuego.nombreJugadorEquis.setForeground(new Color(240,240,240,100));
            
            FormJuego.imgJugadorCirculo.setRuta(Ruta.JUGADORCIRCULO);
            FormJuego.imgJugadorCirculo.repaint();
            FormJuego.nombreJugadorCirculo.setForeground(new Color(0,255,0));
        }else if(jugadorAct == TipoImagen.EQUIS){
            FormJuego.imgJugadorCirculo.setRuta(Ruta.JUGADORAUXILIAR);
            FormJuego.imgJugadorCirculo.repaint();
            FormJuego.nombreJugadorCirculo.setForeground(new Color(240,240,240,100));
            
            FormJuego.imgJugadorEquis.setRuta(Ruta.JUGADOREQUIS);
            FormJuego.imgJugadorEquis.repaint();
            FormJuego.nombreJugadorEquis.setForeground(new Color(255,0,0));
        }
    }
    public void resultado(TipoImagen tipoImagenResultado, TipoImagen jugadorGanador){
        if(tipoImagenResultado == TipoImagen.EMPATE){
            System.out.println("Empate");
            
            
            Tablero tablero = this;
            Timer timer = new Timer();
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    FormResultado formResultado = new FormResultado(TipoImagen.EMPATE, tablero);
                    formResultado.setVisible(true);
                }
            };
            timer.schedule(tarea, 800);
        }
        else if(tipoImagenResultado != null){
            System.out.println("Hay un ganador");
            Ruta.cambiarRutas(jugadorGanador);
            cuadroFrontal.setTipoImagen(tipoImagenResultado);
            desactivarCuadros(true);
            
            Tablero tablero = this;
            Timer timer = new Timer();
            TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    FormResultado formResultado = new FormResultado(jugadorGanador, tablero);
                    formResultado.setVisible(true);
                }
            };
            timer.schedule(tarea, 800);
        }
    }
    
    public void reinciarTablero(TipoImagen ganador){
        desactivarCuadros(false);
        borrarImagenes();
        cuadroFrontal.setTipoImagen(null);
        if(ganador == TipoImagen.EQUIS){
            int puntuajeNuevo = Integer.parseInt(FormJuego.puntajeEquis.getText())+1;
            FormJuego.puntajeEquis.setText(String.valueOf(puntuajeNuevo));
        }
        else if(ganador == TipoImagen.CIRCULO){
            int puntuajeNuevo = Integer.parseInt(FormJuego.puntajeCirculo.getText())+1;
            FormJuego.puntajeCirculo.setText(String.valueOf(puntuajeNuevo));
        }
        
        if(turnoPartida == TipoImagen.EQUIS){
            jugadorActual = TipoImagen.CIRCULO;
            turnoPartida = TipoImagen.CIRCULO;
        }
        else if(turnoPartida == TipoImagen.CIRCULO){
            jugadorActual = TipoImagen.EQUIS;
            turnoPartida = TipoImagen.EQUIS;
        }
        CambiarEstilos(jugadorActual);
        jugador1.limpiar();
        jugador2.limpiar();
        repaint();
    }
    
    public void desactivarCuadros(boolean valor){
        for(Cuadro cuadro : cuadros){
            cuadro.setDibujado(valor);
        }
    }
    
    public void borrarImagenes(){
        for(Cuadro cuadro : cuadros){
            cuadro.setTipoImagen(null);
        }
    }

    public TipoImagen getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(TipoImagen jugadorActual) {
        this.jugadorActual = jugadorActual;
    }
    
    public ArrayList<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(ArrayList<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }
    
    public int getAnchoCI() {
        return anchoCI;
    }

    public void setAnchoCI(int anchoCI) {
        this.anchoCI = anchoCI;
    }

    public int getAlturaCI() {
        return alturaCI;
    }

    public void setAlturaCI(int alturaCI) {
        this.alturaCI = alturaCI;
    }

    public int getMargen() {
        return margen;
    }

    public void setMargen(int margen) {
        this.margen = margen;
    }

    public Color getColorTablero() {
        return colorTablero;
    }

    public void setColorTablero(Color colorTablero) {
        this.colorTablero = colorTablero;
    }

    public Color getColorCI() {
        return colorCI;
    }

    public void setColorCI(Color colorCI) {
        this.colorCI = colorCI;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }
    
    
}
