#!/bin/bash

JDK_VERSION=26

mkdir -p /usr/lib/jvm
cd /usr/lib/jvm
wget https://download.java.net/java/GA/jdk${JDK_VERSION}/c3cc523845074aa0af4f5e1e1ed4151d/35/GPL/openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz
tar xvf openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz
mv jdk-${JDK_VERSION} openjdk-${JDK_VERSION}
rm -f openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz

