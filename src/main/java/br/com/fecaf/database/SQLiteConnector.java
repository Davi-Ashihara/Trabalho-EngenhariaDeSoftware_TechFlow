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
package br.com.fecaf.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnector {

    private static final String DB_FOLDER_PATH = System.getProperty("user.home") + File.separator + "TaskFlowApp";
    private static final String DB_FILE_PATH = DB_FOLDER_PATH + File.separator + "taskflow.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE_PATH;

    /**
     * Estabelece e retorna uma NOVA conexão com a base de dados.
     * @return um objeto de Connection.
     * @throws SQLException se ocorrer um erro ao conectar.
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Método de inicialização. Garante que a pasta e a base de dados existem.
     */
    public static void initializeDatabase() {
        System.out.println("A inicializar a base de dados em: " + DB_FILE_PATH);

        try {
            File dbFolder = new File(DB_FOLDER_PATH);
            if (!dbFolder.exists()) { // verifica se a pasta já existe, se não, cria a pasta especial para ela
                System.out.println("A criar a pasta da aplicação...");
                boolean folderCreated = dbFolder.mkdir();
                if (folderCreated) {
                    System.out.println("Pasta criada com sucesso.");
                } else {
                    System.err.println("ERRO CRÍTICO: Não foi possível criar a pasta da aplicação.");
                    return; // Sai se não conseguir criar a pasta
                }
            }

            // Passo 2: Conectar e criar a tabela se não existir
            try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS tasks (\n"
                        + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "    title TEXT NOT NULL,\n"
                        + "    status TEXT NOT NULL,\n"
                        + "    observation TEXT DEFAULT ''\n"
                        + ");";
                stmt.execute(sql);
                System.out.println("Base de dados pronta para uso.");

            } catch (SQLException e) {
                System.err.println("Erro crítico ao criar a tabela na base de dados: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado durante a inicialização: " + e.getMessage());
            e.printStackTrace();
        }
    }
}