# run and fun - docker

## Getting started

Normally the docker version is used to build and run the productive version, so it is prepared as productive.

Create environment file for `docker` and `docker-compose` and check the [configuration](#configuration).

```bash
cp default.env .env
```

**Note**: This file will not be under version control but listed in .gitignore.

Check for the existence of `environment.prod.ts` as described in [run and fun - client](./client).

Check for the existence of `application-prod.yml` as described in [run and fun - server](./server).

## Usage

```bash
# build the image and container
docker-compose build

# run the container
docker-compose up -d

# run the container and rebuild
docker-compose up -d --build

# stop the container
docker-compose down
```

## Configuration

### Table of contents

* [PORT](#port)
* [PROFILE](#profile)

### `PORT`

Defines the port from external access.

* default: `8080`
* type: `number`

### `PROFILE`

Defines the profile which should be used.

* default: `prod`
* type: `string`
* values: `dev`/`prod`
