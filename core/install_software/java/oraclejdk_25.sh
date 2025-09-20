#!/bin/bash

JDK_VERSION=25

mkdir -p /usr/lib/jvm
cd /usr/lib/jvm
wget https://download.oracle.com/java/${JDK_VERSION}/latest/jdk-${JDK_VERSION}_linux-x64_bin.tar.gz
tar xvf jdk-${JDK_VERSION}_linux-x64_bin.tar.gz
rm -f jdk-${JDK_VERSION}_linux-x64_bin.tar.gz

