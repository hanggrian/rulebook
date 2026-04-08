from importlib.resources import files


def get_code(library, directory, filename):
    with files(f'tests.{library}.resources.{directory}') \
        .joinpath(filename) \
        .open('r', encoding='UTF-8') as file:
        return file.read()
