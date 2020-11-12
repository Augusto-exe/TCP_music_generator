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
    private int instrumentoAtual, oitavaAtual, oitavaPadrao, bpmAtual, volumeAtual, volumePadrao;
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
                case DEFINE_INSTRUMENTO:
                case INCREMENTA_INSTRUMENTO :
                case DOBRA_VOLUME:
                case SILENCIO :     
                case AUMENTA_OITAVA:
                default:
                    break;
            }
            
            this.tickAtual++;

        }

        insereNota(musicaGerada,45);
        this.tickAtual++;
        insereNota(musicaGerada,46);
        this.tickAtual++;
        insereNota(musicaGerada,47);
        this.tickAtual++;
        insereNota(musicaGerada,48);
        this.tickAtual++;
        insereNota(musicaGerada,49);
        this.tickAtual++;
        insereNota(musicaGerada,50);
        this.tickAtual++;
        insereNota(musicaGerada,51);
        
        
        /*
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
        */
        
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
   
    
    private ShortMessage geraMensagemNota(boolean ligaNota, int nota)
    {
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

    private void insereNota(Track musicaGerada, int nota)
    {
        ShortMessage mensagemLigaNota = geraMensagemNota(LIGA_NOTA,nota);
        
        System.out.println(mensagemLigaNota.getMessage());
        
        musicaGerada.add( geraEventoMIDI(mensagemLigaNota,this.tickAtual) );
        
        this.tickAtual++;
        
        ShortMessage mensagemDesligaNota = geraMensagemNota(DESLIGA_NOTA,nota);
        
        System.out.println(mensagemDesligaNota.getMessage());
        
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
    
    private void incrementaOitava(Track musicaGerada)
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
    
    private void incrementaInstrumento(Track musicaGerada)
    {
        if((this.instrumentoAtual + 1) > INSTRUMENTO_MAX )
        {
            this.instrumentoAtual = INSTRUMENTO_MAX;
        }
        else
        {
            this.instrumentoAtual = this.instrumentoAtual + 1;
        }
        
        ShortMessage mensagemInstrumento = geraMensagemInstrumento(this.instrumentoAtual);
        musicaGerada.add( geraEventoMIDI(mensagemInstrumento,this.tickAtual) );
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
        
        ShortMessage mensagemInstrumento = geraMensagemInstrumento(this.instrumentoAtual);
        musicaGerada.add( geraEventoMIDI(mensagemInstrumento,this.tickAtual) );
        
        
    }

}
