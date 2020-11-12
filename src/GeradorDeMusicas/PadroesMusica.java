/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorDeMusicas;


/**
 *
 * @author Augusto
 */
public interface PadroesMusica {
    
    
    public static int TOCA_NOTA= 1;
    public static int DEFINE_INSTRUMENTO =2;
    public static int INCREMENTA_INSTRUMENTO = 3;
    public static int DOBRA_VOLUME = 4;
    public static int SILENCIO = 5;
    public static int AUMENTA_OITAVA = 6;
    
    public boolean LIGA_NOTA = true;
    public boolean DESLIGA_NOTA = false;
}
