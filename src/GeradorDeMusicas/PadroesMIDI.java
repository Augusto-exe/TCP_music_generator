/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorDeMusicas;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Augusto
 */
public class PadroesMIDI {
    private final static int canal =1,velocidade =100;
    
    public static int AGOGO= 114;
    public static int CRAVO = 7;
    public static int SINOS = 15;
    public static int FLAUTA_PAN = 76;
    public static int ORGAO_DE_TUBO = 20;
    
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
    
    public ShortMessage geraMensagemNota(boolean ligaNota, int nota,int oitava)
    {
        nota = nota + 12* oitava;
        
        ShortMessage mensagemMIDI = new ShortMessage( );
        try
        {
            if(ligaNota)
                mensagemMIDI.setMessage(ShortMessage.NOTE_ON, canal, nota, velocidade);
            else
                mensagemMIDI.setMessage(ShortMessage.NOTE_OFF, canal, nota, velocidade);
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return mensagemMIDI;
    }
    
    public ShortMessage geraMensagemInstrumento(int instrumento)
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
    
    public ShortMessage geraMensagemVolume(int volume)
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
    
    public MidiEvent geraEventoMIDI(ShortMessage mensagem, long tick)
    {
        MidiEvent eventoMIDI =new MidiEvent(mensagem, tick);
        
        return eventoMIDI;
    }
    
    
    
    //Esse trecho de código foi pego pronto, é utilizado para gerar um evento MIDI de alteração do BPM da música
    public MidiEvent geraEventoBPM(long bpm,long tick) {
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
    
}
