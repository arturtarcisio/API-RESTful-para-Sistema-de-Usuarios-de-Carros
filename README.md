# API-RESTful-para-Sistema-de-Usuarios-de-Carros

Desafio Backend
Este projeto é parte de um desafio de desenvolvimento de software, onde são implementadas diversas funcionalidades para gerenciar usuários e carros em um sistema.
O projeto é uma API em Java e o frontend em Angular.

Abaixo segue histórias de usuários que foram implementadas:

Histórias de Usuário
HU.00 - Criacao do projeto backend
HU.01 - Adicionando dependências springboot no pom
HU.02 - Criando entidade carro
HU.03 - Criando car repository
HU.04 - Criando entidade user e user repository
HU.05 - Configurando application.properties com h2 e massa de dados
HU.06 - Criando associacao entre user e car e ajustando massa de dados
HU.07 - Criando rota /api/users para listar todos os usuarios
HU.08 - Criando rota /api/users para cadastrar usuarios e validacao se carro ja existe
HU.09 - Criando rota /api/users/{id} para buscar usuario por id
HU.10 - Criando rota /api/users/{id} para remover usuario
HU.11 - Trocando os retornos dos endpoint
HU.12 - Criando validacoes e exceptions
HU.13 - Adicionando swagger ao projeto
HU.14 - Adicionando descricao aos endpoint no swagger
HU.15 - Adicionando spring security e configurando jwt
HU.16 - Criando rota /api/signin para autenticar no sistema com token
HU.17 - Criando massa de dados via CLR com senha criptografada
HU.18 - Configurando tokenService para retornar dados do usuario do token e criacao de rota /api/me para retornar esses dados
HU.19 - Configurando retornos para a rota /api/me quando token invalido ou nao enviado
HU.20 - Incrementando a rota /api/me para trazer createdAt e lastLogin no objeto de retorno
HU.21 - Criando rota /api/cars para listar carros do usuario logado
HU.22 - Mudando mapeamento de entidade e criando funcionalidade de cadastrar um carro para usuario logado
HU.22 - Criando funcionalidade uscar um carro do usuário logado pelo id
HU.23 - Criando funcionalidade de remover um carro do usuário logado pelo id
HU.24 - Criando funcionalidade de atualizar um carro do usuário logado pelo id
HU.25 - Ajuste ao salvar usuario por consequencia do mapeamento
