package linter

var Ktlint = Linter{
	Name:            "ktlint",
	ConfigPath:      ".editorconfig",
	DefaultResource: "ktlint.editorconfig",
	GoogleResource:  nil,
	FileExtensions:  []string{"kt", "kts"},
}
