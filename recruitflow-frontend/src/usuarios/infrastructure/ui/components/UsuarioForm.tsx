import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

import { Button, Input } from '@/shared/infrastructure/ui/components';

const usuarioSchema = z.object({
  nombre: z.string().min(1, 'El nombre es obligatorio'),
  apellidos: z.string().min(1, 'Los apellidos son obligatorios'),
  email: z.string().email('Email inválido'),
  rol: z.enum(['ADMIN', 'RECRUITER', 'HIRING_MANAGER', 'RRHH']),
  password: z.string().min(8, 'La contraseña debe tener al menos 8 caracteres'),
});

type UsuarioFormData = z.infer<typeof usuarioSchema>;

export interface UsuarioFormProps {
  onSubmit: (data: UsuarioFormData) => void;
  isSubmitting?: boolean;
  defaultValues?: Partial<UsuarioFormData>;
}

// TODO: implement rol selector dropdown and conditional password field (hide on edit)
function UsuarioForm({ onSubmit, isSubmitting = false, defaultValues }: UsuarioFormProps): JSX.Element {
  const { register, handleSubmit, formState: { errors } } = useForm<UsuarioFormData>({
    resolver: zodResolver(usuarioSchema),
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
      <Input
        id="password"
        label="Contraseña"
        type="password"
        error={errors.password?.message}
        {...register('password')}
      />
      <Button type="submit" isLoading={isSubmitting}>
        Guardar
      </Button>
    </form>
  );
}

export default UsuarioForm;
