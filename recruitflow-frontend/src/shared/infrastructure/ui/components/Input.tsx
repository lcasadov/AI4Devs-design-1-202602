import React from 'react';

export interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

// TODO: implement full Input component with label, error state, and Tailwind styles
function Input({ label, error, id, ...props }: InputProps): JSX.Element {
  return (
    <div>
      {label && <label htmlFor={id}>{label}</label>}
      <input id={id} aria-describedby={error ? `${id}-error` : undefined} {...props} />
      {error && <span id={`${id}-error`} role="alert">{error}</span>}
    </div>
  );
}

export default Input;
