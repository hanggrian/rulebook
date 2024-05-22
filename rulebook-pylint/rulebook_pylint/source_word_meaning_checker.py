from typing import TYPE_CHECKING

import regex
from astroid import ClassDef
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options
from regex import Pattern

from .internals import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter

class SourceWordMeaningChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning
    """
    MSG_ALL: str = 'source-word-meaning-all'
    MSG_UTIL: str = 'source-word-meaning-util'

    TITLE_CASE_REGEX: Pattern = \
        regex.compile(
            r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
            r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
        )

    name: str = 'source-word-meaning'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(MSG_ALL, MSG_UTIL)
    options: Options = (
        (
            'rulebook-meaningless-words',
            {
                'default': ('Util', 'Utility', 'Helper', 'Manager', 'Wrapper'),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of banned words.',
            },
        ),
    )

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        for match in self.TITLE_CASE_REGEX.findall(node.name):
            word = match[0]
            if word not in self.linter.config.rulebook_meaningless_words:
                continue

            if word in {'Util', 'Utility'}:
                self.add_message(
                    SourceWordMeaningChecker.MSG_UTIL,
                    node=node,
                    args=node.name[:node.name.index(word)] + 's',
                )
                return None
            self.add_message(SourceWordMeaningChecker.MSG_ALL, node=node, args=word)
        return None

def register(linter: 'PyLinter') -> None:
    linter.register_checker(SourceWordMeaningChecker(linter))
