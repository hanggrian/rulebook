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

    # Follows naming convention of `com.pinterest.ktlint.rule.engine.core.api.Rule.beforeFirstNode`
    def before_run(self, args: dict[str, str]) -> None:
        pass

    # Follows naming convention of `cppcheck.addons.cppcheck.runcheckers`
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
        if line:
            token.linenr = line
        if column:
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
    def get_scope_set(self) -> set[str]:
        pass

    @abstractmethod
    def visit_scope(self, scope: Scope) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        [
            self.visit_scope(scope) for scope in configuration.scopes
            if scope.type in self.get_scope_set()
        ]


class RulebookTokenChecker(BaseChecker, ABC):
    @abstractmethod
    def process_token(self, token: list[Token]) -> None:
        pass

    @override
    def run_check(self, configuration: Configuration) -> None:
        [self.process_token(token) for token in configuration.tokenlist]


class RulebookFileChecker(BaseChecker, ABC):
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
