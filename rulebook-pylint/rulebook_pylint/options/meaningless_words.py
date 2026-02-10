from pylint.typing import OptionDict

MEANINGLESS_WORDS_OPTION: tuple[str, OptionDict] = \
    (
        'rulebook-meaningless-words',
        {
            'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
            'type': 'csv',
            'metavar': '<comma-separated values>',
            'help': 'A set of banned names.',
        },
    )
