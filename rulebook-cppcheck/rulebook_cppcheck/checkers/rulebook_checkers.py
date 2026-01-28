from abc import ABC, abstractmethod
from typing import override

try:
    from cppcheckdata import Configuration, Token, reportError
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Configuration, Token, reportError


class RulebookChecker(ABC):
    ADDON: str = 'rulebook-cppcheck'
    SEVERITY: str = 'warning'

    id: str
    args: list[str] = []

    # Follows naming convention of `com.pinterest.ktlint.rule.engine.core.api.Rule.beforeFirstNode`
    def before_run(self, args: dict[str, str]) -> None:
        pass

    # Follows naming convention of `cppcheck.addons.cppcheck.runcheckers`
    @abstractmethod
    def run_check(self, configuration: Configuration) -> None:
        pass

    def report_error(self, token: Token | None, message: str) -> None:
        reportError(
            addon=self.ADDON,
            severity=self.SEVERITY,
            errorId=self.id,
            location=token,
            message=message,
        )


class RulebookFileChecker(RulebookChecker, ABC):
    @abstractmethod
    def check_file(self, token: Token, content: str) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        file_tokens: set[str] = set()
        for token in reversed(configuration.tokenlist):
            if token.file in file_tokens:
                continue
            file_tokens.add(token.file)
            with open(token.file, 'r', encoding='UTF-8') as file:
                content: str = file.read()
                if content:
                    self.check_file(token, content)
