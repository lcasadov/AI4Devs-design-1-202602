export type CandidatoEstado = 'ACTIVO' | 'DESCARTADO' | 'CONTRATADO' | 'EN_PROCESO';

export interface Candidato {
  id: string;
  nombre: string;
  apellidos: string;
  email: string;
  telefono?: string;
  linkedinUrl?: string;
  cvUrl?: string;
  skills: string[];
  experienciaAnios: number;
  ubicacion: string;
  estado: CandidatoEstado;
  fechaRegistro: string;
}

export interface CreateCandidatoRequest {
  nombre: string;
  apellidos: string;
  email: string;
  telefono?: string;
  linkedinUrl?: string;
  skills: string[];
  experienciaAnios: number;
  ubicacion: string;
}

export interface UpdateCandidatoRequest extends Partial<CreateCandidatoRequest> {
  estado?: CandidatoEstado;
}
