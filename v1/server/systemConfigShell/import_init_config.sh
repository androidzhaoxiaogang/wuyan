#!/bin/bash

source ./db.config

$PATH_MONGO $DB_HOST:$DB_PORT/$DB_NAME -u $DB_USERNAME -p $DB_PASSWORD ./config.js

if [ $? == 0 ]; then
        echo "Import config successfully!"
else
        echo "Import config failed!"
fi
