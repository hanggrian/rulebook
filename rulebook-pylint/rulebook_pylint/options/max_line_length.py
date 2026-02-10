from pylint.typing import OptionDict

MAX_LINE_LENGTH_OPTION: tuple[str, OptionDict] = \
    (
        'rulebook-max-line-length',
        {
            'default': 100,
            'type': 'int',
            'metavar': '<integer>',
            'help': 'Max length of a line.',
        },
    )
