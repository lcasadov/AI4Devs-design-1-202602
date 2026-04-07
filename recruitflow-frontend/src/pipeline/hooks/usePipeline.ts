import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import type { MoverEtapaRequest } from '@/pipeline/domain';
import { getAplicacionesByVacante, moverEtapa } from '@/pipeline/infrastructure/api';

const QUERY_KEY = 'pipeline';

// TODO: add optimistic updates for drag-and-drop kanban interactions

export function usePipelineByVacante(vacanteId: string) {
  return useQuery({
    queryKey: [QUERY_KEY, vacanteId],
    queryFn: () => getAplicacionesByVacante(vacanteId),
    enabled: typeof vacanteId === 'string' && vacanteId.trim().length > 0,
  });
}

export function useMoverEtapa() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ aplicacionId, request }: { aplicacionId: string; request: MoverEtapaRequest }) =>
      moverEtapa(aplicacionId, request),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: [QUERY_KEY] }),
  });
}
