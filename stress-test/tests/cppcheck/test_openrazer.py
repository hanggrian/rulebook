from unittest import main
from unittest.mock import patch

from rulebook_cppcheck.checkers import \
    AssignmentWrapChecker, \
    CaseSeparatorChecker, \
    CommentSpacesChecker, \
    DuplicateSpaceChecker, \
    ImportOrderChecker, \
    IndentStyleChecker, \
    LineLengthChecker, \
    ParameterWrapChecker, \
    ParenthesesTrimChecker, \
    RedundantDefaultChecker
from .all_checkers_case import AllCheckersTestCase
from ..code import get_code


class TestOpenRazer(AllCheckersTestCase):
    @patch.object(AssignmentWrapChecker, 'report_error')
    @patch.object(CaseSeparatorChecker, 'report_error')
    @patch.object(CommentSpacesChecker, 'report_error')
    @patch.object(DuplicateSpaceChecker, 'report_error')
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
        duplicate_whitespace_report_error,
        comment_space_report_error,
        case_separator_report_error,
        assignment_wrap_report_error,
    ):
        config = self.dump_configs(get_code('cppcheck', 'openrazer', 'razerkraken_driver.c'))[0]
        [checker.run_check(config) for checker in self.checkers]
        tokens = config.tokenlist
        assignment_wrap_report_error.assert_has_calls(
            [self.assignment_wrap_called(tokens, 62)],
        )
        case_separator_report_error.assert_has_calls(
            [
                self.case_separator_called(tokens, '3', 539),
                self.case_separator_called(tokens, '2', 542),
                self.case_separator_called(tokens, 'USB_DEVICE_ID_RAZER_KRAKEN_KITTY_V2', 708),
                self.case_separator_called(tokens, 'USB_DEVICE_ID_RAZER_KRAKEN_CLASSIC_ALT', 758),
            ],
            any_order=True,
        )
        comment_space_report_error.assert_has_calls(
            [
                self.comment_space_called(tokens, 168, 9),
                self.comment_space_called(tokens, 847, 5),
            ],
            any_order=True,
        )
        duplicate_whitespace_report_error.assert_has_calls(
            [
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 63, 35),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 64, 55),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 65, 33),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 66, 33),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 67, 31),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 68, 32),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 69, 52),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 450, 54),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 476, 54),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 678, 25),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 679, 28),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 680, 32),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 681, 34),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 682, 32),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 685, 42),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 686, 39),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 687, 43),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 688, 41),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 689, 41),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 690, 41),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 749, 59),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 750, 56),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 751, 63),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 752, 65),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 753, 68),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 754, 63),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 759, 74),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 760, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 761, 77),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 767, 74),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 768, 78),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 769, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 770, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 771, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 772, 77),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 809, 59),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 810, 56),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 811, 63),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 812, 65),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 813, 68),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 814, 63),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 819, 74),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 820, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 821, 77),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 828, 74),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 830, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 831, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 832, 76),
                self.duplicate_whitespace_called(tokens, ';', 885, 39, 833, 77),
            ],
            any_order=True,
        )
        import_order_report_error.assert_has_calls(
            [
                self.import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/module.h' before 'linux/slab.h'.",
                    8,
                ),
                self.import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/init.h' before 'linux/module.h'.",
                    9,
                ),
                self.import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Arrange directive 'linux/hid.h' before 'linux/usb/input.h'.",
                    11,
                ),
                self.import_order_called(
                    tokens,
                    ';',
                    885,
                    39,
                    "Remove blank line before directive 'razerkraken_driver.h'.",
                    14,
                ),
                self.import_order_called(
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
                self.indent_style_called(tokens, 'request', 63),
                self.indent_style_called(tokens, 'request_type', 64),
                self.indent_style_called(tokens, 'value', 65),
                self.indent_style_called(tokens, 'index', 66),
                self.indent_style_called(tokens, 'buf', 67),
                self.indent_style_called(tokens, 'size', 68),
                self.indent_style_called(tokens, 'USB_CTRL_SET_TIMEOUT', 69),
            ],
            any_order=True,
        )
        line_length_report_error.assert_has_calls(
            [
                self.line_length_called(tokens, 47),
                self.line_length_called(tokens, 91),
                self.line_length_called(tokens, 122),
                self.line_length_called(tokens, 150),
                self.line_length_called(tokens, 198),
                self.line_length_called(tokens, 238),
                self.line_length_called(tokens, 258),
                self.line_length_called(tokens, 261),
                self.line_length_called(tokens, 283),
                self.line_length_called(tokens, 286),
                self.line_length_called(tokens, 308),
                self.line_length_called(tokens, 311),
                self.line_length_called(tokens, 312),
                self.line_length_called(tokens, 316),
                self.line_length_called(tokens, 357),
                self.line_length_called(tokens, 360),
                self.line_length_called(tokens, 361),
                self.line_length_called(tokens, 365),
                self.line_length_called(tokens, 396),
                self.line_length_called(tokens, 407),
                self.line_length_called(tokens, 418),
                self.line_length_called(tokens, 421),
                self.line_length_called(tokens, 431),
                self.line_length_called(tokens, 450),
                self.line_length_called(tokens, 451),
                self.line_length_called(tokens, 476),
                self.line_length_called(tokens, 477),
                self.line_length_called(tokens, 478),
                self.line_length_called(tokens, 520),
                self.line_length_called(tokens, 566),
                self.line_length_called(tokens, 586),
                self.line_length_called(tokens, 605),
                self.line_length_called(tokens, 625),
                self.line_length_called(tokens, 641),
                self.line_length_called(tokens, 651),
                self.line_length_called(tokens, 661),
                self.line_length_called(tokens, 678),
                self.line_length_called(tokens, 682),
                self.line_length_called(tokens, 686),
                self.line_length_called(tokens, 687),
                self.line_length_called(tokens, 688),
                self.line_length_called(tokens, 690),
                self.line_length_called(tokens, 749),
                self.line_length_called(tokens, 750),
                self.line_length_called(tokens, 751),
                self.line_length_called(tokens, 752),
                self.line_length_called(tokens, 753),
                self.line_length_called(tokens, 754),
                self.line_length_called(tokens, 760),
                self.line_length_called(tokens, 761),
                self.line_length_called(tokens, 768),
                self.line_length_called(tokens, 769),
                self.line_length_called(tokens, 770),
                self.line_length_called(tokens, 771),
                self.line_length_called(tokens, 772),
                self.line_length_called(tokens, 809),
                self.line_length_called(tokens, 810),
                self.line_length_called(tokens, 811),
                self.line_length_called(tokens, 812),
                self.line_length_called(tokens, 813),
                self.line_length_called(tokens, 814),
                self.line_length_called(tokens, 820),
                self.line_length_called(tokens, 821),
                self.line_length_called(tokens, 829),
                self.line_length_called(tokens, 830),
                self.line_length_called(tokens, 831),
                self.line_length_called(tokens, 832),
                self.line_length_called(tokens, 833),
            ],
            any_order=True,
        )
        parameter_wrap_report_error.assert_has_calls(
            [self.parameter_wrap_called(tokens, 'usb_sndctrlpipe', 62, 36)],
        )
        parentheses_trim_report_error.assert_has_calls(
            [
                self.parentheses_trim_called(tokens, '}', 475, 5, 474),
                self.parentheses_trim_called(tokens, '}', 507, 5, 506),
                self.parentheses_trim_called(tokens, '{', 573, 35, 574),
                self.parentheses_trim_called(tokens, '}', 595, 5, 594),
                self.parentheses_trim_called(tokens, '{', 611, 42, 612),
                self.parentheses_trim_called(tokens, '}', 852, 5, 851),
            ],
            any_order=True,
        )
        redundant_default_report_error.assert_has_calls(
            [self.redundant_default_called(tokens, 545)],
        )


if __name__ == '__main__':
    main()
