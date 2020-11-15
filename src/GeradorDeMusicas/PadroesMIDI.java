package GeradorDeMusicas;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
/*
*   Define Constantes e métodos necessários para geração de Eventos MIDI com inserção de Notas específicas.
*   Também define os Instrumentos Padrões e constantes auxiliares.
*/

public class PadroesMIDI {
    
    //  Padroes utilizados na geração de música.
    private final static int CANAL =1,VELOCIDADE =100;
    
    //  Constantes que definem os valores dos Instrumentos na codificação MIDI.
    public static int AGOGO= 114;
    public static int CRAVO = 7;
    public static int SINOS = 15;
    public static int FLAUTA_PAN = 76;
    public static int ORGAO_DE_TUBO = 20;
    
    //  Constantes que definem os valores das Notas na codificação MIDI.
    public static int NOTA_DO = 0;
    public static int NOTA_RE = 2;
    public static int NOTA_MI = 4;
    public static int NOTA_FA = 5;
    public static int NOTA_SOL = 7;
    public static int NOTA_LA = 9;
    public static int NOTA_SI = 11;
    
    //  Demais constantes auxiliares utilizadas nas funções.
    public static int VALOR_OITAVA = 12;
    public static int MIDI_SEL_VOLUME = 7;
    public static int VOLUME_MAX = 127;
    public static int OITAVA_MAX = 9;
    public static int INSTRUMENTO_MAX = 128;
    
    //  Constantes utilizadas para gerar mensagem de ligar e desligar nota.
    public static boolean LIGA_NOTA = true;
    public static boolean DESLIGA_NOTA = false;
    
    
    /*
    *   Função que gera uma mensagem de ligar ou desligar nota de acordo com as entradas.
    *   Calcula qual o valor que deve ser inserido na mensgaem de acordo com a nota e a oitava.
    */
    public ShortMessage geraMensagemNota(boolean ligaNota, int nota,int oitava)
    {
        //Ve qual valor de nota deve ser executado de acordo com a oitava
        //No padrao MIDI cada oitava de uma nota é separada por 12 unidades
        nota = nota + VALOR_OITAVA * oitava;
        
        ShortMessage mensagemMIDI = new ShortMessage( );
        try
        { 
            if(ligaNota)
                mensagemMIDI.setMessage(ShortMessage.NOTE_ON, CANAL, nota, VELOCIDADE);
            else
                mensagemMIDI.setMessage(ShortMessage.NOTE_OFF, CANAL, nota, VELOCIDADE);
            
        }
        catch (InvalidMidiDataException e) {
            System.out.println(e);
        }
        return mensagemMIDI;
    }
    
    /*
    *   Função que gera uma mensagem de troca de instrumento para o instrumento de Entrada.
    */
    public ShortMessage geraMensagemInstrumento(int instrumento)
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
        try
        {
            //PROGRAM_CHANGE sinaliza que será feita uma de instrumento e "instrumento" indica qual instrumento será colocado
            mensagemMIDI.setMessage(ShortMessage.PROGRAM_CHANGE, CANAL, instrumento, CANAL);
            
        }
        catch (InvalidMidiDataException e) {
            System.out.println(e);
        }
        return mensagemMIDI;
        
    }
    
    /*
    *   Função que gera uma mensagem de troca de volume para o valor de Entrada.
    */
    public ShortMessage geraMensagemVolume(int volume)
    {
        ShortMessage mensagemMIDI = new ShortMessage( );
        
        try
        {
            //CONTROL_CHANGE sinaliza que será feita uma alteração MIDI_SEL_VOLUME indica que será troca de volume
            mensagemMIDI.setMessage(ShortMessage.CONTROL_CHANGE, CANAL, MIDI_SEL_VOLUME , volume );
            
        }
        catch (InvalidMidiDataException e) {
            System.out.println(e);
        }
        return mensagemMIDI;
    }
    
    /*
    *   Função que gera um evento MIDI a partir de uma mensagem midi que comtém as ações.
    *   O evente é gerado no tick de entrada.
    */
    public MidiEvent geraEventoMIDI(ShortMessage mensagem, long tick)
    {
        MidiEvent eventoMIDI =new MidiEvent(mensagem, tick);
        
        return eventoMIDI;
    }
    
    
    
    //Esse trecho de código foi pego pronto, é utilizado para gerar um evento MIDI de alteração do BPM da música
    public MidiEvent geraEventoBPM(long bpm,long tick) {
        long mpqn = 60000000 / bpm; //60000000  é o valor de ms para que  se passe um minuto na música, esse valor pode variar de acordo com o indice de ticks por batida

        MetaMessage metaMessage = new MetaMessage();

        byte[] array = new byte[] { 0, 0, 0 }; // Cria array de bytes com valor mpqn  

        for (int i = 0; i < 3; i++) {
            int shift = (3 - 1 - i) * 8;
            array[i] = (byte) (mpqn >> shift);
        }

        try {
            metaMessage.setMessage(81, array, 3); // Cria meta mensagem de  alteração de bpm (tempo) 
        } catch (InvalidMidiDataException e) {
            System.out.println(e);
            System.exit(1);
        }

        return new MidiEvent(metaMessage, tick); // Retorna o Evento MIDI criado
    }
    
}
