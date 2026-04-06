import { Spinner } from '@/shared/infrastructure/ui/components';
import { CandidatoCard } from '@/candidatos/infrastructure/ui/components';
import { useCandidatos } from '@/candidatos/hooks';

// TODO: add search/filter bar, skills filter, and "Nuevo candidato" button
function CandidatosPage(): JSX.Element {
  const { data, isLoading, isError } = useCandidatos();

  if (isLoading) return <Spinner label="Cargando candidatos..." />;
  if (isError) return <div role="alert">Error al cargar los candidatos.</div>;

  return (
    <main aria-label="Listado de candidatos">
      <h1>Candidatos</h1>
      {data?.content.map((candidato) => (
        <CandidatoCard key={candidato.id} candidato={candidato} />
      ))}
      {data?.content.length === 0 && <p>No hay candidatos registrados.</p>}
    </main>
  );
}

export default CandidatosPage;
