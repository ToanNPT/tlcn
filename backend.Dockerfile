FROM maven:3-adoptopenjdk-11
WORKDIR /backend
COPY . .
#mount .m2 dir of maven in host dir windows to docker image when build
VOLUME C:/Users/ASUS/.m2:/root/.m2

#build jar file and skip the test
RUN mvn package -Dmaven.test.skip=true

#run project
CMD mvn spring-boot:run

EXPOSE 8080