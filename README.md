# Educational institution
## - Brief -
>### A project that represents the teacher-student (many-to-many) relationship with possible manipulation of relevant data. 
## - Features -
- ### Creating new student/teacher as well as deleting or editing them. (At the time of creation, bear in mind that the data will be checked for validity)
- ### Assigning (or unsubscribing) new teacher to a certain student and vice versa.
- ### Displaying all with pagination and sorting (default: zero page, 10 models, sort: id, ascending.).
- ### Filtering implementation: it is possible to display a certain number of students or teachers by the students/teachers assigned. As well as displaying students/teachers by a specific name.
## - Project structure
### The project back-end part is based on `three-tier architecture` with `Model-View-Controller` scheme which makes it more comfortable to use in practice and further expansion of the project.
- ### 🖥️ Presentation Layer
>##### Level of software operation, interaction with the service layer, intuitive for the user.
- ### ⚙️ Service Layer
> ##### Layer that organises the business logic of the programme, operating on commands from the top layer.
- ### 🗃️ Data Layer
> ##### Full interaction with the database and logical layer.
## - Technologies -
- ### Java, Maven
- ### Spring Boot (MVC, Data JPA)
- ### InMemory database H2, Liquibase
- ### Swagger (endpoint documentation)
- ### JUnit 5
- ## - Project launch -
### 1. ⬇️ Create a new project using the link from the VCS.
### 2. ⚙️ Configure the database property to the one you want or leave H2.
### 3. ▶️ Run spring boot project.: