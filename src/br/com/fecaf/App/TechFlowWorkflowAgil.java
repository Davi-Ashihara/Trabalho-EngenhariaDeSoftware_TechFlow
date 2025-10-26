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
package br.com.fecaf.App;

import br.com.fecaf.database.SQLiteConnector; // <-- Verifique esta importação
import br.com.fecaf.view.MainFrame;
import javax.swing.UIManager;

public class TechFlowWorkflowAgil {

    public static void main(String[] args) {
        
        // ESTA LINHA É ESSENCIAL!
        // Ela prepara a base de dados antes de qualquer outra coisa.
        SQLiteConnector.initializeDatabase();
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Falha ao configurar o Look and Feel do sistema.");
            System.err.println(ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}