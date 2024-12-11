FROM ubuntu:latest AS build
LABEL authors="suwag"
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

ENTRYPOINT ["top", "-b"]