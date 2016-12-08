FROM ubuntu:14.04
MAINTAINER caffe-maint@googlegroups.com

RUN apt-get update && apt-get install -y --no-install-recommends \
        apache2 \
        libapache2-mod-php5 \
        apache2-bin \
        apache2-data \
        libapache2-mod-python \
        php5 \
        php5-cli \
        build-essential \
        cmake \
        git \
        wget \
        libatlas-base-dev \
        libboost-all-dev \
        libgflags-dev \
        libgoogle-glog-dev \
        libhdf5-serial-dev \
        libleveldb-dev \
        liblmdb-dev \
        libopencv-dev \
        libprotobuf-dev \
        libsnappy-dev \
        protobuf-compiler \
        openssh-server \
        openssh-sftp-server \
        openssh-client \
        python-dev \
        python-numpy \
        python-pip \
        unzip \
        vim-tiny \
        python-scipy && \
    rm -rf /var/lib/apt/lists/*

ENV CAFFE_ROOT=/opt/caffe
WORKDIR $CAFFE_ROOT

# FIXME: clone a specific git tag and use ARG instead of ENV once DockerHub supports this.
ENV CLONE_TAG=master

RUN git clone -b ${CLONE_TAG} --depth 1 https://github.com/BVLC/caffe.git . && \
    for req in $(cat python/requirements.txt) pydot; do pip install $req; done && \
    mkdir build && cd build && \
    cmake -DCPU_ONLY=1 .. && \
    make -j"$(nproc)"

ENV PYCAFFE_ROOT $CAFFE_ROOT/python
ENV PYTHONPATH $PYCAFFE_ROOT:$PYTHONPATH
ENV PATH $CAFFE_ROOT/build/tools:$PYCAFFE_ROOT:$PATH
RUN echo "$CAFFE_ROOT/build/lib" >> /etc/ld.so.conf.d/caffe.conf && ldconfig

COPY property.zip /opt/caffe/examples/
COPY property_senze.zip /var/www/html/

VOLUME ["/opt/caffe/", "/workspace/"]

EXPOSE 80 443 22

WORKDIR /workspace
