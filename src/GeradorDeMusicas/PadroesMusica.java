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
    
    public static int NOTA_DO = 0;
    public static int NOTA_RE = 2;
    public static int NOTA_MI = 4;
    public static int NOTA_FA = 5;
    public static int NOTA_SOL = 7;
    public static int NOTA_LA = 9;
    public static int NOTA_SI = 11;
    
    public static int VOLUME_MAX = 127;
    public static int OITAVA_MAX = 9;
    public static int INSTRUMENTO_MAX = 128;
    
    public boolean LIGA_NOTA = true;
    public boolean DESLIGA_NOTA = false;
}
