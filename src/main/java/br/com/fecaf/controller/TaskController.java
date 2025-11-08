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
package br.com.fecaf.controller;
import br.com.fecaf.DAO.TaskDAO;
import br.com.fecaf.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Este é o Controlador REST. Ele substitui a lógica de eventos do 'MainFrame'.
 * Ele expõe URLs (endpoints) que o frontend JavaScript pode chamar.
 * * @RestController - Marca esta classe como um controlador que retorna JSON.
 * @RequestMapping("/api/tasks") - Define o URL base para todos os métodos nesta classe.
 * @CrossOrigin("*") - Permite que o frontend chame esta API,
 * mesmo que estejam em "origens" diferentes (ex: file:// e localhost:8080).
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*") 
public class TaskController {

    // @Autowired é a "Injeção de Dependência" do Spring.
    // Ele automaticamente encontra e injeta uma instância do seu TaskDAO.
    // **IMPORTANTE:** Para isso funcionar, você deve anotar sua classe TaskDAO
    // com @Service ou @Component.
    @Autowired
    private TaskDAO taskDAO;

    /**
     * SUBSTITUI: O método 'loadTasks()' do MainFrame.
     * URL: GET /api/tasks
     * Ação: Busca todas as tarefas no banco.
     * Retorna: Uma lista de tarefas em formato JSON.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    /**
     * SUBSTITUI: O 'btnAddTaskActionPerformed' do MainFrame.
     * URL: POST /api/tasks
     * Ação: Cria uma nova tarefa.
     * Espera: Um corpo JSON com o título, ex: { "title": "Nova Tarefa" }
     * Retorna: A tarefa recém-criada (ou um status de sucesso).
     */
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("O título é obrigatório.");
        }
        
        // Usa o mesmo método do seu DAO antigo
        taskDAO.addTask(title); 
        
        return ResponseEntity.ok("Tarefa criada com sucesso.");
    }

    /**
     * SUBSTITUI: O 'btnDeleteTaskActionPerformed' do MainFrame.
     * URL: DELETE /api/tasks/{id}
     * Ação: Exclui uma tarefa com base no ID fornecido no URL.
     * Retorna: Um status de sucesso (sem conteúdo).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        // Usa o mesmo método do seu DAO antigo
        taskDAO.deleteTask(id); 
        return ResponseEntity.noContent().build();
    }

    /**
     * SUBSTITUI: 'btnDoneActionPerformed', 'btnInProgressActionPerformed', 'btntodoActionPerformed'.
     * URL: PUT /api/tasks/{id}/status
     * Ação: Atualiza o status de uma tarefa específica.
     * Espera: Um corpo JSON com o novo status, ex: { "status": "Concluída" }
     * Retorna: Um status de sucesso.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("O status é obrigatório.");
        }
        // Usa o mesmo método do seu DAO antigo
        taskDAO.updateTaskStatus(id, status); 
        return ResponseEntity.ok("Status da tarefa atualizado.");
    }
@PutMapping("/{id}/observation")
public ResponseEntity<String> updateTaskObservation(@PathVariable int id, @RequestBody Map<String, String> payload) {
    String observation = payload.get("observation");

    // A observação pode ser nula ou vazia (para limpar)
    if (observation == null) {
        observation = ""; // Garante que não seja nulo para o DAO
    }

    taskDAO.updateTaskObservation(id, observation); 
    return ResponseEntity.ok("Observação da tarefa atualizada.");
}
}