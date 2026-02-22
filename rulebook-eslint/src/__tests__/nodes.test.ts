import { SourceLocation, Statement } from 'estree';
import { SinonSandbox, assert, createSandbox } from 'sinon';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { hasJumpStatement, isMultiline } from '../nodes';

describe('NodesTest', () => {
    let sandbox: SinonSandbox;

    beforeEach(() => {
        sandbox = createSandbox();
    });

    afterEach(() => sandbox.restore());

    it(
        'hasJumpStatement',
        () => {
            expect(hasJumpStatement({ type: 'ReturnStatement' }))
                .toBeTruthy();
            expect(hasJumpStatement({ type: 'ThrowStatement' } as Statement))
                .toBeTruthy();
            expect(hasJumpStatement({ type: 'BreakStatement' }))
                .toBeTruthy();
            expect(hasJumpStatement({ type: 'ContinueStatement' }))
                .toBeTruthy();

            const body = [] as Statement[];
            const someStub = sandbox.stub(body, 'some').returns(true);
            expect(hasJumpStatement({ type: 'BlockStatement', body } as Statement)).toBeTruthy();

            assert.calledOnce(someStub);
        },
    );

    it(
        'isMultiline',
        () => {
            expect(isMultiline({} as Statement)).toBeFalsy();

            const singleLine = {
                loc: {
                    start: { line: 1, column: 0 },
                    end: { line: 1, column: 10 },
                } as SourceLocation,
            };
            expect(isMultiline(singleLine as Statement)).toBeFalsy();

            const multiLine = {};
            const locStub =
                sandbox.stub().returns({
                    start: { line: 1, column: 0 },
                    end: { line: 2, column: 5 },
                });
            Object.defineProperty(multiLine, 'loc', {
                get: locStub,
                configurable: true,
            });
            expect(isMultiline(multiLine as Statement)).toBeTruthy();

            assert.calledOnce(locStub);
        },
    );
});
