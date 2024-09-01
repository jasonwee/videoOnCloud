#!/bin/bash


# docker run -it almalinux:8

dnf makecache

#dnf install -y svn texinfo-tex flex zip libgcc glibc-devel git glibc.i686 glibc-devel.i686 libgcc.i686 wget gcc-c++.x86_64 autoconf.noarch
dnf install -y libgcc.x86_64 glibc-devel.x86_64 glibc-headers.x86_64  glibc-devel.i686  
dnf install -y vim gcc-toolset-11.x86_64 gcc-toolset-11-toolchain.x86_64 gcc-toolset-11-make.x86_64 gcc-toolset-11-gcc.x86_64 gcc-toolset-11-gcc-c++.x86_64 wget bzip2

scl list-collections
scl enable gcc-toolset-11 bash

GCC_VERSION=14.2.0

wget --no-check-certificate https://ftp.gnu.org/gnu/gcc/gcc-${GCC_VERSION}/gcc-${GCC_VERSION}.tar.gz

tar xzvf gcc-${GCC_VERSION}.tar.gz
mkdir obj.gcc-${GCC_VERSION}
cd gcc-${GCC_VERSION}
./contrib/download_prerequisites

cd ../obj.gcc-${GCC_VERSION}
../gcc-${GCC_VERSION}/configure --prefix=/opt/weetech/gcc-${GCC_VERSION}/

make -j $(nproc)
sudo make install
# exit scl shell
exit
GCC_VERSION=14.2.0
/opt/weetech/gcc-${GCC_VERSION}/bin/gcc --version
/opt/weetech/gcc-${GCC_VERSION}/bin/g++ --version

