# run-and-fun

Provide searching, statistics and visualization for activities from [runkeeper.com](http://runkeeper.com).

# Usage

    git clone https://github.com/inpercima/run-and-fun
    cd run-and-fun

    # copy application.properties.default to application.properties
    cp src/main/resources/application.properties.default src/main/resources/application.properties

    # use existing runkeeper app or register new app at http://runkeeper.com/partner/applications
    # add clientId and clientSecret to application.properties

    # run project within gradle
    ./gradlew bootRun

    # build and run project as jar
    ./gradlew build
    java -jar build/libs/run-and-fun-*.jar

