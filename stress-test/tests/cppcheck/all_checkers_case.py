from pathlib import Path
from subprocess import run
from tempfile import TemporaryDirectory
from unittest import TestCase
from unittest.mock import call

from rulebook_cppcheck.checkers import (
    AbbreviationAsWordChecker,
    AssignmentWrapChecker,
    BlockCommentSpacesChecker,
    BlockCommentTrimChecker,
    BlockTagIndentationChecker,
    BlockTagPunctuationChecker,
    BracesSpacesChecker,
    CaseSeparatorChecker,
    ChainCallWrapChecker,
    ClassNameChecker,
    CommentSpacesChecker,
    CommentTrimChecker,
    ComplicatedAssignmentChecker,
    DuplicateBlankLineChecker,
    DuplicateBlankLineInBlockCommentChecker,
    DuplicateBlankLineInCommentChecker,
    DuplicateSpaceChecker,
    FileNameChecker,
    FileSizeChecker,
    GenericNameChecker,
    IdentifierNameChecker,
    IllegalCatchChecker,
    IllegalThrowChecker,
    IllegalVariableNameChecker,
    ImportOrderChecker,
    IndentStyleChecker,
    InnerClassPositionChecker,
    LineLengthChecker,
    LonelyCaseChecker,
    LonelyIfChecker,
    LowercaseFChecker,
    LowercaseHexadecimalChecker,
    MeaninglessWordChecker,
    MemberOrderChecker,
    MemberSeparatorChecker,
    OperatorWrapChecker,
    PackageNameChecker,
    ParameterWrapChecker,
    ParenthesesClipChecker,
    ParenthesesTrimChecker,
    RedundantDefaultChecker,
    RedundantElseChecker,
    RedundantIfChecker,
    TodoCommentChecker,
    TrailingNewlineChecker,
    UnnecessaryReturnChecker,
    UnnecessaryTrailingWhitespaceChecker,
    UppercaseLChecker,
)

try:
    from cppcheckdata import parsedump
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import parsedump


class AllCheckersTestCase(TestCase):
    def setUp(self):
        self.checkers = (
            AbbreviationAsWordChecker(),
            AssignmentWrapChecker(),
            BlockCommentSpacesChecker(),
            BlockCommentTrimChecker(),
            BlockTagIndentationChecker(),
            BlockTagPunctuationChecker(),
            BracesSpacesChecker(),
            CaseSeparatorChecker(),
            ChainCallWrapChecker(),
            ClassNameChecker(),
            CommentSpacesChecker(),
            CommentTrimChecker(),
            ComplicatedAssignmentChecker(),
            DuplicateBlankLineChecker(),
            DuplicateBlankLineInBlockCommentChecker(),
            DuplicateBlankLineInCommentChecker(),
            DuplicateSpaceChecker(),
            FileNameChecker(),
            FileSizeChecker(),
            TrailingNewlineChecker(),
            GenericNameChecker(),
            IdentifierNameChecker(),
            IllegalCatchChecker(),
            IllegalThrowChecker(),
            IllegalVariableNameChecker(),
            ImportOrderChecker(),
            IndentStyleChecker(),
            InnerClassPositionChecker(),
            LineLengthChecker(),
            LonelyCaseChecker(),
            LonelyIfChecker(),
            LowercaseFChecker(),
            LowercaseHexadecimalChecker(),
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
            RedundantIfChecker(),
            TodoCommentChecker(),
            UnnecessaryReturnChecker(),
            UnnecessaryTrailingWhitespaceChecker(),
            UppercaseLChecker(),
        )
        self.temp_dir = None

    def dump_configs(self, code: str):
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
    def assignment_wrap_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == '=' and t.linenr == line),
            'Break assignment into newline.',
        )

    @staticmethod
    def block_comment_spaces_called(tokens, line, col, i):
        return call(
            next(t for t in tokens if t.str == '}' and t.linenr == line and t.column == col),
            "Add space before '*/'.",
            i,
        )

    @staticmethod
    def case_separator_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            'Add blank line after multiline branch.',
        )

    @staticmethod
    def complicated_assignment_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            "Use assignment operator '+='.",
        )

    @staticmethod
    def chain_call_wrap_called(tokens, line, col):
        return call(
            next(t for t in tokens if t.str == '.' and t.linenr == line and t.column == col),
            "Put newline before '.'.",
        )

    @staticmethod
    def comment_space_called(tokens, i, j):
        return call(
            next(t for t in tokens if t.str == ';' and t.linenr == 885),
            "Put one space after '//'.",
            i,
            j,
        )

    @staticmethod
    def duplicate_blank_line_called(tokens, s, line, col, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Remove consecutive blank line.',
            i,
        )

    @staticmethod
    def duplicate_whitespace_called(tokens, s, line, col, i, j):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Remove consecutive whitespace.',
            i,
            j,
        )

    @staticmethod
    def generic_name_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            'Use pascal-case name.',
        )

    @staticmethod
    def identifier_name_called(tokens, s, s2, line, col):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            f"Rename identifier to '{s2}'.",
        )

    @staticmethod
    def illegal_catch_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == 'catch' and t.linenr == line),
            'Catch a narrower type.',
        )

    @staticmethod
    def import_order_called(tokens, s, line, col, msg, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            msg,
            i,
        )

    @staticmethod
    def indent_style_called(tokens, s, line):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line),
            "Indent with '4' spaces.",
        )

    @staticmethod
    def line_length_called(tokens, line):
        return call(
            next(t for t in tokens if t.linenr == line),
            "Code exceeds max line length of '100'.",
        )

    @staticmethod
    def operator_wrap_called(tokens, s, line, col, is_put):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            f"Put newline after operator '{s}'." \
                if is_put \
                else f"Omit newline before operator '{s}'.",
        )

    @staticmethod
    def parameter_wrap_called(tokens, s, line, col):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            'Break each parameter into newline.',
        )

    @staticmethod
    def parentheses_clip_called(tokens, line, col):
        return call(
            next(t for t in tokens if t.str == '{' and t.linenr == line and t.column == col),
            "Convert into '{}'.",
        )

    @staticmethod
    def parentheses_trim_called(tokens, s, line, col, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            f"Remove blank line {'before' if s == '}' else 'after'} '{s}'.",
            i,
        )

    @staticmethod
    def redundant_default_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == ':' and t.linenr == line),
            "Omit redundant 'default' condition.",
        )

    @staticmethod
    def redundant_else_called(tokens, line):
        return call(
            next(t for t in tokens if t.str == 'else' and t.linenr == line),
            "Omit redundant 'else' condition.",
        )

    @staticmethod
    def todo_comment_called(tokens, s, line, col, msg, i):
        return call(
            next(t for t in tokens if t.str == s and t.linenr == line and t.column == col),
            msg,
            i,
        )
