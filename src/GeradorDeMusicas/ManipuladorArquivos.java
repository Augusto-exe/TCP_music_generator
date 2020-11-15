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

/*
*   Classe responsável pela manipulação de arquivos.
*   Usada para pegar informações de uma rquivo de texto e gravar Sequencia em um arquivo MIDI com nome Padronizado.
*/
public class ManipuladorArquivos 
{
    /*
    *   Salva a sequencia gerada pelo programa em um arquivo MIDI no diretório do programa.
    *   O nome é automaticamente gerado pela geraNomeArquivoMIDI().
    */
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
    
    /*
    *   Recebe o arquivo de texto que foi inserido e preenche uma váriavel para o valor de retorno.
    *   A finalidade desse método é preencher a caixa de texto da tela do programa.
    */
    public String copiaTextoArquivo(File arquivoDeEntrada)
    {
        String  textoFinal = new String() , linha;
        //Inicia a leitura de arquivo a partir de um BufferReader
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
        
    /*
    *   Método gera um nome padrão para todos os arquivos MIDI gerados. O método começa o nome com "SaidaMIDI"
    * em seguida a data e hora, assim o nome do arquivo nunca se repete. E no final a extensão do arquivo.
    */
    
    private String geraNomeArquivoMIDI()
    {
        String nomeArquivo;

        Locale local = new Locale("pt","BR");
        GregorianCalendar calendario = new GregorianCalendar();
        SimpleDateFormat formatador = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss",local);
        nomeArquivo = "SaidaMidi-" + formatador.format(calendario.getTime()) + ".midi";

        return nomeArquivo;
    }
}

