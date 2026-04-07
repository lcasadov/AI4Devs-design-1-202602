import { useNavigate } from 'react-router-dom';

import { Spinner } from '@/shared/infrastructure/ui/components';
import { VacanteCard } from '@/vacantes/infrastructure/ui/components';
import { useVacantes } from '@/vacantes/hooks';

// TODO: add pagination controls, filter bar, and "Nueva vacante" button with modal
function VacantesPage(): JSX.Element {
  const navigate = useNavigate();
  const { data, isLoading, isError } = useVacantes();

  const handleCardClick = (id: string): void => {
    navigate(`/vacantes/${id}`);
  };

  if (isLoading) return <Spinner label="Cargando vacantes..." />;
  if (isError) return <div role="alert">Error al cargar las vacantes.</div>;

  return (
    <main aria-label="Listado de vacantes">
      <h1>Vacantes</h1>
      {data?.content.map((vacante) => (
        <VacanteCard key={vacante.id} vacante={vacante} onClick={handleCardClick} />
      ))}
      {data?.content.length === 0 && <p>No hay vacantes disponibles.</p>}
    </main>
  );
}

export default VacantesPage;
