# Project Management

### Business assumptions:
- A progress value between 0.0 and 100.0 is returned for each project having the state field as active, otherwise it's returned as null.
- If the progress value is below 0 or higher than 100, it will have a default value of 0.
- The project must have an owner.
- Only Managers can be the owners of the project.
- Participants and managers can be assigned to a project.
- The participant with the same department as the owner of a project can be assigned to that project.
- If the owner of the project is changed, no validation happens on the current participants departments (no action is taken in that case).

### Database seed:
For testing purposes I added at the beginning of the application execution:  
- 2 Departments.
- 6 employees (4 participants and 2 managers).
- The database seed is located at /src/main/resources/data.sql

### Endpoints:
- Overview of all projects.
    - method: GET
    - relative path: /api/projects
- Create new project.
    - method: POST
    - relative path: /api/projects
    - body template:
      ```
      {
      "name": "project name",
      "ownerId": 3,
      "state": "active",
      "progress": "25"
      }
      ```
- Update an existing project.
    - method: PUT
    - relative path: /api/projects/{id}
    - body template:
      ```
      {
      "state": "FAILED"
      }
      ```
- Assign a participant to a project.
    - method: POST
    - relative path: /api/projects/assign
    - body template:
      ```
      {
      "project_id": 1,
      "participant_id": 2
      }
      ```
      
### Technologies:
- Java 17.
- Spring boot 3.0.0.
- Spring data jpa.
- Spring web.
- Maven.
- h2 in-memory database.
- Junit.

### Prerequisites
- JDK 17.
- Maven.
- Docker.

### Implementation guide:
- Clone the repo ```https://github.com/helixBoy/projects```
- Run ```mvn clean package```
- Build a docker image: ```docker build -t incorta-projects:latest .```
- Start the app: ```docker run -p 8080:8080 incorta-projects```
- Use this base url to call the APIs ```http://127.0.0.1:8080```
- Use Insomnia.json file in the root directory for sample API calls.