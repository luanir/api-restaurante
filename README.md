# Helpdesk

API REST e frontend simples para gerenciamento de chamados de suporte. O projeto usa Spring Boot, Spring Security com JWT, JPA/Hibernate e PostgreSQL. O frontend fica em um unico arquivo HTML servido pelo proprio Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven Wrapper
- HTML, CSS e JavaScript embutidos em `index.html`

## Funcionalidades

- Cadastro e consulta de usuarios
- Cadastro e consulta de categorias
- Abertura, edicao, fechamento, reabertura e exclusao de chamados
- Cadastro, edicao, consulta e exclusao de comentarios
- Login com JWT
- Frontend operacional em `src/main/resources/static/index.html`

## Requisitos

- JDK 17 instalado
- PostgreSQL rodando localmente
- Banco de dados `helpdesk` criado
- Maven Wrapper do projeto, via `mvnw.cmd` no Windows ou `./mvnw` no Linux/macOS

## Configuracao do Banco

As configuracoes atuais estao em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/helpdesk
spring.datasource.username=postgres
spring.datasource.password=luan
spring.jpa.hibernate.ddl-auto=update
```

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE helpdesk;
```

Se o seu usuario, senha ou porta forem diferentes, ajuste o arquivo `application.properties`.

## Como Rodar

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

Depois acesse:

```text
http://localhost:8080/
```

A API tambem fica disponivel em:

```text
http://localhost:8080
```

## Frontend

O frontend fica em:

```text
src/main/resources/static/index.html
```

Ele nao usa CSS ou JavaScript externo. Ao rodar a aplicacao, o Spring Boot serve a tela inicial em `/`.

Na lateral da tela existe o campo `Base da API`. Normalmente ele fica como `http://localhost:8080`. Se abrir o HTML fora do Spring Boot, ajuste esse campo para apontar para a API.

## Autenticacao

Login:

```http
POST /auth/login
```

Body:

```json
{
  "email": "admin@email.com",
  "senha": "12345"
}
```

Resposta:

```json
{
  "token": "jwt-gerado-pela-api"
}
```

Para endpoints protegidos, envie:

```http
Authorization: Bearer jwt-gerado-pela-api
```

Verificar usuario autenticado:

```http
GET /auth/me
```

## Regras de Acesso

As regras principais configuradas em `SecurityConfig` sao:

| Recurso | Regra |
| --- | --- |
| `GET /usuarios/**` | Publico |
| `POST/PUT/PATCH/DELETE /usuarios/**` | `ADMIN` |
| `GET /chamados/**` | Publico |
| `POST/PUT/PATCH/DELETE /chamados/**` | `TECNICO` ou `ADMIN` |
| `/comentarios/**` | Publico |
| `GET /categorias/**` | Publico |
| `POST/PUT/DELETE /categorias/**` | `TECNICO` ou `ADMIN` |
| `/auth/login` | Publico |
| `/auth/me` | Autenticado |

## Endpoints Principais

### Usuarios

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| `POST` | `/usuarios` | Cria usuario |
| `GET` | `/usuarios` | Lista usuarios |
| `GET` | `/usuarios/{id}` | Busca por ID |
| `GET` | `/usuarios/email/{email}` | Busca por email |
| `GET` | `/usuarios/nome/{nome}` | Busca por nome |
| `GET` | `/usuarios/role/{role}` | Busca por perfil |
| `PUT` | `/usuarios/{id}` | Atualiza usuario |
| `DELETE` | `/usuarios/{id}` | Remove usuario |

Payload de criacao/edicao:

```json
{
  "nome": "Luan",
  "email": "luan@email.com",
  "senha": "12345",
  "role": "ADMIN"
}
```

Roles:

```text
CLIENTE, TECNICO, ADMIN
```

### Categorias

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| `POST` | `/categorias` | Cria categoria |
| `GET` | `/categorias` | Lista categorias |
| `GET` | `/categorias/{id}` | Busca por ID |
| `GET` | `/categorias/nome/{nome}` | Busca por nome |
| `PUT` | `/categorias/{id}` | Atualiza categoria |
| `DELETE` | `/categorias/{id}` | Remove categoria |

Payload:

```json
{
  "nome": "Hardware",
  "descricao": "Problemas com equipamentos"
}
```

### Chamados

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| `POST` | `/chamados` | Cria chamado |
| `GET` | `/chamados` | Lista chamados |
| `GET` | `/chamados/{id}` | Busca por ID |
| `GET` | `/chamados/titulo/{titulo}` | Busca por titulo |
| `GET` | `/chamados/tecnico/{tecnicoId}` | Busca por tecnico |
| `GET` | `/chamados/cliente/{clienteId}` | Busca por cliente |
| `GET` | `/chamados/categoria/{categoriaId}` | Busca por categoria |
| `GET` | `/chamados/prioridade/{prioridade}` | Busca por prioridade |
| `GET` | `/chamados/status/{status}` | Busca por status |
| `GET` | `/chamados/dataabertura/{dataAbertura}` | Busca por data de abertura |
| `GET` | `/chamados/datafechamento/{dataFechamento}` | Busca por data de fechamento |
| `PATCH` | `/chamados/{id}/fechar` | Fecha chamado |
| `PATCH` | `/chamados/{id}/abrir` | Reabre chamado |
| `PUT` | `/chamados/{id}` | Atualiza chamado |
| `DELETE` | `/chamados/{id}` | Remove chamado |

Payload:

```json
{
  "titulo": "Notebook nao liga",
  "descricao": "Cliente informou que o notebook nao liga.",
  "prioridade": "ALTA",
  "clienteId": 1,
  "tecnicoId": 2,
  "categoriaId": 3
}
```

Prioridades:

```text
BAIXA, MEDIA, ALTA, URGENTE
```

Status:

```text
ABERTO, EM_ANDAMENTO, RESOLVIDO, FECHADO, CANCELADO
```

### Comentarios

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| `POST` | `/comentarios` | Cria comentario |
| `GET` | `/comentarios` | Lista comentarios |
| `GET` | `/comentarios/chamado/{chamadoId}` | Busca por chamado |
| `GET` | `/comentarios/usuario/{usuarioId}` | Busca por usuario |
| `GET` | `/comentarios/datacomentario/{dataComentario}` | Busca por data |
| `PUT` | `/comentarios/{id}` | Atualiza comentario |
| `DELETE` | `/comentarios/{id}` | Remove comentario |

Payload de criacao/edicao:

```json
{
  "mensagem": "Atendimento iniciado.",
  "chamadosId": 1,
  "usuarioId": 2
}
```

Observacao: no request o campo esperado e `chamadosId`. Na resposta JSON, o getter atual expoe o ID do chamado como `chamadoId`.

## Estrutura do Projeto

```text
src/main/java/com/luan/helpdesk
  config/        Configuracoes de seguranca
  controller/    Controllers REST
  dto/           Objetos de entrada e saida da API
  entity/        Entidades JPA
  enums/         Roles, prioridades e status
  repository/    Repositorios Spring Data
  security/      JWT e filtro de autenticacao
  service/       Regras de negocio

src/main/resources
  application.properties
  static/index.html
```

## Testes e Build

Rodar testes:

```powershell
.\mvnw.cmd test
```

Gerar build:

```powershell
.\mvnw.cmd clean package
```

No Linux/macOS, use `./mvnw` no lugar de `.\mvnw.cmd`.

## Observacao Sobre Dependencia Local

O `pom.xml` atual possui a dependencia:

```xml
<dependency>
  <groupId>com.restaurante</groupId>
  <artifactId>restaurante</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Se o Maven falhar dizendo que nao encontrou `com.restaurante:restaurante:0.0.1-SNAPSHOT`, instale essa dependencia no repositorio Maven local ou remova-a do `pom.xml` caso ela nao seja mais usada pelo projeto.

## Problemas Comuns

### Erro ao conectar no banco

Confira se o PostgreSQL esta rodando e se o banco `helpdesk` existe.

### Erro 401 ou 403

Faca login pelo frontend e use um usuario com o perfil exigido pelo endpoint. Chamados e categorias exigem `TECNICO` ou `ADMIN` para mutacoes. Usuarios exigem `ADMIN` para criacao, edicao e exclusao.

### Frontend nao carrega dados

Confira o campo `Base da API` na lateral da tela. Ele deve apontar para a URL onde o Spring Boot esta rodando, normalmente:

```text
http://localhost:8080
```
