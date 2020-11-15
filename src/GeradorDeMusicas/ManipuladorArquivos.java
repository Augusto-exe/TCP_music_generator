package GeradorDeMusicas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.MidiSystem;
import java.text.SimpleDateFormat;
import javax.sound.midi.*;
import java.util.GregorianCalendar;
import java.util.Locale;


public class ManipuladorArquivos 
{
    
    public void arquivoMIDIEscrita(Sequence musica)
    {
        String nomeArquivo = this.geraNomeArquivoMIDI();
        File arquivo = new File(nomeArquivo);  
        
        try
        {
            MidiSystem.write(musica, 1, arquivo);
        }
        catch(IOException e)
        {
           System.out.println(e);
        }
    }
    
    public String preencheTextoBox(File arquivoDeEntrada)
    {
        String  textoFinal = new String() , linha;
        try (BufferedReader leitorBuffer = new BufferedReader( new FileReader (arquivoDeEntrada))) 
            {
                while ((linha = leitorBuffer.readLine()) != null) 
                {
                    textoFinal = textoFinal + linha;
                    textoFinal = textoFinal + "\n";
                }
            } 
            catch (IOException exp) 
            {
                System.out.println(exp);

            }
        return textoFinal;
    } 
        
    private String geraNomeArquivoMIDI()
    {
        String nomeArquivo = new String();

        Locale local = new Locale("pt","BR");
        GregorianCalendar calendario = new GregorianCalendar();
        SimpleDateFormat formatador = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss",local);
        nomeArquivo = "SaidaMidi-" + formatador.format(calendario.getTime()) + ".midi";

        return nomeArquivo;
    }
    
    
}

