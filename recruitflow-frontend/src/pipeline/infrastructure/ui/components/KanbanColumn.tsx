import type { AplicacionPipeline, EtapaPipeline } from '@/pipeline/domain';

export interface KanbanColumnProps {
  etapa: EtapaPipeline;
  aplicaciones: AplicacionPipeline[];
  onMoverAplicacion?: (aplicacionId: number, etapa: EtapaPipeline) => void;
}

// TODO: implement drag-and-drop with @dnd-kit/core or react-beautiful-dnd
function KanbanColumn({ etapa, aplicaciones }: KanbanColumnProps): JSX.Element {
  return (
    <div aria-label={`Columna ${etapa}`}>
      <h3>{etapa}</h3>
      <ul>
        {aplicaciones.map((aplicacion) => (
          <li key={aplicacion.id}>
            <span>{aplicacion.candidatoNombre}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default KanbanColumn;
