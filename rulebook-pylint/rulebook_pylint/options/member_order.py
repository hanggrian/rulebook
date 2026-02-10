from pylint.typing import OptionDict

MEMBER_ORDER_OPTION: tuple[str, OptionDict] = \
    (
        'rulebook-member-order',
        {
            'default': ('property', 'constructor', 'function', 'static'),
            'type': 'csv',
            'metavar': '<comma-separated values>',
            'help': 'The structure of a class body.',
        },
    )
