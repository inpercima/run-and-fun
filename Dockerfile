FROM node:12-alpine AS build-js

LABEL maintainer="Marcel Jänicke <inpercima@gmail.com>"

ENV USER node
USER ${USER}
WORKDIR /home/${USER}

COPY client/src/ ./src/
COPY client/angular.json .
COPY client/browserslist .
COPY client/package.json .
COPY client/tsconfig.app.json .
COPY client/tsconfig.json .
COPY client/tsconfig.spec.json .
COPY client/e2e/tsconfig.json ./e2e/
COPY client/tslint.json .
COPY client/webpack.config.js .
COPY client/yarn.lock .

RUN yarn
RUN yarn build:prod
