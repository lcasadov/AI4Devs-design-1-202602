import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import type { PageRequest } from '@/shared/domain/types';
import type { CreateCandidatoRequest, UpdateCandidatoRequest } from '@/candidatos/domain';
import {
  createCandidato,
  getCandidatoById,
  getCandidatos,
  updateCandidato,
} from '@/candidatos/infrastructure/api';

const QUERY_KEY = 'candidatos';

// TODO: add error handling with useToast once toast module is implemented

export function useCandidatos(params?: PageRequest) {
  return useQuery({
    queryKey: [QUERY_KEY, params],
    queryFn: () => getCandidatos(params),
  });
}

export function useCandidato(id: number) {
  return useQuery({
    queryKey: [QUERY_KEY, id],
    queryFn: () => getCandidatoById(id),
    enabled: id > 0,
  });
}

export function useCreateCandidato() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (request: CreateCandidatoRequest) => createCandidato(request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}

export function useUpdateCandidato() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, request }: { id: number; request: UpdateCandidatoRequest }) =>
      updateCandidato(id, request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}
