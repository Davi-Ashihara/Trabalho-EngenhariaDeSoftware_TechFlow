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
package br.com.fecaf.DAO;

import org.springframework.stereotype.Component;
import br.com.fecaf.database.SQLiteConnector;
import br.com.fecaf.model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para a entidade Task.
 * Esta classe é responsável por toda a comunicação com a tabela 'tasks' na base de dados.
 */
@Component
public class TaskDAO {

    /**
     * Busca todas as tarefas da base de dados.
     * @return uma lista de objetos Task.
     */
    public List<Task> getAllTasks() {
        String sql = "SELECT id, title, status, observation FROM tasks";
        List<Task> tasks = new ArrayList<>();

        try (Connection conn = SQLiteConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("observation")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar tarefas: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Adiciona uma nova tarefa à base de dados.
     * @param title O título da nova tarefa.
     * @return true se a tarefa foi adicionada com sucesso, false caso contrário.
     */
    public boolean addTask(String title) {
        String sql = "INSERT INTO tasks(title, status, observation) VALUES(?, ?, ?)";

        try (Connection conn = SQLiteConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, title);
            pstmt.setString(2, "A Fazer");
            pstmt.setString(3, ""); // Adiciona uma observação vazia por defeito
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar tarefa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Atualiza o status de uma tarefa existente.
     * @param id O ID da tarefa a ser atualizada.
     * @param newStatus O novo status ("Em Progresso" ou "Concluído").
     * @return true se o status foi atualizado com sucesso, false caso contrário.
     */
    public boolean updateTaskStatus(int id, String newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = SQLiteConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da tarefa: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Atualiza a observação de uma tarefa existente.
     * @param id O ID da tarefa a ser atualizada.
     * @param newObservation O novo texto da observação.
     * @return true se a observação foi atualizada com sucesso, false caso contrário.
     */
    public boolean updateTaskObservation(int id, String newObservation) {
        String sql = "UPDATE tasks SET observation = ? WHERE id = ?";

        try (Connection conn = SQLiteConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newObservation);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar observação: " + e.getMessage());
            return false;
        }
    }

    /**
     * Apaga uma tarefa da base de dados com base no seu ID.
     * @param id O ID da tarefa a ser apagada.
     * @return true se a tarefa foi apagada com sucesso, false caso contrário.
     */
    public boolean deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = SQLiteConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao apagar tarefa: " + e.getMessage());
            return false;
        }
    }
}