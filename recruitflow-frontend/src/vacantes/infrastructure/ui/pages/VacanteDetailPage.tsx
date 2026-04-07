import { useParams } from 'react-router-dom';

import { Spinner } from '@/shared/infrastructure/ui/components';
import { useVacante } from '@/vacantes/hooks';

// TODO: add candidatos section, pipeline status, edit/close actions with RBAC guards
function VacanteDetailPage(): JSX.Element {
  const { id = '' } = useParams<{ id: string }>();
  const { data: vacante, isLoading, isError } = useVacante(id);

  if (isLoading) return <Spinner label="Cargando vacante..." />;
  if (isError || !vacante) return <div role="alert">Vacante no encontrada.</div>;

  return (
    <main aria-label={`Detalle de vacante: ${vacante.titulo}`}>
      <h1>{vacante.titulo}</h1>
      <p>{vacante.descripcion}</p>
      <dl>
        <dt>Departamento</dt>
        <dd>{vacante.departamento}</dd>
        <dt>Ubicación</dt>
        <dd>{vacante.ubicacion}</dd>
        <dt>Modalidad</dt>
        <dd>{vacante.modalidad}</dd>
        <dt>Estado</dt>
        <dd>{vacante.estado}</dd>
      </dl>
    </main>
  );
}

export default VacanteDetailPage;
