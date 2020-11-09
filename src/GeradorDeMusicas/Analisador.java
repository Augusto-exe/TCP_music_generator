/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorDeMusicas;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.sound.midi.*;

/**
 *
 * @author Augusto
 */
public class Analisador implements Instrumentos , PadroesMusica //classe analisador implementa as duas interfaces de constantes
{
    private final static int canal =0,velocidade =100;
    private int instrumentoAtual, oitavaAtual, bpmAtual, volume, tickAtual = 0;
    public Sequence sequenciaGerada;
    
    public void geraMusica(String textoEntrada, int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada)
    {
        
        int tamanhoTexto = textoEntrada.length();
        char letraAtual, letraAnterior;
        
        inicializaAtributos(bpmEntrada,volumeEntrada,oitavaEntrada,instrumentoEntrada);
        try
        {
            
        sequenciaGerada = new Sequence(Sequence.PPQ, 4); // cria nova sequuencia com 4 ticks por batida
        Track musicaGerada = sequenciaGerada.createTrack();
        
        incializaMusica();
        
        for(int posTexto =0; posTexto <tamanhoTexto;posTexto++)
        {
            letraAtual  = textoEntrada.charAt(posTexto);
            if(posTexto > 0)
                letraAnterior  = textoEntrada.charAt(posTexto - 1);
            
            //GERAR EVENTO DE ACORDO COM LETRAATUAL E LETRAANTERIOR  PELO SWITCH
            

            //ADICIONAR EVENTO À TRACK
        }

        
        ShortMessage volumeMessage = new ShortMessage( );
        volumeMessage.setMessage( ShortMessage.CONTROL_CHANGE, canal, 7, (int)(0.7*127) );
        musicaGerada.add(new MidiEvent(volumeMessage, 0));
        
        musicaGerada.add(geraEventoBPM(0, 40));
        
        ShortMessage sm1 = new ShortMessage( );
        sm1.setMessage(ShortMessage.NOTE_ON, canal, 45, velocidade);
        musicaGerada.add(new MidiEvent(sm1, 1));
        
        musicaGerada.add(geraEventoBPM(5, 180));
        
        ShortMessage sm2 = new ShortMessage( );
        sm2.setMessage(ShortMessage.NOTE_OFF, canal, 45, velocidade);
        musicaGerada.add(new MidiEvent(sm2, 4));

        ShortMessage sm3 = new ShortMessage( );
        sm3.setMessage(ShortMessage.PROGRAM_CHANGE, canal, ORGAO_DE_TUBO, velocidade);
        musicaGerada.add(new MidiEvent(sm3, 4));
        
        ShortMessage volumeMessage1 = new ShortMessage( );
        volumeMessage1.setMessage( ShortMessage.CONTROL_CHANGE, canal, 7, (int)(0.8*127) );
        musicaGerada.add(new MidiEvent(volumeMessage1, 5));
        
        ShortMessage sm4 = new ShortMessage( );
        sm4.setMessage(ShortMessage.NOTE_ON, canal, 36, velocidade);
        musicaGerada.add(new MidiEvent(sm4, 5));
        
        ShortMessage sm5 = new ShortMessage( );
        sm5.setMessage(ShortMessage.NOTE_OFF, canal, 36, velocidade);
        musicaGerada.add(new MidiEvent(sm5, 8));
        
        ShortMessage sm6 = new ShortMessage( );
        sm6.setMessage(ShortMessage.NOTE_ON, canal, 43, velocidade);
        musicaGerada.add(new MidiEvent(sm6, 9));
        
        ShortMessage sm7 = new ShortMessage( );
        sm7.setMessage(ShortMessage.NOTE_OFF, canal, 43, velocidade);
        musicaGerada.add(new MidiEvent(sm7, 12));
        
        
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
   
    
    private ShortMessage geraMensagemNota(boolean ligada, int Nota)
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
        try
        {
            if(ligada)
                mensagemMIDI.setMessage(ShortMessage.NOTE_ON, canal, Nota, velocidade);
            else
                mensagemMIDI.setMessage(ShortMessage.NOTE_OFF, canal, Nota, velocidade);
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return mensagemMIDI;
    }
    private ShortMessage geraMensagemInstrumento(int instrumento)
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
        try
        {
            
            mensagemMIDI.setMessage(ShortMessage.PROGRAM_CHANGE, canal, instrumento, velocidade);
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return mensagemMIDI;
        
    }
    private ShortMessage geraMensagemVolume(int volume)
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
        
        try
        {
            
            mensagemMIDI.setMessage( ShortMessage.CONTROL_CHANGE, canal, 7, volume );
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return mensagemMIDI;
    }
    
    private MidiEvent geraEventoMIDI(ShortMessage mensagem, int ticks)
    {
        MidiEvent eventoMIDI =new MidiEvent(mensagem, ticks);
        
        return eventoMIDI;
    }
    
    
    
    //Esse trecho de código foi pego pronto, é utilizado para gerar um evento MIDI de alteração do BPM da música
    private MidiEvent geraEventoBPM(long tick, long bpm) {
        // microseconds per quarternote
        long mpqn = 60000000 / bpm; //60000000  é o valor de ms para que  se passe um minuto na música, esse valor pode variar de acordo com o indice de ticks por batida

        MetaMessage metaMessage = new MetaMessage();

        // create the tempo byte array
        byte[] array = new byte[] { 0, 0, 0 }; // Cria array de bytes com valor mpqn  

        for (int i = 0; i < 3; i++) {
            int shift = (3 - 1 - i) * 8;
            array[i] = (byte) (mpqn >> shift);
        }

        // now set the message
        try {
            metaMessage.setMessage(81, array, 3); // Cria meta mensagem de  alteração de bpm (tempo) 
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new MidiEvent(metaMessage, tick); // Retorna o Evento MIDI criado
    }

    private void inicializaAtributos(int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada)
    {
        
        oitavaAtual = oitavaEntrada;
        bpmAtual = bpmEntrada;
        volume = volumeEntrada;
        instrumentoAtual = instrumentoEntrada;
        tickAtual = 0;
        
    }

    private void incializaMusica() 
    {
        
    }

}
