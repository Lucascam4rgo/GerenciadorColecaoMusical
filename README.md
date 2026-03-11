# Gerenciador de Coleção Musical

Sistema de gerenciamento de coleção musical desenvolvido em Java, com persistência de dados em PostgreSQL via JDBC. O projeto aplica o padrão de arquitetura DAO (Data Access Object) para separação de responsabilidades entre as camadas de acesso a dados, regras de negócio e apresentação.

> **Em desenvolvimento** — CRUD de artistas, álbuns e faixas já implementado. Funcionalidades de busca avançada e interface de usuário em progresso.

---

## Tecnologias utilizadas

- **Java** (JDK 21)
- **PostgreSQL**
- **JDBC** — conexão e manipulação direta do banco de dados
- **Git / GitHub** — versionamento

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
src/
├── app/            # Ponto de entrada da aplicação (Program.java)
├── db/             # Gerenciamento de conexão com o banco (DB.java) e exceções customizadas
├── model/
│   ├── entities/   # Entidades: Artist, Album, Track
│   ├── dao/        # Interfaces DAO
│   └── dao/impl/   # Implementações JDBC dos DAOs
└── service/        # Camada de serviço com regras de negócio
```

**Padrões aplicados:**
- **DAO Pattern** — abstração do acesso ao banco via interfaces, desacoplando a lógica de negócio da implementação JDBC
- **Factory Pattern** — `DaoFactory` centraliza a criação dos DAOs
- **Layered Architecture** — separação clara entre entidades, acesso a dados e serviços

---

## Funcionalidades implementadas

### Artistas
- Cadastrar novo artista (nome, gênero, país)
- Buscar artista por ID
- Atualizar dados do artista
- Remover artista por ID
- Listar todos os artistas

### Álbuns
- Cadastrar novo álbum vinculado a um artista
- Buscar álbum por ID
- Buscar álbum por artista e título
- Listar álbuns de um artista
- Listar todos os álbuns
- Atualizar e remover álbum

### Faixas (Tracks)
- Cadastrar faixas vinculadas a um álbum
- Buscar faixa por ID (com JOIN para artista e álbum)
- Listar todas as faixas com dados do álbum e artista
- Atualizar e remover faixa
- Duração armazenada em segundos e convertida via `EXTRACT(EPOCH)`

---

## Modelo de dados

```
artist
├── id (PK)
├── name
├── genre
└── country

album
├── id (PK)
├── title
├── release_year
└── artist_id (FK → artist)

track
├── id (PK)
├── title
├── duration (INTERVAL)
└── album_id (FK → album)
```

---

## Como executar

### Pré-requisitos
- Java 21+
- PostgreSQL instalado e rodando
- IntelliJ IDEA (ou qualquer IDE Java)

### Configuração do banco

1. Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE musical_collection;
```

2. Crie as tabelas:
```sql
CREATE TABLE artist (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    genre VARCHAR(100),
    country VARCHAR(100)
);

CREATE TABLE album (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    release_year DATE,
    artist_id INTEGER REFERENCES artist(id)
);

CREATE TABLE track (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    duration INTERVAL,
    album_id INTEGER REFERENCES album(id),
    CONSTRAINT uk_album_title UNIQUE (title, album_id)
);
```

3. Configure o arquivo `db.properties` na raiz do projeto:
```properties
dburl=jdbc:postgresql://localhost:5432/musical_collection
user=seu_usuario
password=sua_senha
```

### Execução
Abra o projeto na sua IDE e execute `src/app/Program.java`.

---

## Próximas etapas

- [ ] Interface de usuário via console (menu interativo)
- [ ] Migração para JPA/Hibernate
- [ ] Testes unitários com JUnit

---

## Autor

**Lucas Camargo**  
[LinkedIn](https://linkedin.com/in/lcs-camargo) · [GitHub](https://github.com/lucascam4rgo)
