export type EtapaPipeline =
  | 'shortlisted'
  | 'contacted'
  | 'internal_interview'
  | 'proposed'
  | 'client_interview'
  | 'offer'
  | 'hired'
  | 'discarded';

export interface AplicacionPipeline {
  id: string;
  candidatoId: string;
  candidatoNombre: string;
  candidatoEmail: string;
  vacanteId: string;
  vacanteTitulo: string;
  etapa: EtapaPipeline;
  fechaAplicacion: string;
  fechaUltimaActualizacion: string;
  notas?: string;
}

export interface MoverEtapaRequest {
  etapa: EtapaPipeline;
  notas?: string;
}
