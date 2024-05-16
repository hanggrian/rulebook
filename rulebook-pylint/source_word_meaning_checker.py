import re
from typing import TYPE_CHECKING

from astroid import ClassDef
from pylint.checkers import BaseChecker
from pylint.typing import MessageDefinitionTuple, Options

from messages import Messages

if TYPE_CHECKING:
    from pylint.lint import PyLinter


class SourceWordMeaningChecker(BaseChecker):
    """See wiki: https://github.com/hendraanggrian/rulebook/wiki/Rules#source-word-meaning
    """
    TITLE_CASE_REGEX: re.Pattern = re.compile(
        r'((^[a-z]+)|([0-9]+)|([A-Z]{1}[a-z]+)|' +
        r'([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))',
    )

    name: str = 'source-word-meaning'
    msgs: dict[str, MessageDefinitionTuple] = Messages.get(name)
    options: Options = (
        (
            'rulebook-meaningless-words',
            {
                'default': (
                    'Abstract',
                    'Base',
                    'Util',
                    'Utility',
                    'Helper',
                    'Manager',
                    'Wrapper',
                    'Data',
                    'Info',
                ),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'A set of banned words.',
            },
        ),
        (
            'rulebook-meaningless-words-ignored',
            {
                'default': (),
                'type': 'csv',
                'metavar': '<comma-separated names>',
                'help': 'Exclusion to the banned words.',
            }
        ),
    )

    def visit_classdef(self, node: ClassDef) -> None:
        # checks for violation
        for match in self.TITLE_CASE_REGEX.findall(node.name):
            word = match[0]
            if word not in self.linter.config.rulebook_meaningless_words:
                continue
            if word in self.linter.config.rulebook_meaningless_words_ignored:
                continue
            self.add_message(SourceWordMeaningChecker.name, node=node, args=word)


def register(linter: 'PyLinter') -> None:
    linter.register_checker(SourceWordMeaningChecker(linter))
