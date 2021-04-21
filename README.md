# README #

### What is this repository for? ###

* Web Application Project Your Way To Italy
* Version 1.0

# How do I run tests? #

### CURL TEST COMMANDS for advertisement REST resources ###

#### Request to get advertisement with id=1 INFORMATION in JSON format  ### 
* curl -v -X GET -H "Accept: application/json" http://localhost:8080/ywti_wa2021_war/adv/1

#### Request to get advertising with id=1 RATING in JSON format   ### 
* curl -v -X GET -H "Accept: application/json" http://localhost:8080/ywti_wa2021_war/adv/1/rate

#### Request to get advertising with id=1 IMAGES information and location in JSON format   ### 
* curl -v -X GET -H "Accept: application/json" http://localhost:8080/ywti_wa2021_war/adv/1/image

#### Request to get advertising with id=1 FEEDBACK list in JSON format    ### 
* curl -v -X GET -H "Accept: application/json" http://localhost:8080/ywti_wa2021_war/adv/1/feedback

#### Request to get advertising with id=1 BOOKING list in JSON format    ### 
* curl -v -H "Accept: application/json" http://localhost:8080/ywti_wa2021_war/adv/1/booking

#### Request to get LIST OF ADVERTISEMENT respecting the search filters specified in the request via JSON format    ### 
* curl -v -X GET -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"searchParameters\":{\"idType\":\"6\",\"idCity\":\"28\",\"dateStart\":\"2021-04-20\"}}" http://localhost:8080/ywti_wa2021_war/adv

#### Request to DELETE advertising with id=1   ### 
* curl -v -X DELETE -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/ywti_wa2021_war/adv/1

#### Request to CREATE advertising with id=1  passing parameters via JSON format   ### 
* curl -v --request POST -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"advertisement\":{\"idAdvertisement\":\"1\",\"title\":\"Capodanno sulle dolomiti\",\"description\":\"Sette giorni di relax in mezzo alla neve delle dolomiti in Trentino alto adige a capodanno\",\"score\":\"100\",\"price\":\"800\",\"numTotItem\":\"24\",\"dateStart\":\"2021-12-28\",\"dateEnd\":\"2021-12-28\",\"timeStart\":\"14:14:14\",\"timeEnd\":\"14:14:15\",\"emailCompany\":\"hotelcentrale@gmail.com\",\"idType\":\"6\"}}" http://localhost:8080/ywti_wa2021_war/adv-create

#### Request to EDIT advertising with id=1 passing parameters via JSON format    ### 
* curl -v --request PUT -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"advertisement\":{\"idAdvertisement\":\"1\",\"title\":\"EDITED Delicious dinner in the Dolomites, da Pino\",\"description\":\"Sette giorni di relax in mezzo alla neve delle dolomiti in Trentino alto adige a capodanno\",\"score\":\"100\",\"price\":\"800\",\"numTotItem\":\"29\",\"dateStart\":\"2021-12-28\",\"dateEnd\":\"2021-12-28\",\"timeStart\":\"14:14:14\",\"timeEnd\":\"14:14:15\",\"emailCompany\":\"hotelcentrale@gmail.com\",\"idType\":\"6\"}}" http://localhost:8080/ywti_wa2021_war/adv/5

### IMAGES UPLOAD for specified advertisement ###

* /image-do-upload/ID -> JSP to choose uploaded images

* /image-upload --> servlet receives images from JSP ( idAdvertisement passed via hidden form)

### HOME PAGE ###

#### From the homepage it's possible to access the login and register pages where it's possible to register, login and logout ####
NOTE : The search functionality in the home page doesn't work. It has been used initially for DEBUG. 
Now the search functionality works via REST using the CURL command specified before

* http://localhost:8080/ywti_wa2021_war/index

### LOGIN/REGISTER PAGES ###
#### Page to login ####

* http://localhost:8080/ywti_wa2021_war/user/do-login

#### Page to register ####

* http://localhost:8080/ywti_wa2021_war/user/do-register


### USER PAGES ###

#### From the profile page it's possible to see profile information ###
The link requires an active session to work with any role. To create the session use the link provided in Home Page
* http://localhost:8080/ywti_wa2021_war/user/profile

#### From this it's possible to edit profile information ###
The link requires an active session to work with any role. To create the session use the link provided in Home Page
* http://localhost:8080/ywti_wa2021_war/user/do-edit






