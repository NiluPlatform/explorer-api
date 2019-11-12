# explorer-api
Expose RestApi to explore Nilu and other eth-based crypto currencies.
The Explorer is a client-side only web application that connects directly to a Nilu JSON RPC compatible node. 
This means you can have your own private Nilu Explorer should you wish so.
No need for servers, hosting or trusting any third parties to display chain data.
#JOIN US
## discord:
Discuss this project at: [discord.io/nilu](https://discord.io/nilu)

## twitter
Follow us on Twitter [NiluPlatfrom](https://twitter.com/NiluPlatform)

## Technical Details
The project is built on a Java、MongoDB and web3J stack.
Use web3J to listen to the generated block data

## Getting started
### Prerequisites
If you run project in you computer,Please make sure you have the following installed and running properly
- JDK1.8+
- MongoDB
- Maven
### Installation
```
    git clone https://github.com/NiluPlatform/explorer-api.git
```
then run it in idea/Eclipse  will launch , you can browse to the project at [localhost:8089](http://localhost:8089/)

## Docker RUN
You can run it in "docker run" or Docker compose
#### Dockerfile
after you git clone ,in explorer-api directory
````
mvn clean package
docker build --build-arg JAR_FILE=target/explorer-0.0.1-SNAPSHOT.jar -f Dockerfile -t nilu-api .
    
````
then you can user docker images show nilu images.
if you run in docker,you should first run mongodb image
````
docker run -p 27017:27017 -v $PWD/db:/mongo/db -d mongo
docker run --name nilu-api --link you_mongodb_image_name:mongo -p 8089:8089 -t nilu-api
````

# explorer-api
该项目是用于查询Nilu区块相关信息接口，包括使用block number、address、transaction hash这些值来查询区块信息，并展示在相关页面上
