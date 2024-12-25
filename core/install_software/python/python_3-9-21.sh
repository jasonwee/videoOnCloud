#!/bin/bash

# run as root
# docker run -it debian:latest bash
# apt update
# apt install -y wget xz-utils vim nano build-essential libreadline-dev libncursesw5-dev libssl-dev libsqlite3-dev tk-dev libgdbm-dev libc6-dev libbz2-dev libffi-dev zlib1g-dev libbz2-dev

# install
cd /usr/local/src
wget https://www.python.org/ftp/python/3.9.21/Python-3.9.21.tar.xz
tar xf Python-3.9.21.tar.xz
cd Python-3.9.21
./configure --enable-optimizations --prefix=/opt/weetech/python-3.9.21
make
# make test
# if error, run clean below and redo on the configure
# make clean && make distclean
# if still error, just skip make and do make altinstall only
make altinstall


# check
/opt/weetech/python-3.9.21/bin/pip3.9 --version
/opt/weetech/python-3.9.21/bin/python3.9 --version
