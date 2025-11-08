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
package br.com.fecaf.model;

public class Task {

    private int id;
    private String title;
    private String status;
    private String observation;

    //Construtor task
    public Task() {}
    public Task(int id, String title, String status, String observation) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.observation = observation;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}