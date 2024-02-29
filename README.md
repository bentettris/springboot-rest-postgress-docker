# springboot-rest-postgress-docker
A spring boot rest api using postgress as the db and docker as the containerized runtime. Java 17.

Hi. Thank you for your time.

You will need to have docker running in order to run this project.

Here are the instructions:  (For a Macbook as well as other linux variants.))

1. build the code using the following command:  mvn clean package.
1a. You must build the code before the next command since you will need the built jar.
1b. You should also have java 17 working on your system with the JAVA_HOME properly set.
2. Run the code using the following commande: docker-compose up
3. The application will run in the console (the better to see the logging).  To stop it just use ctrl-c
4. Issuing the command docker-compose in the same shell will bring down the containers.

Once it is running you can open postman and enter commands.

You can import the openapi.json openapi v3 spec into postman
to see and use the entire api.

You may also navigate your browser to:  http://localhost:8089/swagger-ui/index.html

That will show you the api as well.

The code is here for you to peruse in the src directory.

More detailed instructions:

To build the code issue the command:  mvn clean package

To test the code issue the command: mvn clean test

To run the code, build the code and then start Docker servics and issue the command:  docker-compose up

I recommend Postman to interact with the API. 

I also recommend you import the openapi.json file into postman as that will
make is much easier to create and send and receive payloads.

You can also import the codebase into Jetbrains Intellij IDEA IDE as a maven project
to view the code an run the tests.

Again, Thank you very much for your time.
