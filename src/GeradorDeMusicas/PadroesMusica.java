package GeradorDeMusicas;

import java.util.ArrayList;
import java.util.Arrays;


public interface PadroesMusica {
    
    
    public static int TOCA_NOTA= 1;
    public static int DEFINE_INSTRUMENTO =2;
    public static int DOBRA_VOLUME = 3;
    public static int SILENCIO = 4;
    public static int AUMENTA_OITAVA = 5;
    
    public static int SELECAO_NOTA = 0;
    public static int SELECAO_INSTRUMENTO = 1;
    public static int SELECAO_SOMA_INSTRUMENTO = 2;
    public static int SELECAO_OITAVA = 3;
    public static int SELECAO_VOLUME = 4;
    public static int SELECAO_VERIFICA_ANTERIOR = 5;
    
    /*
    *       As declarações a seguir servem como lista de sensibilidade para geração de ações.
    *       Em caso de alteração da relação de caraceteres com sua respectiva ação, basta alterar nos ArrayList abaixo.
    *       Se o caractere acionar uma nota ou um instrumento, é necessário também alterar sua função no analisador.
    */
    public static ArrayList<Character> CARACTERES_INSTRUMENTO = new ArrayList<>(Arrays.asList('i','I','o','O','u','U','!','\n',';',','));
    public static ArrayList<Character> CARACTERES_NOTAS = new ArrayList<>(Arrays.asList('A','B','C','D','E','F','G'));
    public static ArrayList<Character> CARACTERES_OITAVA = new ArrayList<>(Arrays.asList('?','.'));
    public static ArrayList<Character> CARACTERES_SOMA = new ArrayList<>(Arrays.asList('0','1','2','3','4','5','6','7','8','9'));    
    public static ArrayList<Character> CARACTERES_VOLUME = new ArrayList<>(Arrays.asList(' '));
    
    public boolean LIGA_NOTA = true;
    public boolean DESLIGA_NOTA = false;
}
