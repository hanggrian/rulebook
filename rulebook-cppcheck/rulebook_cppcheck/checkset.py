from sys import argv

from rulebook_cppcheck.checkers.block_comment_spaces import BlockCommentSpacesChecker
from rulebook_cppcheck.checkers.block_tag_indentation import BlockTagIndentationChecker
from rulebook_cppcheck.checkers.case_separator import CaseSeparatorChecker
from rulebook_cppcheck.checkers.class_name import ClassNameChecker
from rulebook_cppcheck.checkers.class_name_abbreviation import ClassNameAbbreviationChecker
from rulebook_cppcheck.checkers.comment_space import CommentSpaceChecker
from rulebook_cppcheck.checkers.duplicate_blank_line import DuplicateBlankLineChecker
from rulebook_cppcheck.checkers.duplicate_space import DuplicateSpaceChecker
from rulebook_cppcheck.checkers.file_size import FileSizeChecker
from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from rulebook_cppcheck.checkers.identifier_name import IdentifierNameChecker
from rulebook_cppcheck.checkers.illegal_class_name_suffix import IllegalClassNameSuffixChecker
from rulebook_cppcheck.checkers.illegal_variable_name import IllegalVariableNameChecker
from rulebook_cppcheck.checkers.import_order import ImportOrderChecker
from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.checkers.inner_class_position import InnerClassPositionChecker
from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.checkers.lowercase_f import LowercaseFChecker
from rulebook_cppcheck.checkers.member_order import MemberOrderChecker
from rulebook_cppcheck.checkers.member_separator import MemberSeparatorChecker
from rulebook_cppcheck.checkers.package_name import PackageNameChecker
from rulebook_cppcheck.checkers.required_generics_name import RequiredGenericsNameChecker
from rulebook_cppcheck.checkers.rulebook_checkers import BaseChecker
from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker
from rulebook_cppcheck.checkers.uppercase_l import UppercaseLChecker

try:
    from cppcheckdata import CppcheckData
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import CppcheckData

if __name__ == '__main__':
    checkers: list[BaseChecker] = [
        # Clipping
        # Declaring
        LowercaseFChecker(),
        UppercaseLChecker(),
        # Formatting
        FileSizeChecker(),
        FinalNewlineChecker(),
        IndentStyleChecker(),
        LineLengthChecker(),
        # Naming
        ClassNameAbbreviationChecker(),
        ClassNameChecker(),
        IdentifierNameChecker(),
        IllegalClassNameSuffixChecker(),
        IllegalVariableNameChecker(),
        PackageNameChecker(),
        RequiredGenericsNameChecker(),
        # Ordering
        ImportOrderChecker(),
        InnerClassPositionChecker(),
        MemberOrderChecker(),
        # Spacing
        BlockCommentSpacesChecker(),
        BlockTagIndentationChecker(),
        CaseSeparatorChecker(),
        CommentSpaceChecker(),
        MemberSeparatorChecker(),
        # Stating
        UnnecessarySwitchChecker(),
        # Trimming
        DuplicateBlankLineChecker(),
        DuplicateSpaceChecker(),  # Wrapping
    ]

    system_args: list[str] = argv
    for checker in checkers:
        mapped_args: dict[str, str] = {}
        must_continue: bool = False
        for arg in checker.ARGS:
            for system_arg in system_args.copy():
                if not system_arg.startswith(f'--{arg}='):
                    continue
                mapped_args[arg] = system_arg.split('=')[1]
                system_args.remove(system_arg)
                must_continue = True
                continue
            if must_continue:
                continue
        if mapped_args:
            checker.before_run(mapped_args)

    for dump_file in [arg for arg in argv[1:] if arg.endswith('.dump')]:
        for config in CppcheckData(dump_file).iterconfigurations():
            [checker.run_check(config) for checker in checkers]
