lsof -n -i -g | grep 'rmiregist[ ]' | grep '\*\:' | sed -r 's/rmiregist[ ]*[0-9]*[ ]([0-9]*).*$/\1/' | xargs -I {} kill -9 {}
