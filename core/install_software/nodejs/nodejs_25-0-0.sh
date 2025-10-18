#!/bin/bash

# docker run -it debian:latest bash
# cat /etc/debian_version
# 13.1
# apt update
# apt -y install curl xz-utils libatomic1


if [ $UID -ne 0 ]; then
  echo "runme as root"
  exit
fi

NODE_VERSION=25.0.0

mkdir -p /opt/weetech
cd /opt/weetech
curl -O https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-x64.tar.xz
tar -xf node-v${NODE_VERSION}-linux-x64.tar.xz
mv node-v${NODE_VERSION}-linux-x64 node-v${NODE_VERSION}
cd node-v${NODE_VERSION}
cat > init-node-v${NODE_VERSION}.sh <<EOF
#!/bin/bash

export PATH="$PATH:/opt/weetech/node-v${NODE_VERSION}/bin"
EOF
rm -f /opt/weetech/node-v${NODE_VERSION}-linux-x64.tar.xz

# check
./bin/node --version
