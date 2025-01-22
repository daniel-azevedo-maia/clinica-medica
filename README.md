# Clínica Médica - API

## Descrição
Este é um projeto de API REST para gerenciar uma clínica médica. Ele permite o cadastro, consulta, atualização e remoção de médicos e pacientes, além de associar médicos a pacientes e vice-versa. A aplicação foi desenvolvida utilizando Java com Spring Boot e inclui uma interface em HTML, CSS e JavaScript.

---

## Estrutura do Projeto
- **Controller**: Contém as classes responsáveis pelo controle das requisições HTTP.
  - `MedicoController`: Gerencia operações relacionadas a médicos.
  - `PacienteController`: Gerencia operações relacionadas a pacientes.

- **Domain.Exception**: Contém exceções customizadas.
  - `EntidadeEmUsoException`
  - `EntidadeNaoEncontradaException`

- **Model**: Define as entidades da aplicação.
  - `Medico`
  - `Paciente`
  - `Especialidade`

- **Repository**: Contém as interfaces de persistência de dados.
  - `MedicoRepository`
  - `PacienteRepository`

- **Service**: Contém a lógica de negócios.
  - `MedicoService`
  - `PacienteService`

- **Resources**: Diretório contendo os arquivos estáticos e templates HTML para interagir com a API.

---

## Pré-requisitos
1. **Java 17**.
2. **Spring Boot 2.7.9**.
3. **PostgreSQL** como banco de dados.
4. **Maven** para gerenciamento de dependências.

---

## Configuração do Projeto
### 1. Configurar Banco de Dados
Crie um banco de dados no PostgreSQL com o nome desejado. Configure o arquivo `application.properties` com as seguintes variáveis:

```properties
# Configurações de conexão com o banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/SEU_BANCO_DE_DADOS
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Flyway
spring.flyway.enabled=true
```

### 2. Executar o Projeto
No terminal, navegue até o diretório raiz do projeto e execute os seguintes comandos:

```bash
mvn clean install
mvn spring-boot:run
```

---

## Endpoints Disponíveis
### Médicos
- `GET /medicos`: Lista todos os médicos.
- `POST /medicos`: Adiciona um novo médico.
- `GET /medicos/{crm}`: Busca um médico pelo CRM.
- `GET /medicos/nome/{nome}`: Busca médicos pelo nome.
- `PUT /medicos/{crm}`: Atualiza as informações de um médico.
- `DELETE /medicos/{crm}`: Remove um médico.
- `DELETE /medicos/{crm}/pacientes/{pacienteId}`: Remove um paciente de um médico.

### Pacientes
- `GET /pacientes`: Lista todos os pacientes.
- `POST /pacientes`: Adiciona um novo paciente.
- `POST /pacientes/{pacienteId}/adicionarmedico/{crm}`: Associa um médico a um paciente.
- `GET /pacientes/{pacienteId}`: Busca um paciente pelo ID.
- `GET /pacientes/nome/{nome}`: Busca pacientes pelo nome.
- `GET /pacientes/{pacienteId}/medicos`: Lista médicos associados a um paciente.
- `PUT /pacientes/{pacienteId}`: Atualiza as informações de um paciente.
- `DELETE /pacientes/{pacienteId}`: Remove um paciente.
- `DELETE /pacientes/{pacienteId}/medicos/{crm}`: Remove um médico de um paciente.

---

## Como Usar
1. **Subir o servidor**: Após configurar o banco de dados, inicie a aplicação.
2. **Acessar a API**: Utilize ferramentas como Postman ou cURL para consumir os endpoints.
3. **Interface HTML**: Acesse os arquivos HTML disponíveis no diretório `resources/static` para realizar testes visuais.

---

## Observações
- Este projeto é proprietário e não pode ser redistribuído sem autorização do autor.

---

## Autor
Este projeto foi desenvolvido por Daniel Azevedo.
