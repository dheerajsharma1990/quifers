#!/bin/bash
./g.sh distZip
scp -i quifers-dev-pk.pem web/build/distributions/web.zip email-util/build/distributions/email-util.zip ubuntu@52.74.237.216:~/artifacts