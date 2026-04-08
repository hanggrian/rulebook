from contextlib import contextmanager
from importlib.resources import files

from astroid.nodes import Assign, Call, ClassDef, For, FunctionDef, Match, Module, While
from pylint.testutils import UnittestLinter
from pylint.testutils.global_test_linter import linter
from pylint.utils import ASTWalker

from rulebook_pylint.checkers import \
    AbbreviationAsWordChecker, \
    BlockCommentClipChecker, \
    BlockCommentTrimChecker, \
    CaseSeparatorChecker, \
    CommentSpacesChecker, \
    CommentTrimChecker, \
    CommonFunctionPositionChecker, \
    ComplicatedAssignmentChecker, \
    ConfusingAssertionChecker, \
    DuplicateBlankLineChecker, \
    DuplicateBlankLineInBlockCommentChecker, \
    DuplicateBlankLineInCommentChecker, \
    DuplicateSpaceChecker, \
    GenericNameChecker, \
    InnerClassPositionChecker, \
    InternalErrorChecker, \
    LineFeedChecker, \
    LonelyCaseChecker, \
    LowercaseHexadecimalChecker, \
    MeaninglessWordChecker, \
    MemberOrderChecker, \
    MemberSeparatorChecker, \
    NamedImportOrderChecker, \
    NestedIfElseChecker, \
    ParameterWrapChecker, \
    ParenthesesClipChecker, \
    ParenthesesTrimChecker, \
    RedundantDefaultChecker, \
    TodoCommentChecker, \
    TrailingCommaChecker, \
    UnnecessaryAbstractChecker, \
    UnnecessaryBlankLineAfterColonChecker, \
    UnnecessaryContinueChecker, \
    UnnecessaryInitialBlankLineChecker


# noinspection PyPep8Naming
class AllCheckersTestCase:
    CHECKER_CLASSES = (
        AbbreviationAsWordChecker,
        BlockCommentClipChecker,
        BlockCommentTrimChecker,
        CaseSeparatorChecker,
        CommentSpacesChecker,
        CommentTrimChecker,
        CommonFunctionPositionChecker,
        ComplicatedAssignmentChecker,
        ConfusingAssertionChecker,
        DuplicateBlankLineChecker,
        DuplicateBlankLineInBlockCommentChecker,
        DuplicateBlankLineInCommentChecker,
        DuplicateSpaceChecker,
        GenericNameChecker,
        InnerClassPositionChecker,
        InternalErrorChecker,
        LineFeedChecker,
        LonelyCaseChecker,
        LowercaseHexadecimalChecker,
        MeaninglessWordChecker,
        MemberOrderChecker,
        MemberSeparatorChecker,
        NamedImportOrderChecker,
        NestedIfElseChecker,
        ParameterWrapChecker,
        ParenthesesClipChecker,
        ParenthesesTrimChecker,
        RedundantDefaultChecker,
        TodoCommentChecker,
        TrailingCommaChecker,
        UnnecessaryAbstractChecker,
        UnnecessaryBlankLineAfterColonChecker,
        UnnecessaryContinueChecker,
        UnnecessaryInitialBlankLineChecker,
    )
    CONFIG = {}

    @staticmethod
    def get_code(directory: str, filename: str) -> str:
        with files(f'tests.pylint.resources.{directory}') \
            .joinpath(filename) \
            .open('r', encoding='UTF-8') as file:
            return file.read()

    # noinspection PyAttributeOutsideInit
    def setup_method(self):
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.setup_method`"""
        self.linter = UnittestLinter()
        self.checkers = [c(self.linter) for c in self.CHECKER_CLASSES]
        for checker in self.checkers:
            for key, value in self.CONFIG.items():
                setattr(checker.linter.before_run, key, value)
            checker.open()

    def walk(self, node):
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.walk`"""
        walker = ASTWalker(linter)
        for checker in self.checkers:
            walker.add_checker(checker)
        walker.walk(node)

    @contextmanager
    def assertNoMessages(self):
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertNoMessages`"""
        with self.assertAddsMessages():
            yield

    @contextmanager
    def assertAddsMessages(self, *messages, ignore_position=False):
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertAddsMessages`"""
        yield
        got = self.linter.release_messages()
        no_msg = 'No message.'
        expected = '\n'.join(repr(m) for m in messages) or no_msg
        got_str = '\n'.join(repr(m) for m in got) or no_msg
        message = \
            'Expected messages did not match actual.\n\n' + \
            f'Expected:\n{expected}\n\nGot:\n{got_str}\n'

        assert len(messages) == len(got), message

        for expected_msg, gotten_msg in zip(messages, got):
            assert expected_msg.msg_id == gotten_msg.msg_id, message
            assert expected_msg.node == gotten_msg.node, message
            assert expected_msg.args == gotten_msg.args, message
            assert expected_msg.confidence == gotten_msg.confidence, message

            if ignore_position:
                # Do not check for line, col_offset etc...
                continue

            assert expected_msg.line == gotten_msg.line, message
            assert expected_msg.col_offset == gotten_msg.col_offset, message
            assert expected_msg.end_line == gotten_msg.end_line, message
            assert expected_msg.end_col_offset == gotten_msg.end_col_offset, message

    @staticmethod
    def assert_tokens(checker, tokens):
        if hasattr(checker, 'process_tokens'):
            checker.process_tokens(tokens)

    def assert_all(self, checker, node):
        if hasattr(checker, 'process_module') and isinstance(node, Module):
            checker.process_module(node)
            if hasattr(checker, 'visit_module'):
                checker.visit_module(node)
        if not node.body:
            return
        for n in node.body:
            if hasattr(checker, 'visit_classdef') and isinstance(n, ClassDef):
                checker.visit_classdef(n)
                self.assert_all(checker, n)
            if hasattr(checker, 'visit_functiondef') and isinstance(n, FunctionDef):
                checker.visit_functiondef(n)
                self.assert_all(checker, n)
            if hasattr(checker, 'visit_assign') and isinstance(n, Assign):
                checker.visit_assign(n)
            if hasattr(checker, 'visit_match') and isinstance(n, Match):
                checker.visit_match(n)
                for case in n.cases:
                    self.assert_all(checker, case)
            if hasattr(checker, 'visit_for') and isinstance(n, For):
                if hasattr(checker, 'visit_call') and isinstance(n.iter, Call):
                    checker.visit_call(n)
                checker.visit_for(n)
                self.assert_all(checker, n)
            if not hasattr(checker, 'visit_while') or not isinstance(n, While):
                continue
            checker.visit_while(n)
            self.assert_all(checker, n)
