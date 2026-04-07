import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import type { PageRequest } from '@/shared/domain/types';
import type { CreateUsuarioRequest, UpdateUsuarioRequest } from '@/usuarios/domain';
import {
  createUsuario,
  getUsuarioById,
  getUsuarios,
  updateUsuario,
} from '@/usuarios/infrastructure/api';

const QUERY_KEY = 'usuarios';

// TODO: add error handling with useToast once toast module is implemented

export function useUsuarios(params?: PageRequest) {
  return useQuery({
    queryKey: [QUERY_KEY, params],
    queryFn: () => getUsuarios(params),
  });
}

export function useUsuario(id: string) {
  return useQuery({
    queryKey: [QUERY_KEY, id],
    queryFn: () => getUsuarioById(id),
    enabled: id.length > 0,
  });
}

export function useCreateUsuario() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (request: CreateUsuarioRequest) => createUsuario(request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}

export function useUpdateUsuario() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, request }: { id: string; request: UpdateUsuarioRequest }) =>
      updateUsuario(id, request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}
