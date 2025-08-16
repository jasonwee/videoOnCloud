#!/bin/bash


# docker run -it almalinux:10

dnf makecache

dnf install -y libgcc.x86_64 glibc-devel.x86_64 kernel-headers.x86_64
dnf install -y make.x86_64 gcc-c++.x86_64 wget bzip2
dnf groupinstall -y "Development Tools"

GCC_VERSION=15.2.0
wget --no-check-certificate https://ftp.gnu.org/gnu/gcc/gcc-${GCC_VERSION}/gcc-${GCC_VERSION}.tar.gz
tar xzvf gcc-${GCC_VERSION}.tar.gz
mkdir obj.gcc-${GCC_VERSION}
cd gcc-${GCC_VERSION}
./contrib/download_prerequisites
cd ../obj.gcc-${GCC_VERSION}
../gcc-${GCC_VERSION}/configure --prefix=/opt/weetech/gcc-${GCC_VERSION}/ --disable-multilib
make -j $(nproc)
make install
/opt/weetech/gcc-${GCC_VERSION}/bin/gcc --version
/opt/weetech/gcc-${GCC_VERSION}/bin/g++ --version
