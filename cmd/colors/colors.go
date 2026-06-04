package colors

import "github.com/fatih/color"

func Red(text string) string {
	return color.New(color.FgRed).Sprint(text)
}

func Green(text string) string {
	return color.New(color.FgGreen).Sprint(text)
}

func Yellow(text string) string {
	return color.New(color.FgYellow).Sprint(text)
}

func Blue(text string) string {
	return color.New(color.FgBlue).Sprint(text)
}

func Cyan(text string) string {
	return color.New(color.FgCyan).Sprint(text)
}

func Magenta(text string) string {
	return color.New(color.FgMagenta).Sprint(text)
}

func B(text string) string {
	return color.New(color.Bold).Sprint(text)
}

func D(text string) string {
	return color.New(color.Faint).Sprint(text)
}

func U(text string) string {
	return color.New(color.Underline).Sprint(text)
}
