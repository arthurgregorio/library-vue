# Library Vue

This is an architecture model just like [the original Library](https://github.com/arthurgregorio/library) but using VueJS and Spring Boot instead of JavaEE and JSF.

> This is a work in progress and until I finish the whole application, i recommend you to not use this in production or something.. For now is just a study repository.

Have questions or sugestions? [Fill an issue](https://github.com/arthurgregorio/library-vue/issues)!

## Setting the environment

To run the project on your computer, first install:

1. Node and Yarn
2. Java JDK version 11 or above
3. Any Java IDE compatible with Maven projects (I recomend [IntelliJ IDEA CE](https://www.jetbrains.com/pt-br/idea/download/))
4. Any IDE for JavaScript/TypeScript development, compatible with VueJS (I recomend [VSCode](https://code.visualstudio.com/Download) with Vetur extension)

All set? Lets clone the repository!

After that, you will see two folders inside the project root, ```backend``` and ```frontend``` no need to explain that those folders represent the Java backend webservice layer and the other is the VueJS frontend layer.

## Runing

First, create a Postgres database named ```library-vue``` and an user onwner of this database named as ```sa_library_vue```, use the username as password. 

Use the script on the [migrations folder](https://github.com/arthurgregorio/library-vue/tree/master/backend/src/main/resources/db/migrations) to create the DB structure.

Import the backend on the Java IDE like any Maven project and open the folder of the frontend on your VSCode or similar, on the console, inside the frontend folder, type ```yarn install``` and hit enter to let Yarn download and install all dependencies.

To run the frontend use: ```yarn serve``` and to run the backend simpy use the IDE tools to run the main class.

## Packaging with maven

If you whant to build and package the project with ```maven```, run the following commands:

```mvn clean package -Pdevelopment```

This should build the frontend and the backend and put everything togheter inside a JAR file. 

You can find the final artifact inside the ```target``` folder on the backend, to run it type at the console:

```java -jar backend-1.0.0-ALPHA.jar```

This will make Spring start the services and serve the frontend on the ```localhost:8080``` address.
