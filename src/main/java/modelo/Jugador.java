package modelo;

public class Jugador {
    private String nombre;
    private TipoImagen tipoImagen;
    private int tablero[][];
    
    public Jugador(){
        tablero = new int[3][3];
        limpiar();
    }
    public Jugador(TipoImagen tipoImagen){
        this.tipoImagen = tipoImagen;
        tablero = new int[3][3];
        limpiar();
    }
    public Jugador(String nombre, TipoImagen tipoImagen){
        this.nombre = nombre;
        this.tipoImagen = tipoImagen;
        tablero = new int[3][3];
        limpiar();
    }
    public void limpiar(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tablero[i][j] = 0;
            }
        }
    }
    public TipoImagen TresEnRaya(Jugador jugadorRival){
        if(tablero[0][0]==1&&tablero[0][1]==1&&tablero[0][2]==1) return tipoImagen.LINEA1;
        if(tablero[1][0]==1&&tablero[1][1]==1&&tablero[1][2]==1) return tipoImagen.LINEA2;
        if(tablero[2][0]==1&&tablero[2][1]==1&&tablero[2][2]==1) return tipoImagen.LINEA3;
        if(tablero[0][0]==1&&tablero[1][0]==1&&tablero[2][0]==1) return tipoImagen.LINEA4;
        if(tablero[0][1]==1&&tablero[1][1]==1&&tablero[2][1]==1) return tipoImagen.LINEA5;
        if(tablero[0][2]==1&&tablero[1][2]==1&&tablero[2][2]==1) return tipoImagen.LINEA6;
        if(tablero[2][0]==1&&tablero[1][1]==1&&tablero[0][2]==1) return tipoImagen.LINEA7;
        if(tablero[0][0]==1&&tablero[1][1]==1&&tablero[2][2]==1) return tipoImagen.LINEA8;
        
        int contador = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(tablero[i][j] == 1) contador++;
                if(jugadorRival.getTablero()[i][j] == 1) contador++;
            }
        }
        
        if(contador == 9) return TipoImagen.EMPATE;
        
        return null;
    }

    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoImagen getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(TipoImagen tipoImagen) {
        this.tipoImagen = tipoImagen;
    }
    
    
}
