# API Restaurante

API REST para gerenciamento de um restaurante, com cadastro de usuarios, mesas e reservas. O projeto tambem inclui um frontend simples em HTML, CSS e JavaScript puro, servido pelo proprio Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Maven

## Funcionalidades

- Cadastro, listagem, edicao e remocao de usuarios
- Cadastro, listagem, edicao e remocao de mesas
- Cadastro, listagem, edicao e cancelamento de reservas
- Controle de status de mesas
- Controle de status de reservas
- Autenticacao com JWT
- Frontend web integrado em `src/main/resources/static/index.html`

## Regras principais

### Status de mesa

Os status aceitos para mesas sao:

```text
disponivel
reservada
inativa
```

### Status de reserva

Os status aceitos para reservas sao:

```text
ativo
cancelado
```

### Perfis de usuario

Os perfis aceitos sao:

```text
cliente
administrador
```

## Configuracao do banco

O projeto usa PostgreSQL. Configure o arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurante
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

Crie o banco no PostgreSQL antes de iniciar a aplicacao:

```sql
CREATE DATABASE restaurante;
```

## Como rodar

Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

O frontend pode ser acessado em:

```text
http://localhost:8080/
```

## Autenticacao

O login retorna um token JWT.

### Login

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
  "token": "jwt_gerado_pela_api"
}
```

Para acessar rotas protegidas, envie o token no header:

```http
Authorization: Bearer jwt_gerado_pela_api
```

## Endpoints

### Autenticacao

| Metodo | Rota | Descricao |
| --- | --- | --- |
| POST | `/auth/login` | Realiza login e retorna JWT |
| GET | `/auth/me` | Retorna o usuario autenticado |

### Usuarios

| Metodo | Rota | Descricao |
| --- | --- | --- |
| POST | `/usuarios` | Cria um usuario |
| GET | `/usuarios` | Lista todos os usuarios |
| GET | `/usuarios/{id}` | Busca usuario por ID |
| GET | `/usuarios/nome/{nome}` | Busca usuarios por nome |
| GET | `/usuarios/email/{email}` | Busca usuario por email |
| GET | `/usuarios/roles/{roles}` | Busca usuarios por perfil |
| PATCH | `/usuarios/nome/{id}` | Atualiza apenas o nome |
| PATCH | `/usuarios/email/{id}` | Atualiza apenas o email |
| PATCH | `/usuarios/senha/{id}` | Atualiza apenas a senha |
| PATCH | `/usuarios/roles/{id}` | Atualiza perfil via JSON |
| PATCH | `/usuarios/{id}/roles/{roles}` | Atualiza perfil pela URL |
| PUT | `/usuarios/{id}` | Atualiza todos os dados |
| DELETE | `/usuarios/{id}` | Remove usuario |

Exemplo de criacao:

```json
{
  "nome": "Luan",
  "email": "luan@email.com",
  "senha": "12345",
  "roles": "administrador"
}
```

### Mesas

| Metodo | Rota | Descricao |
| --- | --- | --- |
| POST | `/mesas` | Cria uma mesa |
| GET | `/mesas` | Lista todas as mesas |
| GET | `/mesas/{id}` | Busca mesa por ID |
| GET | `/mesas/capacidade/{capacidade}` | Busca mesas por capacidade |
| GET | `/mesas/status/{status}` | Busca mesas por status |
| PATCH | `/mesas/numero/{id}` | Atualiza apenas o numero |
| PATCH | `/mesas/capacidade/{id}` | Atualiza apenas a capacidade |
| PATCH | `/mesas/status/{id}` | Atualiza status via JSON |
| PATCH | `/mesas/{id}/status/{status}` | Atualiza status pela URL |
| PUT | `/mesas/{id}` | Atualiza todos os dados |
| DELETE | `/mesas/{id}` | Remove mesa |

Exemplo de criacao:

```json
{
  "numero": 1,
  "capacidade": 4,
  "status": "disponivel"
}
```

### Reservas

| Metodo | Rota | Descricao |
| --- | --- | --- |
| POST | `/reservas` | Cria uma reserva com status `ativo` |
| GET | `/reservas` | Lista todas as reservas |
| GET | `/reservas/{id}` | Busca reserva por ID |
| GET | `/reservas/data/{dataReserva}` | Busca reservas por data |
| GET | `/reservas/mesa/{mesaId}` | Busca reservas por mesa |
| GET | `/reservas/usuario/{usuarioId}` | Busca reservas por usuario |
| GET | `/reservas/status/{status}` | Busca reservas por status |
| PATCH | `/reservas/datareserva/{id}` | Atualiza data da reserva |
| PATCH | `/reservas/status/{id}` | Atualiza status via JSON |
| PATCH | `/reservas/{id}/status/{status}` | Atualiza status pela URL |
| PATCH | `/reservas/{id}/cancelar` | Cancela uma reserva e libera a mesa |
| PATCH | `/reservas/{id}/reservar` | Reativa uma reserva e marca a mesa como reservada |
| PUT | `/reservas/{id}` | Atualiza usuario, mesa e data da reserva |
| DELETE | `/reservas/{id}` | Remove reserva e libera a mesa |

Exemplo de criacao:

```json
{
  "usuarioId": 1,
  "mesaId": 1,
  "dataReserva": "2026-05-27T20:30:00"
}
```

Ao criar uma reserva, a API define automaticamente:

```json
{
  "status": "ativo"
}
```

A mesa precisa estar com status `disponivel`. Se a mesa estiver `reservada` ou `inativa`, a API retorna erro.

## Frontend

O frontend esta em:

```text
src/main/resources/static/index.html
```

Ele e servido automaticamente pelo Spring Boot em:

```text
http://localhost:8080/
```

Tambem pode ser aberto diretamente no navegador. Nesse caso, o HTML tenta chamar a API em `http://localhost:8080`. A forma recomendada continua sendo acessar pelo proprio backend para evitar problemas de origem:

```text
http://localhost:8080/
```

## Testes

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

## Observacoes

- As rotas `GET` principais sao publicas.
- Rotas de alteracao, como `PUT`, `PATCH` e `DELETE`, exigem usuario autenticado com perfil `administrador`.
- O endpoint `POST /usuarios` esta liberado para permitir criar usuarios iniciais.
- Ao criar uma reserva, a reserva nasce como `ativo` e a mesa associada passa para `reservada`.
- Nao e permitido criar reserva em mesa `reservada` ou `inativa`.
- Ao cancelar uma reserva por `/reservas/{id}/cancelar`, a mesa associada passa para `disponivel`.
- Ao deletar uma reserva, a mesa associada tambem passa para `disponivel`.
- Ao reativar uma reserva por `/reservas/{id}/reservar`, a mesa precisa estar `disponivel`.
