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
    private int instrumentoAtual, oitavaAtual, oitavaPadrao, bpmAtual, volumeAtual, volumePadrao,notaAtual;
    private long tickAtual = 0;
    public Sequence sequenciaGerada;
    
    public void geraMusica(String textoEntrada, int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada)
    {
        
        int tamanhoTexto = textoEntrada.length();
        int  tipoEvento;
        char letraAtual, letraAnterior;
        
        inicializaAtributos(bpmEntrada,volumeEntrada,oitavaEntrada,instrumentoEntrada);
        
        try
        {
            
            sequenciaGerada = new Sequence(Sequence.PPQ, 4); // cria nova sequencia com 4 ticks por batida
            Track musicaGerada = sequenciaGerada.createTrack();

            incializaMusica(musicaGerada);

            for(int posTexto =0; posTexto < tamanhoTexto; posTexto++)
            {
                letraAtual  = textoEntrada.charAt(posTexto);
                if(posTexto > 0)
                    letraAnterior  = textoEntrada.charAt(posTexto - 1);

                //ACHAR TIPO DO EVENTO DE ACORDO COM LETRA ATUAL E LETRA ANTERIOR  PELO SWITCH
                tipoEvento = TOCA_NOTA;

                //INSERE EVENTO NA TRACK DE ACORDO COM O TIPO DE EVENTO;
                switch(tipoEvento)
                {
                    case TOCA_NOTA:
                        insereNota(musicaGerada,this.notaAtual,this.oitavaAtual);
                        this.tickAtual++;
                        break;

                    case DEFINE_INSTRUMENTO:
                        defineInstrumento(musicaGerada);
                        break;

                    case INCREMENTA_INSTRUMENTO :
                        incrementaInstrumento(musicaGerada,1);
                        break;

                    case DOBRA_VOLUME:
                        dobraVolume(musicaGerada);
                        break;

                    case SILENCIO :
                        this.tickAtual++;
                        break;

                    case AUMENTA_OITAVA:
                        incrementaOitava();
                        break;

                    default:
                        break;
                }
            }
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }

    }
   
    
    private ShortMessage geraMensagemNota(boolean ligaNota, int nota,int oitava)
    {
        nota = nota + oitava;
        
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
    
    private MidiEvent geraEventoMIDI(ShortMessage mensagem, long tick)
    {
        MidiEvent eventoMIDI =new MidiEvent(mensagem, tick);
        
        return eventoMIDI;
    }
    
    
    
    //Esse trecho de código foi pego pronto, é utilizado para gerar um evento MIDI de alteração do BPM da música
    private MidiEvent geraEventoBPM(long bpm,long tick) {
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
        
        this.oitavaAtual = oitavaEntrada;
        this.oitavaPadrao = oitavaEntrada;
        this.bpmAtual = bpmEntrada;
        this.volumeAtual = volumeEntrada * VOLUME_MAX / 100;
        this.volumePadrao = volumeEntrada * VOLUME_MAX / 100;
        this.instrumentoAtual = instrumentoEntrada;
        this.tickAtual = 0;
        
    }

    private void insereNota(Track musicaGerada, int nota,int oitava)
    {
        ShortMessage mensagemLigaNota = geraMensagemNota(LIGA_NOTA,nota,oitava);
        
        musicaGerada.add( geraEventoMIDI(mensagemLigaNota,this.tickAtual) );
        
        this.tickAtual++;
        
        ShortMessage mensagemDesligaNota = geraMensagemNota(DESLIGA_NOTA,nota,oitava);
        
        musicaGerada.add( geraEventoMIDI(mensagemDesligaNota,this.tickAtual) );
    }
    
    private void dobraVolume(Track musicaGerada)
    {
        if((this.volumeAtual * 2) > VOLUME_MAX )
        {
            this.volumeAtual = this.volumePadrao;
        }
        else
        {
            this.volumeAtual = this.volumeAtual * 2;
        }
        
        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add( geraEventoMIDI(mensagemVolume,this.tickAtual) );
        
    }
    
    private void incrementaOitava()
    {
        if((this.oitavaAtual + 1) > OITAVA_MAX )
        {
            this.oitavaAtual = this.oitavaAtual;
        }
        else
        {
            this.oitavaAtual = this.oitavaAtual + 1;
        }
        
       
    }
    
    private void incrementaInstrumento(Track musicaGerada,int incremento)
    {
        if((this.instrumentoAtual + incremento) > INSTRUMENTO_MAX )
        {
            this.instrumentoAtual = INSTRUMENTO_MAX;
        }
        else
        {
            this.instrumentoAtual = this.instrumentoAtual + incremento;
        }
        
        defineInstrumento(musicaGerada);
    }
    
    private void defineInstrumento(Track musicaGerada)
    {
        
        ShortMessage mensagemInstrumento = geraMensagemInstrumento(this.instrumentoAtual);
        musicaGerada.add( geraEventoMIDI(mensagemInstrumento,this.tickAtual) );
    
    }
    
    
    private void incializaMusica(Track musicaGerada) 
    {
                     
        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add( geraEventoMIDI(mensagemVolume,this.tickAtual) );
        
        musicaGerada.add( geraEventoBPM(this.bpmAtual,this.tickAtual) );
        
        defineInstrumento(musicaGerada);
        
        
        
    }

}
