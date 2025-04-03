from contextlib import contextmanager
from os.path import join, dirname
from typing import Any, Iterator, Generator
from unittest import main

from astroid import parse, extract_node, NodeNG, ClassDef, Module, FunctionDef, Assign, Match, For, \
    While, Call
from pylint.testutils import UnittestLinter, MessageTest
from pylint.testutils.global_test_linter import linter
from pylint.utils import ASTWalker
from rulebook_pylint.abstract_class_definition import AbstractClassDefinitionChecker
from rulebook_pylint.block_comment_trim import BlockCommentTrimChecker
from rulebook_pylint.built_in_function_position import BuiltInFunctionPositionChecker
from rulebook_pylint.case_separator import CaseSeparatorChecker
from rulebook_pylint.class_name_acronym import ClassNameAcronymChecker
from rulebook_pylint.code_block_trim import CodeBlockTrimChecker
from rulebook_pylint.comment_trim import CommentTrimChecker
from rulebook_pylint.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_pylint.duplicate_blank_line_in_block_comment import \
    DuplicateBlankLineInBlockCommentChecker
from rulebook_pylint.duplicate_blank_line_in_comment import DuplicateBlankLineInCommentChecker
from rulebook_pylint.exception_inheritance import ExceptionInheritanceChecker
from rulebook_pylint.file_size import FileSizeChecker
from rulebook_pylint.illegal_class_final_name import IllegalClassFinalNameChecker
from rulebook_pylint.inner_class_position import InnerClassPositionChecker
from rulebook_pylint.member_order import MemberOrderChecker
from rulebook_pylint.member_separator import MemberSeparatorChecker
from rulebook_pylint.nested_if_else import NestedIfElseChecker
from rulebook_pylint.parameter_wrap import ParameterWrapChecker
from rulebook_pylint.redundant_default import RedundantDefaultChecker
from rulebook_pylint.required_generic_name import RequiredGenericNameChecker
from rulebook_pylint.string_quotes import StringQuotesChecker
from rulebook_pylint.todo_comment import TodoCommentChecker
from rulebook_pylint.trailing_comma_in_call import TrailingCommaInCallChecker
from rulebook_pylint.trailing_comma_in_declaration import TrailingCommaInDeclarationChecker
from rulebook_pylint.unnecessary_blank_line_before_package import \
    UnnecessaryBlankLineBeforePackageChecker
from rulebook_pylint.unnecessary_switch import UnnecessarySwitchChecker

from ..tests import msg


class TestAllCheckers:
    CHECKER_CLASSES: tuple = (
        AbstractClassDefinitionChecker,
        BlockCommentTrimChecker,
        BuiltInFunctionPositionChecker,
        CaseSeparatorChecker,
        ClassNameAcronymChecker,
        CodeBlockTrimChecker,
        CommentTrimChecker,
        DuplicateBlankLineChecker,
        DuplicateBlankLineInBlockCommentChecker,
        DuplicateBlankLineInCommentChecker,
        ExceptionInheritanceChecker,
        # FileSizeChecker, TODO throwing error, find out why
        IllegalClassFinalNameChecker,
        InnerClassPositionChecker,
        MemberOrderChecker,
        MemberSeparatorChecker,
        NestedIfElseChecker,
        ParameterWrapChecker,
        RedundantDefaultChecker,
        RequiredGenericNameChecker,
        StringQuotesChecker,
        TodoCommentChecker,
        TrailingCommaInCallChecker,
        TrailingCommaInDeclarationChecker,
        UnnecessaryBlankLineBeforePackageChecker,
        UnnecessarySwitchChecker,
    )
    CONFIG: dict[str, Any] = {}

    def test_pylint_pylint_messages(self):
        with open(
            join(dirname(__file__), '../resources/pylint_messages.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            node_all = parse(file.read())
        with self.assertAddsMessages(
            msg(NestedIfElseChecker.MSG_INVERT, (172, 4, 180, 75), node_all.body[33].body[-1]),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (266, 8, 276, 21),
                node_all.body[38].body[-2].body[-1],
            ),
        ):
            for checker in self.checkers:
                self._assert_all(checker, node_all)

    def test_django_schema(self):
        with open(
            join(dirname(__file__), '../resources/schema.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            node_all = parse(file.read())
        with self.assertNoMessages():
            for checker in self.checkers:
                self._assert_all(checker, node_all)

    def test_numpy_matlib(self):
        with open(
            join(dirname(__file__), '../resources/matlib.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            node_all = parse(file.read())
        with self.assertAddsMessages(
            msg(BlockCommentTrimChecker.MSG_LAST, (62, 4, 7), node_all.body[8].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (105, 4, 7), node_all.body[9].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (149, 4, 7), node_all.body[10].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (184, 4, 7), node_all.body[11].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (229, 4, 7), node_all.body[12].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (273, 4, 7), node_all.body[13].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (326, 4, 7), node_all.body[14].doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (367, 4, 7), node_all.body[15].doc_node),
        ):
            for checker in self.checkers:
                self._assert_all(checker, node_all)

    def test_matplotlib_pyplot(self):
        with open(
            join(dirname(__file__), '../resources/pyplot.py'),
            'r',
            encoding='UTF-8',
        ) as file:
            s = file.read()
            node_all = parse(s)
        switch_backend = node_all.body[64]
        _warn_if_gui_out_of_main_thread = node_all.body[65]
        _auto_draw_if_interactive = node_all.body[81]
        subplot = node_all.body[99]
        last_subplots = node_all.body[103]
        last_subplot_mosaic = node_all.body[107]
        subplot2grid = node_all.body[108]
        xticks = node_all.body[115]
        clim = node_all.body[122]
        matshow = node_all.body[127]
        with self.assertAddsMessages(
            msg(BlockCommentTrimChecker.MSG_LAST, (388, 4, 7), switch_backend.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1516, 4, 7), subplot.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1776, 4, 7), last_subplots.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (1949, 4, 7), last_subplot_mosaic.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (2551, 4, 7), clim.doc_node),
            msg(BlockCommentTrimChecker.MSG_LAST, (2668, 4, 7), matshow.doc_node),
            msg(DuplicateBlankLineInBlockCommentChecker.MSG, (2173, 4, 2229, 7), xticks.doc_node),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (545, 4, 548, 20),
                _warn_if_gui_out_of_main_thread.body[-1]
            ),
            msg(
                NestedIfElseChecker.MSG_INVERT,
                (1086, 4, 1094, 34),
                _auto_draw_if_interactive.body[-1],
            ),
            msg(ParameterWrapChecker.MSG_ARGUMENT, (1963, 28, 48), subplot2grid.args.args[1]),
            msg(ParameterWrapChecker.MSG_ARGUMENT, (1964, 22, 34), subplot2grid.args.args[3]),
            msg(TrailingCommaInDeclarationChecker.MSG_MULTI, (166, 4, 23)),
            msg(TrailingCommaInDeclarationChecker.MSG_MULTI, (177, 4, 33)),
            msg(TrailingCommaInDeclarationChecker.MSG_MULTI, (348, 4, 22)),
        ):
            for checker in self.checkers:
                self._assert_all(checker, node_all)

    @staticmethod
    def _assert_all(checker, node):
        if hasattr(checker, 'process_module') and isinstance(node, Module):
            checker.process_module(node)
            if hasattr(checker, 'visit_module'):
                checker.visit_module(node)
        if not node.body:
            return
        for n in node.body:
            if hasattr(checker, 'visit_classdef') and isinstance(n, ClassDef):
                checker.visit_classdef(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_functiondef') and isinstance(n, FunctionDef):
                checker.visit_functiondef(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_assign') and isinstance(n, Assign):
                checker.visit_assign(n)
            if hasattr(checker, 'visit_match') and isinstance(n, Match):
                checker.visit_match(n)
                for case in n.cases:
                    TestAllCheckers._assert_all(checker, case)
            if hasattr(checker, 'visit_for') and isinstance(n, For):
                if hasattr(checker, 'visit_call') and isinstance(n.iter, Call):
                    checker.visit_call(n)
                checker.visit_for(n)
                TestAllCheckers._assert_all(checker, n)
            if hasattr(checker, 'visit_while') and isinstance(n, While):
                checker.visit_while(n)
                TestAllCheckers._assert_all(checker, n)

    def setup_method(self) -> None:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.setup_method`"""
        self.linter = UnittestLinter()
        self.checkers = [c(self.linter) for c in self.CHECKER_CLASSES]
        for checker in self.checkers:
            for key, value in self.CONFIG.items():
                setattr(checker.linter.config, key, value)
            checker.open()

    @contextmanager
    def assertNoMessages(self) -> Iterator[None]:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertNoMessages`"""
        with self.assertAddsMessages():
            yield

    @contextmanager
    def assertAddsMessages(
        self,
        *messages: MessageTest,
        ignore_position: bool = False,
    ) -> Generator[None]:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.assertAddsMessages`"""
        yield
        got = self.linter.release_messages()
        no_msg = "No message."
        expected = "\n".join(repr(m) for m in messages) or no_msg
        got_str = "\n".join(repr(m) for m in got) or no_msg
        msg = (
            "Expected messages did not match actual.\n"
            f"\nExpected:\n{expected}\n\nGot:\n{got_str}\n"
        )

        assert len(messages) == len(got), msg

        for expected_msg, gotten_msg in zip(messages, got):
            assert expected_msg.msg_id == gotten_msg.msg_id, msg
            assert expected_msg.node == gotten_msg.node, msg
            assert expected_msg.args == gotten_msg.args, msg
            assert expected_msg.confidence == gotten_msg.confidence, msg

            if ignore_position:
                # Do not check for line, col_offset etc...
                continue

            assert expected_msg.line == gotten_msg.line, msg
            assert expected_msg.col_offset == gotten_msg.col_offset, msg
            assert expected_msg.end_line == gotten_msg.end_line, msg
            assert expected_msg.end_col_offset == gotten_msg.end_col_offset, msg

    def walk(self, node: NodeNG) -> None:
        """:func:`~pylint.testutils.checker_test_case.CheckerTestCase.walk`"""
        walker = ASTWalker(linter)
        for checker in self.checkers:
            walker.add_checker(checker)
        walker.walk(node)


if __name__ == '__main__':
    main()
