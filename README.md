
# 🧠 Entendo Meu Dia

**Entendo Meu Dia** é um sistema desenvolvido para auxiliar no gerenciamento de rotinas personalizadas para pessoas com necessidades específicas, como autismo ou outras condições que exigem acompanhamento próximo. O sistema oferece acessibilidade, organização e controle de atividades diárias por meio de uma interface clara e inclusiva.

---

## 📚 Sobre o Projeto

Este projeto foi desenvolvido em **Java**, utilizando a arquitetura orientada a objetos com foco em boas práticas, como:
- Separação por **camadas**: `model`, `service`, `loader`, `testes`
- **Leitura de arquivos texto** para simulação de persistência
- Utilização de **Map** como estrutura de dados
- Uso de **injeção de dependência** entre loaders e services

---

## 📁 Estrutura do Projeto

```
entendomeudia/
├── model/                         # Classes principais de negócio (Usuário, Rotina, Atividade...)
├── loader/                        # Leitura dos dados a partir de arquivos .txt
├── service/                       # Serviços responsáveis por armazenar e fornecer os dados
├── testes/                        # Testes manuais para as funcionalidades
├── resources/dados/              # Arquivos .txt com dados simulados
├── README.md
```

---

## 📌 Funcionalidades

- 👤 Cadastro de usuários (principal e responsável)
- 🗓️ Criação e acompanhamento de rotinas e atividades
- 📈 Geração de relatórios com progresso
- 🦻 Configurações de acessibilidade (fonte, contraste, leitura em voz alta)
- 🧪 Testes de carregamento, leitura e serviços

---

## ✅ Como Executar

1. Clone o projeto:

```bash
git clone https://github.com/seu-usuario/entendomeudia.git
```

2. Importe no IntelliJ como um projeto Maven.

3. Certifique-se de que os arquivos `.txt` estão no caminho `src/main/resources/dados/`.

4. Execute qualquer classe de teste para validar a leitura dos dados:

```java
src/test/java/com/entendomeudia/testes/model/TesteUsuario.java
```

---

## 🧪 Exemplos de Arquivos `.txt`

**usuarios.txt**
```
id=1
nome=Ana
tipo=principal
email=ana@email.com
telefone=11999999999
senha=1234

id=2
nome=João
tipo=responsavel
email=joao@email.com
telefone=11888888888
senha=0000
```

**rotina.txt**
```
id=r1
data=2025-04-01
usuarioId=1
```

---

## ✨ Tecnologias Utilizadas

- Java 17+
- IntelliJ IDEA
- Maven
- Arquitetura em camadas
- Boas práticas de POO
