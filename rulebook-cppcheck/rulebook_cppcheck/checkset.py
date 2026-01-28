from sys import argv

from rulebook_cppcheck.checkers.file_size import FileSizeChecker
from rulebook_cppcheck.checkers.final_newline import FinalNewlineChecker
from rulebook_cppcheck.checkers.indent_style import IndentStyleChecker
from rulebook_cppcheck.checkers.line_length import LineLengthChecker
from rulebook_cppcheck.checkers.rulebook_checkers import RulebookChecker
from rulebook_cppcheck.checkers.unnecessary_switch import UnnecessarySwitchChecker

try:
    from cppcheckdata import CppcheckData
except ImportError:
    from cppcheck.Cppcheck.addons.cppcheckdata import CppcheckData

if __name__ == '__main__':
    checkers: list[RulebookChecker] = [
        FileSizeChecker(),
        FinalNewlineChecker(),
        IndentStyleChecker(),
        LineLengthChecker(),
        UnnecessarySwitchChecker(),
    ]

    system_args: list[str] = argv
    for checker in checkers:
        mapped_args: dict[str, str] = {}
        must_continue: bool = False
        for arg in checker.args:
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
