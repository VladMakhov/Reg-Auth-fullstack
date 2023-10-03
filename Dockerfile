FROM node:18.18.0

WORKDIR /frontend

COPY package*.json ./

RUN npm ci

COPY . .

CMD ["npm", "run", "dev"]

FROM openjdk:17

ADD /target/security-jwt-0.0.1-SNAPSHOT.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]


