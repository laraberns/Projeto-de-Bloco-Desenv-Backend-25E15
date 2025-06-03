# 🧠 Entendo Meu Dia

**Entendo Meu Dia** é um sistema desenvolvido para auxiliar no gerenciamento de rotinas personalizadas para pessoas com necessidades específicas, como autismo ou outras condições que exigem acompanhamento próximo. O sistema oferece acessibilidade, organização e controle de atividades diárias por meio de uma interface clara e inclusiva.

---

## 📚 Sobre o Projeto

Este projeto foi desenvolvido em **Java**, com uso de **Spring Boot**, seguindo uma arquitetura em camadas e boas práticas de programação orientada a objetos.

Principais conceitos utilizados:
- Arquitetura em **camadas** (`model`, `service`, `repository`, `controller`)
- Utilização de **JPA (Hibernate)** para persistência de dados
- Banco de dados **H2** embarcado
- Injeção de dependência com Spring
- **Testes automatizados** com JUnit
- **API RESTful** com controle via HTTP

---

## 📁 Estrutura do Projeto

```
entendomeudia/
├── model/                         # Entidades JPA (Usuário, Rotina, Atividade...)
├── repository/                   # Interfaces de repositório com Spring Data JPA
├── service/                      # Camada de serviço com regras de negócio
├── controller/                   # Controladores REST (endpoints)
├── test/                         # Testes automatizados (JUnit)
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

### ✅ FEATURE 13 – Criação da Camada de Controle (Controller)
- Implementação dos endpoints REST responsáveis por gerenciar as operações das entidades.
- Anotação das classes com `@RestController` e `@RequestMapping`.
- Implementação dos métodos REST:
  - `@PostMapping` – inclusão de novos registros.
  - `@DeleteMapping("/{id}")` – exclusão por ID.
  - `@PutMapping("/{id}")` – atualização de dados.
  - `@GetMapping` – obtenção da lista completa.
  - `@GetMapping("/{id}")` – busca de um único registro.
- Integração com a camada de serviço usando `@Autowired` ou injeção via construtor.
- Testes realizados com Postman/Insomnia em diversos cenários:
  - Inserção válida
  - Exclusão de inexistente
  - Atualização incompleta

### ✅ FEATURE 14 – Mapeamento de Relacionamentos com JPA (@OneToOne e @OneToMany)
- Adição de relacionamentos entre as entidades com:
  - `@OneToOne` e `@JoinColumn`
  - `@OneToMany(mappedBy = "...")`
  - `@ManyToOne` com `@JoinColumn`
- Atualização do modelo para refletir a estrutura correta dos dados.
- Prevenção de loops e erros de serialização com `@JsonIgnore`, `@JsonManagedReference`, `@JsonBackReference`.
- Testes:
  - Criação de instâncias em cascata (ex: um usuário com rotinas e atividades).
  - Verificação da criação de tabelas e FKs pelo console H2 e logs SQL.
  - Testes via endpoints REST com JSON refletindo os relacionamentos.

---

## 📦 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (modo console)
- Maven
- IntelliJ IDEA
- JUnit 5
