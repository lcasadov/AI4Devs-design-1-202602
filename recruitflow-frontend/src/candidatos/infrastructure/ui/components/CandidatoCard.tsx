import type { Candidato } from '@/candidatos/domain';

export interface CandidatoCardProps {
  candidato: Candidato;
  onClick?: (id: string) => void;
}

// TODO: implement card with skills chips, experience badge, and action buttons
function CandidatoCard({ candidato, onClick }: CandidatoCardProps): JSX.Element {
  const handleClick = (): void => {
    onClick?.(candidato.id);
  };

  return (
    <div
      onClick={handleClick}
      role={onClick ? 'button' : undefined}
      tabIndex={onClick ? 0 : undefined}
      onKeyDown={onClick ? (e) => e.key === 'Enter' && handleClick() : undefined}
    >
      <h3>{`${candidato.nombre} ${candidato.apellidos}`}</h3>
      <p>{candidato.email}</p>
      <span>{candidato.estado}</span>
    </div>
  );
}

export default CandidatoCard;
