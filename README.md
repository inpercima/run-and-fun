# About this - run-and-fun
[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)
[![dependencies Status](https://david-dm.org/inpercima/run-and-fun/status.svg)](https://david-dm.org/inpercima/run-and-fun)
[![devDependencies Status](https://david-dm.org/inpercima/run-and-fun/dev-status.svg)](https://david-dm.org/inpercima/run-and-fun?type=dev)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2f2a5b3cb41e46328678cfc7c6d79f73)](https://www.codacy.com/app/inpercima/run-and-fun?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inpercima/run-and-fun&amp;utm_campaign=Badge_Grade)

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com).

# Prerequisites
## Node, npm or yarn
* `node 8.10.0` or higher in combination with
  * `npm 5.7.1` or higher or
  * `yarn 1.5.1` or higher, used in this repository

# Getting started

```
# clone project
git clone https://github.com/inpercima/run-and-fun
cd run-and-fun
```

# Usage

```
# copy application.properties to application-default.properties
cp src/main/resources/application.properties src/main/resources/application-default.properties

# use existing runkeeper app or register new app at http://runkeeper.com/partner/applications
# define app.clientId, app.clientSecret and app.redirectUri within application-default.properties and remove all unchanged properties

# install tools and frontend dependencies
yarn
# or
npm install

# run project within gradle
./gradlew bootRun

# alternatively build and run project as jar
./gradlew build
java -jar build/libs/run-and-fun-*.jar

# open site with browser
http://localhost:8080/
```

# Test

```
# run karma tests with jasmine
npm test
```
