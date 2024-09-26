# Caju Challenge

## Contexto

 Esse projeto foi desenvolvido para submissão em etapa técnica na vaga de [Pessoa Desenvolvedora Back-End Sênior (Novos Negócios)](https://caju.gupy.io/jobs/7572535?jobBoardSource=gupy_public_page) da empresa [Caju](https://www.caju.com.br/) 

## Requisitos

- [X] Utilização de linguagem Scala, Kotlin ou com paradigma funcional a sua escolha
- [X] Banco de dados a sua escolha
- [X] Disponibilização de código
- [X] Criação de README claro e explicativo
- [X] Implementação de autorizador de transação recebendo o payload fornecido
- [X] Identificação de saldo a ser debitado com base no MCC fornecido no payload
- [X] Implementação de response no autorizador com códigos fornecido no body
- [X] Status de response sempre OK(200) no response do autorizador 
- [X] Manipulação de saldo ao autorizar uma transação
- [ ] Autorizador com fallback para MCCs não identificados
- [X] Implementação de testes automatizados
- [X] Sugerir ideia para questão aberta fornecida no teste

> [!WARNING]
> Sobre o autorizador com fallback para MCCs não identificados: Por questão do prazo, não tive tempo hábil pra pensar e implementar essa solução

> [!WARNING]
> Sobre testes automatizados: Por questão do prazo, não tive tempo hábil finalizar a cobertura em que gostaria e acharia justo para o desafio (em torno de 95% de cobertura), mas deixei alguns testes unitários  implementados. Além disso, gostaria de ter implementado testes de integração e testes de api, que acharia justo para o cargo também, porém não consegui a tempo.  

## Tecnologias utilizadas

- **Kotlin**
- **SpringBoot**
- **PostgreSQL**
- **Docker**

## Sobre o projeto

Pensando em entregar um projeto com mais conceitos demonstrados, extrapolei um pouco os requisitos solicitados e implementei conceitos de autenticação, usuário logado, saldos e algumas configurações. 
- Nesse projeto é necessário criar um usuário antes de utilizar, através da rota `POST /v1/users`. 
- Ao criar o usuário, será criado automaticamente os três saldos que o usuário deve ter (FOOD, MEAL e CASH). 
- Para autenticar o usuário, utilize a rota `POST /v1/auth/user` passando o email e senha, o que retornará um `accessToken` para ser utilizado com autorização `Bearer`. 
- Para adicionar saldo ao usuário, utilize a rota `POST /v1/balances/add-credits` passando o tipo de saldo (FOOD, MEAL ou CASH) e o valor que deseja **em centavos**. 
- Para verificar o saldo do usuário utilize a rota `GET /v1/users/me`. 
- Por fim, para realizar uma transação, utilize a rota `POST /v1/transactions`  
- Todas as rotas, exceto `POST v1/users`, `POST /v1/auth/user`, `POST /v1/transactions` são protegidas, sendo necessário passar o token `Bearer` no `Authorization` header

## Testes

Execute o comando `./gradlew test`

## Cobertura

Execute o comando `./gradlew test jacocoTestReport` e verifique o arquivo `build\coverage\index.html`. Segue cobertura atual

![image](https://github.com/user-attachments/assets/a8a9cabb-e43a-49d4-bf61-9d84111a75b7)


## Como rodar o projeto

Para executar de maneira fácil, existem duas formas:

### Docker
O projeto está dockerizado, portando você pode executar os comandos a seguir para rodar em um container:
```
# buildando docker
docker build -t caju-challenge:latest --build-arg JWT_SECRET=7c066a7b-c429-4cf2-ac7b-83dae682f7ed --build-arg DB_URL=jdbc:postgresql://<POSTGRES_HOST>:5432/<DB_NAME> --build-arg DB_PASSWORD=<DB_PASSWORD> --build-arg DB_USER=<DB_USER> .

# Executando imagem
docker run --name caju-challenge -p 8080:800 -d caju-challenge:latest
```

### Docker compose
Foi incluído o docker-compose no projeto que ja os networks com o PostgreSQL configurado, assim como as envs. Dessa forma, basta rodar o comando a seguir

```
# Executando docker compose
docker-compose up --build

```

## Documentação de endpoints

Foi incluído no projeto um arquivo chamado docs.yaml com todas as referências OpenAI dos endpoints, requests e responses que podem ser utilizadas como guia. Caso queira verificar em uma GUI, sugiro copiar e colar o arquivo no [SwaggerEditor](https://editor-next.swagger.io/)

# Questão aberta

Transações simultâneas: dado que o mesmo cartão de crédito pode ser utilizado em diferentes serviços online, existe uma pequena mas existente probabilidade de ocorrerem duas transações ao mesmo tempo. O que você faria para garantir que apenas uma transação por conta fosse processada em um determinado momento? Esteja ciente do fato de que todas as solicitações de transação são síncronas e devem ser processadas rapidamente (menos de 100 ms), ou a transação atingirá o timeout.

## Resposta
Para lidar com esse tipo de situação, assim como lidar com possíveis processamentos mais pesados, lidando com concorrência e sem impactar o usuário, eu utilizaria uma arquitetura com mensageria FIFO, possivelmente Kafka, RabbitMQ ou um AWS SQS/SNS.

Partindo dessa ideia, teriamos que implementar alguns conceitos. O primeiro ponto é que a transação não poderia ser retornada imediatamente aprovada/rejeitada. O endpoint para criar transação seria responsável apenas por verificar parâmetros, registrar a transação em banco, publicar uma mensagem em um tópico para informar que existe uma nova transação a ser processada e por fim, retornar ao usuário a transação com status PENDENTE, dessa forma, conseguimos nos aproximar o máximo possível de uma requisição em 100ms.

Com a mensagem publicada em um tópico com configuração FIFO, cada transação seria processada sequenciamente, garantindo a correta utilização de saldo e lidando com concorrência.

Após isso, seria muito importante avisar aos clients via implementação de webhooks e status final da transação, visto que eles receberam uma transação com status pendente. Uma vez que temos webhooks, o client entenderá que a transação foi finalizada e qual seu status final, podendo prosseguir com a autorização do seu lado.


![image](https://github.com/user-attachments/assets/2c2556c4-bf76-44af-9b9d-c9ec348a6d60)

