package linter

var Cppcheck = Linter{
	Name:            "cppcheck",
	ConfigPath:      "addon.json",
	DefaultResource: "cppcheck_core.json",
	GoogleResource:  new("cppcheck_google.json"),
	FileExtensions:  []string{"c", "cc", "cpp", "cxx", "h", "hh", "hpp", "hxx"},
}
