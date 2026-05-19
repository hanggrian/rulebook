package colors

import "github.com/fatih/color"

func Bold(text string) string {
	return color.New(color.Bold).Sprint(text)
}

func Italic(text string) string {
	return color.New(color.Italic).Sprint(text)
}

func Underline(text string) string {
	return color.New(color.Underline).Sprint(text)
}

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
