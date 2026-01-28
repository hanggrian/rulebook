from typing import override

from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.messages import _Messages

try:
    from cppcheckdata import Configuration
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Configuration


class UnnecessarySwitchChecker(RulebookChecker):
    """See detail: https://hanggrian.github.io/rulebook/rules/#unnecessary-switch"""
    MSG: str = 'unnecessary.switch'

    id: str = 'unnecessary-switch'

    @override
    def run_check(self, configuration: Configuration) -> None:
        for scope in configuration.scopes:
            if scope.type != 'Switch':
                continue
            case_count: int = 0
            curr = scope.bodyStart
            while curr and curr != scope.bodyEnd:
                if curr.str == 'case':
                    case_count += 1
                curr = curr.next
            if case_count > 1:
                continue
            switch_token = scope.bodyStart
            while switch_token and switch_token.str != 'switch':
                switch_token = switch_token.previous
            if not switch_token:
                continue
            self.report_error(switch_token, _Messages.get(self.MSG))
