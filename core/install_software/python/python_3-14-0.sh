#!/bin/bash

# docker run -it debian:latest bash
# apt update && apt -y upgrade
# apt install -y wget xz-utils vim nano build-essential libreadline-dev libncursesw5-dev libssl-dev libsqlite3-dev tk-dev libgdbm-dev libc6-dev libbz2-dev libffi-dev zlib1g-dev libbz2-dev libsqlite3-dev libsqlite3-0

PYTHON_VERSION=3.14.0
# install
cd /usr/local/src
wget https://www.python.org/ftp/python/$PYTHON_VERSION/Python-$PYTHON_VERSION.tar.xz
tar xf Python-$PYTHON_VERSION.tar.xz
cd Python-$PYTHON_VERSION
./configure --enable-optimizations --prefix=/opt/weetech/python-$PYTHON_VERSION 
make
# make test
# if error, run clean below and redo on the configure
# make clean && make distclean
# if still error, just skip make and do make altinstall only
make altinstall


# check
/opt/weetech/python-$PYTHON_VERSION/bin/pip${PYTHON_VERSION%.0} --version
/opt/weetech/python-$PYTHON_VERSION/bin/python${PYTHON_VERSION%.0} --version
