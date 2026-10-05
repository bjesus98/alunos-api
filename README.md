# Sistema de Gestão de Alunos | Backend

API REST do Sistema de Gestão de Alunos, responsável por autenticação, autorização, persistência, validações, regras de negócio, paginação, busca, cadastro, atualização e exclusão lógica de alunos.

## Status do projeto

**Backend funcional, em desenvolvimento e adequado para demonstração local.**

Funcionalidades implementadas:

- autenticação com JWT;
- senhas protegidas com BCrypt;
- perfis Administrador e Leitura;
- autorização por método HTTP;
- cadastro de aluno;
- listagem paginada;
- ordenação por nome;
- busca por nome e início da matrícula;
- filtro por status;
- consulta de detalhes por ID;
- atualização de aluno;
- exclusão lógica;
- validação de entrada com Bean Validation;
- verificação de CPF e e-mail duplicados;
- tratamento global de exceções;
- banco H2;
- criação automática de usuários e alunos de demonstração.

Pendências e pontos de melhoria:

- revisar e consolidar os inicializadores de dados;
- substituir o status textual por enum;
- ampliar a cobertura de testes;
- mover segredos para variáveis de ambiente;
- configurar banco persistente para produção;
- documentar a API com OpenAPI/Swagger;
- adicionar tratamento para erros inesperados;
- revisar a busca e os filtros em todos os cenários de integração.

## Índice

- [Sobre o projeto](#sobre-o-projeto)
- [Nível de desenvolvimento](#nível-de-desenvolvimento)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Regras principais](#regras-principais)
- [Segurança e perfis](#segurança-e-perfis)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Como executar](#como-executar)
- [Como usar a API](#como-usar-a-api)
- [Endpoints](#endpoints)
- [Paginação, busca e filtros](#paginação-busca-e-filtros)
- [Validações](#validações)
- [Tratamento de exceções](#tratamento-de-exceções)
- [Dados iniciais](#dados-iniciais)
- [Testes](#testes)
- [Limitações conhecidas](#limitações-conhecidas)
- [Próximos passos](#próximos-passos)

## Sobre o projeto

O `alunos-api` é o backend do Sistema de Gestão de Alunos. A aplicação fornece endpoints REST protegidos por JWT e organiza o código em camadas para separar comunicação HTTP, regras de negócio e persistência.

Fluxo principal:

```text
Cliente HTTP
  -> Controller
  -> DTO e validações
  -> Service
  -> Repository
  -> JPA
  -> H2
  -> DTO de resposta
  -> JSON
```

O backend disponibiliza operações completas de CRUD. Na versão atual da solução, o frontend consome login, listagem, detalhes e cadastro. Os endpoints de atualização e exclusão já existem na API, mas ainda não possuem telas finais no frontend.

## Nível de desenvolvimento

O projeto possui nível de desenvolvimento **intermediário-avançado**, com:

- arquitetura em camadas;
- injeção de dependências;
- DTOs de entrada e saída;
- validação declarativa;
- transações;
- repository com JPQL;
- paginação e ordenação;
- exceções de negócio;
- tratamento global de erros;
- autenticação e autorização com JWT;
- dados iniciais para demonstração.

A aplicação é adequada para desenvolvimento, estudo, testes e apresentação local. Antes de produção, recomenda-se revisar segredos, persistência, observabilidade, testes, documentação da API e configuração de ambientes.

## Tecnologias utilizadas

- Java 21;
- Spring Boot 4.1.1;
- Spring Web MVC;
- Spring Data JPA;
- Spring Security;
- OAuth2 Resource Server para validação JWT;
- Bean Validation;
- H2 Database;
- Maven;
- BCrypt;
- JUnit e dependências de teste do Spring.

## Arquitetura

### Controller

Recebe requisições HTTP, converte JSON em DTO, chama o service e devolve respostas HTTP.

### Service

Contém as regras de negócio, como:

- validação de paginação;
- normalização de busca e status;
- verificação de duplicidade;
- geração de matrícula;
- cadastro, atualização e exclusão lógica;
- conversão de entidade para DTO.

### Repository

Acessa o banco por meio do Spring Data JPA. Inclui métodos derivados e consulta JPQL para busca, status, paginação e ordenação.

### Entity

A entidade `Aluno` representa a tabela `alunos` no banco.

### DTO

Os DTOs definem os contratos de entrada e saída da API, evitando exposição direta das entidades.

### Exception Handler

O tratamento global converte exceções em respostas JSON padronizadas.

### Config

As classes de configuração cuidam de JWT, Spring Security, CORS, codificação de senhas e inicialização de dados.

## Regras principais

### Cadastro

- nome obrigatório;
- CPF obrigatório e único;
- e-mail obrigatório e único;
- telefone obrigatório;
- matrícula gerada automaticamente;
- novo aluno criado com status `ATIVO`;
- novo registro criado com `ativo=true`.

### Matrícula

A matrícula é formada pelo ano atual e pelo ID com quatro posições.

Exemplo:

```text
Ano: 2026
ID: 19
Matrícula: 20260019
```

### Status e exclusão lógica

O projeto possui dois conceitos:

```text
status = ATIVO ou INATIVO
ativo = true ou false
```

O `status` representa a situação acadêmica exibida ao usuário. O campo `ativo` controla a exclusão lógica.

Exemplo de aluno inativo e ainda existente:

```text
status = INATIVO
ativo = true
```

Exemplo de registro excluído logicamente:

```text
ativo = false
```

As consultas principais retornam somente registros com `ativo=true`.

## Segurança e perfis

A API utiliza JWT assinado com HS256.

O login público é:

```http
POST /auth/login
```

As demais operações exigem:

```http
Authorization: Bearer TOKEN_JWT
```

### Administrador

Pode:

- consultar;
- cadastrar;
- atualizar;
- excluir.

### Leitura

Pode:

- listar;
- buscar;
- filtrar;
- consultar detalhes.

### Regras HTTP configuradas

```text
POST /auth/login        -> público
GET /alunos/**          -> ADMINISTRADOR ou LEITURA
POST /alunos/**         -> ADMINISTRADOR
PUT /alunos/**          -> ADMINISTRADOR
PATCH /alunos/**        -> ADMINISTRADOR
DELETE /alunos/**       -> ADMINISTRADOR
```

### Senhas

As senhas são armazenadas como hash BCrypt. A senha original não é salva de forma reversível.

### CORS

A origem local liberada é:

```text
http://localhost:4200
```

## Pré-requisitos

Instale:

- Java 21;
- Git;
- Maven, opcional se o Maven Wrapper estiver disponível;
- frontend `alunos-web`, opcional para uso pela interface gráfica;
- Bruno, Postman ou ferramenta equivalente, opcional para testes manuais.

Confira:

```bash
java --version
git --version
```

Se utilizar Maven instalado:

```bash
mvn --version
```

## Instalação

Clone o repositório:

```bash
git clone https://github.com/bjesus98/alunos-api.git
```

Acesse a pasta:

```bash
cd alunos-api
```

No Linux ou Git Bash, dê permissão ao wrapper se necessário:

```bash
chmod +x mvnw
```

Baixe dependências e compile:

```bash
./mvnw clean compile
```

No Windows PowerShell ou Prompt de Comando:

```powershell
mvnw.cmd clean compile
```

## Configuração

As configurações ficam em:

```text
src/main/resources/application.properties
```

A aplicação precisa definir uma chave JWT, por exemplo por variável de ambiente:

```text
JWT_SECRET=UMA_CHAVE_SEGURA_COM_TAMANHO_ADEQUADO
```

Evite versionar segredos reais no repositório.

A porta padrão utilizada pelo frontend é:

```text
8080
```

## Como executar

No Linux ou Git Bash:

```bash
./mvnw spring-boot:run
```

No Windows PowerShell ou Prompt de Comando:

```powershell
mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Para compilar:

```bash
./mvnw clean compile
```

Para empacotar:

```bash
./mvnw clean package
```

Para executar os testes:

```bash
./mvnw test
```

## Como usar a API

### Obter o token

Faça uma requisição:

```http
POST http://localhost:8080/auth/login
Content-Type: application/json
```

Body do Administrador:

```json
{
  "login": "admin",
  "senha": "Admin@123"
}
```

Body do perfil Leitura:

```json
{
  "login": "leitura",
  "senha": "Leitura@123"
}
```

A resposta possui estrutura semelhante a:

```json
{
  "token": "TOKEN_JWT",
  "tipo": "Bearer",
  "perfil": "ADMINISTRADOR",
  "expiracaoEmSegundos": 3600
}
```

Use o token nas requisições protegidas:

```http
Authorization: Bearer TOKEN_JWT
```

> As credenciais acima são apenas para demonstração local.

## Endpoints

### Listar alunos

```http
GET /alunos
```

Exemplo:

```text
GET /alunos?page=0&size=10&status=ATIVO
```

### Buscar aluno por ID

```http
GET /alunos/{id}
```

### Cadastrar aluno

```http
POST /alunos
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Nome do Aluno",
  "email": "aluno@email.com",
  "cpf": "12345678901",
  "telefone": "81999999999"
}
```

Resposta esperada:

```http
201 Created
```

### Atualizar aluno

```http
PUT /alunos/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Nome Atualizado",
  "email": "atualizado@email.com",
  "telefone": "81988888888",
  "status": "INATIVO"
}
```

### Excluir aluno logicamente

```http
DELETE /alunos/{id}
```

Resposta esperada:

```http
204 No Content
```

## Paginação, busca e filtros

### Paginação

```text
page = índice da página, iniciando em 0
size = quantidade de registros, entre 1 e 100
```

### Busca

O parâmetro `busca` pesquisa:

- nome, de forma parcial e sem diferenciar maiúsculas de minúsculas;
- início da matrícula.

Exemplo:

```text
GET /alunos?page=0&size=10&status=TODOS&busca=Amanda
```

### Status

Valores aceitos:

```text
ATIVO
INATIVO
TODOS
```

Regras:

```text
ATIVO   -> retorna status ATIVO
INATIVO -> retorna status INATIVO
TODOS   -> ignora o filtro de status
```

Quando nenhum status é enviado, o padrão atual é `ATIVO`.

### Ordenação

A resposta é ordenada por nome em ordem crescente, ignorando diferenças entre letras maiúsculas e minúsculas.

## Validações

### Cadastro

- nome entre 3 e 120 caracteres;
- e-mail válido, com até 150 caracteres;
- CPF com exatamente 11 números;
- telefone com 10 ou 11 números.

### Atualização

- nome entre 3 e 120 caracteres;
- e-mail válido;
- telefone com 10 ou 11 números;
- status `ATIVO` ou `INATIVO`.

### Regras de negócio

- CPF não pode estar duplicado;
- e-mail não pode estar duplicado;
- atualização não pode utilizar e-mail de outro aluno;
- aluno precisa existir e estar logicamente ativo;
- página não pode ser negativa;
- tamanho deve estar entre 1 e 100.

## Tratamento de exceções

A API utiliza um formato padronizado:

```json
{
  "status": 409,
  "erro": "Conflict",
  "mensagem": "Mensagem descritiva",
  "caminho": "/alunos"
}
```

Principais respostas:

```text
400 Bad Request   -> validação ou argumento inválido
401 Unauthorized  -> credenciais ou token inválido
403 Forbidden     -> usuário sem permissão
404 Not Found     -> aluno não encontrado
409 Conflict      -> CPF ou e-mail duplicado
```

## Dados iniciais

O projeto cria automaticamente:

- 25 alunos de demonstração;
- alunos com status Ativo e Inativo;
- usuário Administrador;
- usuário Leitura.

Existe também uma classe `DataLoader` simples além do `AlunoDataInitializer`. A recomendação é manter apenas um inicializador para evitar redundância e dependência da ordem de execução.

O banco H2 pode ser configurado em memória. Nesse caso, reiniciar o backend recria os dados iniciais e remove cadastros feitos durante a execução anterior.

## Testes

Execute:

```bash
./mvnw test
```

Áreas importantes para cobertura:

- cadastro válido;
- CPF duplicado;
- e-mail duplicado;
- validações dos DTOs;
- aluno inexistente;
- busca por nome;
- busca por matrícula;
- filtro por status;
- paginação;
- autorização dos perfis;
- login válido e inválido;
- exclusão lógica.

## Limitações conhecidas

- banco H2 voltado ao ambiente local;
- possível redundância entre `DataLoader` e `AlunoDataInitializer`;
- status representado como `String`, em vez de enum;
- matrícula exige duas gravações no cadastro;
- segredo JWT precisa ser externalizado com segurança;
- ausência de documentação OpenAPI/Swagger;
- ausência de observabilidade e logs estruturados;
- tratamento de erro inesperado pode ser ampliado;
- integração de busca e filtros precisa de revisão final;
- edição e exclusão ainda não estão disponíveis no frontend.

## Próximos passos

- remover o inicializador redundante;
- criar enum para status;
- ampliar testes unitários e de integração;
- documentar endpoints com OpenAPI;
- adicionar banco persistente para outros ambientes;
- criar perfis de configuração para desenvolvimento, teste e produção;
- externalizar segredos;
- melhorar logs e observabilidade;
- revisar busca combinada com status;
- integrar edição e exclusão no frontend;
- adicionar pipeline de integração contínua.
