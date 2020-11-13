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
public class Analisador extends PadroesMIDI implements  PadroesMusica  //classe analisador implementa as duas interfaces de constantes
{
    
    private int instrumentoAtual, oitavaAtual, oitavaPadrao, bpmAtual, volumeAtual, volumePadrao, notaAtual;
    private long tickAtual = 0;
    public Sequence sequenciaGerada;

    public void geraMusica(String textoEntrada, int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada) {

        int tamanhoTexto = textoEntrada.length();
        int tipoEvento;
        char letraAtual, letraAnterior = 'z'; //anterior escolhida como z para caso não exista, não executar nada

        inicializaAtributos(bpmEntrada, volumeEntrada, oitavaEntrada, instrumentoEntrada);

        try {

            sequenciaGerada = new Sequence(Sequence.PPQ, 4); // cria nova sequencia com 4 ticks por batida
            Track musicaGerada = sequenciaGerada.createTrack();

            incializaMusica(musicaGerada);

            for (int posTexto = 0; posTexto < tamanhoTexto; posTexto++) {
                letraAtual = textoEntrada.charAt(posTexto);
                if (posTexto > 0) {
                    letraAnterior = textoEntrada.charAt(posTexto - 1);
                }

                //ACHAR TIPO DO EVENTO DE ACORDO COM LETRA ATUAL E LETRA ANTERIOR  PELO SWITCH
                tipoEvento = leCaractere(letraAtual, letraAnterior);

                //INSERE EVENTO NA TRACK DE ACORDO COM O TIPO DE EVENTO;
                switch (tipoEvento) {
                    case TOCA_NOTA:
                        insereNota(musicaGerada, this.notaAtual, this.oitavaAtual);
                        this.tickAtual++;
                        break;

                    case DEFINE_INSTRUMENTO:
                        defineInstrumento(musicaGerada);
                        break;

                    case INCREMENTA_INSTRUMENTO:
                        incrementaInstrumento(musicaGerada, 1);
                        break;

                    case DOBRA_VOLUME:
                        dobraVolume(musicaGerada);
                        break;

                    case SILENCIO:
                        this.tickAtual++;
                        break;

                    case AUMENTA_OITAVA:
                        incrementaOitava();
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void inicializaAtributos(int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada) {

        this.oitavaAtual = oitavaEntrada;
        this.oitavaPadrao = oitavaEntrada;
        this.bpmAtual = bpmEntrada;
        this.volumeAtual = volumeEntrada * VOLUME_MAX / 100;
        this.volumePadrao = volumeEntrada * VOLUME_MAX / 100;
        this.instrumentoAtual = instrumentoEntrada;
        this.instrumentoPadrao = instrumentoEntrada;
        this.tickAtual = 0;

    }

    private void insereNota(Track musicaGerada, int nota, int oitava) {
        ShortMessage mensagemLigaNota = geraMensagemNota(LIGA_NOTA, nota, oitava);

        musicaGerada.add(geraEventoMIDI(mensagemLigaNota, this.tickAtual));

        this.tickAtual++;

        ShortMessage mensagemDesligaNota = geraMensagemNota(DESLIGA_NOTA, nota, oitava);

        musicaGerada.add(geraEventoMIDI(mensagemDesligaNota, this.tickAtual));
    }

    private void dobraVolume(Track musicaGerada) {
        if ((this.volumeAtual * 2) > VOLUME_MAX) {
            this.volumeAtual = this.volumePadrao;
        } else {
            this.volumeAtual = this.volumeAtual * 2;
        }

        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add(geraEventoMIDI(mensagemVolume, this.tickAtual));

    }

    private void incrementaOitava() {
        if ((this.oitavaAtual + 1) > OITAVA_MAX) {
            this.oitavaAtual = this.oitavaAtual;
        } else {
            this.oitavaAtual = this.oitavaAtual + 1;
        }

    }

    private void incrementaInstrumento(Track musicaGerada, int incremento) {
        if ((this.instrumentoAtual + incremento) > INSTRUMENTO_MAX) {
            this.instrumentoAtual = INSTRUMENTO_MAX;
        } else {
            this.instrumentoAtual = this.instrumentoAtual + incremento;
        }

        defineInstrumento(musicaGerada);
    }

    private void defineInstrumento(Track musicaGerada) {

        ShortMessage mensagemInstrumento = geraMensagemInstrumento(this.instrumentoAtual);
        musicaGerada.add(geraEventoMIDI(mensagemInstrumento, this.tickAtual));

    }

    private void incializaMusica(Track musicaGerada) {

        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add(geraEventoMIDI(mensagemVolume, this.tickAtual));

        musicaGerada.add(geraEventoBPM(this.bpmAtual, this.tickAtual));

        defineInstrumento(musicaGerada);
        
        
        
    }   


    public int leCaractere(char letraAtual, char letraAnterior) {

        int instrucao = 0;

        switch (seletorAcao(letraAtual)) {
            case SELECAO_NOTA:
                instrucao = seletorNota(letraAtual);
                break;

            case SELECAO_INSTRUMENTO:
                instrucao = seletorInstrumento(letraAtual);
                break;

            case SELECAO_SOMA_INSTRUMENTO:
                instrucao = somaInstrumento(letraAtual);
                break;

            case SELECAO_OITAVA:
                instrucao = AUMENTA_OITAVA;
                break;

            case SELECAO_VOLUME:
                instrucao = DOBRA_VOLUME;
                break;

            case SELECAO_VERIFICA_ANTERIOR:
            default:
                instrucao = verificaLetra(letraAnterior);
                break;

        }

        return instrucao;
    }

    private int verificaLetra(char letraAtual) {

        int instrucao = 0;

        if (CARACTERES_NOTAS.contains(letraAtual)) {

            instrucao = seletorNota(letraAtual);

        } else {

            instrucao = SILENCIO;
        }

        return instrucao;
    }

    private int seletorAcao(char letraAtual) {

        int codigoSaida;

        if (CARACTERES_NOTAS.contains(letraAtual)) {

            codigoSaida = SELECAO_NOTA;

        } else {
            if (CARACTERES_INSTRUMENTO.contains(letraAtual)) {

                codigoSaida = SELECAO_INSTRUMENTO;

            } else {
                if (CARACTERES_OITAVA.contains(letraAtual)) {

                    codigoSaida = SELECAO_OITAVA;

                } else {
                    if (CARACTERES_SOMA.contains(letraAtual)) {

                        codigoSaida = SELECAO_SOMA_INSTRUMENTO;

                    } else {
                        if (CARACTERES_VOLUME.contains(letraAtual)) {

                            codigoSaida = SELECAO_VOLUME;

                        } else {
                            codigoSaida = SELECAO_VERIFICA_ANTERIOR;
                        }

                    }
                }

            }
        }
        return codigoSaida;
    }

    private int seletorNota(char letraAtual) {

        int instrucao = TOCA_NOTA;

        switch (letraAtual) {

            case 'A':
                this.notaAtual = NOTA_LA;
                break;
            case 'B':
                this.notaAtual = NOTA_SI;
                break;
            case 'C':
                this.notaAtual = NOTA_DO;
                break;
            case 'D':
                this.notaAtual = NOTA_RE;
                break;
            case 'E':
                this.notaAtual = NOTA_MI;
                break;
            case 'F':
                this.notaAtual = NOTA_FA;
                break;
            case 'G':
                this.notaAtual = NOTA_SOL;
                break;
        }

        return instrucao;

    }

    private int seletorInstrumento(char letraAtual) {

        int instrucao = DEFINE_INSTRUMENTO;

        switch (letraAtual) {

            case 'i':
            case 'I':
            case 'o':
            case 'O':
            case 'u':
            case 'U':
                this.instrumentoAtual = CRAVO;
                break;
            case '!':
                this.instrumentoAtual = AGOGO;
                break;
            case '\n':
                this.instrumentoAtual = SINOS;
                break;
            case ';':
                this.instrumentoAtual = FLAUTA_PAN;
                break;
            case ',':
                this.instrumentoAtual = ORGAO_DE_TUBO;
                break;
            default:
                this.instrumentoAtual = AGOGO;
                break;
   
        }

        return instrucao;

    }
    
    private int somaInstrumento(char letraAtual){
        
        int instrucao = DEFINE_INSTRUMENTO;
        int conversao = Character.getNumericValue(letraAtual) + this.instrumentoAtual;
        
        if(conversao < INSTRUMENTO_MAX)
        {
            this.instrumentoAtual = conversao;
        }
        else
        {
            this.instrumentoAtual = this.instrumentoPadrao;
        }
        
        return instrucao;
    }

}
