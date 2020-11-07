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
public class Analisador {
    private final static int canal =0,velocidade =100;
    public Sequence sequenciaGerada;
    
    public void geraMusica(String textoEntrada)
    {
        try
        {

        sequenciaGerada = new Sequence(Sequence.PPQ, 2); // cria nova sequuencia com 2 ticks por batida
        Track musicaGerada = sequenciaGerada.createTrack();
        
        ShortMessage sm = new ShortMessage( );
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, canal, Instrumentos.agogo, velocidade);
        musicaGerada.add(new MidiEvent(sm, 0));
        
        ShortMessage sm1 = new ShortMessage( );
        sm1.setMessage(ShortMessage.NOTE_ON, canal, 45, velocidade);
        musicaGerada.add(new MidiEvent(sm1, 1));
        
        ShortMessage sm2 = new ShortMessage( );
        sm2.setMessage(ShortMessage.NOTE_OFF, canal, 45, velocidade);
        musicaGerada.add(new MidiEvent(sm2, 4));

        ShortMessage sm3 = new ShortMessage( );
        sm3.setMessage(ShortMessage.PROGRAM_CHANGE, canal, Instrumentos.orgaoDeTubo, velocidade);
        musicaGerada.add(new MidiEvent(sm3, 4));
        
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
   
    
    private ShortMessage geraMensagem()
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
    
        return mensagemMIDI;
    }
    
    private MidiEvent geraEvento(ShortMessage mensagem, int ticks)
    {
        MidiEvent eventoMIDI =new MidiEvent(mensagem, ticks);
        
        return eventoMIDI;
    }
}
