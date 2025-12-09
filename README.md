# Sboot API Wishlist

Guia rápido para baixar, configurar e executar a aplicação com Docker e acessar o Swagger.

## Pré-requisitos
- Docker e Docker Compose instalados
- Opcional (para build local sem Docker): Java 21 e Maven

## Baixar o projeto
1. Clone o repositório:
   - git clone https://github.com/IsabellyDiasLimaDev/sboot-api-wishlist.git
   - cd sboot-api-wishlist

## Configuração de ambiente
- O projeto não inclui credenciais. Use o arquivo .env.example como base:
  1. Copie: .env.example -> .env
  2. Preencha as variáveis:
     - MONGO_INITDB_ROOT_USERNAME
     - MONGO_INITDB_ROOT_PASSWORD
     - MONGO_INITDB_DATABASE (ex.: wishlistdb)
     - SPRING_DATA_MONGODB_URI (ex.: mongodb://<user>:<pass>@mongo:27017/<db>?authSource=admin)

Observações:
- O serviço do Mongo no Compose chama-se mongo.
- A aplicação lê SPRING_DATA_MONGODB_URI do ambiente.

## Executar com Docker Compose
1. Build e subida dos serviços:
   - docker compose build
   - docker compose up
2. A aplicação aguardará o Mongo estar saudável antes de iniciar.
3. A API ficará disponível em: http://localhost:8080

## Executar localmente (sem Docker)
1. Certifique-se de ter um MongoDB local rodando.
2. Exporte a variável de ambiente SPRING_DATA_MONGODB_URI ou configure no application.properties.
3. Build e run:
   - ./mvnw clean package
   - java -jar target/sboot-api-wishlist-0.0.1-SNAPSHOT.jar

## Swagger (API docs)
- Acesse a documentação interativa:
  - http://localhost:8080/swagger-ui/index.html
- Esquema OpenAPI (se disponível):
  - http://localhost:8080/v3/api-docs

## Coverage Report (IntelliJ)
- O relatório de cobertura gerado pelo IntelliJ está disponível na pasta htmlReport.
- Para abrir:
  - Via navegador: abra o arquivo htmlReport/index.html diretamente (duplo clique ou arraste para o navegador).
  - Via IntelliJ: View > Tool Windows > Project, navegue até htmlReport e abra index.html.
- Também há relatórios por namespace/classe em subpastas (ns-*/), todos acessíveis a partir do index.html.

## Troubleshooting
- Connection refused para o Mongo:
  - Verifique variáveis do .env e se o serviço mongo iniciou.
  - O Compose já inclui healthcheck e espera de porta.
- Mudou as variáveis?
  - Reinicie com: docker compose down && docker compose up -d --build

## Estrutura relevante
- Dockerfile: build da aplicação Java.
- docker-compose.yml: orquestra app + Mongo, healthcheck e ordem de inicialização.
- src/main/resources/application.properties: lê SPRING_DATA_MONGODB_URI do ambiente.
- .env.example: modelo de variáveis sem segredos.
