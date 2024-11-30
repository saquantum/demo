#! /usr/bin/env bash

if [ ${#} -eq 2 ];then
    NAME=$(basename ${2} .c)
    if [ ! -f "${NAME}.c" ];then
        echo "non-existing file"
        exit 1
    fi
    
    if [ "${1}" = "compile" ];then
        gcc -Wall -std=c99 "${NAME}.c" -o "${NAME}"
        exit 0
    fi

    if [ "${1}" = "run" ];then
        ./"${NAME}"
        exit 0
    fi

    if [ "${1}" = "build" ];then
        gcc -Wall -std=c99 "${NAME}.c" -o "${NAME}"
        ./"${NAME}"
        exit 0
    fi
fi

echo "run the bash with \"./b build FILENAME.\" "
