from __future__ import annotations

from typing import TYPE_CHECKING

from pylint.utils import register_plugins

from rulebook_pylint.checkers.abbreviation_as_word import AbbreviationAsWordChecker
from rulebook_pylint.checkers.abstract_function_in_abstract_class import (
    AbstractFunctionInAbstractClassChecker,
)
from rulebook_pylint.checkers.block_comment_clipping import BlockCommentClippingChecker
from rulebook_pylint.checkers.block_comment_trimming import BlockCommentTrimmingChecker
from rulebook_pylint.checkers.case_separator import CaseSeparatorChecker
from rulebook_pylint.checkers.comment_spacing import CommentSpacingChecker
from rulebook_pylint.checkers.comment_trimming import CommentTrimmingChecker
from rulebook_pylint.checkers.common_function_position import CommonFunctionPositionChecker
from rulebook_pylint.checkers.exception_inheritance import ExceptionInheritanceChecker
from rulebook_pylint.checkers.generic_name import GenericNameChecker
from rulebook_pylint.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_pylint.checkers.lowercase_hex import LowercaseHexChecker
from rulebook_pylint.checkers.no_meaningless_word import NoMeaninglessWordChecker
from rulebook_pylint.checkers.member_order import MemberOrderChecker
from rulebook_pylint.checkers.member_separator import MemberSeparatorChecker
from rulebook_pylint.checkers.named_import_order import NamedImportOrderChecker
from rulebook_pylint.checkers.no_blank_line_after_colon import (
    NoBlankLineAfterColonChecker,
)
from rulebook_pylint.checkers.no_confusing_assertion import NoConfusingAssertionChecker
from rulebook_pylint.checkers.no_duplicate_blank_line import NoDuplicateBlankLineChecker
from rulebook_pylint.checkers.no_duplicate_blank_line_in_block_comment import (
    NoDuplicateBlankLineInBlockCommentChecker,
)
from rulebook_pylint.checkers.no_duplicate_blank_line_in_comment import (
    NoDuplicateBlankLineInCommentChecker,
)
from rulebook_pylint.checkers.no_duplicate_import import NoDuplicateImportChecker
from rulebook_pylint.checkers.no_duplicate_space import NoDuplicateSpaceChecker
from rulebook_pylint.checkers.no_leading_blank_line import (
    NoLeadingBlankLineChecker,
)
from rulebook_pylint.checkers.no_lonely_case import NoLonelyCaseChecker
from rulebook_pylint.checkers.no_nested_if_else import NoNestedIfElseChecker
from rulebook_pylint.checkers.no_redundant_default import NoRedundantDefaultChecker
from rulebook_pylint.checkers.no_useless_continue import NoUselessContinueChecker
from rulebook_pylint.checkers.parameter_wrapping import ParameterWrappingChecker
from rulebook_pylint.checkers.parentheses_clipping import ParenthesesClippingChecker
from rulebook_pylint.checkers.parentheses_in_import import ParenthesesInImportChecker
from rulebook_pylint.checkers.parentheses_trimming import ParenthesesTrimmingChecker
from rulebook_pylint.checkers.shorthand_assignment import ShorthandAssignmentChecker
from rulebook_pylint.checkers.specific_assertion import SpecificAssertionChecker
from rulebook_pylint.checkers.todo_comment_style import TodoCommentStyleChecker
from rulebook_pylint.checkers.trailing_comma import TrailingCommaChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


def initialize(linter: PyLinter) -> None:
    register_plugins(linter, __path__[0])


__all__: list[str] = [
    'AbbreviationAsWordChecker',
    'AbstractFunctionInAbstractClassChecker',
    'BlockCommentClippingChecker',
    'BlockCommentTrimmingChecker',
    'CaseSeparatorChecker',
    'CommentSpacingChecker',
    'CommentTrimmingChecker',
    'CommonFunctionPositionChecker',
    'ExceptionInheritanceChecker',
    'GenericNameChecker',
    'InnerClassPositionChecker',
    'LowercaseHexChecker',
    'MemberOrderChecker',
    'MemberSeparatorChecker',
    'NamedImportOrderChecker',
    'NoConfusingAssertionChecker',
    'NoBlankLineAfterColonChecker',
    'NoDuplicateBlankLineChecker',
    'NoDuplicateBlankLineInBlockCommentChecker',
    'NoDuplicateBlankLineInCommentChecker',
    'NoDuplicateImportChecker',
    'NoDuplicateSpaceChecker',
    'NoLeadingBlankLineChecker',
    'NoLonelyCaseChecker',
    'NoMeaninglessWordChecker',
    'NoNestedIfElseChecker',
    'NoRedundantDefaultChecker',
    'NoUselessContinueChecker',
    'ParameterWrappingChecker',
    'ParenthesesClippingChecker',
    'ParenthesesInImportChecker',
    'ParenthesesTrimmingChecker',
    'ShorthandAssignmentChecker',
    'SpecificAssertionChecker',
    'TodoCommentStyleChecker',
    'TrailingCommaChecker',
    'initialize',
]
