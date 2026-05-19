package linter

var ESLint = Linter{
	Name:            "eslint",
	ConfigPath:      "eslint.config.js",
	DefaultResource: "eslint_crockford.config.js",
	GoogleResource:  ptr("eslint_google.config.js"),
	FileExtensions:  []string{"js", "jsx"},
}
