# CadastroEE – Sistema de Cadastro de Produtos

Projeto desenvolvido para a disciplina **Backend Java Cloud**, com o objetivo de implementar uma aplicação Web em Java EE para cadastro e listagem de produtos, utilizando arquitetura em camadas, JPA, EJB, Servlets, JSP e Bootstrap.

## 👨‍🎓 Informações do aluno

- **Aluno:** Edson Victor Miranda de Oliveira  
- **Matrícula:** 2024 0836 7775 
- **Campus:** POLO PARANGABA - FORTALEZA/CE  
- **Disciplina:** Desenv. Back-end Corporativo Com Java e Cloud  
- **Semestre letivo:** 2025/3

## 🎯 Objetivo do projeto

Desenvolver uma aplicação Web corporativa com:

- Camada de **persistência** usando **JPA** (mapeamento objeto-relacional).
- Camada de **negócio** com **EJB (Session Beans)**.
- Camada de **controle** utilizando **Servlets** no padrão **Front Controller**.
- Camada de **visão** com **JSPs** e páginas responsivas usando **Bootstrap**.
- Funcionalidade principal: **CRUD de produtos** (cadastrar, listar, editar e excluir).

## 🧱 Tecnologias utilizadas

- **Java EE**
- **JPA / Hibernate**
- **EJB (Session Beans)**
- **Servlets e JSP**
- **Bootstrap**
- **Servidor de aplicação**: GlassFish / Payara (ou o que você usou)
- **IDE**: NetBeans

## 🗂️ Estrutura do projeto (resumo)

- `src/java` – classes Java (Entidades, DAOs, Session Beans, Servlets)
- `web/` – páginas JSP, arquivos HTML, CSS e recursos estáticos
- `META-INF` / `WEB-INF` – configurações da aplicação
- `RelatorioMissaoPratica.pdf` – relatório da Missão Prática (anexado no Git)
  
## 🚀 Como executar o projeto

1. Importar o projeto no **NetBeans**.
2. Configurar o servidor de aplicação (GlassFish/Payara).
3. Configurar a conexão com o banco de dados no `persistence.xml`.
4. Fazer o **deploy** do projeto pelo NetBeans.
5. Acessar no navegador, por exemplo:  
   `http://localhost:8080/CadastroEE`

## 📄 Relatório da prática

O relatório em PDF contendo os procedimentos, análises e respostas teóricas está anexado no repositório com o nome:

`RelatorioMissaoPratica-BACKENDJAVACLOUD-EDSON-VICTOR.pdf`