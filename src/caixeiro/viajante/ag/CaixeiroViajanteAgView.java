package caixeiro.viajante.ag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Dalbosco
 */
class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Text documents (*.txt)";
    }
}

public class CaixeiroViajanteAgView extends javax.swing.JFrame {

    public static ArrayList<Cities> citiesList;
    public static String textFormat = "";

    /**
     * Creates new form CaixeiroViajanteAgView
     */
    public CaixeiroViajanteAgView() {
        citiesList = new ArrayList<Cities>();
        initComponents();
    }
    
    public void execution(int evolutionNumber, double mortalityRate) {
        
      //  int evolutionNumber = 3000;
       // double mortalityRate = 0.2;

        formatMatriceData();
        textFormat += "======================================================\n";
        textFormat += "\t                  CONFIGURATION \n\n";
        textFormat += "Evolution Number: " + evolutionNumber + "\n";
        textFormat += "Mortality Rate: " + mortalityRate + "\n";
        textFormat += "======================================================\n\n";

        for (Cities cities : citiesList) {
            textFormat += "Solution for " + cities.getQuantity() + " cities: \n";
            textFormat += "Population: " + cities.getPopulation() + " \n";
            textFormat += GeneticAlgorithm.resolve(cities.getQuantity(),
                    cities.matrice,
                    (float) 0.5,
                    cities.getPopulation(),
                    3000,
                    false
            );
        }

        txtCodigo.setText(textFormat);
    }

    /**
     * Formata data set para matrizes de adjacências
     */
    public static void formatMatriceData() {
        for (Cities cities : citiesList) {
            ArrayList<Integer> distances = cities.getDistances();
            distances.remove(0);

            int[][] matrice = new int[cities.getQuantity()][cities.getQuantity()];

            //System.out.println(distances);
            //System.out.println("\n");
            // Inicialização da matriz com "0"
            for (int i = 0; i < cities.getQuantity(); i++) {
                for (int j = 0; j < cities.getQuantity(); j++) {
                    if (i == j) {
                        matrice[i][j] = 0;
                    } else {
                        matrice[i][j] = -1;
                    }
                }
            }

            // Declara variaveis para poder utilizar
            int aux = 0, aux_2 = 1;

            // Percorre as distancias de cada cidade
            for (int i = 0; i < distances.size(); i++) {

                // Caso checar no final da matriz entra
                if (aux_2 >= cities.getQuantity()) {

                    if (matrice[aux][0] == -1 || matrice[0][aux] == -1) {
                        matrice[aux][0] = distances.get(i);
                        matrice[0][aux] = distances.get(i);
                    }

                    // Percorre verificando todas as conexões e adiciona caso não tenha 
                    for (int j = 0; j < cities.getQuantity(); j++) {
                        for (int k = 0; k < cities.getQuantity(); k++) {
                            if (matrice[j][k] == -1 || matrice[k][j] == -1) {
                                i++;
                                matrice[j][k] = distances.get(i);
                                matrice[k][j] = distances.get(i);
                            }
                        }
                    }

                    break;
                }

                if (matrice[aux][aux_2] == -1 || matrice[aux_2][aux] == -1) {
                    matrice[aux][aux_2] = distances.get(i);
                    matrice[aux_2][aux] = distances.get(i);
                }

                aux++;
                aux_2++;
            }

            String saida = "";
            for (int i = 0; i < cities.getQuantity(); i++) {
                for (int j = 0; j < cities.getQuantity(); j++) {
                    saida += matrice[i][j] + "\t";
                }
                saida += "\n";
            }

            //System.out.println(saida);
            cities.matrice = matrice;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldMortalityRate = new javax.swing.JTextField();
        jTextFieldGenerations = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Abrir = new javax.swing.JMenuItem();

        fileChooser.setDialogTitle("This is my open dialog");
        fileChooser.setFileFilter(new MyCustomFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtCodigo.setColumns(20);
        txtCodigo.setRows(5);
        txtCodigo.setText("======================================================\n\t       INSTRUCTION FOR BUILD \n\n1 - Pra executar a aplicação necessário clicar na barra de tarefas “menu”, selecionar a opção “Abrir”.\n2 -  Após isto você deve selecionar o arquivo .txt com as cidades\n=====================================================");
        jScrollPane4.setViewportView(txtCodigo);

        jLabel1.setText("Result");

        jLabel2.setText("mortality rate");

        jLabel3.setText("generations");

        jTextFieldMortalityRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMortalityRateActionPerformed(evt);
            }
        });

        jTextFieldGenerations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGenerationsActionPerformed(evt);
            }
        });

        jButton1.setText("começar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenu1.setText("Menu");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        Abrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(Abrir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldMortalityRate, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)
                        .addComponent(jTextFieldGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldMortalityRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
                
                for (List<String> record : records) {

                    Cities cities = new Cities(Integer.parseInt(record.get(0)));
                    for (int i = 0; i < record.size(); i++) {
                        cities.addDistance(Integer.parseInt(record.get(i).replaceAll("\\s+", "")));
                    }
                    citiesList.add(cities);
                }

                // Vai para execução
                //execution(Integer.parseInt(jTextFieldGenerations.getText()), Double.parseDouble(jTextFieldMortalityRate.getText()));
            } catch (ExceptionInInitializerError ex) {
                System.out.println(ex.getMessage());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                System.out.println("Favor selecionar o arquivo correto!");
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_AbrirActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jTextFieldMortalityRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMortalityRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMortalityRateActionPerformed

    private void jTextFieldGenerationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGenerationsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGenerationsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        execution(Integer.parseInt(jTextFieldGenerations.getText()), Double.parseDouble(jTextFieldMortalityRate.getText()));
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CaixeiroViajanteAgView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaixeiroViajanteAgView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Abrir;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextFieldGenerations;
    private javax.swing.JTextField jTextFieldMortalityRate;
    private javax.swing.JTextArea txtCodigo;
    // End of variables declaration//GEN-END:variables
}
