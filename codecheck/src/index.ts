import urlInDocumentationRule from './url-in-documentation.js';

export default {
    rules: {
        'url-in-documentation': urlInDocumentationRule,
    },
    configs: {
        default: {
            rules: {
                '@codecheck/url-in-documentation': 'error',
            },
        },
    },
};
