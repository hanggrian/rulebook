import { defineConfig, UserConfig } from 'vite';

export default defineConfig({
    test: {
        include: ['./__tests__/*.js', './__tests__/*.ts'],
        environment: 'jsdom',
        globals: true,
        exclude: [
            '**/node_modules/**',
            '**/dist/**',
            '**/__tests__/tests.ts',
        ],
    },
} as UserConfig);
