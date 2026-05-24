package resources

import (
	"embed"
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
)

//go:embed checkstyle_google.xml checkstyle_sun.xml codenarc.xml cppcheck_core.json cppcheck_google.json cppcheck_checkset.py rulebook_cppcheck.zip rulebook-checkstyle.jar rulebook-ktlint.jar eslint_crockford.config.js eslint_google.config.js ktlint.editorconfig pylint_google pylint_pylint typescript_eslint_crockford.config.js typescript_eslint_google.config.js
var embedded embed.FS

func Read(name string) ([]byte, error) {
	content, err := embedded.ReadFile(name)
	if err != nil {
		return nil, err
	}
	return content, nil
}

func Path(name string) (string, error) {
	targetDir := filepath.Join(os.TempDir(), "rulebook")
	targetPath := filepath.Join(targetDir, name)
	if info, err := os.Stat(targetPath); err == nil && info.Mode().IsRegular() {
		return targetPath, nil
	}
	content, err := Read(name)
	if err != nil {
		return "", err
	}
	if err := os.MkdirAll(targetDir, 0o755); err != nil {
		return "", err
	}
	if err := os.WriteFile(targetPath, content, 0o644); err != nil {
		return "", err
	}
	return targetPath, nil
}

func MustPath(name string) string {
	path, err := Path(name)
	if err != nil {
		panic(fmt.Sprintf("resource %q unavailable: %v", name, err))
	}
	return path
}

func CppcheckAddon(resourceName string) (string, error) {
	targetRoot := filepath.Join(os.TempDir(), "rulebook")
	addonPath := filepath.Join(targetRoot, "addon.json")
	scriptPath := filepath.Join(targetRoot, "cppcheck_checkset.py")
	payloadPath := filepath.Join(targetRoot, "rulebook_cppcheck.zip")
	if info, err := os.Stat(addonPath); err == nil && info.Mode().IsRegular() {
		if _, err := os.Stat(scriptPath); err == nil {
			if _, err := os.Stat(payloadPath); err == nil {
				return addonPath, nil
			}
		}
	}

	if err := os.MkdirAll(targetRoot, 0o755); err != nil {
		return "", err
	}
	bootstrap, err := Read("cppcheck_checkset.py")
	if err != nil {
		return "", err
	}
	if err := os.WriteFile(scriptPath, bootstrap, 0o755); err != nil {
		return "", err
	}
	payload, err := Read("rulebook_cppcheck.zip")
	if err != nil {
		return "", err
	}
	if err := os.WriteFile(payloadPath, payload, 0o644); err != nil {
		return "", err
	}

	content, err := Read(resourceName)
	if err != nil {
		return "", err
	}
	manifest := map[string]any{}
	if err := json.Unmarshal(content, &manifest); err != nil {
		return "", err
	}
	manifest["script"] = scriptPath
	content, err = json.MarshalIndent(manifest, "", "  ")
	if err != nil {
		return "", err
	}
	content = append(content, '\n')
	if err := os.WriteFile(addonPath, content, 0o644); err != nil {
		return "", err
	}
	return addonPath, nil
}
