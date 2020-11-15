package GeradorDeMusicas;

import javax.sound.midi.*;

/*
*   Classe responsável por fazer o parse do Texto para uma Sequencia MIDI.
*   O método contém o atributo sequenciaGerada que pode ser passado para um Sequencer(Sequenciador).
*   Essa sequencia pode também ser gravada em um arquivo do tipo MIDI.
*/
public class Analisador extends PadroesMIDI implements  PadroesMusica  //classe analisador implementa as duas interfaces de constantes
{
    
    private int instrumentoAtual,instrumentoPadrao, oitavaAtual, oitavaPadrao, bpmAtual, volumeAtual, volumePadrao, notaAtual;
    private long tickAtual = 0;
    public Sequence sequenciaGerada;
    

    /*
    *   Atualiza o atributo de sequencia (música) gerada de acordo com o texto de entrada.
    *   Utiliza os valores de entrada como padrão para incializar a sequencia (música).
    */
    public void geraMusica(String textoEntrada, int bpmEntrada, int volumeEntrada, int oitavaEntrada, int instrumentoEntrada) {

        int tamanhoTexto = textoEntrada.length();
        int tipoEvento;
        char letraAtual, letraAnterior;

        inicializaAtributos(bpmEntrada, volumeEntrada, oitavaEntrada, instrumentoEntrada);

        try {

            sequenciaGerada = new Sequence(Sequence.PPQ, 4); // Cria nova sequencia MIDI com 4 ticks por batida
            Track musicaGerada = sequenciaGerada.createTrack();

            incializaMusica(musicaGerada);

            //Percore o texto definindo caractere atual e anterior.
            for (int posTexto = 0; posTexto < tamanhoTexto; posTexto++) {
                letraAtual = textoEntrada.charAt(posTexto);
                if (posTexto > 0) {
                    letraAnterior = textoEntrada.charAt(posTexto - 1);
                }
                else {
                    letraAnterior = letraAtual; // Garante que nada aconteça quando a primeira letra lida for alguma que depende da letra anterior
                }

                //Busca qual ação deve ser executada de acordo com a combinação de caracteres
                tipoEvento = buscaAcao(letraAtual, letraAnterior);

                //Insere evento na sequencia de Acordo com a ação desejada
                switch (tipoEvento) {
                    case TOCA_NOTA:
                        insereNota(musicaGerada, this.notaAtual, this.oitavaAtual);
                        this.tickAtual++;
                        break;

                    case DEFINE_INSTRUMENTO:
                        defineInstrumento(musicaGerada,this.instrumentoAtual);
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
        } catch (InvalidMidiDataException e) {
            System.out.println(e);
        }

    }

    /*
    *   Define os atributos da classe de acordo com os valores de entrada.
    */
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

    /*
    *   Insere na sequencia um evento de Ligar nota seguido por um evento de desligar nota no tick seguinte.
    */
    private void insereNota(Track musicaGerada, int nota, int oitava) {
        
        ShortMessage mensagemLigaNota = geraMensagemNota(LIGA_NOTA, nota, oitava);

        musicaGerada.add(geraEventoMIDI(mensagemLigaNota, this.tickAtual));

        this.tickAtual++;

        ShortMessage mensagemDesligaNota = geraMensagemNota(DESLIGA_NOTA, nota, oitava);

        musicaGerada.add(geraEventoMIDI(mensagemDesligaNota, this.tickAtual));
    }

    /*
    *   Define o volume da sequencia MIDI para o dorbo do atual.
    *   Caso passe do valor limite define para o valor padrão de entrada.
    */
    private void dobraVolume(Track musicaGerada) {
        
        if ((this.volumeAtual * 2) > VOLUME_MAX) {
            this.volumeAtual = this.volumePadrao;
        } else {
            this.volumeAtual = this.volumeAtual * 2;
        }

        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add(geraEventoMIDI(mensagemVolume, this.tickAtual));

    }

    /*
    *   Incrementa oitava, caso não seja possivel retorna ao valor padrão de entrda.
    */
    private void incrementaOitava() {
        
        if ((this.oitavaAtual + 1) > OITAVA_MAX) {
            this.oitavaAtual = this.oitavaPadrao;
        } else {
            this.oitavaAtual = this.oitavaAtual + 1;
        }

    }

    /*
    *   Define o instrumento da sequenciaGerada de acordo com o instrumento de entrada.
    */
    private void defineInstrumento(Track musicaGerada,int instrumento) {

        ShortMessage mensagemInstrumento = geraMensagemInstrumento(instrumento);
        musicaGerada.add(geraEventoMIDI(mensagemInstrumento, this.tickAtual));

    }

    /*
    *   Define os  valores iniciais da sequencia de acordo com os valores dos atributos.
    */
    private void incializaMusica(Track musicaGerada) {

        ShortMessage mensagemVolume = geraMensagemVolume(this.volumeAtual);
        musicaGerada.add(geraEventoMIDI(mensagemVolume, this.tickAtual));

        musicaGerada.add(geraEventoBPM(this.bpmAtual, this.tickAtual));

        defineInstrumento(musicaGerada,this.instrumentoAtual);
        
        
        
    }   

    /*
    *   Busca acao que deve ser realizada de acordo com a letra atual.
    *   Em caso de acao que utilizada letra anterior ela é avaliada em um segundo teste.
    */
    public int buscaAcao(char letraAtual, char letraAnterior) {

        int instrucao;
        
        
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
                instrucao = verificaLetraAnterior(letraAnterior);
                break;

        }

        return instrucao;
    }

    /*
    *   Confere se a letra de entrada faz parte da lista de sensibilidae de notas
    */
    private int verificaLetraAnterior(char letraAnalisada) {

        int instrucao;
        
        
        if (CARACTERES_NOTAS.contains(letraAnalisada)) {

            instrucao = seletorNota(letraAnalisada);

        } else {

            instrucao = SILENCIO;
        }

        return instrucao;
    }

    /*
    *   Ve a qual lista de sensibilidade a letra pertence.
    *   Caso não pertença a nenhuma pré-definida entra no caso de verificação da letra anterior.
    */
    private int seletorAcao(char letraAnalisada) {

        int codigoSaida;

        if (CARACTERES_NOTAS.contains(letraAnalisada)) {

            codigoSaida = SELECAO_NOTA;

        } else {
            if (CARACTERES_INSTRUMENTO.contains(letraAnalisada)) {

                codigoSaida = SELECAO_INSTRUMENTO;

            } else {
                if (CARACTERES_OITAVA.contains(letraAnalisada)) {

                    codigoSaida = SELECAO_OITAVA;

                } else {
                    if (CARACTERES_SOMA.contains(letraAnalisada)) {

                        codigoSaida = SELECAO_SOMA_INSTRUMENTO;

                    } else {
                        if (CARACTERES_VOLUME.contains(letraAnalisada)) {

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

    /*
    *   Define a nota a partir da letra de entrada.
    *   Esse método só deve ser chamado após a verificação de que a letra está na lista de sensibilidade de notas.
    */
    private int seletorNota(char letraAnalisada) {

        int instrucao = TOCA_NOTA;
        
        switch (letraAnalisada) {

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

    /*
    *   Define o instrumento de acordo com a letra de entrada.
    */
    private int seletorInstrumento(char letraAnalisada) {

        int instrucao = DEFINE_INSTRUMENTO;

        switch (letraAnalisada) {

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
    
    /*
    *   Soma o instrumento atual com o valor da letra de entrada.
    *   Caso ultrapasse o limite define como instrumento padrão de entrada.
    */
    private int somaInstrumento(char letraAnalisada){
        
        int instrucao = DEFINE_INSTRUMENTO;
        int conversao = Character.getNumericValue(letraAnalisada) + this.instrumentoAtual;
        
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
