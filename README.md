# run and fun

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2f2a5b3cb41e46328678cfc7c6d79f73)](https://www.codacy.com/app/inpercima/run-and-fun?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inpercima/run-and-fun&amp;utm_campaign=Badge_Grade)

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com).

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate) version 2.0.0-SNAPSHOT.

## Prerequisites

### Angular CLI

* `angular-cli 11.0.5` or higher

### Docker

* `docker 19.03.5` or higher
* `docker-compose 1.25.0` or higher

### Java

* `jdk 11` or higher

### Node, npm or yarn

* `node 12.19.0` or higher in combination with
  * `npm 6.14.8` or higher or
  * `yarn 1.22.5` or higher, used in this repository

### Runkeeper app registration

A runkeeper app is needed.
Use existing or register new app at [runkeeper.com](http://runkeeper.com/partner/applications).

## Dependency check

Some libraries could not be updated b/c of peer dependencies or knowing issues.

| library    | current version | wanted version | reason |
| ---------- | --------------- | -------------- | ------ |
| typescript | 4.0.5           | 4.1.3          | "@angular-devkit/build-angular@0.1100.5" has incorrect peer dependency "typescript@~4.0.0" |
| zone.js    | 0.10.3          | 0.11.3         | "@angular/core@11.0.5" has incorrect peer dependency "zone.js@~0.10.3" |

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
