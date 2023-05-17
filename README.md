# Synchrony-assignment

# Project Name
Imgur image manager
## Software prerequisite
1. Java 11
2. Maven 3.8.8
3. Postman or any other api platform
4. Kafka 2.5 (Optional)
## Steps
1. Clone the project -> **git clone https://github.com/Talim1/Synchrony-assignment.git**
2. Go to root project folder and run -> **mvn clean install**
3. In the same root folder, run -> **mvn spring-boot:run**. After successfully executing the command, you'll see the application started message.
![image](https://github.com/Talim1/Synchrony-assignment/assets/25170304/7bbc3288-3409-41d0-ba5b-6c5dcbf502d4)
5. The in-memory h2 database can be accessed at **http://localhost:8080/h2**. The userId, password and db name is in the application.properties file
6. Import the **Synchrony.postman_collection.json** file into Postman tool. After successful import you'll see something like this in postman.
![image](https://github.com/Talim1/Synchrony-assignment/assets/25170304/80acfe81-29be-4d11-8e30-35abdb7e2c0b)

## API definations
1. **Register User** -> Register an user in the Database
2. **User Login** -> User needs to login with their username/password in order use the functionalities like image upload, view, delete
3. **Upload image** -> Upload an image to imgur using imgur image upload api
4. **View Image** -> View the uploaded image using file id
5. **Delete Image** -> Delete the uploaded image using file id
6. **View User** -> View an user and image metadata using username
**Note:** For the payload and other api parameters, please refer to the postman collection json
