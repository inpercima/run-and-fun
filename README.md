# run-and-fun

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)
[![dependencies Status](https://david-dm.org/inpercima/run-and-fun/status.svg)](https://david-dm.org/inpercima/run-and-fun)
[![devDependencies Status](https://david-dm.org/inpercima/run-and-fun/dev-status.svg)](https://david-dm.org/inpercima/run-and-fun?type=dev)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2f2a5b3cb41e46328678cfc7c6d79f73)](https://www.codacy.com/app/inpercima/run-and-fun?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inpercima/run-and-fun&amp;utm_campaign=Badge_Grade)

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com)

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate).

## Prerequisites

### Node, npm or yarn

* `node 8.11.3` or higher in combination with
  * `npm 5.6.0` or higher or
  * `yarn 1.7.0` or higher, used in this repository

## Getting started

```bash
# clone project
git clone https://github.com/inpercima/run-and-fun
cd run-and-fun

# copy src/config.default.json to src/config.json
cp src/config.default.json src/config.json

# install tools and frontend dependencies
yarn
```

## Usage

```bash
# build in devMode
yarn run build:dev

# build in prodMode, compressed
yarn run build:prod

# build in devMode and start a server, rebuild after changes
yarn run serve
# open result in browser
http://localhost:4200/
```

## Configuration

### General

All options have to bet set but some of them do not need to be changed.

### Table of contents

* [appname](#appname)
* [routes/default](#routesdefault)
* [routes/features/show](#routesfeaturesshow)
* [routes/login/activate](#routesloginactivate)
* [routes/login/show](#routesloginshow)
* [routes/notFound/redirect](#routesnotfoundredirect)
* [theme](#theme)

### `appname`

Applicationwide title of the app, displayed in title and toolbar.

* default: `run-and-fun`
* type: `string`

### `routes/default`

The main route and the redirect route after login if no route is stored.

* default: `dash`
* type: `string`

### `routes/features/show`

Defines whether feature routes will be displayed or not.

* default: `true`
* type: `boolean`
* values: `true`/`false`

### `routes/login/activate`

Defines whether a login will be used or not.

* default: `true`
* type: `boolean`
* values: `true`/`false`

### `routes/login/show`

Defines whether login route will be displayed or not.

* default: `false`
* type: `boolean`
* values: `true`/`false`

### `routes/notFound/redirect`

Defines whether the 404 route will redirect to the default route or not.

* default: `false`
* type: `boolean`
* values: `true`/`false`

### `theme`

Name of a build-in theme from angular-material.

* default: `indigo-pink`
* type: `string`
* values: `deeppurple-amber`/`indigo-pink`/`pink-bluegrey`/`purple-green`
