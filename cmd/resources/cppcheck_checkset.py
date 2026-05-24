from pathlib import Path
from runpy import run_module
import sys

payload = Path(__file__).resolve().parent / 'rulebook_cppcheck.zip'
if str(payload) not in sys.path:
    sys.path.insert(0, str(payload))

run_module('rulebook_cppcheck.checkset', run_name='__main__')
