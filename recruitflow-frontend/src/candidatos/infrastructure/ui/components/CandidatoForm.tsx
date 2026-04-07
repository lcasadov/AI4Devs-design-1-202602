import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { Button, Input } from '@/shared/infrastructure/ui/components';

const candidatoSchema = z.object({
  nombre: z.string().min(1, 'El nombre es obligatorio'),
  apellidos: z.string().min(1, 'Los apellidos son obligatorios'),
  email: z.string().email('Email inválido'),
  telefono: z.string().optional(),
  linkedinUrl: z.string().url('URL de LinkedIn inválida').optional().or(z.literal('')),
  ubicacion: z.string().min(1, 'La ubicación es obligatoria'),
  experienciaAnios: z.number().min(0, 'La experiencia no puede ser negativa'),
});

type CandidatoFormData = z.infer<typeof candidatoSchema>;

export interface CandidatoFormProps {
  onSubmit: (data: CandidatoFormData) => void;
  isSubmitting?: boolean;
  defaultValues?: Partial<CandidatoFormData>;
}

// TODO: add skills multi-select field and CV file upload
function CandidatoForm({ onSubmit, isSubmitting = false, defaultValues }: CandidatoFormProps): JSX.Element {
  const { register, handleSubmit, formState: { errors } } = useForm<CandidatoFormData>({
    resolver: zodResolver(candidatoSchema),
    defaultValues,
  });

  return (
    <form onSubmit={handleSubmit(onSubmit)} noValidate>
      <Input id="nombre" label="Nombre" error={errors.nombre?.message} {...register('nombre')} />
      <Input
        id="apellidos"
        label="Apellidos"
        error={errors.apellidos?.message}
        {...register('apellidos')}
      />
      <Input id="email" label="Email" type="email" error={errors.email?.message} {...register('email')} />
      <Input id="ubicacion" label="Ubicación" error={errors.ubicacion?.message} {...register('ubicacion')} />
      <Input
        id="experienciaAnios"
        label="Años de experiencia"
        type="number"
        min={0}
        error={errors.experienciaAnios?.message}
        {...register('experienciaAnios', { valueAsNumber: true })}
      />
      <Button type="submit" isLoading={isSubmitting}>
        Guardar
      </Button>
    </form>
  );
}

export default CandidatoForm;
