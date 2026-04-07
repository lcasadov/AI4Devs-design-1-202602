import { apiClient } from '@/shared/infrastructure/api';
import type { Page, PageRequest } from '@/shared/domain/types';
import type { CreateUsuarioRequest, UpdateUsuarioRequest, Usuario } from '@/usuarios/domain';

// TODO: implement full API calls once backend endpoints are confirmed

export async function getUsuarios(params?: PageRequest): Promise<Page<Usuario>> {
  const { data } = await apiClient.get<Page<Usuario>>('/usuarios', { params });
  return data;
}

export async function getUsuarioById(id: string): Promise<Usuario> {
  const { data } = await apiClient.get<Usuario>(`/usuarios/${id}`);
  return data;
}

export async function createUsuario(request: CreateUsuarioRequest): Promise<Usuario> {
  const { data } = await apiClient.post<Usuario>('/usuarios', request);
  return data;
}

export async function updateUsuario(id: string, request: UpdateUsuarioRequest): Promise<Usuario> {
  const { data } = await apiClient.put<Usuario>(`/usuarios/${id}`, request);
  return data;
}
