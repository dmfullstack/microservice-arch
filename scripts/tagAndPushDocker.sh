#!/usr/bin/env bash

REGISTRY_URI=localhost:5000/

docker tag model-generator ${REGISTRY_URI}model-generator
docker push ${REGISTRY_URI}model-generator

docker tag kubernetes-monitor ${REGISTRY_URI}kubernetes-monitor
docker push ${REGISTRY_URI}kubernetes-monitor
