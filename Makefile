all:

update:
	./gradlew dependencyUpdates

clean:
	./gradlew vaadinClean clean
	rm -rf package* vite.*.ts frontend/generated node_modules

.PHONY: update clean
