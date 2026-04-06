export type VacanteEstado = 'ABIERTA' | 'EN_PROCESO' | 'CERRADA' | 'PAUSADA';

export interface Vacante {
  id: number;
  titulo: string;
  descripcion: string;
  departamento: string;
  ubicacion: string;
  modalidad: 'PRESENCIAL' | 'REMOTO' | 'HIBRIDO';
  salarioMin?: number;
  salarioMax?: number;
  estado: VacanteEstado;
  fechaPublicacion: string;
  fechaCierre?: string;
  reclutadorId: number;
}

export interface CreateVacanteRequest {
  titulo: string;
  descripcion: string;
  departamento: string;
  ubicacion: string;
  modalidad: 'PRESENCIAL' | 'REMOTO' | 'HIBRIDO';
  salarioMin?: number;
  salarioMax?: number;
}

export interface UpdateVacanteRequest extends Partial<CreateVacanteRequest> {
  estado?: VacanteEstado;
}
