# API-RESTful-para-Sistema-de-Usuarios-de-Carros

Desafio Backend
Este projeto é parte de um desafio de desenvolvimento de software, onde são implementadas diversas funcionalidades para gerenciar usuários e carros em um sistema.
O projeto é uma API em Java e o frontend em Angular.

Histórias de Usuário
HU.00 - Criacao do projeto backend

Início do projeto backend, configurando as dependências básicas.
HU.01 - Adicionando dependências springboot no pom

Adição das dependências do Spring Boot no arquivo pom.xml.
HU.02 - Criando entidade carro

Implementação da entidade Carro com os atributos necessários.
HU.03 - Criando car repository

Criação do repositório para a entidade Carro.
HU.04 - Criando entidade user e user repository

Criação da entidade Usuário e seu repositório correspondente.
HU.05 - Configurando application.properties com h2 e massa de dados

Configuração do arquivo application.properties para usar o banco de dados H2 e adição de dados iniciais.
HU.06 - Criando associacao entre user e car e ajustando massa de dados

Implementação da associação entre Usuário e Carro e ajustes na massa de dados.
HU.07 - Criando rota /api/users para listar todos os usuarios

Desenvolvimento da rota para listar todos os usuários cadastrados.
HU.08 - Criando rota /api/users para cadastrar usuarios e validacao se carro ja existe

Implementação da rota para cadastrar usuários e validação se o carro já existe.
HU.09 - Criando rota /api/users/{id} para buscar usuario por id

Desenvolvimento da rota para buscar um usuário por ID.
HU.10 - Criando rota /api/users/{id} para remover usuario

Implementação da rota para remover um usuário por ID.
HU.11 - Trocando os retornos dos endpoint

Alteração dos retornos dos endpoints para utilizar ResponseEntity.
HU.12 - Criando validacoes e exceptions

Implementação de validações e exceções para melhor tratamento de erros.
HU.13 - Adicionando swagger ao projeto

Integração do Swagger para documentação da API.
HU.14 - Adicionando descricao aos endpoint no swagger

Adição de descrições aos endpoints no Swagger.
HU.15 - Adicionando spring security e configurando jwt

Implementação do Spring Security e configuração do JWT para autenticação.
HU.16 - Criando rota /api/signin para autenticar no sistema com token

Desenvolvimento da rota para autenticar no sistema e obter um token JWT.
HU.17 - Criando massa de dados via CLR com senha criptografada

Criação de dados iniciais através do CommandLineRunner com senhas criptografadas.
HU.18 - Configurando tokenService para retornar dados do usuario do token e criacao de rota /api/me para retornar esses dados

Configuração do TokenService para retornar dados do usuário a partir do token e implementação da rota para retornar esses dados.
HU.19 - Configurando retornos para a rota /api/me quando token invalido ou nao enviado

Implementação de tratamentos de retorno para a rota /api/me quando o token é inválido ou não é enviado.
HU.20 - Incrementando a rota /api/me para trazer createdAt e lastLogin no objeto de retorno

Adição das informações createdAt e lastLogin ao retorno da rota /api/me.
HU.21 - Criando rota /api/cars para listar carros do usuario logado

Implementação da rota para listar os carros do usuário logado.
HU.22 - Mudando mapeamento de entidade e criando funcionalidade de cadastrar um carro para usuario logado

Alteração no mapeamento da entidade Carro e criação da funcionalidade para cadastrar um carro para o usuário logado.
HU.22 - Criando funcionalidade uscar um carro do usuário logado pelo id

Desenvolvimento da funcionalidade para buscar um carro do usuário logado pelo ID.
HU.23 - Criando funcionalidade de remover um carro do usuário logado pelo id

Implementação da funcionalidade para remover um carro do usuário logado pelo ID.
