package linter

import "strings"

var All = []Linter{
	Checkstyle,
	CodeNarc,
	Cppcheck,
	ESLint,
	Ktlint,
	Pylint,
	TypeScriptESLint,
}

func ByName(name string) (Linter, bool) {
	lowerName := strings.ToLower(name)
	for _, item := range All {
		if strings.ToLower(item.Name) == lowerName {
			return item, true
		}
	}
	return Linter{}, false
}

func ByExtension(extension string) (Linter, bool) {
	ext := strings.ToLower(strings.TrimPrefix(extension, "."))
	for _, item := range All {
		for _, e := range item.FileExtensions {
			if strings.ToLower(e) == ext {
				return item, true
			}
		}
	}
	return Linter{}, false
}
