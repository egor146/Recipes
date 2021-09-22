# Recipes
Java multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.  
To learn more about this project, please visit the [Hyperskill website](https://hyperskill.org/projects/180?track=12).  
The source code is located in the following folder:  

    Recipes/task/src/recipes

A recipe includes 6 fields:  
- `name`;   
- `category` represents a category of a recipe;   
- `date` stores the date when the recipe has been added (or the last update);  
- `description` stores a short description of the dish;  
- `ingredients` stores required ingredients (array);   
- `directions` stores directions for how to prepare the dish (array).  
    
This web service has the following REST endpoints:

1. `POST /api/register`
    - Receives a JSON object with two fields: `email` (string), and `password` (string). If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with `200 (Ok)`. If a user is already in the database, the service responds with the `400 (Bad Request)` status code. Both fields are required and must be valid: `email` should contain @ and . symbols, `password` should contain at least 8 characters and shouldn't be blank. If the fields do not meet these restrictions, the service responds with `400 (Bad Request)`.
2. `POST /api/recipe/new`
    - Receives a recipe as a JSON object and returns a JSON object with one `id` field.
3. `GET /api/recipe/{id}`
    - Returns a recipe with a specified `id` as a JSON object.
4. `DELETE /api/recipe/{id}`
    - Deletes a recipe with a specified {id}. The server responds with the `204 (No Content)` status code. If a recipe with a specified id does not exist, the server returns `404 (Not found)`.
5. `PUT /api/recipe/{id}`
    - Receives a recipe as a JSON object and updates a recipe with a specified `id`. The server returns the `204 (No Content)` status code. If a recipe with a specified id does not exist, the server returns `404 (Not found)`. The server responds with `400 (Bad Request)` if a recipe doesn't follow the restrictions (all fields are required, string fields can't be blank, arrays should have at least one item).
6. `GET /api/recipe/search`
    - Takes one of the two mutually exclusive query parameters:
        - `category` – if this parameter is specified, it returns a JSON array of all recipes of the specified category.
        - `name` – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter.  
    - If no recipes are found, the program returns an empty JSON array. If 0 parameters were passed, or more than 1, or if the specified parameters are not valid the server returns `400 (Bad Request)`.
The service uses h2 database to store data.
