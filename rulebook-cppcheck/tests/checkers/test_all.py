# pylint: disable=file-size

from os.path import dirname, join
from pathlib import Path
from subprocess import run
from tempfile import TemporaryDirectory
from unittest import TestCase, main
from unittest.mock import call, patch

from rulebook_cppcheck.checkers.abbreviation_as_word import AbbreviationAsWordChecker
from rulebook_cppcheck.checkers.assignment_wrap import AssignmentWrapChecker
from rulebook_cppcheck.checkers.block_comment_spaces import BlockCommentSpacesChecker
from rulebook_cppcheck.checkers.block_comment_trim import BlockCommentTrimChecker
from rulebook_cppcheck.checkers.block_tag_indentation import BlockTagIndentationChecker
from rulebook_cppcheck.checkers.block_tag_punctuation import BlockTagPunctuationChecker
from rulebook_cppcheck.checkers.case_separator import CaseSeparatorChecker
from rulebook_cppcheck.checkers.chain_call_wrap import ChainCallWrapChecker
from rulebook_cppcheck.checkers.class_name import ClassNameChecker
from rulebook_cppcheck.checkers.comment_space import CommentSpaceChecker
from rulebook_cppcheck.checkers.comment_trim import CommentTrimChecker
from rulebook_cppcheck.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_cppcheck.checkers.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_cppcheck.checkers.duplicate_blank_line_in_comment import \
    DuplicateBlankLineInCommentChecker
from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_cppcheck.checkers.empty_file import EmptyFileChecker
from rulebook_cppcheck.checkers.file_name import FileNameChecker
from rulebook_cppcheck.checkers.file_size import FileSizeChecker
from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from rulebook_cppcheck.checkers.generic_name import GenericNameChecker
from rulebook_cppcheck.checkers.illegal_catch import IllegalCatchChecker
from rulebook_cppcheck.checkers.illegal_throw import IllegalThrowChecker
from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from rulebook_cppcheck.checkers.import_order import ImportOrderChecker
from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.checkers.lowercase_f import LowercaseFChecker
from rulebook_cppcheck.checkers.meaningless_word import MeaninglessWordChecker
from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from rulebook_cppcheck.checkers.member_separator import MemberSeparatorChecker
from rulebook_cppcheck.checkers.operator_wrap import OperatorWrapChecker
from rulebook_cppcheck.checkers.package_name import PackageNameChecker
from rulebook_cppcheck.checkers.parameter_wrap import ParameterWrapChecker
from rulebook_cppcheck.checkers.parentheses_clip import ParenthesesClipChecker
from rulebook_cppcheck.checkers.parentheses_trim import ParenthesesTrimChecker
from rulebook_cppcheck.checkers.redundant_default import RedundantDefaultChecker
from rulebook_cppcheck.checkers.redundant_else import RedundantElseChecker
from rulebook_cppcheck.checkers.todo_comment import TodoCommentChecker
from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from rulebook_cppcheck.checkers.uppercase_l import UppercaseLChecker

try:
    from cppcheckdata import parsedump
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import parsedump


class TestAllCheckers(TestCase):
    def setUp(self):
        self.checkers = (
            AbbreviationAsWordChecker(),
            AssignmentWrapChecker(),
            BlockCommentSpacesChecker(),
            BlockCommentTrimChecker(),
            BlockTagIndentationChecker(),
            BlockTagPunctuationChecker(),
            CaseSeparatorChecker(),
            ChainCallWrapChecker(),
            ClassNameChecker(),
            CommentSpaceChecker(),
            CommentTrimChecker(),
            DuplicateBlankLineChecker(),
            DuplicateBlankLineInBlockCommentChecker(),
            DuplicateBlankLineInCommentChecker(),
            DuplicateSpaceChecker(),
            EmptyFileChecker(),
            FileNameChecker(),
            FileSizeChecker(),
            FinalNewlineChecker(),
            GenericNameChecker(),
            IllegalCatchChecker(),
            IllegalThrowChecker(),
            IllegalVariableNameChecker(),
            ImportOrderChecker(),
            IndentStyleChecker(),
            InnerClassPositionChecker(),
            LineLengthChecker(),
            LowercaseFChecker(),
            MeaninglessWordChecker(),
            MemberOrderChecker(),
            MemberSeparatorChecker(),
            OperatorWrapChecker(),
            PackageNameChecker(),
            ParameterWrapChecker(),
            ParenthesesClipChecker(),
            ParenthesesTrimChecker(),
            RedundantDefaultChecker(),
            RedundantElseChecker(),
            TodoCommentChecker(),
            UnnecessarySwitchChecker(),
            UppercaseLChecker(),
        )
        self.temp_dir = None

    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(BlockCommentSpacesChecker, 'report_error')
    @patch.object(ChainCallWrapChecker, 'report_error')
    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch.object(DuplicateSpaceChecker, 'report_error')
    @patch.object(GenericNameChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(IndentStyleChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    @patch.object(ParameterWrapChecker, 'report_error')
    @patch.object(ParenthesesClipChecker, 'report_error')
    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch.object(RedundantElseChecker, 'report_error')
    @patch.object(TodoCommentChecker, 'report_error')
    def test_boost_options_description(
        self,
        todo_comment_report_error,
        redundant_else_report_error,
        parentheses_trim_report_error,
        parentheses_clip_report_error,
        parameter_wrap_report_error,
        operator_wrap_report_error,
        line_length_report_error,
        indent_style_report_error,
        import_order_report_error,
        generic_name_report_error,
        duplicate_space_report_error,
        duplicate_blank_line_report_error,
        chain_call_wrap_report_error,
        block_comment_spaces_report_error,
        assignment_wrap_report_error,
    ):
        with open(
            join(dirname(__file__), '../resources/boost_options_description.cpp'),
            'r',
            encoding='UTF-8',
        ) as file:
            config = self._dump_configs(file.read())[0]
            [checker.run_check(config) for checker in self.checkers]
            tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self._assignment_wrap_called(tokens, 353)],
        )
        block_comment_spaces_report_error.assert_has_calls(
            [self._block_comment_spaces_called(tokens, 700, 2, 657)],
        )
        chain_call_wrap_report_error.assert_has_calls(
            [
                self._chain_call_wrap_called(tokens, 241, 39),
                self._chain_call_wrap_called(tokens, 241, 55),
                self._chain_call_wrap_called(tokens, 242, 48),
            ],
            any_order=True,
        )
        duplicate_blank_line_report_error.assert_has_calls(
            [
                self._duplicate_blank_line_called(tokens, '}', 700, 2, 16),
                self._duplicate_blank_line_called(tokens, '}', 700, 2, 47),
                self._duplicate_blank_line_called(tokens, '}', 700, 2, 60),
                self._duplicate_blank_line_called(tokens, '}', 700, 2, 89),
                self._duplicate_blank_line_called(tokens, '}', 700, 2, 174),
            ],
            any_order=True,
        )
        duplicate_space_report_error.assert_has_calls(
            [
                self._duplicate_space_called(tokens, 'const_iterator', 484, 25),
                self._duplicate_space_called(tokens, 'const_iterator', 589, 18),
            ],
            any_order=True,
        )
        generic_name_report_error.assert_has_calls(
            [self._generic_name_called(tokens, 'charT', 34)],
        )
        import_order_report_error.assert_has_calls(
            [
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'boost/program_options/parsers.hpp'.",
                    14,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'boost/lexical_cast.hpp' before " +
                    "'boost/program_options/parsers.hpp'.",
                    17,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'boost/lexical_cast.hpp'.",
                    17,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'boost/detail/workaround.hpp' before 'boost/tokenizer.hpp'.",
                    19,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Remove blank line before directive 'cassert'.",
                    22,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'cstdarg' before 'cstring'.",
                    25,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Arrange directive 'iterator' before 'sstream'.",
                    27,
                ),
            ],
            any_order=True,
        )
        indent_style_report_error.assert_has_calls(
            [
                self._indent_style_called(tokens, 'namespace', 32),
                self._indent_style_called(tokens, 'template', 34),
                self._indent_style_called(tokens, 'std', 35),
                self._indent_style_called(tokens, '{', 36),
                self._indent_style_called(tokens, 'std', 37),
                self._indent_style_called(tokens, 'for', 38),
                self._indent_style_called(tokens, '{', 39),
                self._indent_style_called(tokens, 'result', 40),
                self._indent_style_called(tokens, '}', 41),
                self._indent_style_called(tokens, 'return', 42),
                self._indent_style_called(tokens, '}', 43),
                self._indent_style_called(tokens, 'const', 54),
                self._indent_style_called(tokens, 'const', 63),
                self._indent_style_called(tokens, 'const', 64),
                self._indent_style_called(tokens, 'bool', 76),
                self._indent_style_called(tokens, 'bool', 77),
                self._indent_style_called(tokens, 'bool', 78),
                self._indent_style_called(tokens, 'append', 242),
                self._indent_style_called(tokens, 'const', 264),
                self._indent_style_called(tokens, 'const', 279),
                self._indent_style_called(tokens, 'const', 289),
                self._indent_style_called(tokens, 'const', 290),
                self._indent_style_called(tokens, 'int', 301),
                self._indent_style_called(tokens, 'int', 310),
                self._indent_style_called(tokens, 'int', 311),
                self._indent_style_called(tokens, 'bool', 349),
                self._indent_style_called(tokens, 'bool', 350),
                self._indent_style_called(tokens, 'bool', 351),
                self._indent_style_called(tokens, 'long_ignore_case', 354),
                self._indent_style_called(tokens, 'bool', 368),
                self._indent_style_called(tokens, 'bool', 369),
                self._indent_style_called(tokens, 'bool', 370),
                self._indent_style_called(tokens, 'std', 436),
                self._indent_style_called(tokens, 'int', 437),
                self._indent_style_called(tokens, 'int', 438),
                self._indent_style_called(tokens, '(', 499),
                self._indent_style_called(tokens, 'reverse_iterator', 520),
                self._indent_style_called(tokens, "' '", 521),
                self._indent_style_called(tokens, 'line_length', 595),
                self._indent_style_called(tokens, 'os', 625),
                self._indent_style_called(tokens, 'for', 626),
                self._indent_style_called(tokens, '{', 627),
                self._indent_style_called(tokens, 'os', 628),
                self._indent_style_called(tokens, '}', 629),
                self._indent_style_called(tokens, 'for', 631),
                self._indent_style_called(tokens, '{', 632),
                self._indent_style_called(tokens, 'os', 633),
                self._indent_style_called(tokens, '}', 634),
                self._indent_style_called(tokens, 'first_column_width', 638),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self._line_length_called(tokens, 83),
                self._line_length_called(tokens, 542),
                self._line_length_called(tokens, 631),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [self._operator_wrap_called(tokens, '==', 95, 25, False)],
        )
        parameter_wrap_report_error.assert_has_calls(
            [
                self._parameter_wrap_called(tokens, 'approx', 353, 58),
                self._parameter_wrap_called(tokens, 'short_ignore_case', 354, 58),
                self._parameter_wrap_called(tokens, '*', 594, 38),
                self._parameter_wrap_called(tokens, 'first_column_width', 594, 49),
                self._parameter_wrap_called(tokens, 'const', 612, 43),
                self._parameter_wrap_called(tokens, 'int', 613, 54),
                self._parameter_wrap_called(tokens, 'opt', 637, 40),
                self._parameter_wrap_called(tokens, 'line_length', 638, 56),
            ],
            any_order=True,
        )
        parentheses_clip_report_error.assert_has_calls(
            [
                self._parentheses_clip_called(tokens, 49, 5),
                self._parentheses_clip_called(tokens, 71, 5),
            ],
            any_order=True,
        )
        parentheses_trim_report_error.assert_has_calls(
            [
                self._parentheses_trim_called(tokens, '{', 30, 45, 31),
                self._parentheses_trim_called(tokens, '{', 32, 14, 33),
                self._parentheses_trim_called(tokens, '}', 45, 5, 44),
                self._parentheses_trim_called(tokens, '{', 87, 43, 88),
                self._parentheses_trim_called(tokens, '}', 113, 9, 112),
                self._parentheses_trim_called(tokens, '}', 700, 1, 699),
            ],
            any_order=True,
        )
        redundant_else_report_error.assert_has_calls(
            [
                self._redundant_else_called(tokens, 104),
                self._redundant_else_called(tokens, 145),
                self._redundant_else_called(tokens, 142),
                self._redundant_else_called(tokens, 170),
                self._redundant_else_called(tokens, 252),
            ],
            any_order=True,
        )
        todo_comment_report_error.assert_has_calls(
            [
                self._todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    12,
                ),
                self._todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    268,
                ),
                self._todo_comment_called(
                    tokens,
                    '}',
                    700,
                    2,
                    "Omit separator ':'.",
                    396,
                ),
            ],
            any_order=True,
        )

    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(DuplicateBlankLineChecker, 'report_error')
    @patch.object(DuplicateSpaceChecker, 'report_error')
    @patch.object(IllegalCatchChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(IndentStyleChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    @patch.object(ParameterWrapChecker, 'report_error')
    @patch.object(RedundantElseChecker, 'report_error')
    @patch.object(TodoCommentChecker, 'report_error')
    def test_cppcheck_suppression(
        self,
        todo_comment_report_error,
        redundant_else_report_error,
        parameter_wrap_report_error,
        operator_wrap_report_error,
        line_length_report_error,
        indent_style_report_error,
        import_order_report_error,
        illegal_catch_report_error,
        duplicate_space_report_error,
        duplicate_blank_line_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        with open(
            join(dirname(__file__), '../resources/cppcheck_suppression.cpp'),
            'r',
            encoding='UTF-8',
        ) as file:
            config = self._dump_configs(file.read())[0]
            [checker.run_check(config) for checker in self.checkers]
            tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [
                self._assignment_wrap_called(tokens, 275),
                self._assignment_wrap_called(tokens, 319),
                self._assignment_wrap_called(tokens, 663),
            ],
            any_order=True,
        )
        case_separator_report_error.assert_has_calls(
            [
                self._case_separator_called(tokens, "'*'", 66),
                self._case_separator_called(tokens, 'Result', 444),
                self._case_separator_called(tokens, 'Result', 446),
                self._case_separator_called(tokens, 'State', 839),
                self._case_separator_called(tokens, 'State', 845),
                self._case_separator_called(tokens, 'State', 852),
            ],
            any_order=True,
        )
        duplicate_blank_line_report_error.assert_has_calls(
            [
                self._duplicate_blank_line_called(tokens, '}', 900, 1, 109),
                self._duplicate_blank_line_called(tokens, '}', 900, 1, 733),
                self._duplicate_blank_line_called(tokens, '}', 900, 1, 808),
                self._duplicate_blank_line_called(tokens, '}', 900, 1, 892),
            ],
            any_order=True,
        )
        duplicate_space_report_error.assert_has_calls(
            [self._duplicate_space_called(tokens, 'State', 832, 0)],
        )
        illegal_catch_report_error.assert_has_calls(
            [self._illegal_catch_called(tokens, 240)],
        )
        import_order_report_error.assert_has_calls(
            [
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'errorlogger.h' before 'suppressions.h'.",
                    21,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'errorlogger.h'.",
                    21,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'token.h' before 'utils.h'.",
                    26,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'settings.h' before 'tokenlist.h'.",
                    29,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Arrange directive 'algorithm' before 'settings.h'.",
                    31,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'algorithm'.",
                    31,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    900,
                    1,
                    "Remove blank line before directive 'xml.h'.",
                    38,
                ),
            ],
            any_order=True,
        )
        indent_style_report_error.assert_has_calls(
            [
                self._indent_style_called(tokens, 'std', 276),
                self._indent_style_called(tokens, 'std', 320),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self._line_length_called(tokens, 42),
                self._line_length_called(tokens, 115),
                self._line_length_called(tokens, 119),
                self._line_length_called(tokens, 121),
                self._line_length_called(tokens, 123),
                self._line_length_called(tokens, 126),
                self._line_length_called(tokens, 140),
                self._line_length_called(tokens, 151),
                self._line_length_called(tokens, 160),
                self._line_length_called(tokens, 178),
                self._line_length_called(tokens, 196),
                self._line_length_called(tokens, 276),
                self._line_length_called(tokens, 320),
                self._line_length_called(tokens, 389),
                self._line_length_called(tokens, 394),
                self._line_length_called(tokens, 404),
                self._line_length_called(tokens, 415),
                self._line_length_called(tokens, 474),
                self._line_length_called(tokens, 489),
                self._line_length_called(tokens, 506),
                self._line_length_called(tokens, 543),
                self._line_length_called(tokens, 627),
                self._line_length_called(tokens, 631),
                self._line_length_called(tokens, 634),
                self._line_length_called(tokens, 712),
                self._line_length_called(tokens, 734),
                self._line_length_called(tokens, 828),
                self._line_length_called(tokens, 833),
                self._line_length_called(tokens, 882),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [self._operator_wrap_called(tokens, '||', 705, 37, True)],
        )
        parameter_wrap_report_error.assert_has_calls(
            [
                self._parameter_wrap_called(tokens, 'mSuppressions', 275, 65),
                self._parameter_wrap_called(tokens, 'mSuppressions', 319, 65),
            ],
            any_order=True,
        )
        redundant_else_report_error.assert_has_calls(
            [self._redundant_else_called(tokens, 403)],
        )
        todo_comment_report_error.assert_has_calls(
            [
                self._todo_comment_called(tokens, '}', 900, 1, "Omit separator ':'.", 120),
                self._todo_comment_called(tokens, '}', 900, 1, "Omit separator ':'.", 597),
            ],
            any_order=True,
        )

    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(OperatorWrapChecker, 'report_error')
    @patch.object(RedundantElseChecker, 'report_error')
    def test_libtransmission_handshake(
        self,
        redundant_else_report_error,
        operator_wrap_report_error,
        line_length_report_error,
        import_order_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        with open(
            join(dirname(__file__), '../resources/libtransmission_handshake.cc'),
            'r',
            encoding='UTF-8',
        ) as file:
            config = self._dump_configs(file.read())[0]
            [checker.run_check(config) for checker in self.checkers]
            tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self._assignment_wrap_called(tokens, 435)],
        )
        case_separator_report_error.assert_has_calls(
            [
                self._case_separator_called(tokens, 'State', 832),
                self._case_separator_called(tokens, 'State', 834),
                self._case_separator_called(tokens, 'State', 836),
                self._case_separator_called(tokens, 'State', 838),
                self._case_separator_called(tokens, 'State', 840),
                self._case_separator_called(tokens, 'State', 842),
                self._case_separator_called(tokens, 'State', 844),
                self._case_separator_called(tokens, 'State', 848),
                self._case_separator_called(tokens, 'State', 850),
                self._case_separator_called(tokens, 'State', 852),
            ],
            any_order=True,
        )
        import_order_report_error.assert_has_calls(
            [
                self._import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Arrange directive 'fmt/format.h' before 'utility'.",
                    15,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Remove blank line before directive 'fmt/format.h'.",
                    15,
                ),
                self._import_order_called(
                    tokens,
                    '}',
                    886,
                    1,
                    "Remove blank line before directive 'libtransmission/bitfield.h'.",
                    17,
                ),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self._line_length_called(tokens, 64),
                self._line_length_called(tokens, 87),
                self._line_length_called(tokens, 164),
                self._line_length_called(tokens, 188),
                self._line_length_called(tokens, 216),
                self._line_length_called(tokens, 240),
                self._line_length_called(tokens, 259),
                self._line_length_called(tokens, 294),
                self._line_length_called(tokens, 330),
                self._line_length_called(tokens, 340),
                self._line_length_called(tokens, 370),
                self._line_length_called(tokens, 393),
                self._line_length_called(tokens, 412),
                self._line_length_called(tokens, 456),
                self._line_length_called(tokens, 458),
                self._line_length_called(tokens, 516),
                self._line_length_called(tokens, 527),
                self._line_length_called(tokens, 582),
                self._line_length_called(tokens, 634),
                self._line_length_called(tokens, 653),
                self._line_length_called(tokens, 693),
                self._line_length_called(tokens, 765),
                self._line_length_called(tokens, 861),
            ],
            any_order=True,
        )
        operator_wrap_report_error.assert_has_calls(
            [
                self._operator_wrap_called(tokens, '+', 88, 28, True),
                self._operator_wrap_called(tokens, '+', 88, 46, True),
                self._operator_wrap_called(tokens, '+', 436, 23, True),
                self._operator_wrap_called(tokens, '+', 436, 49, True),
            ],
            any_order=True,
        )
        redundant_else_report_error.assert_has_calls(
            [
                self._redundant_else_called(tokens, 263),
            ],
            any_order=True,
        )

    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(CommentSpaceChecker, 'report_error')
    @patch.object(ImportOrderChecker, 'report_error')
    @patch.object(IndentStyleChecker, 'report_error')
    @patch.object(LineLengthChecker, 'report_error')
    @patch.object(ParameterWrapChecker, 'report_error')
    @patch.object(ParenthesesTrimChecker, 'report_error')
    @patch.object(RedundantDefaultChecker, 'report_error')
    def test_openrazer_razerkraken_driver(
        self,
        redundant_default_report_error,
        parentheses_trim_report_error,
        parameter_wrap_report_error,
        line_length_report_error,
        indent_style_report_error,
        import_order_report_error,
        comment_space_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        with open(
            join(dirname(__file__), '../resources/openrazer_razerkraken_driver.c'),
            'r',
            encoding='UTF-8',
        ) as file:
            config = self._dump_configs(file.read())[0]
            [checker.run_check(config) for checker in self.checkers]
            tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self._assignment_wrap_called(tokens, 62)],
        )
        case_separator_report_error.assert_has_calls(
            [
                self._case_separator_called(tokens, '3', 539),
                self._case_separator_called(tokens, '2', 542),
                self._case_separator_called(tokens, 'USB_DEVICE_ID_RAZER_KRAKEN_KITTY_V2', 708),
                self._case_separator_called(tokens, 'USB_DEVICE_ID_RAZER_KRAKEN_CLASSIC_ALT', 758),
            ],
            any_order=True,
        )
        comment_space_report_error.assert_has_calls(
            [
                self._comment_space_called(tokens, 168, 9),
                self._comment_space_called(tokens, 847, 5),
            ],
            any_order=True,
        )
        import_order_report_error.assert_has_calls(
            [
                self._import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/module.h' before 'linux/slab.h'.",
                    8,
                ),
                self._import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/init.h' before 'linux/module.h'.",
                    9,
                ),
                self._import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/hid.h' before 'linux/usb/input.h'.",
                    11,
                ),
                self._import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Remove blank line before directive 'razerkraken_driver.h'.",
                    14,
                ),
                self._import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'razercommon.h' before 'razerkraken_driver.h'.",
                    15,
                ),
            ],
            any_order=True,
        )
        indent_style_report_error.assert_has_calls(
            [
                self._indent_style_called(tokens, 'request', 63),
                self._indent_style_called(tokens, 'request_type', 64),
                self._indent_style_called(tokens, 'value', 65),
                self._indent_style_called(tokens, 'index', 66),
                self._indent_style_called(tokens, 'buf', 67),
                self._indent_style_called(tokens, 'size', 68),
                self._indent_style_called(tokens, 'USB_CTRL_SET_TIMEOUT', 69),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self._line_length_called(tokens, 47),
                self._line_length_called(tokens, 91),
                self._line_length_called(tokens, 122),
                self._line_length_called(tokens, 150),
                self._line_length_called(tokens, 198),
                self._line_length_called(tokens, 238),
                self._line_length_called(tokens, 258),
                self._line_length_called(tokens, 261),
                self._line_length_called(tokens, 283),
                self._line_length_called(tokens, 286),
                self._line_length_called(tokens, 308),
                self._line_length_called(tokens, 311),
                self._line_length_called(tokens, 312),
                self._line_length_called(tokens, 316),
                self._line_length_called(tokens, 357),
                self._line_length_called(tokens, 360),
                self._line_length_called(tokens, 361),
                self._line_length_called(tokens, 365),
                self._line_length_called(tokens, 396),
                self._line_length_called(tokens, 407),
                self._line_length_called(tokens, 418),
                self._line_length_called(tokens, 421),
                self._line_length_called(tokens, 431),
                self._line_length_called(tokens, 450),
                self._line_length_called(tokens, 451),
                self._line_length_called(tokens, 476),
                self._line_length_called(tokens, 477),
                self._line_length_called(tokens, 478),
                self._line_length_called(tokens, 520),
                self._line_length_called(tokens, 566),
                self._line_length_called(tokens, 586),
                self._line_length_called(tokens, 605),
                self._line_length_called(tokens, 625),
                self._line_length_called(tokens, 641),
                self._line_length_called(tokens, 651),
                self._line_length_called(tokens, 661),
                self._line_length_called(tokens, 678),
                self._line_length_called(tokens, 682),
                self._line_length_called(tokens, 686),
                self._line_length_called(tokens, 687),
                self._line_length_called(tokens, 688),
                self._line_length_called(tokens, 690),
                self._line_length_called(tokens, 749),
                self._line_length_called(tokens, 750),
                self._line_length_called(tokens, 751),
                self._line_length_called(tokens, 752),
                self._line_length_called(tokens, 753),
                self._line_length_called(tokens, 754),
                self._line_length_called(tokens, 760),
                self._line_length_called(tokens, 761),
                self._line_length_called(tokens, 768),
                self._line_length_called(tokens, 769),
                self._line_length_called(tokens, 770),
                self._line_length_called(tokens, 771),
                self._line_length_called(tokens, 772),
                self._line_length_called(tokens, 809),
                self._line_length_called(tokens, 810),
                self._line_length_called(tokens, 811),
                self._line_length_called(tokens, 812),
                self._line_length_called(tokens, 813),
                self._line_length_called(tokens, 814),
                self._line_length_called(tokens, 820),
                self._line_length_called(tokens, 821),
                self._line_length_called(tokens, 829),
                self._line_length_called(tokens, 830),
                self._line_length_called(tokens, 831),
                self._line_length_called(tokens, 832),
                self._line_length_called(tokens, 833),
            ],
            any_order=True,
        )
        parameter_wrap_report_error.assert_has_calls(
            [self._parameter_wrap_called(tokens, 'usb_sndctrlpipe', 62, 36)],
        )
        parentheses_trim_report_error.assert_has_calls(
            [
                self._parentheses_trim_called(tokens, '}', 475, 5, 474),
                self._parentheses_trim_called(tokens, '}', 507, 5, 506),
                self._parentheses_trim_called(tokens, '{', 573, 35, 574),
                self._parentheses_trim_called(tokens, '}', 595, 5, 594),
                self._parentheses_trim_called(tokens, '{', 611, 42, 612),
                self._parentheses_trim_called(tokens, '}', 852, 5, 851),
            ],
            any_order=True,
        )
        redundant_default_report_error.assert_has_calls(
            [self._redundant_default_called(tokens, 545)],
        )

    def _dump_configs(self, code: str):
        if not self.temp_dir:
            # pylint: disable=consider-using-with
            self.temp_dir = TemporaryDirectory()
        temp_dir_name = self.temp_dir.name
        source_file = Path(temp_dir_name) / 'test.cpp'
        source_file.write_text(code)
        run(
            ['cppcheck', '--dump', '--quiet', str(source_file)],
            cwd=temp_dir_name,
            check=True,
            capture_output=True,
        )
        return parsedump(str(source_file) + '.dump').configurations

    @staticmethod
    def _assignment_wrap_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == '=' and t.linenr == line),
            'Break assignment into newline.',
        )

    @staticmethod
    def _block_comment_spaces_called(tokens, line, col, i):
        return call(
            next(t for t in tokens if t.str == '}' and t.linenr == line and t.column == col),
            "Add space before '*/'.",
            i,
        )

    @staticmethod
    def _case_separator_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            'Add blank line after multiline branch.',
        )

    @staticmethod
    def _chain_call_wrap_called(tokens, line, col):
        return call(
            next(t for t in tokens if t.str == '.' and t.linenr == line and t.column == col),
            "Put newline before '.'.",
        )

    @staticmethod
    def _comment_space_called(tokens, i, j):
        return call(
            next(t for t in tokens if t.str == ';' and t.linenr == 885),
            "Put one space after '//'.",
            i,
            j,
        )

    @staticmethod
    def _duplicate_blank_line_called(tokens, s, line, col, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Remove consecutive blank line.',
            i,
        )

    @staticmethod
    def _duplicate_space_called(tokens, s, line, col):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Remove consecutive space.',
        )

    @staticmethod
    def _generic_name_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            'Use single uppercase letter.',
        )

    @staticmethod
    def _illegal_catch_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == 'catch' and t.linenr == line),
            'Catch a narrower type.',
        )

    @staticmethod
    def _import_order_called(tokens, s, line, col, msg, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            msg,
            i,
        )

    @staticmethod
    def _indent_style_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            "Indent with '4' spaces.",
        )

    @staticmethod
    def _line_length_called(tokens, line):
        return call(
            next(t for t in tokens if t.linenr == line),
            "Code exceeds max line length of '100'.",
        )

    @staticmethod
    def _operator_wrap_called(tokens, s, line, col, is_put):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            f"Put newline after operator '{s}'." \
                if is_put \
                else f"Omit newline before operator '{s}'.",
        )

    @staticmethod
    def _parameter_wrap_called(tokens, s, line, col):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Break each parameter into newline.',
        )

    @staticmethod
    def _parentheses_clip_called(tokens, line, col):
        return call(
            next(t for t in tokens if t.str == '{' and t.linenr == line and t.column == col),
            "Convert into '{}'.",
        )

    @staticmethod
    def _parentheses_trim_called(tokens, s, line, col, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            f"Remove blank line {'before' if s == '}' else 'after'} '{s}'.",
            i,
        )

    @staticmethod
    def _redundant_default_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == ':' and t.linenr == line),
            "Omit redundant 'default' condition.",
        )

    @staticmethod
    def _redundant_else_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == 'else' and t.linenr == line),
            "Omit redundant 'else' condition.",
        )

    @staticmethod
    def _todo_comment_called(tokens, s, line, col, msg, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            msg,
            i,
        )


if __name__ == '__main__':
    main()
