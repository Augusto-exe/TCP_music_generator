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
public class PlayerMusica {
    
    private Sequencer player;
    
    public void setSequencia(Sequence sequenciaEntrada)
    {
        try
        {
            player = MidiSystem.getSequencer();
            player.open();
            player.setSequence(sequenciaEntrada);
        
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    
    }
    
    public void play(){
         try
        {
            
            player.start();
        
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
    
    public void pause(){
         try
        {
            
            player.stop();
        
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
}
