import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import type { PageRequest } from '@/shared/domain/types';
import type { CreateVacanteRequest, UpdateVacanteRequest } from '@/vacantes/domain';
import {
  createVacante,
  deleteVacante,
  getVacanteById,
  getVacantes,
  updateVacante,
} from '@/vacantes/infrastructure/api';

const QUERY_KEY = 'vacantes';

// TODO: add error handling with useToast once toast module is implemented

export function useVacantes(params?: PageRequest) {
  return useQuery({
    queryKey: [QUERY_KEY, params],
    queryFn: () => getVacantes(params),
  });
}

export function useVacante(id: string) {
  return useQuery({
    queryKey: [QUERY_KEY, id],
    queryFn: () => getVacanteById(id),
    enabled: id.length > 0,
  });
}

export function useCreateVacante() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (request: CreateVacanteRequest) => createVacante(request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}

export function useUpdateVacante() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, request }: { id: string; request: UpdateVacanteRequest }) =>
      updateVacante(id, request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}

export function useDeleteVacante() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: string) => deleteVacante(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}
