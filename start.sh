#!/bin/bash

set -a
source .env
set +a

nohup java -jar elder-ly-1.0.jar > application.log 2>&1 &