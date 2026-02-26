from abc import ABC, abstractmethod
from typing import override

try:
    from cppcheckdata import Configuration, Scope, Token, reportError
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import Configuration, Scope, Token, reportError


class BaseChecker(ABC):
    ADDON: str = 'rulebook-cppcheck'
    SEVERITY: str = 'warning'
    ID: str
    ARGS: list[str] = []

    # follows naming convention of `com.pinterest.ktlint.rule.engine.core.api.Rule.beforeFirstNode`
    def before_run(self, args: dict[str, str]) -> None:
        pass

    # follows naming convention of `cppcheck.addons.cppcheck.runcheckers`
    @abstractmethod
    def run_check(self, configuration: Configuration) -> None:
        pass

    def report_error(
        self,
        token: Token | None,
        message: str,
        line: int | None = None,
        column: int | None = None,
    ) -> None:
        if line is not None:
            token.linenr = line
        if column is not None:
            token.column = column
        reportError(
            addon=self.ADDON,
            severity=self.SEVERITY,
            errorId=self.ID,
            location=token,
            message=message,
        )


class RulebookChecker(BaseChecker, ABC):
    @abstractmethod
    def get_scopeset(self) -> set[str]:
        pass

    @abstractmethod
    def visit_scope(self, scope: Scope) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        [
            self.visit_scope(scope) for scope in configuration.scopes
            if scope.type in self.get_scopeset()
        ]


class RulebookTokenChecker(BaseChecker, ABC):
    @abstractmethod
    def process_tokens(self, tokens: list[Token]) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        self.process_tokens(configuration.tokenlist)


class RulebookFileChecker(BaseChecker, ABC):
    @abstractmethod
    def check_file(self, token: Token, content: str) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        for token in reversed(configuration.tokenlist):
            if not token.file:
                continue
            with open(token.file, 'r', encoding='UTF-8') as file:
                self.check_file(token, file.read())
            return
