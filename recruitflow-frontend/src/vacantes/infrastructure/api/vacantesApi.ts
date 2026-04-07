import { apiClient } from '@/shared/infrastructure/api';
import type { Page, PageRequest } from '@/shared/domain/types';
import type { CreateVacanteRequest, UpdateVacanteRequest, Vacante } from '@/vacantes/domain';

// TODO: implement full API calls once backend endpoints are confirmed

export async function getVacantes(params?: PageRequest): Promise<Page<Vacante>> {
  const { data } = await apiClient.get<Page<Vacante>>('/vacantes', { params });
  return data;
}

export async function getVacanteById(id: string): Promise<Vacante> {
  const { data } = await apiClient.get<Vacante>(`/vacantes/${id}`);
  return data;
}

export async function createVacante(request: CreateVacanteRequest): Promise<Vacante> {
  const { data } = await apiClient.post<Vacante>('/vacantes', request);
  return data;
}

export async function updateVacante(id: string, request: UpdateVacanteRequest): Promise<Vacante> {
  const { data } = await apiClient.put<Vacante>(`/vacantes/${id}`, request);
  return data;
}

export async function deleteVacante(id: string): Promise<void> {
  await apiClient.delete(`/vacantes/${id}`);
}
