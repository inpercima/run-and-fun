# run-and-fun

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com).

# Usage

    git clone https://github.com/inpercima/run-and-fun
    cd run-and-fun

    # copy application.properties.default to application.properties
    cp src/main/resources/application.properties.default src/main/resources/application.properties

    # use existing runkeeper app or register new app at http://runkeeper.com/partner/applications
    # add clientId, clientSecret and redirectUri to application.properties

    # install tools and frontend dependencies
    npm install

    # check, concat and minify javascript
    grunt build

    # run project within gradle
    ./gradlew bootRun

    # alternatively build and run project as jar
    ./gradlew build
    java -jar build/libs/run-and-fun-*.jar

    # open site with browser
    http://localhost:8080/

