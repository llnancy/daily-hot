.PHONY: clean build build-native-image push docker-compose docker-rm-f

IMAGE_NAME=llnancy/daily-hot
IMAGE_TAG=$(shell awk -F "=" '/version/ {gsub(/[\047|"]/,""); print $$2}' build.gradle | tr -d ' ')

clean:
	gradle clean

build:
	gradle build

build-native-image:
	gradle bootBuildImage

push:
	docker push ${IMAGE_NAME}:${IMAGE_TAG}

docker-compose:
	docker-compose -f docker-compose.yml up -d

docker-rm-f:
	docker rm -f daily-hot
