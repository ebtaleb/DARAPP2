#!/bin/bash

db=""

function init_db {
    local db_name=$1
    mysql -hlocalhost -uroot -pnyanyanya -e "CREATE DATABASE $db_name DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
}

if [ $# -eq 0 ]
then
    echo "No arguments supplied"
    exit 1
fi

if [ $# -lt 2 ]
then
    echo "create_db [-cd] db_name"
    exit 1
fi

while getopts ":c:d:" opt; do
    case "$opt" in
        c)
            db="$OPTARG"
            init_db $db
            echo "database $db created"
            break
            ;;
        d)
            mysqladmin -uroot -pnyanyanya drop $OPTARG
            db="$OPTARG"
            echo "database $db deleted"
            exit 0
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            exit 1
            ;;
    esac
done

mysql -hlocalhost -uroot -pnyanyanya $db < "users.sql"

if [ "$?" -ne "0" ]; then
  echo "Sorry, table USERS could not be created"
  exit 1
else
  echo "table USERS created"
fi

mysql -hlocalhost -uroot -pnyanyanya $db < "events.sql"

if [ "$?" -ne "0" ]; then
  echo "Sorry, table EVENTS could not be created"
  exit 1
else
  echo "table EVENTS created"
fi

mysql -hlocalhost -uroot -pnyanyanya $db < "comments.sql"

if [ "$?" -ne "0" ]; then
  echo "Sorry, table COMMENTS could not be created"
  exit 1
else
  echo "table COMMENTS created"
fi

mysql -hlocalhost -uroot -pnyanyanya $db < "follows.sql"

if [ "$?" -ne "0" ]; then
  echo "Sorry, table FOLLOWS could not be created"
  exit 1
else
  echo "table FOLLOWS created"
fi