all:
	scripts/db_manager.sh -c TEST
	mvn clean compile exec:java

run:
	mvn clean compile exec:java

.PHONY: clean

clean:
	mvn clean
	scripts/db_manager.sh -d TEST
