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
        - Deletar todas as sessões:
            `/deleteAll`
        - Deletar todas as sessões de um usuário:
            `/user/{userId}`
