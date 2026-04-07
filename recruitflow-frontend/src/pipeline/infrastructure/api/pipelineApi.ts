import { apiClient } from '@/shared/infrastructure/api';
import type { AplicacionPipeline, MoverEtapaRequest } from '@/pipeline/domain';

// TODO: implement full API calls once backend endpoints are confirmed

export async function getAplicacionesByVacante(vacanteId: string): Promise<AplicacionPipeline[]> {
  const { data } = await apiClient.get<AplicacionPipeline[]>(`/vacantes/${vacanteId}/aplicaciones`);
  return data;
}

export async function moverEtapa(
  aplicacionId: string,
  request: MoverEtapaRequest,
): Promise<AplicacionPipeline> {
  const { data } = await apiClient.patch<AplicacionPipeline>(
    `/aplicaciones/${aplicacionId}/etapa`,
    request,
  );
  return data;
}
