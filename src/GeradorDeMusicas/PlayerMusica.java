/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorDeMusicas;
import javax.sound.midi.*;

/**
 *
 * @author Augusto
 */
public class PlayerMusica 
{
    
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
    
    public void play()
    {
         try
        {
            if(playerTerminou())
                player.setTickPosition(0); // redefine para o começo da musica
            player.start();
            
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
    
    public void pause()
    {
         try
        {
            
            player.stop();
            
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
    
    private boolean playerTerminou()
    {
        return player.getTickPosition() >= player.getTickLength();
    }

    public void reset()
    {
        try
        {
            player.setTickPosition(0);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
