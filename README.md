# explorer-api
Explorer-api runs as a standalone SprinBoot application and expose RestApi to explore Nilu and other eth-based crypto currencies.
It connects directly to a Gilu RPC, hence you can setup your own private Nilu Explorer if you wish.
No need for servers, hosting or trusting any third parties to display chain data.
## JOIN US
### discord:
Discuss this project at: [discord.io/nilu](https://discord.io/nilu)

### twitter
Follow us on Twitter [NiluPlatfrom](https://twitter.com/NiluPlatform)

## Technical Details
The project is built on a Java8+, SpingBoot, Web3J and MongoDB as the DBMS.
Use Web3J to listen to an Ethereum RPC Backend, parse generated blocks and then store usefull information in database for further Queries.

## Getting started
### Prerequisites
If you run the project in you computer, please make sure you have the following installed and running properly:
- JDK1.8+
- MongoDB
- Maven
### Installation
```
    git clone https://github.com/NiluPlatform/explorer-api.git
```
Then you can run it in IDE or via command line and browse to the project at [localhost:8089](http://localhost:8089/)

### Docker
You can run the Explorer-api with "docker run" or Docker Compose.
#### Dockerfile
After you cloned the source go to explorer-api directory:
````
mvn clean package
docker build --build-arg JAR_FILE=target/explorer-0.0.1-SNAPSHOT.jar -f Dockerfile -t nilu-api .
    
````
Then you can use docker images to run the Explorer.
If you run in docker, you should first run mongodb image
````
docker run -p 27017:27017 -v $PWD/db:/mongo/db -d mongo
docker run --name nilu-api --link you_mongodb_image_name:mongo -p 8089:8089 -t nilu-api
````
