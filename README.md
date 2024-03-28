<p align="center">
    <img src="./ridesync_frontend/public/ridesyncNewLogo.png" alt="Ridesync" width="300" height="180">
</p>

# Ridesync

Ridesync is a ride-sharing application designed to connect riders and drivers seamlessly. With a focus on convenience and reliability, Ridesync empowers users to efficiently find rides or offer transportation services.

## Tools & Tech Stacks used : -

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)](https://spring.io/projects/spring-boot)
[![React.js](https://img.shields.io/badge/React.js-18.2.0-blue)](https://reactjs.org/)
[![Bootstrap](https://img.shields.io/badge/ChakraUI-4.x-tealblue)](https://chakra-ui.com/)
[![MySQL](https://img.shields.io/badge/MySQL-v8.0-yellow)](https://www.mysql.com/)
[![map](https://img.shields.io/badge/GoogleMaps-v2.19.3-brown)](https://developers.google.com/maps)

## Backend (Java SpringBoot)

#### Dependencies

| Dependency Group             | Description                                               | Artifact                      | Version |
| ---------------------------- | --------------------------------------------------------- | ----------------------------- | ------- |
| org.springframework.boot     | Starter for using Spring Boot's Data JPA                  | spring-boot-starter-data-jpa  |         |
| org.springframework.boot     | Starter for Spring Security                               | spring-boot-starter-security  |         |
| org.springframework.boot     | Starter for building web, including RESTful, applications | spring-boot-starter-web       |         |
| com.mysql                    | MySQL JDBC driver                                         | mysql-connector-j             |         |
| org.projectlombok            | Lombok library for reducing boilerplate code              | lombok                        |         |
| io.jsonwebtoken              | Java JWT: JSON Web Token for Java                         | jjwt-api                      | 0.11.5  |
| io.jsonwebtoken              | Implementation of Java JWT                                | jjwt-impl                     | 0.11.5  |
| io.jsonwebtoken              | Jackson support for Java JWT                              | jjwt-jackson                  | 0.11.5  |
| org.springframework.boot     | Starter for testing Spring Boot applications              | spring-boot-starter-test      |         |
| org.springframework.security | Spring Security testing utilities                         | spring-security-test          |         |
| org.springframework.boot     | Starter for Spring Mail                                   | spring-boot-starter-mail      |         |
| com.google.maps              | Google Maps Services SDK for Java                         | google-maps-services          | 2.2.0   |
| org.springframework.boot     | Starter for WebSocket support                             | spring-boot-starter-websocket |         |
| com.mapbox.mapboxsdk         | Mapbox SDK Turf                                           | mapbox-sdk-turf               | 5.8.0   |
| junit                        | JUnit testing framework for Java                          |

## Frontend (React PWA)

#### Dependencies

| Dependency                       | Description                                           | Version |
| -------------------------------- | ----------------------------------------------------- | ------- |
| @chakra-ui/icons                 | Chakra UI icons library                               | 2.1.1   |
| @chakra-ui/react                 | Chakra UI React components library                    | 2.8.2   |
| @emotion/react                   | React integration for Emotion CSS                     | 11.11.3 |
| @emotion/styled                  | Styled-components for Emotion CSS                     | 11.11.0 |
| @fontsource/roboto               | Roboto font packaged for use with Fontsource          | 5.0.12  |
| @googlemaps/polyline-codec       | Polyline encoding/decoding library for Google Maps    | 1.0.28  |
| @hookform/resolvers              | Resolvers for React Hook Form                         | 3.3.4   |
| @react-google-maps/api           | Google Maps JavaScript API wrapper for React          | 2.19.3  |
| @stomp/stompjs                   | STOMP protocol library for WebSocket communication    | 7.0.0   |
| @testing-library/react           | React testing utilities                               | 13.4.0  |
| @testing-library/user-event      | Testing utilities for user interactions               | 13.5.0  |
| axios                            | Promise-based HTTP client for the browser and Node.js | 1.6.7   |
| framer-motion                    | Animation library for React                           | 11.0.5  |
| moment                           | Date manipulation library                             | 2.30.1  |
| react                            | React library                                         | 18.2.0  |
| react-dom                        | React DOM library                                     | 18.2.0  |
| react-google-places-autocomplete | Google Places Autocomplete component for React        | 4.0.1   |
| react-hook-form                  | Form validation library for React                     | 7.51.0  |
| react-icons                      | Icon library for React                                | 5.0.1   |
| react-router-dom                 | React Router library                                  | 6.22.0  |
| react-scripts                    | Scripts and configuration for React applications      | 5.0.1   |
| sockjs-client                    | WebSocket client library                              | 1.6.1   |
| tailwind-merge                   | Tailwind CSS utility for merging classes              | 2.2.1   |
| tailwindcss-animate              | Animation utility for Tailwind CSS                    | 1.0.7   |
| web-vitals                       | Library for tracking web vital metrics                | 2.1.4   |
| yup                              | JavaScript schema builder for validation              | 1.3.3   |

### Scripts

- `start`: Starts the development server.
- `build`: Builds the production-ready app.
- `test`: Runs tests using React testing framework.
- `eject`: Ejects the configuration for customization.

### Development Dependencies

| Dependency  | Description                           | Version |
| ----------- | ------------------------------------- | ------- |
| tailwindcss | Utility-first CSS framework for React | 3.4.1   |

# Getting Started

## External Dependencies

#### Installation of Dependencies

Before you can build the application, you will need to install the following dependencies on your virtual machine:

##### **Java**

To install Java, run the following commands:

```bash
sudo apt-get update
sudo apt-get install openjdk-17-jdk
```

##### **Maven**

To install maven, run the following commands:

```bash
sudo apt install maven
```

Once the command has finished executing, confirm its installation by running:

```bash
maven --v
```

##### Node.js

To install Node.js, run the following commands:

```bash
sudo apt-get update
sudo apt-get install nodejs
```

#### Backend (Java Spring Boot)

##### 1. Clone the repository

```bash
git clone https://git.cs.dal.ca/courses/2024-winter/csci5308/Group08.git
```

##### 2. Navigate to the backend directory.

```bash
cd ridesync_backend
```

##### 3. Build the project.

```bash
mvn clean package
```

##### 4. Test the project.

```bash
mvn test
```

##### 5. Start the backend server.

```bash
java -jar target/ridesync-0.0.1-SNAPSHOT.jar
```

#### Frontend (React PWA)

##### 1. Clone the repository

```bash
git clone https://git.cs.dal.ca/courses/2024-winter/csci5308/Group08.git
```

##### 2. Navigate to the directory.

```bash
cd ridesync_frontend
```

##### 3. Install dependencies.

```bash
npm install
```

##### 4. Build the frontend.

```bash
npm run build
```

##### 5. Start the development server.

```bash
npm run start
```

Open your web browser and go to http://localhost:3000 to access the website.

### Ridesync Backend and Frontend CI/CD Pipeline

To build the backend Spring Boot application in your CI/CD pipeline, follow these steps:

1. **Create a Job:**

   - Create a new job in your GitLab CI/CD pipeline and define it as a build stage.

2. **Choose Docker Image:**

   - Choose an appropriate Docker image to run the job. You can use the `maven:latest` image to build the Spring Boot application.

3. **Script Execution:**

   - In the script section of the job:
     - Navigate to the project directory using the `cd` command.
     - Use the `mvn clean package` command to build the application.

4. **Define Dependencies:**

   - Define the dependencies of the job, which should include the previous stages of the pipeline (`quality`, `test-backend`, etc.).

5. **Define Artifacts:**

   - Define the `artifacts` section to specify which files should be stored as pipeline artifacts. In this case, you want to store the `target` folder of the Spring Boot project.

6. **Specify Runner Tags:**
   - Finally, specify the runner tags that should be used to execute the job. For instance, you can use `test-runner`.

After the successful execution of the pipeline, you will be able to find `ridesync-0.0.1-SNAPSHOT.jar` in the following path: `/ridesync_backend/target`.

---

## Building Frontend Application

To build the frontend application in your CI/CD pipeline, follow these steps:

1. **Create a Job:**

   - Create a new job in your GitLab CI/CD pipeline and define it as a build stage.

2. **Choose Docker Image:**

   - Choose an appropriate Docker image to run the job. You can use the `node:latest` image for building Node.js-based applications like React.

3. **Script Execution:**

   - In the script section of the job:
     - Navigate to the project directory using the `cd` command.
     - Use the `npm install` command to install dependencies.
     - Use the `npm run build` command to build the application.

4. **Define Dependencies:**

   - Define the dependencies of the job, which should include the previous stages of the pipeline (`test-frontend`, etc.).

5. **Define Artifacts:**

   - Define the `artifacts` section to specify which files should be stored as pipeline artifacts. In this case, you want to store the `build` folder containing the compiled assets of the frontend application.

6. **Specify Runner Tags:**
   - Finally, specify the runner tags that should be used to execute the job. For instance, you can use `test-runner`.

After the successful execution of the pipeline, you will have the compiled assets of the frontend application ready for deployment.

---

## Backend Deployment Steps

To deploy the backend application, follow these steps:

1. **Navigate to Backend Folder:**

   ```bash
   cd ridesync_backend
   ```

2. **Build Docker Container:**

   ```bash
   docker build -t backend .
   ```

3. **Push Docker Container:**

   ```bash
   docker push docker.io/meer2838/cicdtest:backend
   ```

4. **Connect to Remote VM using SSH:**

5. **Remove Existing Docker Container:**

   ```bash
   docker container rm -f my-backend-app
   ```

6. **Pull the docker container:**

   ```bash
   docker pull docker.io/meer2838/cicdtest:backend
   ```

7. **Run Docker Container on VM:**

   ```bash
   docker run -d -p 8073:8073 -e SERVER_PORT=$SERVER_PORT -e DEV_DB_URL=$DEV_DB_URL -e DEV_DB_USER=$DEV_DB_USER -e REMOTE_DB_PASSWORD=$REMOTE_DB_PASSWORD -e FRONTEND_PORT=$FRONTEND_PORT -e IP_ADDRESS=$IP_ADDRESS --name my-backend-app docker.io/meer2838/cicdtest:backend
   ```

8. **Access Application:**
   - Once the Docker container is running successfully, the application will be accessible at port 8073.

These steps will deploy the backend application and make it accessible at port 8073 on the remote VM. Adjust the commands as per your project's configuration and requirements.

---

## Frontend Deployment Steps

To deploy the frontend application, follow these steps:

1. **Navigate to ridesync_frontend Folder:**

   ```bash
   cd ridesync_frontend
   ```

2. **Build Docker Container:**

   ```bash
   docker build -t frontend .
   ```

3. **Push Docker Container:**

   ```bash
   docker push docker.io/meer2838/cicdtest:frontend
   ```

4. **Connect to Remote VM using SSH:**

5. **Remove Existing Docker Container:**

   ```bash
   docker container rm -f my-frontend-app
   ```

6. **Pull the docker container:**

   ```bash
   docker pull docker.io/meer2838/cicdtest:frontend
   ```

7. **Run Docker Container on VM:**

   ```bash
   docker run -d -p 3000:3000 --name my-frontend-app -e REACT_APP_API_URL=$REACT_APP_API_URL docker.io/meer2838/cicdtest:frontend
   ```

8. **Access Application:**
   Once the Docker container is running successfully, the application will be accessible at port 3000.

Adjust the commands as per your project's configuration as per your requirements.

---

## User Scenarios

#### Overview

Ridesync facilitates a simple and efficient process for both riders and drivers. Key features include:

#### Driver Perspective

##### Registration:

Drivers can register by providing necessary documents (license, vehicle registration).

##### Posting a Ride:

Drivers can post rides with details such as start location, end location, start time, description, vehicle and fare.

##### Accepting/Rejecting Rides:

Drivers receive ride requests from riders and can accept or reject them accordingly.

##### Activity Tracking:

Drivers can view their current, upcoming, and completed rides in the activity section.

---

#### Rider Perspective

##### Finding a Ride:

Riders can search for rides based on proximity to available drivers.

##### Requesting a Ride:

Riders can request rides from drivers and wait for acceptance.

##### Tracking Ride Status:

Riders can track the status of their rides, including live location tracking of the driver.

##### Activity Tracking:

Riders can view their current, upcoming, and completed rides in the activity section.

---

- Authentication: User can login, signup and reset password.

  ![Screenshot 1](/ridesync_frontend/public/login.png)
  ![Screenshot 2](/ridesync_frontend/public/registration.png)
  ![Screenshot 2](/ridesync_frontend/public/forgotPassword.png)

- Driver Registration: Drivers can register by providing necessary documents such as license details and vehicle registration.
  ![Screenshot 3](/ridesync_frontend/public/documentreg.png)
  ![Screenshot 4](/ridesync_frontend/public/Vehicleregistration.png)
- Posting Ride: Drivers can post available rides specifying start location, end location, start time, and fare.
  ![Screenshot 5](/ridesync_frontend/public/postride.png)
  ![Screenshot 6](/ridesync_frontend/public/postride2.png)
- Find Ride: Riders can find ride by entering their start location, end location and start time which returns all the available rides.
- Ride Request: Riders can search for rides and request rides based on driver's proximity.
  ![Screenshot 7](/ridesync_frontend/public/findride.png)

- Activity Section: The app includes an activity section with three tabs: current, upcoming, and completed rides, providing users with a comprehensive view of their ride history.

- ###### Upcoming rides

![Screenshot 8](/ridesync_frontend/public/activity.png)

- ###### Current rides

![Screenshot 9](/ridesync_frontend/public/activitySectionCurrent.png)

- ###### Past rides

![Screenshot 10](/ridesync_frontend/public/activitySectionPast.png)

- Get all ride requests: Driver can get all ride requests in the ride info page to accept or reject the ride.

  ![Screenshot 9](/ridesync_frontend/public/rideinfo.png)

- ###### Accept ride

![Screenshot 10](/ridesync_frontend/public/rideinfoRideAccepted.png)

- Live Location Tracking: Ridesync features live location tracking, allowing riders to monitor the real-time location of drivers and drivers to track the pickup location of riders.
  ![Screenshot 11](/ridesync_frontend/public/livelocationtracking.png)

- In-app Notification: The app employs a notification system to keep users informed about accepted or rejected ride requests and other relevant updates.
  ![Screenshot 12](/ridesync_frontend/public/notifications.png)
- In-app Messaging: The app employs a notification system to keep users informed about accepted or rejected ride requests and other relevant updates.
  ![Screenshot 13](/ridesync_frontend/public/Chat.png)

### Test Coverage

[![Coverage Status](https://img.shields.io/badge/Line_Coverage-82%25-brightgreen.svg)](./coverage-report/index.html)
[![Coverage Status](https://img.shields.io/badge/Class_Coverage-82.5%25-brightgreen.svg)](./coverage-report/index.html)
[![Coverage Status](https://img.shields.io/badge/Method_Coverage-83.2%25-brightgreen.svg)](./coverage-report/index.html)
[![Coverage Status](https://img.shields.io/badge/JUnit_Test_Cases-90-brightgreen.svg)](./coverage-report/index.html)

#### Detailed Report

You can view the detailed test coverage report [here](./coverage-report/index.html).

### Contributors

- Meer Patel
- Ayushi Malhotra
- Purushotham Parthy
- Nithin Bharathi
- Sivasubramanian Venkatasubramanian
