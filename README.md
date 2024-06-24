<h1>Account-service</h1>

<h2>Getting started</h2>

1. Install the Docker from https://www.docker.com/get-started/
2. Clone project from git.hub
3. Open the terminal in project folder
4. Enter the command ```docker-compose up```

<h3>Endpoints</h3>

Default url domain: http://localhost:18001/

1. ```POST api/account/all``` - return saved accounts by filter if presents.
    If don't send FilterDto in request body, response returns all accounts.
    1. Optional send FilterDto. Example:</br>
   ```{"surname":"Ivanov", "name":"Ivan", "patronymic":null, "phone": "79000000000", "mail": null}```
2. ```GET api/account/{id}``` - return account by request parameter [id].
3. ```POST api/account``` - create account with validation.
   1. Need to send header ```x-Source```
   2. Need to send AccountDto to request body. Example: <br>
   ```{"bankId":null, "surname":null, "name":"Maria", "patronymic":null, "birth":null, "passport":null, "birthPlace":null, "phone":"79000000000", "mail":null, "registrationAddress":"Moscow", "factAddress":null}```