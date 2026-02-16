from os.path import join, dirname
from pathlib import Path
from subprocess import run
from tempfile import TemporaryDirectory
from unittest import TestCase, main
from unittest.mock import patch, call

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
            join(dirname(__file__), '../resources/razerkraken_driver.c'),
            'r',
            encoding='UTF-8',
        ) as file:
            for config in self._dump_configs(file.read()):
                [checker.run_check(config) for checker in self.checkers]
                tokens = config.tokenlist
                assignment_wrap_report_error.assert_called_once_with(
                    next(t for t in tokens if t.str == '=' and t.linenr == 62),
                    'Break assignment into newline.',
                )

                def case_separator_called(ts, s, line):
                    return call(
                        next(t for t in ts if t.str == s and t.linenr == line),
                        'Add blank line after multiline branch.',
                    )

                case_separator_report_error.assert_has_calls(
                    [
                        case_separator_called(tokens, '3', 539),
                        case_separator_called(tokens, '2', 542),
                        case_separator_called(tokens, 'USB_DEVICE_ID_RAZER_KRAKEN_KITTY_V2', 708),
                        case_separator_called(
                            tokens,
                            'USB_DEVICE_ID_RAZER_KRAKEN_CLASSIC_ALT',
                            758,
                        ),
                    ],
                    any_order=True,
                )

                def comment_space_called(ts, i, j):
                    return call(
                        next(t for t in ts if t.str == ';' and t.linenr == 885),
                        "Put one space after '//'.",
                        i,
                        j,
                    )

                comment_space_report_error.assert_has_calls(
                    [
                        comment_space_called(tokens, 168, 9),
                        comment_space_called(tokens, 847, 5),
                    ],
                    any_order=True,
                )

                def import_order_called(ts, msg, i):
                    return call(next(t for t in ts if t.str == ';' and t.linenr == 885), msg, i)

                import_order_report_error.assert_has_calls(
                    [
                        import_order_called(
                            tokens,
                            "Arrange directive 'linux/module.h' before 'linux/slab.h'.",
                            8,
                        ),
                        import_order_called(
                            tokens,
                            "Arrange directive 'linux/init.h' before 'linux/module.h'.",
                            9,
                        ),
                        import_order_called(
                            tokens,
                            "Arrange directive 'linux/hid.h' before 'linux/usb/input.h'.",
                            11,
                        ),
                        import_order_called(
                            tokens,
                            "Remove blank line before directive 'razerkraken_driver.h'.",
                            14,
                        ),
                        import_order_called(
                            tokens,
                            "Arrange directive 'razercommon.h' before 'razerkraken_driver.h'.",
                            15,
                        ),
                    ],
                    any_order=True,
                )

                def indent_style_called(ts, s, line):
                    return call(
                        next(t for t in ts if t.str == s and t.linenr == line),
                        "Indent with '4' spaces.",
                    )

                indent_style_report_error.assert_has_calls(
                    [
                        indent_style_called(tokens, 'request', 63),
                        indent_style_called(tokens, 'request_type', 64),
                        indent_style_called(tokens, 'value', 65),
                        indent_style_called(tokens, 'index', 66),
                        indent_style_called(tokens, 'buf', 67),
                        indent_style_called(tokens, 'size', 68),
                        indent_style_called(tokens, 'USB_CTRL_SET_TIMEOUT', 69),
                    ],
                    any_order=True,
                )

                def line_length_called(ts, line):
                    return call(
                        next(t for t in ts if t.linenr == line),
                        "Code exceeds max line length of '100'.",
                    )

                line_length_report_error.assert_has_calls(
                    [
                        line_length_called(tokens, 47),
                        line_length_called(tokens, 91),
                        line_length_called(tokens, 122),
                        line_length_called(tokens, 150),
                        line_length_called(tokens, 198),
                        line_length_called(tokens, 238),
                        line_length_called(tokens, 258),
                        line_length_called(tokens, 261),
                        line_length_called(tokens, 283),
                        line_length_called(tokens, 286),
                        line_length_called(tokens, 308),
                        line_length_called(tokens, 311),
                        line_length_called(tokens, 312),
                        line_length_called(tokens, 316),
                        line_length_called(tokens, 357),
                        line_length_called(tokens, 360),
                        line_length_called(tokens, 361),
                        line_length_called(tokens, 365),
                        line_length_called(tokens, 396),
                        line_length_called(tokens, 407),
                        line_length_called(tokens, 418),
                        line_length_called(tokens, 421),
                        line_length_called(tokens, 431),
                        line_length_called(tokens, 450),
                        line_length_called(tokens, 451),
                        line_length_called(tokens, 476),
                        line_length_called(tokens, 477),
                        line_length_called(tokens, 478),
                        line_length_called(tokens, 520),
                        line_length_called(tokens, 566),
                        line_length_called(tokens, 586),
                        line_length_called(tokens, 605),
                        line_length_called(tokens, 625),
                        line_length_called(tokens, 641),
                        line_length_called(tokens, 651),
                        line_length_called(tokens, 661),
                        line_length_called(tokens, 678),
                        line_length_called(tokens, 682),
                        line_length_called(tokens, 686),
                        line_length_called(tokens, 687),
                        line_length_called(tokens, 688),
                        line_length_called(tokens, 690),
                        line_length_called(tokens, 749),
                        line_length_called(tokens, 750),
                        line_length_called(tokens, 751),
                        line_length_called(tokens, 752),
                        line_length_called(tokens, 753),
                        line_length_called(tokens, 754),
                        line_length_called(tokens, 760),
                        line_length_called(tokens, 761),
                        line_length_called(tokens, 768),
                        line_length_called(tokens, 769),
                        line_length_called(tokens, 770),
                        line_length_called(tokens, 771),
                        line_length_called(tokens, 772),
                        line_length_called(tokens, 809),
                        line_length_called(tokens, 810),
                        line_length_called(tokens, 811),
                        line_length_called(tokens, 812),
                        line_length_called(tokens, 813),
                        line_length_called(tokens, 814),
                        line_length_called(tokens, 820),
                        line_length_called(tokens, 821),
                        line_length_called(tokens, 829),
                        line_length_called(tokens, 830),
                        line_length_called(tokens, 831),
                        line_length_called(tokens, 832),
                        line_length_called(tokens, 833),
                    ],
                    any_order=True,
                )

                parameter_wrap_report_error.assert_called_once_with(
                    next(t for t in tokens if t.str == 'usb_sndctrlpipe' and t.linenr == 62),
                    'Break each parameter into newline.',
                )

                def parentheses_trim_called(ts, s, line, col, i):
                    return call(
                        next(t for t in ts if t.str == s and t.linenr == line and t.column == col),
                        f"Remove blank line {'before' if s == '}' else 'after'} '{s}'.",
                        i,
                    )

                parentheses_trim_report_error.assert_has_calls(
                    [
                        parentheses_trim_called(tokens, '}', 475, 5, 474),
                        parentheses_trim_called(tokens, '}', 507, 5, 506),
                        parentheses_trim_called(tokens, '{', 573, 35, 574),
                        parentheses_trim_called(tokens, '}', 595, 5, 594),
                        parentheses_trim_called(tokens, '{', 611, 42, 612),
                        parentheses_trim_called(tokens, '}', 852, 5, 851),
                    ],
                    any_order=True,
                )

                redundant_default_report_error.assert_called_once_with(
                    next(t for t in tokens if t.str == ':' and t.linenr == 545),
                    "Omit redundant 'default' condition.",
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


if __name__ == '__main__':
    main()
