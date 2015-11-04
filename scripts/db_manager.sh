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

table="use $db; CREATE TABLE IF NOT EXISTS TRACKS (ID INT (5) NOT NULL AUTO_INCREMENT,TITLE VARCHAR (20) NOT NULL,SINGER VARCHAR (20) NOT NULL,PRIMARY KEY ( ID ));"

mysql -hlocalhost -uroot -pnyanyanya -e "$table"
if [ "$?" -ne "0" ]; then
  echo "Sorry, table TRACKS could not be created"
  exit 1
else
  echo "table TRACKS created"
fi

mysql -hlocalhost -uroot -pnyanyanya $db < "./scripts/create_user.sql"

if [ "$?" -ne "0" ]; then
  echo "Sorry, table USERS could not be created"
  exit 1
else
  echo "table USERS created"
fi