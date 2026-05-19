package main

import (
	"errors"
	"fmt"
	"os"
	"os/exec"

	"github.com/hanggrian/rulebook/cmd"
)

func main() {
	if err := cmd.Execute(); err != nil {
		var exitErr *exec.ExitError
		if !errors.As(err, &exitErr) {
			fmt.Fprintln(os.Stderr, err)
		}
		os.Exit(1)
	}
}
