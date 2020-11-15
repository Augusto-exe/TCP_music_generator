package GeradorDeMusicas;

import javax.sound.midi.*;


/*
*   Classe utilizada para implementar as funções de player de música.
*   O Sequencer (Sequenciador) player armazena uma sequência e pode reproduzi-lá.
*/    
public class PlayerMusica 
{
    
    private Sequencer player;
    
    /*
    *   Define a sequência do Sequenciador "player" como a sequência de entrada.
    *   Essa sequência contém as informações da música e pode ser reproduzida pelo Sequenciador.
    */    
    public void setSequencia(Sequence sequenciaEntrada)
    {
        try
        {
            // inicia o sequenciador.
            player = MidiSystem.getSequencer(); 
            player.open();
            //define a sequencia a partir da entrada.
            player.setSequence(sequenciaEntrada);
        
        }
        catch(InvalidMidiDataException | MidiUnavailableException e)
        {
           System.out.println(e);
        }
    
    }
    
    /*
    *   Inicia a reprodução da música a partir do ultimo ponto em que foi parada.
    *   Caso a música tenha terminado volta para o começa e inicia a reprodução.
    */  
    public void play()
    {

            if(playerTerminou())
               reset(); // redefine para o começo da musica
            player.start();
            
    }
    
    /*
    *   Pausa a reprodução no momento atual.
    */  
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
    
    /*
    *   Verifica se o player terminou de reproduzir a música.
    */ 
    private boolean playerTerminou()
    {
        return player.getTickPosition() >= player.getTickLength();
    }

    /*
    *   Restabelece a sequencia para o começo da música.
    */ 
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
    
    /*
    *   Para a reprodução e restabelece a sequencia para o começo.
    */ 
    public void cancela()
    {
        reset();
        pause();
    }
}
