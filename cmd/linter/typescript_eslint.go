package linter

var TypeScriptESLint = Linter{
	Name:            "typescript-eslint",
	ConfigPath:      "eslint.config.js",
	DefaultResource: "typescript_eslint_crockford.config.js",
	GoogleResource:  ptr("typescript_eslint_google.config.js"),
	FileExtensions:  []string{".ts", ".tsx"},
}
