import { apiClient } from '@/shared/infrastructure/api';
import type { Page, PageRequest } from '@/shared/domain/types';
import type { Candidato, CreateCandidatoRequest, UpdateCandidatoRequest } from '@/candidatos/domain';

// TODO: implement full API calls once backend endpoints are confirmed

export async function getCandidatos(params?: PageRequest): Promise<Page<Candidato>> {
  const { data } = await apiClient.get<Page<Candidato>>('/candidatos', { params });
  return data;
}

export async function getCandidatoById(id: string): Promise<Candidato> {
  const { data } = await apiClient.get<Candidato>(`/candidatos/${id}`);
  return data;
}

export async function createCandidato(request: CreateCandidatoRequest): Promise<Candidato> {
  const { data } = await apiClient.post<Candidato>('/candidatos', request);
  return data;
}

export async function updateCandidato(
  id: string,
  request: UpdateCandidatoRequest,
): Promise<Candidato> {
  const { data } = await apiClient.put<Candidato>(`/candidatos/${id}`, request);
  return data;
}
