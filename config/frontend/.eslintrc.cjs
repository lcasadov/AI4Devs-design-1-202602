/**
 * RecruitFlow — ESLint Configuration
 * Google Style + OWASP Security Rules
 * Versión: 1.0 | Fecha: 2026-04-04
 *
 * Dependencias (instalar en el frontend):
 *   npm install --save-dev \
 *     eslint @typescript-eslint/eslint-plugin @typescript-eslint/parser \
 *     eslint-plugin-react eslint-plugin-react-hooks \
 *     eslint-plugin-jsx-a11y eslint-plugin-import \
 *     eslint-plugin-security eslint-plugin-no-secrets \
 *     eslint-config-prettier prettier
 */

// @ts-check
/** @type {import('eslint').Linter.Config} */
module.exports = {
  root: true,

  env: {
    browser:  true,
    es2022:   true,
    node:     true,
  },

  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaVersion:     'latest',
    sourceType:      'module',
    ecmaFeatures:    { jsx: true },
    project:         ['./tsconfig.json'],
    tsconfigRootDir: __dirname,
  },

  settings: {
    react: { version: 'detect' },
    'import/resolver': {
      typescript: { alwaysTryTypes: true },
    },
  },

  plugins: [
    '@typescript-eslint',
    'react',
    'react-hooks',
    'jsx-a11y',
    'import',
    'security',
    'no-secrets',
  ],

  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended-type-checked',
    'plugin:@typescript-eslint/stylistic-type-checked',
    'plugin:react/recommended',
    'plugin:react/jsx-runtime',
    'plugin:react-hooks/recommended',
    'plugin:jsx-a11y/recommended',
    'plugin:import/recommended',
    'plugin:import/typescript',
    'plugin:security/recommended',
    'prettier',  // debe ser el último: desactiva reglas que conflictúan con Prettier
  ],

  rules: {

    // ════════════════════════════════════════════════════════════
    // TYPESCRIPT — Seguridad de tipos
    // ════════════════════════════════════════════════════════════

    '@typescript-eslint/no-explicit-any': 'error',
    '@typescript-eslint/no-non-null-assertion': 'error',
    '@typescript-eslint/no-unsafe-assignment': 'error',
    '@typescript-eslint/no-unsafe-call': 'error',
    '@typescript-eslint/no-unsafe-member-access': 'error',
    '@typescript-eslint/no-unsafe-return': 'error',
    '@typescript-eslint/no-unsafe-argument': 'error',
    '@typescript-eslint/explicit-module-boundary-types': 'warn',
    '@typescript-eslint/explicit-function-return-type': 'off',

    '@typescript-eslint/no-unused-vars': [
      'error',
      {
        argsIgnorePattern: '^_',
        varsIgnorePattern: '^_',
        caughtErrorsIgnorePattern: '^_',
      },
    ],

    '@typescript-eslint/consistent-type-imports': [
      'error',
      { prefer: 'type-imports', disallowTypeAnnotations: false },
    ],

    '@typescript-eslint/consistent-type-definitions': ['error', 'interface'],

    // Promesas — evitar olvidarse de await
    '@typescript-eslint/no-floating-promises': 'error',
    '@typescript-eslint/await-thenable': 'error',
    '@typescript-eslint/no-misused-promises': [
      'error',
      { checksVoidReturn: { attributes: false } },
    ],

    // Calidad de tipos
    '@typescript-eslint/prefer-nullish-coalescing': 'error',
    '@typescript-eslint/prefer-optional-chain': 'error',
    '@typescript-eslint/no-unnecessary-type-assertion': 'error',
    '@typescript-eslint/no-redundant-type-constituents': 'warn',
    '@typescript-eslint/prefer-as-const': 'error',

    // ════════════════════════════════════════════════════════════
    // REACT
    // ════════════════════════════════════════════════════════════

    'react/prop-types': 'off',           // TypeScript gestiona los tipos de props
    'react/display-name': 'warn',
    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'warn',

    // OWASP XSS prevention
    'react/no-danger': 'error',
    'react/no-danger-with-children': 'error',

    // OWASP: target="_blank" sin rel="noopener noreferrer" permite tab-napping
    'react/jsx-no-target-blank': ['error', { enforceDynamicLinks: 'always' }],

    // Evitar re-renders innecesarios
    'react/jsx-no-useless-fragment': 'warn',
    'react/jsx-curly-brace-presence': ['warn', { props: 'never', children: 'never' }],

    // ════════════════════════════════════════════════════════════
    // IMPORTS
    // ════════════════════════════════════════════════════════════

    'import/order': [
      'error',
      {
        groups: [
          'builtin',
          'external',
          'internal',
          'parent',
          'sibling',
          'index',
          'type',
        ],
        pathGroups: [
          { pattern: 'react',    group: 'external', position: 'before' },
          { pattern: 'react-*',  group: 'external', position: 'before' },
          { pattern: '@/**',     group: 'internal', position: 'after'  },
        ],
        pathGroupsExcludedImportTypes: ['react'],
        'newlines-between': 'always',
        alphabetize: { order: 'asc', caseInsensitive: true },
      },
    ],
    'import/no-duplicates':      'error',
    'import/no-cycle':           ['error', { maxDepth: 5 }],
    'import/no-default-export':  'warn',    // preferir named exports
    'import/no-extraneous-dependencies': [
      'error',
      { devDependencies: ['**/*.test.*', '**/*.spec.*', 'cypress/**', 'src/test/**'] },
    ],

    // ════════════════════════════════════════════════════════════
    // SEGURIDAD OWASP
    // ════════════════════════════════════════════════════════════

    // A03 Injection — regex peligrosas
    'security/detect-unsafe-regex':           'error',
    'security/detect-non-literal-regexp':     'warn',

    // A03 Injection — acceso a propiedades con variable no sanitizada
    'security/detect-object-injection':       'warn',

    // A02 Cryptographic Failures
    'security/detect-buffer-noassert':        'error',

    // A07 Timing attacks en comparaciones sensibles
    'security/detect-possible-timing-attacks': 'warn',

    // A02 Secrets hardcoded (API keys, tokens, passwords)
    'no-secrets/no-secrets': [
      'error',
      {
        tolerance: 4.5,   // entropía mínima para detectar secrets
        additionalRegexes: {
          'JWT Secret': /['"]ey[A-Za-z0-9_-]{10,}/,
          'Bearer Token': /Bearer\s[A-Za-z0-9_-]{20,}/,
        },
      },
    ],

    // ════════════════════════════════════════════════════════════
    // CALIDAD GENERAL
    // ════════════════════════════════════════════════════════════

    // Logs — solo warn/error en producción
    'no-console': ['warn', { allow: ['warn', 'error'] }],
    'no-debugger': 'error',
    'no-alert':    'error',

    // Modernidad
    'prefer-const':         'error',
    'no-var':               'error',
    'no-let-in-for-vars':   'off',
    'prefer-template':      'error',
    'prefer-destructuring': ['warn', { array: false, object: true }],
    'object-shorthand':     'error',
    'prefer-arrow-callback': 'error',

    // Igualdad estricta
    'eqeqeq': ['error', 'always', { null: 'ignore' }],
    'no-implicit-coercion': ['error', { boolean: false }],

    // Sin mutación de parámetros (evitar side effects)
    'no-param-reassign': ['error', { props: true, ignorePropertyModificationsForRegex: ['^draft'] }],

    // Sin código muerto
    'no-unreachable':     'error',
    'no-unused-labels':   'error',
    'no-useless-return':  'error',

    // ════════════════════════════════════════════════════════════
    // ACCESIBILIDAD (jsx-a11y)
    // ════════════════════════════════════════════════════════════

    'jsx-a11y/anchor-is-valid':             'error',
    'jsx-a11y/interactive-supports-focus':  'error',
    'jsx-a11y/click-events-have-key-events': 'error',
    'jsx-a11y/no-autofocus':                'warn',
    'jsx-a11y/alt-text':                    'error',
    'jsx-a11y/label-has-associated-control': 'error',
  },

  overrides: [
    // ── Tests — relajar restricciones para mejorar legibilidad ──
    {
      files: [
        '**/*.test.{ts,tsx}',
        '**/*.spec.{ts,tsx}',
        'src/test/**/*.{ts,tsx}',
      ],
      rules: {
        '@typescript-eslint/no-non-null-assertion':    'off',
        '@typescript-eslint/no-explicit-any':           'warn',
        '@typescript-eslint/no-unsafe-assignment':      'off',
        '@typescript-eslint/no-unsafe-member-access':   'off',
        'import/no-default-export':                     'off',
        'no-secrets/no-secrets':                        'off',
        'react/display-name':                           'off',
      },
    },

    // ── Cypress E2E ─────────────────────────────────────────────
    {
      files: ['cypress/**/*.{ts,tsx}'],
      env: { 'cypress/globals': true },
      rules: {
        '@typescript-eslint/no-non-null-assertion':  'off',
        '@typescript-eslint/no-unsafe-assignment':   'off',
        '@typescript-eslint/no-unsafe-call':         'off',
        'import/no-default-export':                  'off',
        'no-secrets/no-secrets':                     'off',
      },
    },

    // ── Archivos de configuración de herramientas ───────────────
    {
      files: [
        '*.config.{ts,js,cjs,mjs}',
        'vite.config.*',
        'jest.config.*',
        'vitest.config.*',
        'postcss.config.*',
        'tailwind.config.*',
      ],
      rules: {
        'import/no-default-export':               'off',
        '@typescript-eslint/no-unsafe-assignment': 'off',
        '@typescript-eslint/no-var-requires':      'off',
      },
    },

    // ── MSW Handlers — los default export son convención de MSW ─
    {
      files: ['src/test/mocks/**/*.{ts,tsx}'],
      rules: {
        'import/no-default-export': 'off',
      },
    },
  ],

  ignorePatterns: [
    'dist/',
    'build/',
    'src/generated/',
    'node_modules/',
    '*.min.js',
    '*.min.css',
    'coverage/',
  ],
}
