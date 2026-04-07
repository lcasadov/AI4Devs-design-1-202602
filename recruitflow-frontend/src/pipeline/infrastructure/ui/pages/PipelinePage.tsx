import { Spinner } from '@/shared/infrastructure/ui/components';
import type { EtapaPipeline } from '@/pipeline/domain';
import { KanbanColumn } from '@/pipeline/infrastructure/ui/components';
import { usePipelineByVacante } from '@/pipeline/hooks';

const ETAPAS: EtapaPipeline[] = [
  'shortlisted',
  'contacted',
  'internal_interview',
  'proposed',
  'client_interview',
  'offer',
  'hired',
];

// TODO: add vacante selector, drag-and-drop between columns, and candidate detail panel
function PipelinePage(): JSX.Element {
  // TODO: get selected vacanteId from query param or filter UI
  const selectedVacanteId = '';
  const { data: aplicaciones = [], isLoading } = usePipelineByVacante(selectedVacanteId);

  if (isLoading) return <Spinner label="Cargando pipeline..." />;

  return (
    <main aria-label="Pipeline de selección">
      <h1>Pipeline</h1>
      {selectedVacanteId === '' && <p>Selecciona una vacante para ver el pipeline.</p>}
      <div style={{ display: 'flex', gap: '1rem' }}>
        {ETAPAS.map((etapa) => (
          <KanbanColumn
            key={etapa}
            etapa={etapa}
            aplicaciones={aplicaciones.filter((a) => a.etapa === etapa)}
          />
        ))}
      </div>
    </main>
  );
}

export default PipelinePage;
