lsof -n -i | grep "\*\:" | sed -r 's/java[ ]*(\b[0-9]+\b).*$/\1/' | xargs -I {} kill -9 {} 2> /dev/null
lsof -n -i | grep "\*\:" | sed -r 's/rmiregist[ ]*(\b[0-9]+\b).*$/\1/' | xargs -I {} kill -9 {} 2> /dev/null

