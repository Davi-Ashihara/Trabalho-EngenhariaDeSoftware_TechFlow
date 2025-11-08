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
package br.com.fecaf;

import br.com.fecaf.database.SQLiteConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"br.com.fecaf.controller", "br.com.fecaf.DAO"}) // Diz ao Spring onde encontrar seu Controller e seu DAO
public class TechFlowApplication {


    public static void main(String[] args) {
        
        // 1. INICIALIZA O BANCO DE DADOS (CRÍTICO!)
        SQLiteConnector.initializeDatabase();
        
        // 2. INICIA O SERVIDOR WEB
        SpringApplication.run(TechFlowApplication.class, args);
        
        System.out.println("\n>>> SERVIDOR TECHFLOW INICIADO! <<<");
        System.out.println(">>> Acesse os endpoints em http://localhost:8080/api/tasks <<<");
    }
}