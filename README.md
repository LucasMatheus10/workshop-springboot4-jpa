# TechStore 🛒

A **TechStore** é uma aplicação full-stack de e-commerce desenvolvida como um projeto de estudo para demonstrar a integração entre um backend em **Spring Boot 3** e um frontend dinâmico em **Angular 21**. O projeto está totalmente conteinerizado utilizando **Docker**, facilitando a configuração e execução do ambiente de desenvolvimento.

## 🚀 Tecnologias e Dependências

### Backend (Java)

* **Java Version:** 21.

* **Framework:** Spring Boot 3.3.5.

* **Persistência:** Spring Data JPA com Hibernate.

* **Bases de Dados:** PostgreSQL (Produção/Dev) e H2 (Testes em memória).

* **Segurança:** Spring Security com autenticação Stateless via JWT (java-jwt 4.4.0).

* **Documentação:** Springdoc OpenAPI (Swagger UI).

* **Utilitários:** Lombok para redução de código boilerplate e Bean Validation para integridade dos dados.

### Frontend (Angular)

* **Framework:** Angular 21.0.5.

* **Scripts:** Utiliza Angular CLI para build, testes e execução do servidor de desenvolvimento.

* **Testes:** Configurado para utilizar Vitest como executor de testes unitários.

---

## 🛠️ Funcionalidades e Perfis de Acesso

A aplicação implementa um sistema de controle de acesso baseado em funções (**RBAC**), distinguindo as capacidades de usuários comuns e administradores.

### 👤 Perfil Usuário (ROLE_USER)

Destinado aos clientes da loja, permitindo o fluxo completo de compra:

* **Catálogo de Produtos:** Visualização de todos os itens disponíveis.

* **Filtro por Categorias:** Navegação simplificada através de categorias específicas.

* **Gestão de Pedidos:** Seleção de produtos e fechamento de pedidos.

* **Pagamento:** Fluxo para processamento e confirmação de pagamentos dos pedidos realizados.

### 🔑 Perfil Administrador (ROLE_ADMIN)

Possui acesso total às funcionalidades de usuário e a um painel de gerenciamento exclusivo:

* **Painel de Administração (Dashboard):** Visualização consolidada de métricas ou acessos rápidos.

* **Gerenciamento de Produtos:** Adicionar novos itens, editar informações existentes ou remover produtos do catálogo.

* **Gerenciamento de Categorias:** Criar, atualizar e excluir categorias de produtos.

* **Controle de Estoque e Preços:** Manutenção direta através da interface administrativa.

---

## 🔐 Segurança e Autenticação

* **JWT (JSON Web Token):** Todas as rotas sensíveis são protegidas. O token é gerado no login e deve ser enviado no cabeçalho das requisições subsequentes.

* **Guards & Interceptors:** No frontend, rotas administrativas são protegidas por guardas de rota que verificam o perfil do usuário antes de permitir o acesso.

---

## 🔑 Acesso Administrativo (Seed)

Para testar as funcionalidades de gerenciamento (CRUD de produtos e categorias), a aplicação já vem com um perfil administrador pré-configurado através do banco de dados:

* **Email:** john.doe@email.com

* **Senha:** password

> **Nota:** Os dados iniciais de produtos, categorias e usuários são carregados automaticamente pelo script `import.sql` sempre que a aplicação inicia no perfil de teste ou desenvolvimento.

---

## 📦 Como Executar o Projeto

### Pré-requisitos

* Docker instalado.

* Arquivo `.env` presente na raiz do projeto.

### Utilizando Docker Compose

Para subir toda a infraestrutura (Base de Dados, API e Frontend) de uma só vez, execute o seguinte comando na raiz do projeto:

```bash

docker-compose up -d

```

Após a inicialização, os serviços estarão disponíveis em:

- **Frontend:**

http://localhost:4200

- **Backend (API):**

http://localhost:8080

- **Swagger UI:**

http://localhost:8080/swagger-ui.html

---

## 🛠️ Desenvolvimento Local (Sem Docker)

Caso prefira rodar os serviços individualmente em sua máquina, siga os passos abaixo:

---

### 1️⃣ Base de Dados

O projeto está configurado para utilizar **PostgreSQL** em ambiente de desenvolvimento.

- Certifique-se de ter o PostgreSQL instalado e em execução localmente.

- Crie um banco de dados chamado **techstore**.

- O projeto também possui suporte ao H2 Database para execução rápida em memória durante testes.

## 2️⃣ Backend (Spring Boot)

1. Navegue até a pasta `backend`.

```bash

cd backend

```

2. Execute a aplicação utilizando o Maven Wrapper:

```bash

./mvnw spring-boot:run

```

## 3️⃣ Frontend (Angular)

1. Navegue até a pasta do frontend:

```bash

cd frontend/techstore

```

2. Instale as dependências do projeto:

```bash

npm install

```

3. Inicie a aplicação:

```bash

npm start

```

## 🧪 Testes

### Backend

Para executar os testes unitários e de integração utilizando **JUnit 5**:

```bash

./mvnw test

```

### Frontend

Para executar os testes unitários com o framework **Vitest**:

```bash

npm test

```

## 📂 Estrutura do Repositório

- `/backend`: API construída com Java 21 e Spring Boot

- `/frontend/techstore`: Interface de utilizador desenvolvida em Angular

- `docker-compose.yml`: Orquestração dos serviços (banco de dados, backend e frontend)

- `backend/src/main/resources/import.sql`: Script de carga inicial de dados para o banco
