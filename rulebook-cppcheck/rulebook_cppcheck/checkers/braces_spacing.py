from re import Pattern, compile as re

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BracesSpacingChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#braces-spacing"""
    ID: str = 'braces-spacing'
    _MSG: str = 'braces.spacing'

    _REGEX: Pattern = re(r'(?<![=>])\s*\{([^\n{}]+)\}')

    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        for match in self._REGEX.finditer(content):
            inner: str = match.group(1)
            if inner.startswith(' ') and inner.endswith(' '):
                continue
            index: int = match.start() + match.group(0).find('{')
            self.report_error(
                token,
                Messages.get(self._MSG),
                content.count('\n', 0, index) + 1,
                index - content.rfind('\n', 0, index),
            )
