# CadastroEE – Sistema de Cadastro de Produtos

Projeto desenvolvido para a disciplina **Desenv. Back-end Corporativo Com Java e Cloud**, com o objetivo de implementar uma aplicação Web em Java EE/Jakarta EE para cadastro e listagem de produtos, utilizando arquitetura em camadas, JPA, EJB, Servlets, JSP e Bootstrap.

## 👨‍🎓 Informações do aluno

- **Aluno:** Edson Victor Miranda de Oliveira  
- **Matrícula:** 2024 0836 7775 
- **Campus:** POLO PARANGABA - FORTALEZA/CE  
- **Disciplina:** Desenv. Back-end Corporativo Com Java e Cloud  
- **Semestre letivo:** 2025/3

---

## 🎯 Objetivo do projeto

Desenvolver uma aplicação Web corporativa com:

- Camada de **persistência** usando **JPA** (mapeamento objeto–relacional) conectada ao banco **SQL Server** (`loja`).
- Camada de **negócio** com **EJB (Session Beans)** para encapsular regras de negócio e operações de CRUD.
- Camada de **controle** utilizando **Servlets** no padrão **Front Controller**.
- Camada de **visão** com **JSPs** e páginas responsivas usando **Bootstrap**.
- Funcionalidade principal: **CRUD de produtos** (cadastrar, listar, editar e excluir).

---

## 🧱 Tecnologias utilizadas

- **Jakarta EE 8 / Java EE**
- **JPA / Hibernate** (mapeamento ORM)
- **EJB (Session Beans)**
- **Servlets e JSP**
- **Bootstrap 5 (via CDN)**
- **Servidor de aplicação:** GlassFish 6.2.x  
- **Banco de dados:** SQL Server (`loja`)  
- **IDE:** NetBeans

---

## 🧩 Arquitetura do projeto

O projeto foi criado como uma **Enterprise Application** Ant no NetBeans, gerando três módulos:

1. **CadastroEE**  
   - Projeto principal, empacotado como **EAR**, responsável por agrupar os módulos EJB e Web.

2. **CadastroEE-ejb** (camadas de persistência e negócio)  
   - Pacote `cadastroee.model`: entidades JPA geradas a partir do banco `loja`.  
     - Ex.: `Produto` (id, nome, quantidade, precoVenda, etc.).
   - Pacote `cadastroee.controller`: Session Beans (EJB) gerados para as entidades.  
     - Ex.: `ProdutoFacade` e sua interface `ProdutoFacadeLocal`.  
   - Arquivo `persistence.xml` configurado com:
     - `persistence-unit name="CadastroEE-ejbPU"`
     - `jta-data-source` apontando para o JNDI `jdbc/loja`.

3. **CadastroEE-war** (camada Web)  
   - Pacote `cadastroee.servlets`:
     - `ServletProduto`: Servlet de teste para listar produtos em HTML simples.
     - `ServletProdutoFC`: **Front Controller** responsável por orquestrar todas as ações de CRUD.
   - Páginas JSP:
     - `ProdutoLista.jsp`: tela de listagem de produtos.
     - `ProdutoDados.jsp`: tela de formulário para inclusão/alteração.
   - Configuração em `web.xml` mapeando os Servlets.

A comunicação entre as camadas funciona assim:

- O **Servlet** injeta o EJB via `@EJB ProdutoFacadeLocal facade;`
- O Servlet trata a requisição (parâmetros, ação) e chama os métodos do EJB.
- O EJB utiliza **JPA** para acessar o banco de dados SQL Server.
- O Servlet coloca objetos como **atributos** na requisição e faz `forward` para as JSPs.
- As JSPs exibem os dados, usando Bootstrap para o layout.

---

## 🌐 Endpoints principais e fluxo da aplicação

### 1. Teste de listagem simples

- **URL:**  
  `http://localhost:8080/CadastroEE-war/ServletProduto`
- **Descrição:**  
  Servlet simples utilizado para testar a integração entre Servlet, EJB e JPA.  
  Ele consulta todos os produtos via `ProdutoFacade` e exibe a lista em HTML básico.

---

### 2. Front Controller – ServletProdutoFC

Todas as funcionalidades do cadastro passam pelo endpoint:

- **URL base:**  
  `http://localhost:8080/CadastroEE-war/ServletProdutoFC`

O comportamento é controlado pelo parâmetro **`acao`**:

#### 🔹 Listar produtos
- **Endpoint:**  
  `GET /CadastroEE-war/ServletProdutoFC?acao=listar`
- **O que faz:**  
  - Chama `facade.findAll()` para buscar todos os produtos.  
  - Armazena a lista em um atributo da requisição, ex.: `request.setAttribute("lista", listaProdutos);`  
  - Faz **forward** para `ProdutoLista.jsp`, que monta a tabela com os dados.

#### 🔹 Abrir formulário de inclusão
- **Endpoint:**  
  `GET /CadastroEE-war/ServletProdutoFC?acao=formIncluir`
- **O que faz:**  
  - Apenas define o destino como `ProdutoDados.jsp`.  
  - A JSP entende que se não veio entidade, a ação é **incluir**.  
  - Exibe o formulário vazio para cadastro de um novo produto.

#### 🔹 Incluir produto (POST)
- **Endpoint:**  
  `POST /CadastroEE-war/ServletProdutoFC`
- **Parâmetros esperados (form):**
  - `acao=incluir`
  - `nome`
  - `quantidade`
  - `precoVenda`
- **O que faz:**  
  - Cria um novo objeto `Produto`.  
  - Preenche com os dados recebidos via `request.getParameter(...)`.  
  - Chama `facade.create(produto)`.  
  - Após salvar, monta novamente a lista e faz forward para `ProdutoLista.jsp`.

#### 🔹 Abrir formulário de alteração
- **Endpoint (link gerado na listagem):**  
  `GET /CadastroEE-war/ServletProdutoFC?acao=formAlterar&id=XXX`
- **O que faz:**  
  - Recupera o `id` recebido como parâmetro.  
  - Consulta o produto via `facade.find(id)`.  
  - Coloca a entidade como atributo do request.  
  - Faz forward para `ProdutoDados.jsp`, que exibe o formulário preenchido para edição.

#### 🔹 Alterar produto (POST)
- **Endpoint:**  
  `POST /CadastroEE-war/ServletProdutoFC`
- **Parâmetros esperados (form):**
  - `acao=alterar`
  - `id`
  - `nome`
  - `quantidade`
  - `precoVenda`
- **O que faz:**  
  - Busca o produto atual pelo `id`.  
  - Atualiza os campos com os valores do formulário.  
  - Chama `facade.edit(produto)`.  
  - Atualiza a lista e encaminha para `ProdutoLista.jsp`.

#### 🔹 Excluir produto
- **Endpoint (link gerado na listagem):**  
  `GET /CadastroEE-war/ServletProdutoFC?acao=excluir&id=XXX`
- **O que faz:**  
  - Recupera o `id`.  
  - Busca a entidade (`facade.find(id)`) e chama `facade.remove(produto)`.  
  - Atualiza a lista e faz forward para `ProdutoLista.jsp`.

---

## 🗂️ Estrutura do projeto (resumo)

- `CadastroEE/` – projeto EAR (empacotamento).
- `CadastroEE-ejb/`
  - `src/java/cadastroee/model` – entidades JPA (ex.: `Produto.java`).
  - `src/java/cadastroee/controller` – Session Beans (`ProdutoFacade`, `ProdutoFacadeLocal`, etc.).
  - `META-INF/persistence.xml` – configuração da unidade de persistência (`CadastroEE-ejbPU`).
- `CadastroEE-war/`
  - `src/java/cadastroee/servlets` – `ServletProduto` e `ServletProdutoFC`.
  - `web/ProdutoLista.jsp` – listagem de produtos com tabela Bootstrap.
  - `web/ProdutoDados.jsp` – formulário de inclusão/alteração com Bootstrap.
  - `WEB-INF/web.xml` – mapeamento dos servlets.

- `RelatorioMissaoPratica.pdf` – relatório da Missão Prática (anexado no Git).

---

## 🗄️ Configuração de banco de dados

- **Banco:** SQL Server  
- **Nome do banco:** `loja`  
- **Conexão (GlassFish / Pool):**
  - Pool de conexões: `SQLServerPool`
  - Recurso JDBC (JNDI): `jdbc/loja`
- **Exemplo de URL JDBC:**  
  `jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true;`

É necessário que o banco `loja` esteja criado e populado com a tabela de **Produto** (e demais tabelas da prática anterior) para o sistema funcionar corretamente.

---

## 🚀 Como executar o projeto

1. Importar o projeto **Enterprise Application** no NetBeans.
2. Garantir que o GlassFish 6.2.x esteja configurado no NetBeans.
3. Configurar o pool `SQLServerPool` e o resource `jdbc/loja` no GlassFish.
4. Verificar o `persistence.xml` (unidade `CadastroEE-ejbPU` apontando para `jdbc/loja`).
5. Fazer **Run** ou **Deploy** no projeto principal `CadastroEE`.
6. Acessar no navegador:
   - Teste simples:  
     `http://localhost:8080/CadastroEE-war/ServletProduto`
   - Sistema completo (listagem):  
     `http://localhost:8080/CadastroEE-war/ServletProdutoFC?acao=listar`

---

## 📄 Relatório da prática

O relatório em PDF contendo os procedimentos, análises e respostas teóricas está anexado no repositório com o nome:

`RelatorioMissaoPratica-BACKENDJAVACLOUD-EDSON-VICTOR.pdf`