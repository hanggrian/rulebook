from re import Pattern, compile as re

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookFileChecker
from rulebook_cppcheck.messages import Messages

try:
    from cppcheckdata import Token
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Token


class BracesSpacesChecker(RulebookFileChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#braces-spaces"""
    ID: str = 'braces-spaces'
    _MSG: str = 'braces.spaces'

    _REGEX: Pattern = re(r'{([^\n{}]+)}')

    def check_file(self, token: Token, content: str) -> None:
        # checks for violation
        for match in self._REGEX.finditer(content):
            inner: str = match.group(1)
            if inner.startswith(' ') and inner.endswith(' '):
                continue
            start: int = match.start()
            self.report_error(
                token,
                Messages.get(self._MSG),
                content.count('\n', 0, start) + 1,
                start - content.rfind('\n', 0, start),
            )
