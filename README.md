# Trabalho-EngenhariaDeSoftware_TechFlow

Este projeto foi desenvolvido por **Davi Kazussuke Pontes Ashihara**, estudante regular da **UniFECAF – instituição do grupo FEDERAL EDUCACIONAL LTDA (CNPJ: 17.238.945/0001-49)**, como parte de um trabalho acadêmico. Este Repositorio tem como objetivo conter o trabalho e documentação da atividade da materia de Egenharia de software do curso de Engenharia da computação 4° Semestre.

---

## 📖 Sobre o Projeto

>Este projeto simula o desenvolvimento de um **sistema de gerenciamento de tarefas baseado em metodologias ágeis** para uma startup fictícia de logística.  
O objetivo é **acompanhar o fluxo de trabalho em tempo real, priorizar tarefas críticas e monitorar o desempenho da equipe**.

---

## ⚖️ Licença e Direitos Autorais

Este projeto é licenciado sob a **Apache License 2.0**.  
Veja o arquivo [`LICENSE`](./LICENSE) para o texto completo.

O arquivo [`NOTICE`](./NOTICE) contém:
- Nome completo do desenvolvedor
- Vínculo institucional (UniFECAF / grupo Federal Educacional LTDA)
- Detalhes de contexto acadêmico

### Por que Apache 2.0?
- Permite uso, modificação e redistribuição livre, inclusive comercial
- Protege os direitos autorais do autor
- Garante manutenção da atribuição
- Compatível com a legislação brasileira de copyright

---

💬 Política de Commits

Todos os commits a partir deste ponto são feitos em português para refletir a naturalidade do projeto e facilitar a revisão acadêmica.
Exemplo de padrão de commit:

feat: adiciona classe Tarefa e métodos de CRUD
fix: corrige erro na atualização do status de tarefa
docs: atualiza README com explicações sobre licença e metodologia

📝 Planejamento de Tarefas (Kanban)

O quadro de tarefas no GitHub Projects contém as colunas:
A Fazer: tarefas planejadas e não iniciadas
- **A Fazer**: tarefas planejadas e não iniciadas
- **Em Progresso**: tarefas atualmente sendo desenvolvidas
- **Concluído**: tarefas finalizadas e revisadas

Caso o cliente precise priorizar outra funcionalidade, a coluna A Fazer é atualizada e o Kanban reorganizado, refletindo a metodologia ágil.

🔐 Controle de Qualidade
GitHub Actions configurado para executar testes unitários automaticamente.
Garantia de que alterações no código não quebram funcionalidades existentes.
🎯 Principais Beneficiados
Equipe de desenvolvimento: visibilidade clara das tarefas e responsabilidades.
Gerentes de projeto: acompanhamento em tempo real do progresso.
Stakeholders/cliente: transparência sobre status do projeto e priorização de tarefas críticas.

## 🗂️ Estrutura do Repositório

Trabalho-EngenhariaDeSoftware_TechFlow/
├── build/              # Binários compilados (gerado pelo Ant)
├── dist/               # JAR final ou distribuição (gerado pelo Ant)
├── nbproject/          # Arquivos de configuração do NetBeans
├── src/                # Código-fonte
│   └── br/
│       └── com/
│           └── fecaf/
│               ├── model/
│               ├── view/
│               ├── controller/
│               └── util/
├── LICENSE             # Licença Apache 2.0
├── NOTICE              # Informações de autoria e vínculo acadêmico
└── README.md           # Este arquivo
