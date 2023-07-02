.PHONY: clean package build push docker-compose docker-rm-f

IMAGE=llnancy/daily-hot
VERSION=latest

clean:
	mvn clean

package:
	mvn package

build:
	docker build --platform linux/amd64 -t ${IMAGE}:${VERSION} .

push:
	docker push ${IMAGE}:${VERSION}

docker-compose:
	docker-compose -f docker-compose.yml up -d

docker-rm-f:
	docker rm -f daily-hot
