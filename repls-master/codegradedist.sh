rm -r codegradedist
mkdir -p codegradedist
cd codegradedist
wget https://gitlab.com/vu-oofp/repls/-/archive/master/repls-master.tar.gz
tar -xvf repls-master.tar.gz --strip-components=1
rm repls-master.tar.gz
tar cvzf repls.tar.gz *
