export default {
    type: 'object',
    properties: {
        words: {
            type: 'array',
            items: { type: 'string' },
        },
    },
    additionalProperties: false,
};
