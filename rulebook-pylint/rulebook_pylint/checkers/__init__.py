from __future__ import annotations

from typing import TYPE_CHECKING

from pylint.utils import register_plugins

from .abbreviation_as_word import AbbreviationAsWordChecker
from .block_comment_clip import BlockCommentClipChecker
from .block_comment_trim import BlockCommentTrimChecker
from .case_separator import CaseSeparatorChecker
from .comment_spaces import CommentSpacesChecker
from .comment_trim import CommentTrimChecker
from .common_function_position import CommonFunctionPositionChecker
from .duplicate_blank_line import DuplicateBlankLineChecker
from .duplicate_blank_line_in_block_comment import DuplicateBlankLineInBlockCommentChecker
from .duplicate_blank_line_in_comment import DuplicateBlankLineInCommentChecker
from .duplicate_whitespace import DuplicateWhitespaceChecker
from .generic_name import GenericNameChecker
from .inner_class_position import InnerClassPositionChecker
from .internal_error import InternalErrorChecker
from .line_feed import LineFeedChecker
from .lonely_case import LonelyCaseChecker
from .lowercase_hexadecimal import LowercaseHexadecimalChecker
from .meaningless_word import MeaninglessWordChecker
from .member_order import MemberOrderChecker
from .member_separator import MemberSeparatorChecker
from .named_import_order import NamedImportOrderChecker
from .nested_if_else import NestedIfElseChecker
from .parameter_wrap import ParameterWrapChecker
from .parentheses_clip import ParenthesesClipChecker
from .parentheses_trim import ParenthesesTrimChecker
from .redundant_default import RedundantDefaultChecker
from .todo_comment import TodoCommentChecker
from .trailing_comma import TrailingCommaChecker
from .unnecessary_abstract import UnnecessaryAbstractChecker
from .unnecessary_blank_line_after_colon import UnnecessaryBlankLineAfterColonChecker
from .unnecessary_continue import UnnecessaryContinueChecker
from .unnecessary_initial_blank_line import UnnecessaryInitialBlankLineChecker

if TYPE_CHECKING:
    from pylint.lint import PyLinter


def initialize(linter: PyLinter) -> None:
    register_plugins(linter, __path__[0])


__all__ = [
    'AbbreviationAsWordChecker',
    'BlockCommentClipChecker',
    'BlockCommentTrimChecker',
    'CaseSeparatorChecker',
    'CommentSpacesChecker',
    'CommentTrimChecker',
    'CommonFunctionPositionChecker',
    'DuplicateBlankLineChecker',
    'DuplicateBlankLineInBlockCommentChecker',
    'DuplicateBlankLineInCommentChecker',
    'DuplicateWhitespaceChecker',
    'GenericNameChecker',
    'InnerClassPositionChecker',
    'InternalErrorChecker',
    'LineFeedChecker',
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
    'UnnecessaryInitialBlankLineChecker',
    'initialize',
]
