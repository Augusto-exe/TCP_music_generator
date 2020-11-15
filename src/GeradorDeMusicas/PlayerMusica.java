package GeradorDeMusicas;

import javax.sound.midi.*;


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
        catch(InvalidMidiDataException | MidiUnavailableException e)
        {
           System.out.println(e);
        }
    
    }
    
    public void play()
    {

            if(playerTerminou())
               reset(); // redefine para o começo da musica
            player.start();
            
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
    
    public void cancela()
    {
        reset();
        pause();
    }
}
