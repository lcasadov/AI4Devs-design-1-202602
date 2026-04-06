import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { Button, Input } from '@/shared/infrastructure/ui/components';

const vacanteSchema = z.object({
  titulo: z.string().min(3, 'El título debe tener al menos 3 caracteres'),
  descripcion: z.string().min(10, 'La descripción debe tener al menos 10 caracteres'),
  departamento: z.string().min(1, 'El departamento es obligatorio'),
  ubicacion: z.string().min(1, 'La ubicación es obligatoria'),
  modalidad: z.enum(['PRESENCIAL', 'REMOTO', 'HIBRIDO']),
  salarioMin: z.number().optional(),
  salarioMax: z.number().optional(),
});

type VacanteFormData = z.infer<typeof vacanteSchema>;

export interface VacanteFormProps {
  onSubmit: (data: VacanteFormData) => void;
  isSubmitting?: boolean;
  defaultValues?: Partial<VacanteFormData>;
}

// TODO: complete form with all fields, inline validation errors, and Tailwind styles
function VacanteForm({ onSubmit, isSubmitting = false, defaultValues }: VacanteFormProps): JSX.Element {
  const { register, handleSubmit, formState: { errors } } = useForm<VacanteFormData>({
    resolver: zodResolver(vacanteSchema),
    defaultValues,
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)} noValidate>
      <Input
        id="titulo"
        label="Título"
        error={errors.titulo?.message}
        {...register('titulo')}
      />
      <Input
        id="departamento"
        label="Departamento"
        error={errors.departamento?.message}
        {...register('departamento')}
      />
      <Input
        id="ubicacion"
        label="Ubicación"
        error={errors.ubicacion?.message}
        {...register('ubicacion')}
      />
      <Button type="submit" isLoading={isSubmitting}>
        Guardar
      </Button>
    </form>
  );
}

export default VacanteForm;
