.PHONY: clean package build build-native push docker-compose docker-rm-f

IMAGE_NAME=llnancy/daily-hot
IMAGE_TAG := $(shell grep -m 2 '<version>' pom.xml | sed -n '2s/.*<version>\(.*\)<\/version>.*/\1/p')

clean:
	mvn clean

package:
	mvn package

build:
	docker build --platform linux/amd64 -t ${IMAGE_NAME}:${IMAGE_TAG} .

build-native:
	mvn -Pnative spring-boot:build-image \
		-Dspring-boot.build-image.imageName=${IMAGE_NAME}:${IMAGE_TAG} \

push:
	docker push ${IMAGE_NAME}:${IMAGE_TAG}

docker-compose:
	docker-compose -f docker-compose.yml up -d

docker-rm-f:
	docker rm -f daily-hot
