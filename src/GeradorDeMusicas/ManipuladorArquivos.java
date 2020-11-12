/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorDeMusicas;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sound.midi.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.*;
import java.text.*;
import java.util.Locale;

/**
 *
 * @author Leonardo
 */
public class ManipuladorArquivos 
{
    
    public void midiFileSalva(Sequence musica)
    {
        
        String nomeArquivo = null;
        ManipuladorArquivos manipulador = new ManipuladorArquivos();
        
        nomeArquivo = manipulador.geraNomeArquivo();
        
        try
        {
            File f = new File(nomeArquivo);
            MidiSystem.write(musica,1,f);
        }
        catch(Exception e)
        {
           System.out.println(e);
        }
    }
    
    public String preencheTextoBox(File inputTextFile)
    {
        String  textfinal = null, text = null;
        try (BufferedReader br = new BufferedReader( new FileReader (inputTextFile))) 
            {
                //textAreaTextoEntrada.setText(null);
                while ((text = br.readLine()) != null) 
                {
                    textfinal = text;
                    textfinal = "\n";
                }
                //textAreaTextoEntrada.setCaretPosition(0);
            } 
            catch (IOException exp) 
            {
                exp.printStackTrace();

            }
        return textfinal;
    } 
        
    public String geraNomeArquivo()
    {
        String nomeArquivo = null;

        Locale locale = new Locale("pt","BR");
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat formatador = new SimpleDateFormat("dd_MM_yyyy-HH_mm_ss",locale);
        nomeArquivo = "SaidaMidi-" + formatador.format(calendar.getTime()) + ".midi";

        return nomeArquivo;
    }
    
    
}

