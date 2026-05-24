package linter

import (
	"fmt"
	"io"
	"net/http"
	"os"
	"os/exec"
	"path/filepath"
	"strings"

	"github.com/hanggrian/rulebook/cmd/colors"
	"github.com/hanggrian/rulebook/cmd/resources"
)

const Version = "0.3"
const CheckstyleVersion = "13.4.2"
const KtlintVersion = "1.8.0"
const KtlintPrintVersion = "0.47.0" // the last version with printAST command
const KotlinVersion = "2.3.21"

type Linter struct {
	Name            string
	ConfigPath      string
	DefaultResource string
	GoogleResource  *string
	FileExtensions  []string
}

func (l Linter) ResourceName(google bool) (string, error) {
	if !google {
		return l.DefaultResource, nil
	}
	if l.GoogleResource == nil {
		return "", fmt.Errorf("google variant is unavailable for %s", l.Name)
	}
	return *l.GoogleResource, nil
}

func (l Linter) GetConfigFile(googleVariant bool) string {
	if !googleVariant {
		return l.DefaultResource
	}
	if l.GoogleResource == nil {
		panic(fmt.Sprintf("google variant is unavailable for %s", l.Name))
	}
	return *l.GoogleResource
}

func (l Linter) Print(target string) error {
	switch l.Name {
	case "checkstyle":
		jar, err :=
			downloadTemporary(
				fmt.Sprintf(
					"https://github.com/checkstyle/checkstyle/releases/download/"+
						"checkstyle-%s/checkstyle-%s-all.jar",
					CheckstyleVersion,
					CheckstyleVersion,
				),
				"checkstyle.jar",
				false,
			)
		if err != nil {
			return err
		}

		// java -jar <jar> -T <target> && java -jar <jar> -J <target>
		cmd := exec.Command("java", "-jar", jar, "-T", target)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		if err := cmd.Run(); err != nil {
			return err
		}
		cmd2 := exec.Command("java", "-jar", jar, "-J", target)
		cmd2.Stdout = os.Stdout
		cmd2.Stderr = os.Stderr
		return cmd2.Run()

	case "cppcheck":
		// cppcheck --dump <target
		cmd := exec.Command("cppcheck", "--dump", target)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		if err := cmd.Run(); err != nil {
			return err
		}
		dumpFile := target + ".dump"
		f, err := os.Open(dumpFile)
		if err != nil {
			return err
		}
		defer f.Close()
		if _, err := io.Copy(os.Stdout, f); err != nil {
			_ = os.Remove(dumpFile)
			return err
		}
		_ = os.Remove(dumpFile)
		return nil

	case "ktlint":
		bin, err :=
			downloadTemporary(
				fmt.Sprintf(
					"https://github.com/pinterest/ktlint/releases/download/%s/ktlint",
					KtlintPrintVersion,
				),
				"ktlint_print",
				true,
			)
		if err != nil {
			return err
		}

		// ktlint_print --color printAST <target>
		cmd := exec.Command(bin, "--color", "printAST", target)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		return cmd.Run()

	default:
		return fmt.Errorf("%s cannot print AST.", l.Name)
	}
}

func (l Linter) Lint(target string, google bool) error {
	switch l.Name {
	case "checkstyle":
		checkstyleJar, err :=
			downloadTemporary(
				fmt.Sprintf(
					"https://github.com/checkstyle/checkstyle/releases/download/checkstyle-%s/checkstyle-%s-all.jar",
					CheckstyleVersion,
					CheckstyleVersion,
				),
				"checkstyle.jar",
				false,
			)
		if err != nil {
			return err
		}
		rulebookCheckstyleJar, err :=
			copyTemporary(resources.MustPath("rulebook-checkstyle.jar"), "rulebook-checkstyle.jar", false)
		if err != nil {
			return err
		}
		kotlinStdlibJar, err :=
			downloadTemporary(
				fmt.Sprintf(
					"https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/%s/kotlin-stdlib-%s.jar",
					KotlinVersion,
					KotlinVersion,
				),
				"kotlin-stdlib.jar",
				false,
			)
		if err != nil {
			return err
		}

		// java -cp <rulebook-checkstyle.jar>:<kotlin-stdlib.jar>:<checkstyle.jar> com.puppycrawl.tools.checkstyle.Main -c <config> <target>
		cmd :=
			exec.Command(
				"java",
				"-cp",
				strings.Join(
					[]string{rulebookCheckstyleJar, kotlinStdlibJar, checkstyleJar},
					string(os.PathListSeparator),
				),
				"com.puppycrawl.tools.checkstyle.Main",
				"-c",
				resources.MustPath(l.GetConfigFile(google)),
				target,
			)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		return cmd.Run()

	case "cppcheck":
		addon, err := resources.CppcheckAddon(l.GetConfigFile(google))
		if err != nil {
			return err
		}
		// cppcheck --enable=all --check-level=exhaustive --addon=<addon> <target>
		cmd :=
			exec.Command(
				"cppcheck",
				"-q",
				"--enable=all",
				"--check-level=exhaustive",
				"--addon="+addon,
				"--suppress=checkersReport",
				"--suppress=missingIncludeSystem",
				target,
			)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		return cmd.Run()

	case "ktlint":
		bin, err :=
			downloadTemporary(
				fmt.Sprintf(
					"https://github.com/pinterest/ktlint/releases/download/%s/ktlint",
					KtlintVersion,
				),
				"ktlint",
				true,
			)
		if err != nil {
			return err
		}
		jar, err :=
			copyTemporary(resources.MustPath("rulebook-ktlint.jar"), "rulebook-ktlint.jar", false)
		if err != nil {
			return err
		}

		// ktlint -R <rulebook-ktlint.jar> <target>
		cmd := exec.Command(bin, "-R", jar, target)
		cmd.Stdout = os.Stdout
		cmd.Stderr = os.Stderr
		return cmd.Run()

	default:
		return fmt.Errorf("%s cannot run lint.", l.Name)
	}
}

func ptr(s string) *string { return &s }

func downloadTemporary(url string, filename string, binary bool) (string, error) {
	tmpDir := filepath.Join(os.TempDir(), "rulebook")
	dest := filepath.Join(tmpDir, filename)

	if fi, err := os.Stat(dest); err == nil && fi.Mode().IsRegular() {
		fmt.Printf("%s\n", colors.Green(filename+" found."))
		return dest, nil
	}

	if err := os.MkdirAll(tmpDir, 0o755); err != nil {
		return "", err
	}
	if err := processDownload(dest, url, filename, binary); err != nil {
		return "", err
	}
	return dest, nil
}

func copyTemporary(source string, filename string, binary bool) (string, error) {
	tmpDir := filepath.Join(os.TempDir(), "rulebook")
	dest := filepath.Join(tmpDir, filename)

	if fi, err := os.Stat(dest); err == nil && fi.Mode().IsRegular() {
		fmt.Printf("%s\n", colors.Green(filename+" found."))
		return dest, nil
	}

	if err := os.MkdirAll(tmpDir, 0o755); err != nil {
		return "", err
	}
	if err := processCopy(dest, source, filename, binary); err != nil {
		return "", err
	}
	return dest, nil
}

func processDownload(dest string, url string, filename string, binary bool) error {
	fmt.Printf("%s\n", colors.Yellow("Downloading "+filename+"..."))
	resp, err := http.Get(url)
	if err != nil {
		return err
	}
	defer resp.Body.Close()
	if resp.StatusCode != 200 {
		return fmt.Errorf("Failed to download %s: %s", filename, resp.Status)
	}
	tmp := dest + ".tmp"
	f, err := os.Create(tmp)
	if err != nil {
		return err
	}
	if _, err := io.Copy(f, resp.Body); err != nil {
		f.Close()
		os.Remove(tmp)
		return err
	}
	f.Close()
	if err := processCopy(dest, tmp, filename, binary); err != nil {
		os.Remove(tmp)
		return err
	}
	return os.Remove(tmp)
}

func processCopy(dest string, source string, filename string, binary bool) error {
	fmt.Printf("%s\n", colors.Yellow("Copying "+filename+"..."))
	input, err := os.Open(source)
	if err != nil {
		return err
	}
	defer input.Close()
	output, err := os.Create(dest)
	if err != nil {
		return err
	}
	if _, err := io.Copy(output, input); err != nil {
		output.Close()
		os.Remove(dest)
		return err
	}
	if err := output.Close(); err != nil {
		os.Remove(dest)
		return err
	}
	if binary {
		if err := os.Chmod(dest, 0o755); err != nil {
			return err
		}
	} else {
		if err := os.Chmod(dest, 0o644); err != nil {
			return err
		}
	}
	return nil
}
