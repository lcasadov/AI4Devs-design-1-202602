import type { Vacante } from '@/vacantes/domain';

export interface VacanteCardProps {
  vacante: Vacante;
  onClick?: (id: string) => void;
}

// TODO: implement card with status badge, salary range display, and action buttons
function VacanteCard({ vacante, onClick }: VacanteCardProps): JSX.Element {
  const handleClick = (): void => {
    onClick?.(vacante.id);
  };

  return (
    <div
      onClick={handleClick}
      role={onClick ? 'button' : undefined}
      tabIndex={onClick ? 0 : undefined}
      onKeyDown={onClick ? (e) => e.key === 'Enter' && handleClick() : undefined}
    >
      <h3>{vacante.titulo}</h3>
      <p>{vacante.departamento}</p>
      <span>{vacante.estado}</span>
    </div>
  );
}

export default VacanteCard;
