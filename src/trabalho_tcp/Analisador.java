/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho_tcp;
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
    
    public void geraMusica(String textoEntrada)
    {
        try
        {
        Sequencer player = MidiSystem.getSequencer();
        player.open();
        Sequence sequenciaModelo = new Sequence(Sequence.PPQ, 4); // cria nova sequuencia com 12 tick por batida
        Track musicaGerada = sequenciaModelo.createTrack();
        
        ShortMessage sm = new ShortMessage( );
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, canal, Instrumentos.agogo, velocidade);
        musicaGerada.add(new MidiEvent(sm, 0));
        
        ShortMessage sm1 = new ShortMessage( );
        sm1.setMessage(ShortMessage.NOTE_ON, canal, 44, velocidade);
        musicaGerada.add(new MidiEvent(sm1, 1));
        
        ShortMessage sm2 = new ShortMessage( );
        sm2.setMessage(ShortMessage.NOTE_OFF, canal, 44, velocidade);
        musicaGerada.add(new MidiEvent(sm2, 16));
        
        player.setSequence(sequenciaModelo);
        player.start();
        
        }
        catch (Exception e) {
            System.out.println(e);
        }
 
    }
}
