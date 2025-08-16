#!/bin/bash

#
# sample output : golang_1-25.log
# 
GOLANG_VERSION=1.25.0

mkdir -p /opt/weetech/
cd /opt/weetech/
wget https://go.dev/dl/go$GOLANG_VERSION.linux-amd64.tar.gz
tar xvf go$GOLANG_VERSION.linux-amd64.tar.gz
mv go go-$GOLANG_VERSION
rm -f go$GOLANG_VERSION.linux-amd64.tar.gz
cat > go-$GOLANG_VERSION/init-golang.sh <<EOF
#!/bin/bash


PATH=$PATH:/opt/weetech/go-$GOLANG_VERSION/bin
export PATH
EOF
