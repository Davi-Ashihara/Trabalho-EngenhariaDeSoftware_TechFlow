/*
 * Copyright 2025 Davi Kazussuke Pontes Ashihara
 * Developed as part of academic coursework at UniFECAF –
 * instituição do grupo FEDERAL EDUCACIONAL LTDA (CNPJ: 17.238.945/0001-49),
 * certificada como Centro Universitário UniFECAF.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
     *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.fecaf.view;

// Importações necessárias
import br.com.fecaf.controller.TaskDAO;
import br.com.fecaf.model.Task;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    private final TaskDAO taskDAO;

    public MainFrame() {
        initComponents(); // Gerado pelo NetBeans
        addPlaceholderStyle(txtNewTaskTitle, "Digite o título da nova tarefa...");
        
        this.taskDAO = new TaskDAO();
        this.setLocationRelativeTo(null);
        applyDesignTweaks();
        loadTasks();
    }

    private void applyDesignTweaks() {
        // Renderizador para colorir a coluna "Status" (índice 2)
        tblTasks.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());

        // Ajusta a largura das colunas
        tblTasks.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tblTasks.getColumnModel().getColumn(0).setMaxWidth(60);
        tblTasks.getColumnModel().getColumn(1).setPreferredWidth(250); // Título
        tblTasks.getColumnModel().getColumn(2).setPreferredWidth(120); // Status
        tblTasks.getColumnModel().getColumn(3).setPreferredWidth(150); // Observação
        
        // --- CORREÇÃO 1: ADICIONADO DE VOLTA O LISTENER PARA HABILITAR/DESABILITAR BOTÕES ---
        tblTasks.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean rowIsSelected = tblTasks.getSelectedRow() != -1;
                btnInProgress.setEnabled(rowIsSelected);
                btnDone.setEnabled(rowIsSelected);
                btnDeleteTask.setEnabled(rowIsSelected);
            }
        });

        tblTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    handleDoubleClickOnTable();
                }
            }
        });
        try {
        } catch (Exception ex) {
            System.err.println("Aviso: Ícones não encontrados.");
        }
    }
    
    private void handleDoubleClickOnTable() {
        int selectedRow = tblTasks.getSelectedRow();
        if (selectedRow == -1) return; // Sai se nenhuma linha estiver selecionada

        int taskId = (int) tblTasks.getValueAt(selectedRow, 0);

        // Busca a tarefa completa para obter a observação atual
        Task selectedTask = taskDAO.getAllTasks().stream()
                .filter(t -> t.getId() == taskId)
                .findFirst()
                .orElse(null);

        if (selectedTask != null) {
            String newObservation = JOptionPane.showInputDialog(
                    MainFrame.this,
                    "Editar Observação para a tarefa '" + selectedTask.getTitle() + "':",
                    selectedTask.getObservation() // Mostra a observação atual
            );

            // Se o utilizador clicar em OK (e não em Cancelar)
            if (newObservation != null) {
                taskDAO.updateTaskObservation(taskId, newObservation);
                loadTasks(); // Atualiza a tabela para mostrar a nova observação
            }
        }
    }

    private void loadTasks() {
        List<Task> tasks = taskDAO.getAllTasks();
        DefaultTableModel model = (DefaultTableModel) tblTasks.getModel();
        model.setRowCount(0);

        for (Task task : tasks) {
            model.addRow(new Object[]{
                task.getId(), 
                task.getTitle(), 
                task.getStatus(), 
                task.getObservation()
            });
        }
    }

    private void updateSelectedTaskStatus(String newStatus) {
        int selectedRow = tblTasks.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma tarefa para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int taskId = (int) tblTasks.getValueAt(selectedRow, 0);
        taskDAO.updateTaskStatus(taskId, newStatus);
        loadTasks();
    }

    private class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof String status) {
                switch (status) {
                    case "A Fazer" -> c.setBackground(new Color(204, 229, 255)); // Azul claro
                    case "Em Progresso" -> c.setBackground(new Color(255, 229, 180)); // Laranja claro
                    case "Concluído" -> c.setBackground(new Color(204, 255, 204)); // Verde claro
                    default -> c.setBackground(table.getBackground());
                }
            }
            
            // Mantém a cor de seleção do sistema sobre a cor de fundo customizada
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                 c.setForeground(Color.BLACK);
            }

            return c;
        }
    }
/**
 * Adiciona um estilo de placeholder a um JTextField. O texto de ajuda some
 * quando o campo ganha foco e reaparece se o campo perder o foco e estiver vazio.
 * @param textField O campo de texto ao qual aplicar o estilo.
 * @param placeholder O texto que servirá de ajuda.
 */
private void addPlaceholderStyle(javax.swing.JTextField textField, String placeholder) {
    // Define o estado inicial do placeholder
    textField.setForeground(Color.GRAY);
    textField.setText(placeholder);

    // Adiciona um listener para eventos de foco (clicar dentro/fora do campo)
    textField.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            // Quando o utilizador clica no campo
            if (textField.getForeground() == Color.GRAY) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            // Quando o utilizador clica fora do campo
            if (textField.getText().isEmpty()) {
                textField.setForeground(Color.GRAY);
                textField.setText(placeholder);
            }
        }
    });
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblTasks = new javax.swing.JTable();
        btnAddTask = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnDeleteTask = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();
        btnInProgress = new javax.swing.JButton();
        txtNewTaskTitle = new javax.swing.JTextField();
        btntodo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "Status", "Observação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTasks);

        btnAddTask.setText("Adicionar Tarefa");
        btnAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTaskActionPerformed(evt);
            }
        });

        lblTitle.setText("TeamFlorw - Gerenciador de Tarefas");

        btnDeleteTask.setText("Deletar");
        btnDeleteTask.setActionCommand("btnDeleteTask");
        btnDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaskActionPerformed(evt);
            }
        });

        btnDone.setText("Declarar Feita");
        btnDone.setActionCommand("btnDone");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        btnInProgress.setText("Declarar em progresso");
        btnInProgress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInProgressActionPerformed(evt);
            }
        });

        txtNewTaskTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewTaskTitleActionPerformed(evt);
            }
        });

        btntodo.setText("Declarar a fazer");
        btntodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntodoActionPerformed(evt);
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
                        .addGap(6, 6, 6)
                        .addComponent(btnDeleteTask, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDone)
                        .addGap(12, 12, 12)
                        .addComponent(btnInProgress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btntodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddTask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNewTaskTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddTask)
                    .addComponent(txtNewTaskTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteTask)
                    .addComponent(btnDone)
                    .addComponent(btnInProgress)
                    .addComponent(btntodo))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTaskActionPerformed
        String title = txtNewTaskTitle.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O título da tarefa não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //verificamos o resultado booleano do método addTask
        boolean success = taskDAO.addTask(title);
        if (success) {
            JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            txtNewTaskTitle.setText("");
            loadTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao tentar adicionar a tarefa na base de dados.\nConsulte a janela de 'Output' para mais detalhes.", "Erro de Base de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddTaskActionPerformed

    private void btnInProgressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInProgressActionPerformed
        updateSelectedTaskStatus("Em Progresso");
    }//GEN-LAST:event_btnInProgressActionPerformed

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        updateSelectedTaskStatus("Concluído");
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        int selectedRow = tblTasks.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma tarefa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
        }
        int confirmation = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir a tarefa selecionada?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int taskId = (int) tblTasks.getValueAt(selectedRow, 0);
            taskDAO.deleteTask(taskId);
            loadTasks();
        }
    }//GEN-LAST:event_btnDeleteTaskActionPerformed

    private void txtNewTaskTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewTaskTitleActionPerformed

    }//GEN-LAST:event_txtNewTaskTitleActionPerformed

    private void btntodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntodoActionPerformed
        updateSelectedTaskStatus("A Fazer");
    }//GEN-LAST:event_btntodoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddTask;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnInProgress;
    private javax.swing.JButton btntodo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblTasks;
    private javax.swing.JTextField txtNewTaskTitle;
    // End of variables declaration//GEN-END:variables
}
