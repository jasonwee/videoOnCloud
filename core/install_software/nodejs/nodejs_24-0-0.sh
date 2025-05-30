#!/bin/bash

# docker run -it debian:latest bash
# cat /etc/debian_version
# 12.10
# apt update
# apt -y install curl xz-utils


if [ $UID -ne 0 ]; then
  echo "runme as root"
  exit
fi

mkdir -p /opt/weetech
cd /opt/weetech
curl -O https://nodejs.org/dist/v24.0.0/node-v24.0.0-linux-x64.tar.xz
tar -xf node-v24.0.0-linux-x64.tar.xz
mv node-v24.0.0-linux-x64 node-v24.0.0
cd node-v24.0.0
cat > init-node-v24.0.0.sh <<EOF
#!/bin/bash

export PATH="$PATH:/opt/weetech/node-v24.0.0/bin"
EOF
rm -f /opt/weetech/node-v24.0.0-linux-x64.tar.xz

# check
./bin/node --version
