#!/bin/bash

#
# sample output : golang_1-23.log
# 

mkdir -p /opt/weetech/
cd /opt/weetech/
wget https://go.dev/dl/go1.23.0.linux-amd64.tar.gz
tar xvf go1.23.0.linux-amd64.tar.gz
mv go go-1.23.0
rm go1.23.0.linux-amd64.tar.gz
cat > go-1.23.0/init-golang.sh <<EOF
#!/bin/bash


PATH=$PATH:/opt/weetech/go-1.23.0/bin
export PATH
EOF
