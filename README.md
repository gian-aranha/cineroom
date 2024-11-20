# CineRoom

O CineRoom é um site inovador que facilita a escolha de filmes entre amigos. Nele, você pode criar uma sessão personalizada, convidar seus amigos e, juntos, selecionar seus gêneros favoritos e definir a classificação etária adequada para todos. Com essas preferências, o CineRoom oferece uma lista de opções de filmes que combinam com os gostos do grupo. Ideal para quem quer agilizar a decisão de qual filme assistir e garantir uma experiência cinematográfica perfeita para todos.

# Banco de dados
- Instale o MySQL e crie o usuário `root` e defina a senha também com `root`
- Dentro do MySQL rode o seguinte comando para criar o banco de dados:
```
CREATE DATABASE IF NOT EXISTS cineroom;
```
- Pra acessar o banco:
```
USE cineroom;
```
O projeto possui migrações de banco de dados, ou seja, todas as tabelas serão criadas no banco a partir do momento que o projeto foi executado.

# Informações
- O projeto está sendo desenvolvido na versão 21 do Java, então utilize essa para desenvolver o código.
- É indicado que seja utilizado o IntelliJ como IDE de desenvolvimento, pois já existem configurações no `.gitignore` tratando dos arquivos gerados por essa IDE

# Documentação - Endpoints

- Users `/users`
    - POST
        - Criar novo usuário:
            `/`
    - GET
        - Pegar usuário pelo Id:
            `/{id}`

    - FOMATO OBJETO USER
    ```
        {
            "username":"paulovreis",
            "email":"paulo@email.com",
            "name":"Paulo V",
            "password":"12345"
        }
   ```
       
- Session `/sessions`
    - POST
        - Criar nova sessão:
            `/`
    - GET
        - Pegar todas as sessões:
            `/` -> é so dar get na endpoint `/sessions`
        - Pegar sessão por ID:
            `/{id}`
        - Pegar sessões do usuário:
            `/user/{userId}`
        - Pegar sessão pelo código:
            `/code/{code}`
    - PUT
        - Atualizar sessão:
            `/{id}`
    - DELETE
        - Deletar sessão pelo id:
            `/{id}`
        - Deletar todas as sessões de um usuário:
            `/user/{userId}`

    - FORMATO OBJETO SESSION
    ```
         {
            "code": "teste123",
            "category": "SUPERHERO", 
            "usersLimit": 5,
            "status": "ACTIVE",
            "content": "This is a test session edited.",
            "createdAt": "2024-10-05T12:00:00", 
            "user": {
                "id": 1,
                "username": "paulovreis"
            }
        }
    ```

- Reviews `/reviews`
   - POST
        - Criar nova review:
            `/`
   - GET
        - Pegar todas as reviews do usuário:
            `/user/{userId}`
        - Pegar review por ID:
            `/{id}`
   - PUT
        - Atualizar review:
            `/{id}`
   - DELETE
        - Deletar review pelo id:
            `/{id}`

    - FORMATO OBJETO REVIEW
    ```
         {
            "userId": 123,
            "sessionId": 456,
            "content": "Muito bom, gostei da experiência.",
            "rating": 5,
            "comment": "Recomendo a todos!"
        }
    ```


- API de filmes `/movies`
    - GET
        - Pegar os filmes populares:
            `/`
        - Pegar um filme específico salvo no banco de dados:
            `/{id}`
    - POST
        - Salvar um filme no banco de dados:
            `/`
        - Pegar os filmes de acordo com gênero:
            `/by-genre` -> deve ser passado uma lista no corpo com os gêneros em inglês, dessa forma: `["action", "comedy", "drama"]`
            OBS: Não precisa do {} passa a lista direto mesmo
      
    - FORMATO OBJETO MOVIE salvo no banco
    ```
        {
            "id": 123,
            "title": "The Avengers",
            "overview": "The Avengers is a great movie.",
            "image": "/abc123.jpg",
            "rating": 8.5
        }
    ```

# Orientações
- Os únicos endpoints que aceitam requisições sem token JWT são `/users` (POST) e `/login`
- Para conseguir o token JWT, é necessário fazer login na aplicação, passando o username e a senha do usuário
- Ao receber os objetos dos filmes, existe um campo chamado `poster_path`, ele é somente o final da URL para visualizar o poster do filme. Dessa forma, para ter a URL completa, é necessário concatenar o `poster_path` com o link `https://image.tmdb.org/t/p/{tamanho}/{poster_path}`
- Os tamanhos são:
    `w300` -> 300px de largura
    `w780` -> 720px de largura
    `w1280` -> 1280px de largura
    `original` -> Tamanho original
