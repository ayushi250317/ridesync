<p align="center">
    <img src="./ridesync_frontend/public/ridesyncNewLogo.png" alt="Image Description" width="300" height="180">
</p>

# Ridesync

Ridesync is a ride-sharing application designed to connect riders and drivers seamlessly. With a focus on convenience and reliability, Ridesync empowers users to efficiently find rides or offer transportation services.

## Backend (Java SpringBoot)

##### Dependencies

- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-web
- mysql-connector-j
- lombok
- jjwt-api
- jjwt-impl
- jjwt-jackson
- spring-boot-starter-test
- spring-security-test
- spring-boot-starter-mail
- google-maps-services
- spring-boot-starter-websocket
- mapbox-sdk-turf

##### Plugins

- maven-compiler-plugin
- spring-boot-maven-plugin
- jacoco-maven-plugin

#### Frontend (React PWA)

##### Dependencies

- yup
- axios
- react
- moment
- stompjs
- tailwind
- chakra-ui
- react-dom
- react-icons
- sockjs-client
- react-scripts
- framer-motion
- react-hook-form
- react-router-dom
- react-google-maps
- googlemaps/polyline-codec

- react-google-places-autocomplete

##### Dev Dependencies

- tailwindcss

## Build Documentation

#### Backend (Java Spring Boot)

1. Clone the repository `git clone https://git.cs.dal.ca/courses/2024-winter/csci5308/Group08.git`
2. Navigate to the `cd ridesync_backend` directory.

###### Build

- Run `mvn clean package` to build the project.

###### Test

- Run `mvn test` to test the project.

###### Run

- Run `java -jar target/ridesync-0.0.1-SNAPSHOT.jar` to start the backend server.

#### Frontend (React PWA)

1. Clone the repository `git clone https://git.cs.dal.ca/courses/2024-winter/csci5308/Group08.git`
2. Navigate to the `cd ridesync_frontend` directory.
3. Run `npm install` to install dependencies.
4. Run `npm run build` to build the frontend.
5. Run `npm run start` to start the development server.

## User Scenarios

#### Overview

Ridesync facilitates a simple and efficient process for both riders and drivers. Key features include:

- User Roles: Ridesync caters to two main user roles: rider and driver.

  ![Screenshot 1](/ridesync_frontend/public/login.png)
  ![Screenshot 2](/ridesync_frontend/public/registration.png)

- Driver Registration: Drivers can easily register by providing necessary documents such as license details and vehicle registration.
  ![Screenshot 3](/ridesync_frontend/public/documentreg.png)
  ![Screenshot 4](/ridesync_frontend/public/Vehicleregistration.png)
- Ride Posting: Drivers can post available rides specifying start location, end location, start time, and fare.
  ![Screenshot 5](/ridesync_frontend/public/postride.png)
  ![Screenshot 6](/ridesync_frontend/public/postride2.png)
- Ride Request: Riders can search for rides based on driver proximity and request rides accordingly.
- Find Ride: Riders can find ride by entering their start location, end location and start time which returns all the available rides.
  ![Screenshot 7](/ridesync_frontend/public/findride.png)
- Activity Section: The app includes an activity section with three tabs: current, upcoming, and completed rides, providing users with a comprehensive view of their ride history.

- ##### Upcoming ride

![Screenshot 8](/ridesync_frontend/public/activity.png)

- ##### Current ride

![Screenshot 9](/ridesync_frontend/public/activitySectionCurrent.png)

- ##### Past ride

![Screenshot 10](/ridesync_frontend/public/activitySectionPast.png)

- Ride Acceptance: Drivers have the option to accept or reject ride requests from riders.

- ##### Get all rides

  ![Screenshot 9](/ridesync_frontend/public/rideinfo.png)

- ##### Accept ride

  ![Screenshot 10](/ridesync_frontend/public/rideinfoRideAccepted.png)

- Live Location Tracking: Ridesync features live location tracking, allowing riders to monitor the real-time location of drivers and drivers to track the pickup location of riders.
  ![Screenshot 11](/ridesync_frontend/public/livelocationtracking.png)

- Notification System: The app employs a notification system to keep users informed about accepted or rejected ride requests and other relevant updates.
  ![Screenshot 12](/ridesync_frontend/public/notifications.png)
- Chatting: The app employs a notification system to keep users informed about accepted or rejected ride requests and other relevant updates.
  ![Screenshot 13](/ridesync_frontend/public/Chat.png)

##### Driver Perspective

###### Registration:

Drivers can register by providing necessary documents (license, vehicle registration).

###### Posting a Ride:

Drivers can post rides with details such as start location, end location, start time, description, vehicle and fare.

###### Accepting/Rejecting Rides:

Drivers receive ride requests from riders and can accept or reject them accordingly.

###### Activity Tracking:

Drivers can view their current, upcoming, and completed rides in the activity section.

##### Rider Perspective

###### Finding a Ride:

Riders can search for rides based on proximity to available drivers.

###### Requesting a Ride:

Riders can request rides from drivers and wait for acceptance.

###### Tracking Ride Status:

Riders can track the status of their rides, including live location tracking of the driver.

###### Activity Tracking:

Riders can view their current, upcoming, and completed rides in the activity section.

#### Contributors

- Meer Patel
- Ayushi Malhotra
- Purushotham Parthy
- Nithin Bharathi
- Sivasubramanian Venkatasubramanian
