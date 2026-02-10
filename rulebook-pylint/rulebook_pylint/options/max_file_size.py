from pylint.typing import OptionDict

MAX_FILE_SIZE_OPTION: tuple[str, OptionDict] = \
    (
        'rulebook-max-file-size',
        {
            'default': 1000,
            'type': 'int',
            'metavar': '<integer>',
            'help': 'Max lines of code that is allowed.',
        },
    )
