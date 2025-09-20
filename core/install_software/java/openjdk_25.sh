#!/bin/bash

JDK_VERSION=25

mkdir -p /usr/lib/jvm
cd /usr/lib/jvm
wget https://download.java.net/java/GA/jdk${JDK_VERSION}/bd75d5f9689641da8e1daabeccb5528b/36/GPL/openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz
tar xvf openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz
mv jdk-${JDK_VERSION} openjdk-${JDK_VERSION}
rm -f openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz

