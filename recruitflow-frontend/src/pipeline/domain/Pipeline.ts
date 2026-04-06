export type EtapaPipeline =
  | 'SCREENING'
  | 'ENTREVISTA_TELEFONICA'
  | 'ENTREVISTA_TECNICA'
  | 'ENTREVISTA_FINAL'
  | 'OFERTA'
  | 'CONTRATADO'
  | 'DESCARTADO';

export interface AplicacionPipeline {
  id: number;
  candidatoId: number;
  candidatoNombre: string;
  candidatoEmail: string;
  vacanteId: number;
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
