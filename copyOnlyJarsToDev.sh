#!/bin/bash
./g.sh distZip
scp -i quifers-dev-pk.pem web/build/libs/web.jar db-persister/build/libs/db-persister.jar domain/build/libs/domain.jar ubuntu@52.74.237.216:~/artifacts/web/lib/
scp -i quifers-dev-pk.pem email-util/build/libs/email-util.jar db-persister/build/libs/db-persister.jar domain/build/libs/domain.jar ubuntu@52.74.237.216:~/artifacts/email-util/lib/