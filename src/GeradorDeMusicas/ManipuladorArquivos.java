/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Leonardo
 */
public class ManipuladorArquivos 
{
    
    public void midiFileSalva(Sequence musica)
    {
        
        String nomeArquivo = null;
        ManipuladorArquivos manipulador = new ManipuladorArquivos();
        
        nomeArquivo = manipulador.geraNomeArquivo();
        
        try
        {
            File f = new File(nomeArquivo);
            MidiSystem.write(musica,1,f);
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
    
    public String preencheTextoBox(File inputTextFile)
    {
        String  textfinal = null, linha = null;
        try (BufferedReader br = new BufferedReader( new FileReader (inputTextFile))) 
            {
                //textAreaTextoEntrada.setText(null);
                while ((linha = br.readLine()) != null) 
                {
                    textfinal = textfinal + linha;
                    textfinal = textfinal + "\n";
                }
                //textAreaTextoEntrada.setCaretPosition(0);
            } 
            catch (IOException exp) 
            {
                exp.printStackTrace();

            }
        return textfinal;
    } 
        
    public String geraNomeArquivo()
    {
        String nomeArquivo = null;

        Locale locale = new Locale("pt","BR");
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat formatador = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss",locale);
        nomeArquivo = "SaidaMidi-" + formatador.format(calendar.getTime()) + ".midi";

        return nomeArquivo;
    }
    
    
}

