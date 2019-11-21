unset LD_PRELOAD
lsof -n -i -g | grep 'java[ ]' | grep '\*\:' | sed -r 's/java[ ]*[0-9]*[ ]([0-9]*).*$/\1/' | xargs -I {} kill -9 {}
