package GeradorDeMusicas;

import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
*   Classe da interface gráfica.
*   Gerencia as interações e manipula os objetos de acordo.
*/
public class TelaPrincipal extends javax.swing.JFrame 
{
    public TelaPrincipal() 
    {
        initComponents();
        complementoInicializacao();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooserArquivoEntrada = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaTextoEntrada = new javax.swing.JTextArea();
        botaoPlay = new javax.swing.JButton();
        botaoPause = new javax.swing.JButton();
        botaoCancela = new javax.swing.JButton();
        botaoGeraMusica = new javax.swing.JButton();
        comboBoxInstrumento = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spinnerBPM = new javax.swing.JSpinner();
        spinnerVolume = new javax.swing.JSpinner();
        spinnerOitava = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        botaoInsereArquivo = new javax.swing.JButton();
        botaoSalvarSaida = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAreaTextoEntrada.setColumns(20);
        textAreaTextoEntrada.setRows(5);
        jScrollPane1.setViewportView(textAreaTextoEntrada);

        botaoPlay.setText("<html>&#9654 ");
        botaoPlay.setEnabled(false);
        botaoPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPlayActionPerformed(evt);
            }
        });

        botaoPause.setText("<html>&#9612 <html>&#9612");
        botaoPause.setEnabled(false);
        botaoPause.setMinimumSize(new java.awt.Dimension(41, 23));
        botaoPause.setPreferredSize(new java.awt.Dimension(41, 23));
        botaoPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPauseActionPerformed(evt);
            }
        });

        botaoCancela.setText("<html>&#9209");
        botaoCancela.setEnabled(false);
        botaoCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelaActionPerformed(evt);
            }
        });

        botaoGeraMusica.setText("Gerar Música");
        botaoGeraMusica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoGeraMusicaActionPerformed(evt);
            }
        });

        comboBoxInstrumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione um Instrumento", "Agogô", "Cravo", "Sinos", "Flauta Pan", "Orgão de Tubo" }));
        comboBoxInstrumento.setToolTipText("");

        jLabel1.setText("Volume:");

        jLabel2.setText("BPM:");

        jLabel3.setText("Oitava:");

        spinnerBPM.setModel(new javax.swing.SpinnerNumberModel(120, 40, 240, 1));

        spinnerVolume.setModel(new javax.swing.SpinnerNumberModel(50, 0, 100, 1));
        spinnerVolume.setValue(50);

        spinnerOitava.setModel(new javax.swing.SpinnerNumberModel(4, 0, 9, 1));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel4.setText("Entrada de Texto:");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel5.setText("Valores inciais:");

        botaoInsereArquivo.setText("Inserir Arquivo de Texto");
        botaoInsereArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInsereArquivoActionPerformed(evt);
            }
        });

        botaoSalvarSaida.setText("Salvar Arquivo MIDI");
        botaoSalvarSaida.setEnabled(false);
        botaoSalvarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarSaidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(botaoGeraMusica, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                        .addComponent(botaoInsereArquivo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(botaoPause, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(botaoCancela, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboBoxInstrumento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoSalvarSaida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spinnerVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spinnerBPM, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spinnerOitava, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoInsereArquivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoPlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(botaoPause, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(botaoCancela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(botaoGeraMusica)))
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(spinnerVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(spinnerBPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spinnerOitava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxInstrumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botaoSalvarSaida)
                        .addGap(59, 59, 59))))
        );

        jLabel1.getAccessibleContext().setAccessibleName("volume");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final PlayerMusica playerMusica = new PlayerMusica();
    private final Analisador analisador = new Analisador();
    private final ManipuladorArquivos manipulador = new ManipuladorArquivos();
    
    /*
    *   Função que inicia a geração da música após o usuário solicitar
    */
    private void botaoGeraMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoGeraMusicaActionPerformed

        int bpm,volume,oitava,indiceInstrumento,instrumento;
        
        bpm = (int)(spinnerBPM.getValue());
        volume = (int)(spinnerVolume.getValue());
        oitava = (int)(spinnerOitava.getValue());
        indiceInstrumento = (int)(comboBoxInstrumento.getSelectedIndex());
        instrumento = seletorInstrumento(indiceInstrumento);
        
        //Passa o texto da texBox para o analisador gerar uma sequencia que estará em seu atributo.
        analisador.geraMusica(textAreaTextoEntrada.getText(),bpm,volume,oitava,instrumento);
        //inicializa o player com a sequência gerada pelo analisador
        playerMusica.setSequencia(analisador.sequenciaGerada);
        
        //Habilita  booes de acordo com a necessidade
        setEnableBotoesControle(false);
        setEnableBotoesMusica(true);
        
    }//GEN-LAST:event_botaoGeraMusicaActionPerformed

    /*
    *   Função que pega informações de arquivo de texto aberto e coloca na texBox
    */
    private void botaoInsereArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoInsereArquivoActionPerformed
        
        //Verifica se o usuário selecionou um arquivo
        if (fileChooserArquivoEntrada.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) 
        {
            //Extrai informações e coloca na texBox
            File arquivoDeEntrada = fileChooserArquivoEntrada.getSelectedFile(); 
            textAreaTextoEntrada.setText(manipulador.copiaTextoArquivo(arquivoDeEntrada));
            textAreaTextoEntrada.setCaretPosition(0);
        } 
    }//GEN-LAST:event_botaoInsereArquivoActionPerformed

    /*
    *   Função responsável por solicitar salvamento da sequência gerada em Arquivo MIDI
    */
    private void botaoSalvarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarSaidaActionPerformed
        manipulador.arquivoMIDIEscrita(analisador.sequenciaGerada);
    }//GEN-LAST:event_botaoSalvarSaidaActionPerformed

    /*
    *   Função responsável por solicitar o incio da reprodução da música
    */
    private void botaoPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPlayActionPerformed
        playerMusica.play();
        
    }//GEN-LAST:event_botaoPlayActionPerformed

    /*
    *   Função responsável por solicitar a pausa da reprodução da música
    */
    private void botaoPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPauseActionPerformed
        playerMusica.pause();
    }//GEN-LAST:event_botaoPauseActionPerformed

    /*
    *   Função responsável por cancelar a reprodução da música e habilitar edição do texto de entrada novamente
    */
    private void botaoCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelaActionPerformed
        
        setEnableBotoesControle(true);
        
        setEnableBotoesMusica(false);
        
        playerMusica.cancela();
    }//GEN-LAST:event_botaoCancelaActionPerformed

    /*
    *   "seletorInstrumento" serve para definir o instrumento que vai se usado como padrão pelo analisador
    *   Para acrescentar novos instrumentos segue-se os passos:
    *   1º - Acrescentar o instrumento na combo box
    *   2º - Definir em "PadroesMIDI" a constante do java sound correspondente ao instrumento
    *           As constantes do java sound podem ser encontradas no link: https://en.wikipedia.org/wiki/General_MIDI#Program_change_events
    *   3º - Adicionar um case ao switch da função abaixo
    */
    private int seletorInstrumento(int indiceInstrumento)
    {
        int instrumento;
        switch(indiceInstrumento)
        {
            case 1:
                instrumento = PadroesMIDI.AGOGO;
                break;
            case 2:
                instrumento = PadroesMIDI.CRAVO;
                break;
            case 3:
                instrumento = PadroesMIDI.SINOS;
                break;
            case 4:
                instrumento = PadroesMIDI.FLAUTA_PAN;
                break;
            case 5:
                instrumento = PadroesMIDI.ORGAO_DE_TUBO;
                break;
            default:
                instrumento = PadroesMIDI.AGOGO;
                break;
        }
        return instrumento;
    }

    public static void main(String args[]) {    // Realiza apenas a inicialização da interface do programa
        //<editor-fold defaultstate="collapsed" desc="Funções de inicialização da interface">

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    
    /*
    *   Função para definir estado de habilitação do conjunto de botões relacionados com o player
    */
    private void setEnableBotoesMusica(boolean estado)
    {
        botaoCancela.setEnabled(estado);
        botaoPlay.setEnabled(estado);
        botaoPause.setEnabled(estado);
        botaoSalvarSaida.setEnabled(estado);
    }
    
    /*
    *   Função para definir estado de habilitação do conjunto de botões relacionados com a definição da Entrada.
    */
    private void setEnableBotoesControle(boolean estado)
    {
        botaoGeraMusica.setEnabled(estado);
        spinnerBPM.setEnabled(estado);
        spinnerOitava.setEnabled(estado);
        spinnerVolume.setEnabled(estado);
        comboBoxInstrumento.setEnabled(estado);
        botaoInsereArquivo.setEnabled(estado);
        textAreaTextoEntrada.setEnabled(estado);
    }

    /*
    *   Função utilizada para garantir que só possam ser escolhidos arquivos txt como entrada.
    */
    private void complementoInicializacao() {
        
        fileChooserArquivoEntrada.setAcceptAllFileFilterUsed(false);
        fileChooserArquivoEntrada.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt", "text"));
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancela;
    private javax.swing.JButton botaoGeraMusica;
    private javax.swing.JButton botaoInsereArquivo;
    private javax.swing.JButton botaoPause;
    private javax.swing.JButton botaoPlay;
    private javax.swing.JButton botaoSalvarSaida;
    private javax.swing.JComboBox<String> comboBoxInstrumento;
    private javax.swing.JFileChooser fileChooserArquivoEntrada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spinnerBPM;
    private javax.swing.JSpinner spinnerOitava;
    private javax.swing.JSpinner spinnerVolume;
    private javax.swing.JTextArea textAreaTextoEntrada;
    // End of variables declaration//GEN-END:variables

    
}
