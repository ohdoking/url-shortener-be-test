# High level overview

We have created a simple application that comprises a backend app built in Java with Spring Webflux.

The purpose of the app is to create shortened versions of URLs. For example, given the url `https://github.com/VivyTeam/url-shortener-be-test`, the backend application would create a short url such as `http://localhost:9000/41e515a91a72`.

The application has two endpoints, detailed below:

```
GET /{urlToBeShortened}/short # Shorten the given URL
GET /{shortenedUrl}/full # Returns the full URL given the shortened version
```

# Your task

- Fork the repository
- Implement the logic to shorten a URL
- Implement the logic to get the full URL given a shortened one
- Bonus: implement a new endpoint that redirects the request to the full version of the shortened URL, like
  ```
  GET /{shortenedUrl} # Redirects the request to the full version of the shortened URL
  ```
- Share your repository with us

# Ports
Backend default port: `9000`

# How to run project

## 1. Build Vivy Url Shorten Application

To run Url Shorten Application, we need to build docker images.

### 1.1. Run gradle build
```
    gradle clean build
```

### 1.2. Build docker image
```
    docker build -t vivy/url-service .
```

## 2. Run Quote Application

### 2.1. Execute docker-compose
```
    cd local
    docker-compose up
```
# Test

## How to test with rest api

### 1. Test with curl
#### Create shorten url
```
    curl --location --request POST 'http://localhost:9000/url/short' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "url" : "https://www.naver.com"
    }'
```

#### Get original url by shorten uri

```
    curl --location --request GET 'http://localhost:9000/url/1/original'
```

### 2. Run unit test

This project has implemented unit test.
To run unit test, you can execute below command.
```
    gradle clean test 
```

### 3. Redirect url test

Create shorten url and execute this url in browser
```
  http://localhost:9000/1
```