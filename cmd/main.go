package cmd

import (
	"bufio"
	"errors"
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"

	"github.com/hanggrian/rulebook/cmd/colors"
	"github.com/hanggrian/rulebook/cmd/linter"
	"github.com/hanggrian/rulebook/cmd/resources"
)

const Version = "0.3"

func Execute() error {
	help := false
	version := false
	google := false
	quiet := false
	args := []string{}

	argv := os.Args[1:]
	for i := 0; i < len(argv); i++ {
		tok := argv[i]
		if tok == "--" {
			args = append(args, argv[i+1:]...)
			break
		}
		if strings.HasPrefix(tok, "--") {
			switch tok {
			case "--help":
				help = true
			case "--version":
				version = true
			case "--google":
				google = true
			case "--quiet":
				quiet = true
			default:
				args = append(args, tok)
			}
			continue
		}
		if strings.HasPrefix(tok, "-") && len(tok) > 1 {
			grouped := tok[1:]
			handledAll := true
			for _, ch := range grouped {
				switch ch {
				case 'h':
					help = true
				case 'v':
					version = true
				case 'G', 'g':
					google = true
				case 'q':
					quiet = true
				default:
					handledAll = false
				}
			}
			if !handledAll {
				args = append(args, tok)
			}
			continue
		}
		args = append(args, tok)
	}

	if version {
		fmt.Printf("rulebook %s\n", colors.Bold(Version))
		return nil
	}

	if help || len(args) == 0 {
		fmt.Printf("Helper for Rulebook linter extensions\n\n")
		fmt.Printf("\U0001f680 %s\n", colors.Bold("Usage:"))
		fmt.Printf(
			"   rulebook %s %s %s\n\n",
			colors.Cyan("<command>"),
			colors.Magenta("<arguments>"),
			colors.Blue("[options]"),
		)
		fmt.Printf("\u26a1\ufe0f %s\n", colors.Bold(colors.Cyan("Command:")))
		fmt.Printf("   init <linter> <dir>   Write linter configuration\n")
		fmt.Printf("   lint <path>           Run lint and report violations\n")
		fmt.Printf("   print <file>          Print AST of a source file\n\n")
		fmt.Printf("\U0001f3f7  %s\n", colors.Bold(colors.Magenta("Arguments:")))
		fmt.Printf(
			"   file           Supports %s, %s, %s, %s, %s\n",
			colors.Italic(".c"),
			colors.Italic(".cpp"),
			colors.Italic(".java"),
			colors.Italic(".kt"),
			colors.Italic(".kts"),
		)
		fmt.Printf(
			"                  %s, %s, %s, %s, %s\n",
			colors.Italic(".py"),
			colors.Italic(".js"),
			colors.Italic(".jsx"),
			colors.Italic(".ts"),
			colors.Italic(".tsx"),
		)
		fmt.Printf(
			"   linter         One of %s, %s, %s, %s,\n",
			colors.Italic("checkstyle"),
			colors.Italic("cppcheck"),
			colors.Italic("codenarc"),
			colors.Italic("eslint"),
		)
		fmt.Printf(
			"                  %s, %s or %s\n",
			colors.Italic("ktlint"),
			colors.Italic("pylint"),
			colors.Italic("typescript-eslint"),
		)
		fmt.Printf("   path (=self)   Directory or regular file\n")
		fmt.Printf("   dir (=self)    Target project directory\n\n")
		fmt.Printf("\u2699\ufe0f  %s\n", colors.Bold(colors.Blue("Options:")))
		fmt.Printf("   -g, --google    Use Google style variant if available\n")
		fmt.Printf("   -q, --quiet     Suppress verbose output\n")
		fmt.Printf("   -h, --help      Show this help message and exit\n")
		fmt.Printf("   -v, --version   Show version information and exit\n")
		if len(args) == 0 && !help && !version {
			return errors.New("See --help.")
		}
		return nil
	}

	switch args[0] {
	case "init":
		if len(args) < 2 {
			return errors.New("See --help.")
		}
		linterName := args[1]
		dir := "."
		if len(args) > 2 {
			dir = args[2]
		}
		return initializeConfig(linterName, dir, google, quiet)
	case "lint":
		if len(args) < 2 {
			return errors.New("See --help.")
		}
		target := args[1]
		return lintSource(target, google, quiet)
	case "print":
		if len(args) < 2 {
			return errors.New("See --help.")
		}
		target := args[1]
		return printFile(target, google, quiet)
	default:
		return fmt.Errorf(colors.Red("Unknown command %s."), colors.Bold(args[0]))
	}
}

func initializeConfig(linterName string, dir string, google bool, quiet bool) error {
	// collect parameters
	selected, ok := linter.ByName(linterName)
	if !ok {
		return fmt.Errorf(colors.Red("Unknown linter %q."), linterName)
	}
	resourceName := selected.GetConfigFile(google)
	targetDir, err := filepath.Abs(dir)
	if err != nil {
		return err
	}
	if err := os.MkdirAll(targetDir, 0o755); err != nil {
		return err
	}
	targetPath := filepath.Join(targetDir, selected.ConfigPath)

	// print verbose
	if !quiet {
		flavor := "default"
		if google {
			flavor = "google"
		}
		fmt.Printf("\U0001f4e6 Linter:    %s\n", colors.Bold(selected.Name))
		fmt.Printf("\U0001f4c1 Directory: %s\n", colors.Bold(targetDir))
		fmt.Printf("\U0001f36d Flavor:    %s\n", colors.Bold(flavor))
		fmt.Printf("\U0001f3af Target:    %s\n\n", colors.Bold(targetPath))
	}

	// check existing file
	if info, err := os.Stat(targetPath); err == nil && info.Mode().IsRegular() {
		fmt.Println("File already exists.")
		reader := bufio.NewReader(os.Stdin)
		for {
			fmt.Printf(
				colors.Yellow("Overwrite (%s%s/%so)? "),
				colors.Underline(colors.Bold("y")),
				colors.Bold("es"),
				colors.Underline("n"),
			)
			input, _ := reader.ReadString('\n')
			input = strings.TrimSpace(strings.ToLower(input))
			if input == "n" || input == "no" {
				return errors.New(colors.Red("Configuration canceled."))
			}
			if input == "" || input == "y" || input == "yes" {
				break
			}
		}
	}

	// ensure parent directory exists
	if err := os.MkdirAll(filepath.Dir(targetPath), 0o755); err != nil {
		return err
	}
	content, err := resources.Read(resourceName)
	if err != nil {
		return err
	}

	// write file
	if err := os.WriteFile(targetPath, content, 0o644); err != nil {
		return err
	}
	fmt.Println(colors.Green("Done."))
	fmt.Println()
	fmt.Println("Goodbye!")
	return nil
}

func lintSource(targetPath string, google bool, quiet bool) error {
	info, err := os.Stat(targetPath)
	if err != nil {
		return err
	}

	selected := map[string]linter.Linter{}
	collect := func(path string) {
		ext := strings.TrimPrefix(filepath.Ext(path), ".")
		if found, ok := linter.ByExtension(ext); ok {
			selected[found.Name] = found
		}
	}

	if info.Mode().IsRegular() {
		collect(targetPath)
	} else {
		err :=
			filepath.WalkDir(targetPath, func(path string, d os.DirEntry, walkErr error) error {
				if walkErr != nil {
					return walkErr
				}
				if d.IsDir() {
					return nil
				}
				collect(path)
				return nil
			})
		if err != nil {
			return err
		}
	}

	if len(selected) == 0 {
		return errors.New(colors.Red("No supported source code found in directory."))
	}

	names := make([]string, 0, len(selected))
	for name := range selected {
		names = append(names, name)
	}
	sort.Strings(names)

	if !quiet {
		fmt.Printf("\U0001f4e6 Linters: %s\n", colors.Bold(strings.Join(names, ", ")))
		fmt.Printf("\U0001f4c4 Path:    %s\n\n", colors.Bold(targetPath))
	}

	for _, name := range names {
		if err := selected[name].Lint(targetPath, google); err != nil {
			return err
		}
	}
	return nil
}

func printFile(targetPath string, google bool, quiet bool) error {
	// verify file
	info, err := os.Stat(targetPath)
	if err != nil || !info.Mode().IsRegular() {
		return errors.New(colors.Red("Not a file."))
	}

	// collect parameters
	ext := strings.TrimPrefix(filepath.Ext(targetPath), ".")
	selected, ok := linter.ByExtension(ext)
	if !ok {
		return fmt.Errorf(colors.Red("Unsupported extension %s."), colors.Bold(ext))
	}

	// print verbose
	if !quiet {
		fmt.Printf("\U0001f4e6 Linter: %s\n", colors.Bold(selected.Name))
		fmt.Printf("\U0001f3af Target: %s\n\n", colors.Bold(targetPath))
	}

	// print AST
	return selected.Print(targetPath)
}
