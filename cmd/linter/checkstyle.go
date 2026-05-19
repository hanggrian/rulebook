package linter

var Checkstyle = Linter{
	Name:            "checkstyle",
	ConfigPath:      "config/checkstyle/checkstyle.xml",
	DefaultResource: "checkstyle_sun.xml",
	GoogleResource:  ptr("checkstyle_google.xml"),
	FileExtensions:  []string{"java"},
}
