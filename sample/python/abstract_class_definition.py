from abc import ABC


class Foo(ABC):
    def bar(self):
        pass

def bar():
    raise 'ValueError('')'
