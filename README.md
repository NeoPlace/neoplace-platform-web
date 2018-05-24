# Getting Started
## SCM

```
git clone https://github.com/NeoPlace/neoplacej.git
```
Current branch is: master

## Prerequisites
Following development tools are required to run this project

```
* GIT
* Eclipse / IntelliJ IDEA (IDE)
* Java 8 or upper
* Maven 3.x
* Golang
* Postman to test REST Api
* Docker (TODO docker file)
```

# Install
## Dependencies
### IPFS
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
      <groupId>com.github.ipfs</groupId>
      <artifactId>java-ipfs-api</artifactId>
      <version>$LATEST_VERSION</version>
    </dependency>
</dependencies>
```

### web3j
```
<dependency>
  <groupId>org.web3j</groupId>
  <artifactId>core</artifactId>
  <version>3.4.0</version>
</dependency>
```

## Building
```
mvn clean install
```

# Start a client

## Ethereum
```
$ geth --rinkeby --rpc --rpcapi="personal,eth,network,web3,net"
```
```
Web3j web3 = Web3j.build(new HttpService("https://localhost:8545"));
```
or you can use Infura, a client running in the cloud:
```
Web3j web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/<token>"));
```

## IPFS
Init your IPFS node
```
$ ipfs init
$ #If you want to open your node to outside
$ ipfs config --json API.HTTPHeaders.Access-Control-Allow-Origin '["*"]'
$ ipfs config --json API.HTTPHeaders.Access-Control-Allow-Methods '["PUT", "GET", "POST"]'
$ ipfs config --json API.HTTPHeaders.Access-Control-Allow-Credentials '["true"]'
```
```
IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
```
or you can use Infura, which provides an IPFS node
```
IPFS ipfs = new IPFS("https://ipfs.infura.io/ipfs//5001");
```

## Running NeoPlace node with Docker
Comming soon

#