from glob import glob

from setuptools import setup, find_packages

setup(
    name='source-acronym-capitalization-checker',
    version='0.5',
    packages=find_packages(),
    install_requires=['pylint', 'astroid'],
    entry_points={
        'pylint.plugins': [
            'source_acronym_capitalization_checker = ' +
            'source_acronym_checker:SourceAcronymCapitalizationChecker',
        ],
    },
    data_files=glob('rulebook_pylint.json'),
)
