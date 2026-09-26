#!/bin/bash

JDK_VERSION=27

mkdir -p /usr/lib/jvm
cd /usr/lib/jvm
wget https://download.java.net/java/GA/jdk${JDK_VERSION}/55ce5470a6294008af0057ff4626d0e5/35/GPL/openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz
if [ $? -ne 0 ]; then
  echo "something is wrong with the file, not installing it"
  exit 1
fi
mkdir openjdk-${JDK_VERSION}
tar xvf openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz --strip-components=1 -C openjdk-${JDK_VERSION}
#mv jdk-${JDK_VERSION} openjdk-${JDK_VERSION}
rm -f openjdk-${JDK_VERSION}_linux-x64_bin.tar.gz

