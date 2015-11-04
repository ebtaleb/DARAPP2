all:
	cd scripts && ./db_manager.sh -c TEST && cd ..
	mvn clean compile exec:java

run:
	mvn clean compile exec:java

db:
	cd scripts && ./db_manager.sh -c TEST && cd ..

cleandb:
	cd ./scripts && ./db_manager.sh -d TEST && cd ..

.PHONY: cleanall

cleanall:
	mvn clean
	cd scripts && ./db_manager.sh -d TEST && cd ..
