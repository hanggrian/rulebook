package linter

var Checkstyle = Linter{
	Name:            "checkstyle",
	ConfigPath:      "config/checkstyle/checkstyle.xml",
	DefaultResource: "checkstyle_sun.xml",
	GoogleResource:  new("checkstyle_google.xml"),
	FileExtensions:  []string{"java"},
}
