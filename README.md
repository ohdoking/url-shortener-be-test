# Vivy Url Shorten Application

Vivy Url Shorten Application is a spring framework project that is for creating and redirecting the shorten url.

## Features
- Create shorten URL
- Get original url by shorten URL
- Redirect with full version of the shortened

## Requirements
* Java11
* Spring/Spring-boot
* Postgres
* Hibernate
* Gradle
* Docker/Docker-Compose

## Getting started

### Running the application locally

#### 1. Build Vivy Url Shorten Application

To run Url Shorten Application, we need to build docker images.

##### 1.1. Run gradle build
```
    gradle clean build
```

##### 1.2. Build docker image
```
    docker build -t vivy/url-service .
```

#### 2. Run Quote Application

##### 2.1. Execute docker-compose
```
    cd local
    docker-compose up
```
### Testing

#### 1. Test with curl
##### Create shorten url
```
    curl --location --request POST 'http://localhost:9000/url/short' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "url" : "https://www.naver.com"
    }'
```

##### Get original url by shorten uri

```
    curl --location --request GET 'http://localhost:9000/url/1/original'
```

#### 2. Run unit test

This project has implemented unit test.
To run unit test, you can execute below command.
```
    gradle clean test 
```

#### 3. Redirect url test

Create shorten url and execute this url in browser
```
  http://localhost:9000/1
```