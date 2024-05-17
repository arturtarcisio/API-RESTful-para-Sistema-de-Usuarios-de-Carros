# API-RESTful-para-Sistema-de-Usuarios-de-Carros

## Desafio Backend

Este projeto é parte de um desafio de desenvolvimento de software, onde são implementadas diversas funcionalidades para gerenciar usuários e carros em um sistema. O projeto é uma API em Java e o frontend em Angular.

Abaixo segue histórias de usuários que foram implementadas:

### Histórias de Usuário
1. **HU.00** - Criacao do projeto backend
2. **HU.01** - Adicionando dependências springboot no pom
3. **HU.02** - Criando entidade carro
4. **HU.03** - Criando car repository
5. **HU.04** - Criando entidade user e user repository
6. **HU.05** - Configurando application.properties com h2 e massa de dados
7. **HU.06** - Criando associacao entre user e car e ajustando massa de dados
8. **HU.07** - Criando rota /api/users para listar todos os usuarios
9. **HU.08** - Criando rota /api/users para cadastrar usuarios e validacao se carro ja existe
10. **HU.09** - Criando rota /api/users/{id} para buscar usuario por id
11. **HU.10** - Criando rota /api/users/{id} para remover usuario
12. **HU.11** - Trocando os retornos dos endpoint
13. **HU.12** - Criando validacoes e exceptions
14. **HU.13** - Adicionando swagger ao projeto
15. **HU.14** - Adicionando descricao aos endpoint no swagger
16. **HU.15** - Adicionando spring security e configurando jwt
17. **HU.16** - Criando rota /api/signin para autenticar no sistema com token
18. **HU.17** - Criando massa de dados via CLR com senha criptografada
19. **HU.18** - Configurando tokenService para retornar dados do usuario do token e criacao de rota /api/me para retornar esses dados
20. **HU.19** - Configurando retornos para a rota /api/me quando token invalido ou nao enviado
21. **HU.20** - Incrementando a rota /api/me para trazer createdAt e lastLogin no objeto de retorno
22. **HU.21** - Criando rota /api/cars para listar carros do usuario logado
23. **HU.22** - Mudando mapeamento de entidade e criando funcionalidade de cadastrar um carro para usuario logado
24. **HU.22** - Criando funcionalidade uscar um carro do usuário logado pelo id
25. **HU.23** - Criando funcionalidade de remover um carro do usuário logado pelo id
26. **HU.24** - Criando funcionalidade de atualizar um carro do usuário logado pelo id
27. **HU.25** - Ajuste ao salvar usuario por consequencia do mapeamento
28. **HU.26** - Ajuste de codigo, em validacoes e nas exceptions

# Solução

A API foi desenvolvida em Java versão 22, trata-se de uma API RESTful para controle de usuários e carros.

## Tecnologias Utilizadas

- Spring Boot
- JWT
- Spring Security
- Spring Data JPA
- Criptografia de senha de usuário com BCrypt
- Banco de Dados H2
- Maven
- Tomcat
- Swagger

## Rotas Disponíveis

### Sem Autenticação:

- **POST /api/signin**: Esta rota espera um objeto com os campos login e password e retorna o token de acesso da API (JWT) com as informações do usuário logado.

- **GET /api/users**: Lista todos os usuários.
  
- **POST /api/users**: Cadastra um novo usuário.
  
- **GET /api/users/{id}**: Busca um usuário pelo ID.
  
- **DELETE /api/users/{id}**: Remove um usuário pelo ID.
  
- **PUT /api/users/{id}**: Atualiza um usuário pelo ID.

### Com Autenticação:

- **GET /api/me**: Retorna as informações do usuário logado (firstName, lastName, email, birthday, login, phone, cars) + createdAt (data da criação do usuário) + lastLogin (data da última vez que o usuário realizou login).

- **GET /api/cars**: Lista todos os carros do usuário logado.
  
- **POST /api/cars**: Cadastra um novo carro para o usuário logado.
  
- **GET /api/cars/{id}**: Busca um carro do usuário logado pelo ID.
  
- **DELETE /api/cars/{id}**: Remove um carro do usuário logado pelo ID.
  
- **PUT /api/cars/{id}**: Atualiza um carro do usuário logado pelo ID.

  ![image](https://github.com/arturtarcisio/API-RESTful-para-Sistema-de-Usuarios-de-Carros/assets/42079767/c1988dc2-83e1-4bcc-a779-7390cd62e4dd)
