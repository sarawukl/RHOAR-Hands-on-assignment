#!/bin/bash

oc delete all,configmap -l app=freelancer-service
oc delete all,configmap -l app=project-service
oc delete all,configmap -l app=gateway-service
