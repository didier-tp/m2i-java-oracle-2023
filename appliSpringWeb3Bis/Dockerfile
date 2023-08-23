FROM openjdk:17
# this new image will be create from parent image = openjdk:17(stable)

# Create app directory inside docker image
WORKDIR /usr/app


COPY  target/appliSpringWeb3.jar  ./

#optimisation possible : https://spring.io/guides/topicals/spring-boot-docker

EXPOSE 8181

CMD ["java","-jar","appliSpringWeb3.jar" ]