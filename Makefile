.PHONY: install format

install:
	@if [ "$(CLEAN)" = "true" ]; then \
		go mod download; \
	else \
		go mod tidy; \
	fi

format:
	go fmt ./...
