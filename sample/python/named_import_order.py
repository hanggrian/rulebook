from re import Pattern, compile as f, search
from sys import argv, modules

a: Pattern = f(r'(a)')
search(a, 'string')

print(modules)
print(argv)
