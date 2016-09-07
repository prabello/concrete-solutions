# Projeto para a Concrete Solutions

Para criar um usuário faça um **POST** para https://concrete-solution.herokuapp.com/user/ enviando um JSON, exemplo:

```
{
    "email": "joao@silva.org",
    "name": "João da Silva",
    "password": "hunter2",
    "phones": [
        {
            "ddd": "21",
            "number": "987654321"
        }
    ]
}
```

Para realizar o login faça um **POST** para https://concrete-solution.herokuapp.com/login enviando um JSON, exemplo:

```
{
	"email": "joao@silva.org",
	"password": "hunter2"
}
```

Para visualizar o profile de um usuario você deve fazer um **GET** para https://concrete-solution.herokuapp.com/user/X 
aonde X deve ser o ID do usuario, você também deve informar o TOKEN do usuario no cabeçalho da requisição. 