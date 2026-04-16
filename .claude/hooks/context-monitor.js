#!/usr/bin/env node
/**
 * context-monitor.js — Claude Code UserPromptSubmit hook
 *
 * Injects a context-fill warning into Claude's system context when the
 * conversation is approaching the model's context window limit.
 *
 * Triggers at two configurable thresholds:
 *   ⚠️  WARN     — context >= WARN_THRESHOLD used  (default 65%)
 *   🔴 CRITICAL — context >= CRITICAL_THRESHOLD used (default 80%)
 *
 * Exportable: zero project-specific references.
 * Override defaults via environment variables (see CONFIG block).
 *
 * Inspired by the context-rot pattern from gsd-build/get-shit-done.
 *
 * Registration (add to .claude/settings.json):
 *   {
 *     "hooks": {
 *       "UserPromptSubmit": [
 *         { "hooks": [{ "type": "command", "command": "node .claude/hooks/context-monitor.js" }] }
 *       ]
 *     }
 *   }
 */

'use strict';

const fs = require('fs');

// ─── CONFIG ──────────────────────────────────────────────────────────────────
// All values are overridable via environment variables so teams can tune
// thresholds per project without editing this file.
const CONFIG = {
  // Estimated context window in tokens. Claude 3.x and Claude 4 = 200 000.
  CONTEXT_WINDOW_TOKENS: parseInt(process.env.CONTEXT_WINDOW_TOKENS || '200000', 10),

  // Characters per token approximation for mixed code + prose.
  // 3.5 is conservative (slightly over-estimates usage — better safe).
  CHARS_PER_TOKEN: parseFloat(process.env.CONTEXT_CHARS_PER_TOKEN || '3.5'),

  // Fraction of context REMAINING that triggers the warning (default: 35% left).
  WARN_THRESHOLD: parseFloat(process.env.CONTEXT_WARN_THRESHOLD || '0.35'),

  // Fraction of context REMAINING that triggers the critical alert (default: 20% left).
  CRITICAL_THRESHOLD: parseFloat(process.env.CONTEXT_CRITICAL_THRESHOLD || '0.20'),
};
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Rough token estimate from raw character count.
 * @param {string} text
 * @returns {number}
 */
function estimateTokens(text) {
  return Math.ceil(text.length / CONFIG.CHARS_PER_TOKEN);
}

/**
 * Reads the JSONL transcript file and estimates total tokens used so far.
 * @param {string} transcriptPath
 * @returns {number}
 */
function readTranscriptTokens(transcriptPath) {
  if (!transcriptPath) return 0;
  try {
    const content = fs.readFileSync(transcriptPath, 'utf8');
    return estimateTokens(content);
  } catch {
    return 0;
  }
}

/**
 * Formats a token count as "XXk" for readability.
 * @param {number} tokens
 * @returns {string}
 */
function fmt(tokens) {
  return `${Math.round(tokens / 1000)}k`;
}

async function main() {
  // Read JSON input from stdin (provided by Claude Code hook runtime).
  let raw = '';
  for await (const chunk of process.stdin) {
    raw += chunk;
  }

  let input;
  try {
    input = JSON.parse(raw);
  } catch {
    // Malformed input — exit silently, never block the user's prompt.
    process.exit(0);
  }

  const usedTokens = readTranscriptTokens(input.transcript_path);
  const total = CONFIG.CONTEXT_WINDOW_TOKENS;
  const remainingFraction = Math.max(0, (total - usedTokens) / total);
  const remainingPct = Math.round(remainingFraction * 100);
  const usedPct = 100 - remainingPct;

  let message = null;

  if (remainingFraction <= CONFIG.CRITICAL_THRESHOLD) {
    message = [
      `🔴 CONTEXT CRÍTICO — ${remainingPct}% restante (≈${fmt(usedTokens)}/${fmt(total)} tokens usados, ${usedPct}% lleno).`,
      `La calidad de respuesta baja significativamente en este rango.`,
      `Acciones recomendadas ANTES de continuar:`,
      `  1. Termina la tarea actual y haz commit del trabajo pendiente.`,
      `  2. Documenta el estado en tasks/todo.md o equivalente.`,
      `  3. Inicia una sesión nueva para la siguiente tarea.`,
      `  4. Si debes seguir aquí, usa /compact para condensar el contexto.`,
    ].join('\n');
  } else if (remainingFraction <= CONFIG.WARN_THRESHOLD) {
    message = [
      `⚠️  CONTEXTO ALTO — ${remainingPct}% restante (≈${fmt(usedTokens)}/${fmt(total)} tokens usados, ${usedPct}% lleno).`,
      `Completa la tarea actual antes de iniciar una nueva.`,
      `Si vas a delegar a un agente, hazlo ahora mientras queda margen suficiente.`,
    ].join('\n');
  }

  if (message) {
    // UserPromptSubmit hooks inject additionalContext that Claude sees
    // before processing the user's prompt.
    process.stdout.write(
      JSON.stringify({
        hookSpecificOutput: {
          hookEventName: 'UserPromptSubmit',
          additionalContext: message,
        },
      })
    );
  }

  process.exit(0);
}

main().catch(() => process.exit(0));
