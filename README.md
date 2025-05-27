# 🧠 Entendo Meu Dia

**Entendo Meu Dia** é um sistema desenvolvido para auxiliar no gerenciamento de rotinas personalizadas para pessoas com necessidades específicas, como autismo ou outras condições que exigem acompanhamento próximo. O sistema oferece acessibilidade, organização e controle de atividades diárias por meio de uma interface clara e inclusiva.

---

## 📚 Sobre o Projeto

Este projeto foi desenvolvido em **Java**, com uso de **Spring Boot**, seguindo uma arquitetura em camadas e boas práticas de programação orientada a objetos. 

Principais conceitos utilizados:
- Arquitetura em **camadas** (`model`, `service`, `repository`)
- Utilização de **JPA (Hibernate)** para persistência de dados
- Banco de dados **H2** embarcado
- Injeção de dependência com Spring
- **Testes automatizados** com JUnit

---

## 📁 Estrutura do Projeto

```
entendomeudia/
├── model/                         # Entidades JPA (Usuário, Rotina, Atividade...)
├── repository/                   # Interfaces de repositório com Spring Data JPA
├── service/                      # Camada de serviço com regras de negócio
├── test/                         # Testes automatizados
├── application.properties        # Configuração do banco H2
└── README.md
```

---

## ✨ Funcionalidades

- 👤 Cadastro e persistência de usuários (principal e responsável)
- 🗓️ Criação e acompanhamento de rotinas e atividades
- 📈 Geração de relatórios com progresso
- 🦻 Configurações de acessibilidade (fonte, contraste, leitura em voz alta)
- 💾 Salvamento dos dados no banco de dados H2
- 🔎 Acesso ao banco pelo console em `http://localhost:8080/h2-console`
- 🧪 Testes de integração com persistência real

---

## 🛠️ Funcionalidades Implementadas Recentemente

### ✅ FEATURE 09 – Banco de Dados H2

- Configuração do banco de dados embarcado H2 no projeto.
- Arquivo `application.properties` com parâmetros de conexão.
- Ativação do console do H2 (`/h2-console`).

### ✅ FEATURE 10 – Entidades JPA

- Anotações `@Entity`, `@Id`, `@GeneratedValue`, `@Table` adicionadas às classes de domínio.
- Atributos não persistentes anotados com `@Transient`.
- Geração automática de tabelas com `spring.jpa.hibernate.ddl-auto=create`.

### ✅ FEATURE 11 – Repositórios com Spring Data

- Criação de interfaces `UsuarioRepository`, `RotinaRepository` etc.
- Extensão de `JpaRepository` para operações CRUD automáticas.
- Injeção de repositórios com `@Autowired`.

### ✅ FEATURE 12 – Serviços Integrados ao Banco

- Refatoração dos serviços para remover `Map` e usar repositórios.
- Persistência e leitura de dados agora são feitas diretamente no banco H2.
- Testes de integração utilizando banco real com rollback via `@Transactional`.

---

## 📦 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (modo console)
- Maven
- IntelliJ IDEA
- JUnit 5
