import { Rule } from 'eslint';
import { Identifier, MethodDefinition, Property } from 'estree';

export default {
    create(context: Rule.RuleContext) {
        function process(node: Identifier, key: Identifier, param: Identifier): void {
            let name;
            const functionName = key.name;
            if (functionName === 'create') {
                name = 'context';
            } else if (functionName === 'Literal' ||
                functionName.startsWith('Program') ||
                functionName.endsWith('Declaration') ||
                functionName.endsWith('Declarator') ||
                functionName.endsWith('Statement')
            ) {
                name = 'node';
            } else {
                return;
            }
            if (param.name === name) {
                return;
            }
            context.report({ node, message: `Rename parameter to '${name}'.` });
        }

        return {
            MethodDefinition(node: MethodDefinition) {
                if (node.kind !== 'method') {
                    return;
                }

                const expression = node.key;
                if (expression?.type !== 'Identifier') {
                    return;
                }

                const params = node.value.params;
                if (params?.length > 1) {
                    return;
                }
                const param = params[0];
                if (param?.type !== 'Identifier') {
                    return;
                }
                process(param, expression, param);
            },

            Property(node: Property) {
                if (!node.method) {
                    return;
                }

                const expression = node.key;
                if (expression?.type !== 'Identifier') {
                    return;
                }

                const value = node.value;
                if (value?.type !== 'FunctionExpression') {
                    return;
                }
                const params = value.params;
                if (params?.length > 1) {
                    return;
                }
                const param = params[0];
                if (param?.type !== 'Identifier') {
                    return;
                }
                process(param, expression, param);
            },
        };
    },
};
