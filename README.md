# run-and-fun

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2f2a5b3cb41e46328678cfc7c6d79f73)](https://www.codacy.com/app/inpercima/run-and-fun?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inpercima/run-and-fun&amp;utm_campaign=Badge_Grade)

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com).

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate) version 1.1.0.

## Prerequisites

### Angular CLI

* `angular-cli 8.3.20` or higher

### Java

* `jdk 11` or higher

### Docker

* `docker 19.03.5` or higher
* `docker-compose 1.25.0` or higher

### Node, npm or yarn

* `node 12.3.1` or higher in combination with
  * `npm 6.12.1` or higher or
  * `yarn 1.19.1` or higher, used in this repository

## Dependency check

Some libraries could not be updated b/c of peer dependencies or knowing issues.

| library    | current version | wanted version | reason |
| ---------- | --------------- | -------------- | ------ |
| zone.js    | 0.9.1           | 0.10.2         | @angular/core@8.2.14" has incorrect peer dependency "zone.js@~0.9.1" |
| typescript | 3.5.3           | 3.7.4          | @angular-devkit/build-angular@0.803.21" has incorrect peer dependency "typescript@>=3.1 < 3.6 |
| typescript | 3.5.3           | 3.7.4          | @angular-devkit/build-angular > @ngtools/webpack@8.3.21" has incorrect peer dependency "typescript@>=3.4 < 3.6 |
| typescript | 3.5.3           | 3.7.4          | @angular/compiler-cli@8.2.14" has incorrect peer dependency "typescript@>=3.4 <3.6 |

## Getting started

```bash
# clone project
git clone https://github.com/inpercima/run-and-fun
cd run-and-fun
```

## Usage

### Modules

For the client check [run-and-fun - client](./client).

For the server check [run-and-fun - server](./server).

For the docker check [run-and-fun - docker](./README_docker.md).
