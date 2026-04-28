from __future__ import annotations

from typing import TYPE_CHECKING

from pylint.utils import register_plugins

from rulebook_pylint.checkers.abbreviation_as_word import AbbreviationAsWordChecker
from rulebook_pylint.checkers.block_comment_clip import BlockCommentClipChecker
from rulebook_pylint.checkers.block_comment_trim import BlockCommentTrimChecker
from rulebook_pylint.checkers.case_separator import CaseSeparatorChecker
from rulebook_pylint.checkers.comment_spaces import CommentSpacesChecker
from rulebook_pylint.checkers.comment_trim import CommentTrimChecker
from rulebook_pylint.checkers.common_function_position import CommonFunctionPositionChecker
from rulebook_pylint.checkers.complicated_assertion import ComplicatedAssertionChecker
from rulebook_pylint.checkers.complicated_assignment import ComplicatedAssignmentChecker
from rulebook_pylint.checkers.confusing_assertion import ConfusingAssertionChecker
from rulebook_pylint.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_pylint.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_pylint.checkers.duplicate_blank_line_in_comment import \
    DuplicateBlankLineInCommentChecker
from rulebook_pylint.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_pylint.checkers.generic_name import GenericNameChecker
from rulebook_pylint.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_pylint.checkers.internal_error import InternalErrorChecker
from rulebook_pylint.checkers.lonely_case import LonelyCaseChecker
from rulebook_pylint.checkers.lowercase_hexadecimal import LowercaseHexadecimalChecker
from rulebook_pylint.checkers.meaningless_word import MeaninglessWordChecker
from rulebook_pylint.checkers.member_order import MemberOrderChecker
from rulebook_pylint.checkers.member_separator import MemberSeparatorChecker
from rulebook_pylint.checkers.named_import_order import NamedImportOrderChecker
from rulebook_pylint.checkers.nested_if_else import NestedIfElseChecker
from rulebook_pylint.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_pylint.checkers.parentheses_clip import ParenthesesClipChecker
from rulebook_pylint.checkers.parentheses_trim import ParenthesesTrimChecker
from rulebook_pylint.checkers.redundant_default import RedundantDefaultChecker
from rulebook_pylint.checkers.todo_comment import TodoCommentChecker
from rulebook_pylint.checkers.trailing_comma import TrailingCommaChecker
from rulebook_pylint.checkers.unnecessary_abstract import UnnecessaryAbstractChecker
from rulebook_pylint.checkers.unnecessary_blank_line_after_colon import \
    UnnecessaryBlankLineAfterColonChecker
from rulebook_pylint.checkers.unnecessary_continue import UnnecessaryContinueChecker
from rulebook_pylint.checkers.unnecessary_leading_blank_line import \
    UnnecessaryLeadingBlankLineChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


def initialize(linter: PyLinter) -> None:
    register_plugins(linter, __path__[0])


__all__: list[str] = [
    'AbbreviationAsWordChecker',
    'BlockCommentClipChecker',
    'BlockCommentTrimChecker',
    'CaseSeparatorChecker',
    'CommentSpacesChecker',
    'CommentTrimChecker',
    'CommonFunctionPositionChecker',
    'ComplicatedAssertionChecker',
    'ComplicatedAssignmentChecker',
    'ConfusingAssertionChecker',
    'DuplicateBlankLineChecker',
    'DuplicateBlankLineInBlockCommentChecker',
    'DuplicateBlankLineInCommentChecker',
    'DuplicateSpaceChecker',
    'GenericNameChecker',
    'InnerClassPositionChecker',
    'InternalErrorChecker',
    'LonelyCaseChecker',
    'LowercaseHexadecimalChecker',
    'MeaninglessWordChecker',
    'MemberOrderChecker',
    'MemberSeparatorChecker',
    'NamedImportOrderChecker',
    'NestedIfElseChecker',
    'ParameterWrapChecker',
    'ParenthesesClipChecker',
    'ParenthesesTrimChecker',
    'RedundantDefaultChecker',
    'TodoCommentChecker',
    'TrailingCommaChecker',
    'UnnecessaryAbstractChecker',
    'UnnecessaryBlankLineAfterColonChecker',
    'UnnecessaryContinueChecker',
    'UnnecessaryLeadingBlankLineChecker',
    'initialize',
]
