import { Rule } from 'eslint';
import { ClassDeclaration } from 'estree';

export default {
    create(context: Rule.RuleContext) {
        return {
            ClassDeclaration(node: ClassDeclaration) {
                if (!node.id || node.id.name.startsWith('Rulebook')) {
                    return;
                }

                let baseName = node.id.name;
                if (baseName.endsWith('Rule')) {
                    baseName = baseName.slice(0, -4);
                } else if (baseName.endsWith('Checker')) {
                    baseName = baseName.slice(0, -7);
                } else {
                    return;
                }

                const sourceCode = context.sourceCode;
                const jsdoc =
                    sourceCode
                        .getCommentsBefore(node)
                        .find(comment => comment.type === 'Block' && comment.value.startsWith('*'));

                if (!jsdoc) {
                    context.report({ node, message: 'Missing URL.' });
                    return;
                }
                const kebabName = baseName.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
                if (jsdoc
                    .value
                    .includes(
                        '{@link ' +
                        `https://hanggrian.github.io/rulebook/rules/#${kebabName}|See detail` +
                        '}',
                    )
                ) {
                    return;
                }
                context.report({ node, message: 'Invalid URL.' });
            },
        };
    },
};
