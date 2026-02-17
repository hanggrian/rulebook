from abc import ABC, abstractmethod


class Foo(ABC):
    @abstractmethod
    def bar(self):
        pass

def bar():
    raise ValueError('')
