import React from 'react';

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'danger';
  isLoading?: boolean;
  children: React.ReactNode;
}

// TODO: implement full Button component with variant styles (Tailwind CSS not yet added)
function Button({ children, isLoading = false, disabled, ...props }: ButtonProps): JSX.Element {
  return (
    <button disabled={disabled ?? isLoading} {...props}>
      {isLoading ? 'Cargando...' : children}
    </button>
  );
}

export default Button;
