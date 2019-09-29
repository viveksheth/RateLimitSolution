#!/usr/bin/env bash
echo " -------  --------------  --------------  --------------  --------------  --------------  -------"
echo " ------- Generating 110 requests to hit rate limit of 100 requests per hour   -------"
echo " -------  --------------  --------------  --------------  --------------  --------------  -------"

for i in {1..101};
    do

        curl -s -w "\n%{http_code}" 'http://localhost:8080/getEmployeeData/12343252?format=json' | {
            read body
            read code
            echo "Request #$i Response Code: $code"
        }
    done