export interface SpinnerProps {
  size?: 'sm' | 'md' | 'lg';
  label?: string;
}

// TODO: implement animated spinner with Tailwind CSS classes
function Spinner({ label = 'Cargando...' }: SpinnerProps): JSX.Element {
  return <div role="status" aria-label={label}>{label}</div>;
}

export default Spinner;
