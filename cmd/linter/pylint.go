package linter

var Pylint = Linter{
	Name:            "pylint",
	ConfigPath:      ".pylintrc",
	DefaultResource: "pylint_pylint",
	GoogleResource:  ptr("pylint_google"),
	FileExtensions:  []string{"py"},
}
