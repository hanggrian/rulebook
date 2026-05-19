package linter

var CodeNarc = Linter{
	Name:            "codenarc",
	ConfigPath:      "config/codenarc/codenarc.xml",
	DefaultResource: "codenarc.xml",
	GoogleResource:  nil,
	FileExtensions:  []string{"groovy"},
}
