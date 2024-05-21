# API-RESTful-para-Sistema-de-Usuarios-de-Carros

## Desafio Backend

Este projeto é parte de um desafio de desenvolvimento de software, onde são implementadas diversas funcionalidades para gerenciar usuários e carros em um sistema. O projeto é uma API em Java e o frontend em Angular.

Abaixo segue histórias de usuários que foram implementadas:

### Histórias de Usuário
HU.00 - Criacao do projeto backend <br>
HU.01 - Adicionando dependências springboot no pom <br>
HU.02 - Criando entidade carro <br>
HU.03 - Criando car repository <br>
HU.04 - Criando entidade user e user repository <br>
HU.05 - Configurando application.properties com h2 e massa de dados <br>
HU.06 - Criando associacao entre user e car e ajustando massa de dados <br>
HU.07 - Criando rota /api/users para listar todos os usuarios <br>
HU.08 - Criando rota /api/users para cadastrar usuarios e validacao se carro ja existe <br>
HU.09 - Criando rota /api/users/{id} para buscar usuario por id <br>
HU.10 - Criando rota /api/users/{id} para remover usuario <br>
HU.11 - Trocando os retornos dos endpoint <br> 
HU.12 - Criando validacoes e exceptions <br>
HU.13 - Adicionando swagger ao projeto <br>
HU.14 - Adicionando descricao aos endpoint no swagger <br>
HU.15 - Adicionando spring security e configurando jwt <br>
HU.16 - Criando rota /api/signin para autenticar no sistema com token <br>
HU.17 - Criando massa de dados via CLR com senha criptografada <br>
HU.18 - Configurando tokenService para retornar dados do usuario do token e criacao de rota /api/me para retornar esses dados <br>
HU.19 - Configurando retornos para a rota /api/me quando token invalido ou nao enviado <br>
HU.20 - Incrementando a rota /api/me para trazer createdAt e lastLogin no objeto de retorno <br>
HU.21 - Criando rota /api/cars para listar carros do usuario logado <br>
HU.22 - Mudando mapeamento de entidade e criando funcionalidade de cadastrar um carro para usuario logado <br>
HU.22 - Criando funcionalidade uscar um carro do usuário logado pelo id <br> 
HU.23 - Criando funcionalidade de remover um carro do usuário logado pelo id <br>
HU.24 - Criando funcionalidade de atualizar um carro do usuário logado pelo id <br>
HU.25 - Ajuste ao salvar usuario por consequencia do mapeamento <br>
HU.26 - Ajuste de codigo, em validacoes e nas exception <br>
HU28 - Alteração em toda a estrutura do security e como é gerado token JWT <br>
HU29 - Refatoração da parte da autenticação e criacao de javadoc para todo o projeto <br>
HU30 - Criacao de testes unitarios para meu servico de autenticacao <br>
HU31 - Criacao de testes unitarios para meu servico de carro <br>
HU32 - Criacao de testes unitarios para minhas funcionalidades de JWT <br>
HU33 - Criacao de testes unitarios para meu servico de usuarios <br>
HU34 - Ajuste na funcionalidade de atualizar carro pra usuario logado <br>
HU35 - Ajuste na funcionalidade de login para retornar exceção personalizada <br>
HU36 - Ajuste na funcionalidade de update de usuario <br>
HU37 - Ajuste na rota api/me erro de token <br>
HU38 - Resolvido problema de token com a rota me <br>
HU39 - Arquivo system.properties criado para deploy no heroku <br>
HU40 - Adicionando angular material ao projeto <br>
HU41 - Adicionando imports do material <br>
HU42 - Criando nav e adicionando rota <br>
HU43 - html e css para componente nav <br>
HU44 - Componente home criado <br>
HU45 - Rota para componente home <br>
HU46 - HTML e CSS para componente home <br>
HU47 - HTML e CSS para componente home <br>
HU48 - Componente header criado <br>
HU49 - HTML e CSS para header <br>
HU50 - Componente car-list criado <br>
HU51 - Rota para car-list criado <br>
HU52 - HTML e CSS do componente car-list <br>
HU53 - Criando componente de login e sua rota <br>
HU54 - HTML e CSS do componente de login <br>
HU55 - Validação de formularios para login <br>
HU56 - Adição do toastr ao projeto <br>
HU57 - Mensagem com toast ao logar <br>
HU58 - Componente about e rota criado <br>
HU59 - HTML e CSS do componente about <br>
HU60 - Autenticacao de login <br>
HU61 - Funcionalidade de logout <br>
HU62 - Criacao e configuracao de interceptor para requisicoes http <br>
HU63 - Finalizando componente car-list <br>
HU64 - Criando componente de car-create e sua rota <br>
HU65 - HTML e CSS para componente car-create <br>
HU66 - Finalizado funcionalidade de criar um car <br>
HU67 - Adicionando rotas no interceptor que não irão precisa de token de autenticacao <br>
HU68 - Botoes de acao para car-list <br>
HU69 - Componente car-update finalizado <br>
HU70 - Componente infoUser criado e HTML CSS ROTA <br>
HU71 - Componente car-delete criado e sua rota <br>
HU72 - Funcionalidade de deletar <br>
HU73 - Criando os servicos de user <br>
HU74 - Criado componente user-list <br>
HU74 - Criado componente user-create <br>
HU74 - Criado componente user-delete <br>
HU74 - Criado componente user-update <br>
HU76 - Bug fix do password do user <br>
HU77 - Bug fix de atualização de user <br>
HU78 - Alteracao de tipo de data e github na nav <br>
HU79 - Funcionalidade e componente de create account criado <br>
HU80 - Bugfix no cadastro de user <br>
HU81 - Bugfix relacionado a input de data <br>
HU81 - Projeto finalizado ultimos ajustes <br> 

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

Autenticação na api e na aplicação angular:

Você pode utilizar as seguintes credenciais:
Login : atcs
password: h3ll0

Login : mcsf
password: h3ll038475

- **GET /api/me**: Retorna as informações do usuário logado (firstName, lastName, email, birthday, login, phone, cars) + createdAt (data da criação do usuário) + lastLogin (data da última vez que o usuário realizou login).

- **GET /api/cars**: Lista todos os carros do usuário logado.
  
- **POST /api/cars**: Cadastra um novo carro para o usuário logado.
  
- **GET /api/cars/{id}**: Busca um carro do usuário logado pelo ID.
  
- **DELETE /api/cars/{id}**: Remove um carro do usuário logado pelo ID.
  
- **PUT /api/cars/{id}**: Atualiza um carro do usuário logado pelo ID.

  Exemplo de JSON para criação de usuário:
{ <br>
  "firstName": "Hello",<br>
  "lastName": "World",<br>
  "email": "hello@world.com",<br>
  "birthday": "1990-05-01",<br>
  "login": "hello.world",<br>
  "password": "h3ll0",<br>
  "phone": "988888888",<br>
  "cars": [<br>
    {<br>
    "carYear": 2018,<br>
    "licensePlate": "PDV-0625",<br>
    "model": "Audi",<br>
    "color": "White"<br>
    }<br>
  ]<br>
}<br>

Exemplo de JSON para autenticação:
{
  "login": "atcs",
  "password": "h3ll0"
}

  ![image](https://github.com/arturtarcisio/API-RESTful-para-Sistema-de-Usuarios-de-Carros/assets/42079767/c1988dc2-83e1-4bcc-a779-7390cd62e4dd)

  ### Passo a Passo para Executar uma Aplicação Spring Boot

#### 1. Configuração das Variáveis de Ambiente do Java:

Antes de tudo, é necessário verificar se o Java está instalado em sua máquina e configurar as variáveis de ambiente. Siga estes passos:

- **Verifique se o Java está instalado**: Abra o terminal (no Windows, utilize o prompt de comando) e digite o comando `java -version`. Se o Java estiver instalado, você verá a versão instalada.
  
- **Instale o Java, se necessário**: Se o Java não estiver instalado, você pode baixá-lo e instalá-lo no site oficial do [Java](https://www.java.com/).

- **Configurar as Variáveis de Ambiente**:
   - **No Windows**:
     - Pesquise por "Variáveis de Ambiente" na barra de busca do Windows e clique em "Editar as variáveis de ambiente do sistema".
     - Na janela que abrir, clique em "Variáveis de ambiente...".
     - Em "Variáveis do sistema", clique em "Novo..." e adicione uma nova variável chamada `JAVA_HOME` com o caminho para a pasta onde o Java está instalado (por exemplo, `C:\Program Files\Java\jdk1.8.0_311`).
     - Encontre a variável `Path` em "Variáveis do sistema" e clique em "Editar...".
     - Adicione `%JAVA_HOME%\bin` ao final do valor da variável `Path`.
     - Clique em "OK" para salvar as alterações.

   - **No macOS e Linux**:
     - Abra o terminal e edite o arquivo `.bash_profile` ou `.bashrc` (ou o arquivo de perfil correspondente) com seu editor de texto favorito.
     - Adicione a linha `export JAVA_HOME=/caminho/para/java` com o caminho para a pasta onde o Java está instalado.
     - Adicione a linha `export PATH=$PATH:$JAVA_HOME/bin`.
     - Salve e feche o arquivo.
     - Execute o comando `source ~/.bash_profile` ou `source ~/.bashrc` para aplicar as alterações.

#### 2. Clonando o Repositório:

Agora que o Java está configurado, você pode clonar o repositório da aplicação Spring Boot. Siga estes passos:

1. Abra o terminal.
2. Navegue até o diretório onde deseja clonar o repositório.
3. Execute o seguinte comando:
git clone https://github.com/arturtarcisio/API-RESTful-para-Sistema-de-Usuarios-de-Carros.git

4. Aguarde até que o processo de clonagem seja concluído.

#### 3. Executando a Aplicação:

Com o repositório clonado, você pode executar a aplicação Spring Boot. Siga estes passos:

1. Navegue até o diretório da aplicação clonada.
cd API-RESTful-para-Sistema-de-Usuarios-de-Carros

2. Execute o seguinte comando para iniciar a aplicação:
./mvnw spring-boot:run

Este comando irá compilar e executar a aplicação Spring Boot.

3. Aguarde até que a compilação seja concluída e a aplicação seja iniciada.

4. Uma vez iniciada, você verá mensagens indicando que a aplicação está rodando e pronta para receber requisições.

Agora a aplicação está em execução em sua máquina e você pode acessar os endpoints conforme documentado no README do projeto.

