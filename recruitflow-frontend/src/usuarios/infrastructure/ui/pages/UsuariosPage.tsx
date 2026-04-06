import { Spinner, Table } from '@/shared/infrastructure/ui/components';
import type { TableColumn } from '@/shared/infrastructure/ui/components';
import type { Usuario } from '@/usuarios/domain';
import { useUsuarios } from '@/usuarios/hooks';

const COLUMNS: TableColumn<Usuario>[] = [
  { key: 'nombre', header: 'Nombre' },
  { key: 'apellidos', header: 'Apellidos' },
  { key: 'email', header: 'Email' },
  { key: 'rol', header: 'Rol' },
  { key: 'estado', header: 'Estado' },
];

// TODO: add "Nuevo usuario" button (ADMIN only), row actions (edit/block), and RBAC guard
function UsuariosPage(): JSX.Element {
  const { data, isLoading, isError } = useUsuarios();

  if (isLoading) return <Spinner label="Cargando usuarios..." />;
  if (isError) return <div role="alert">Error al cargar los usuarios.</div>;

  return (
    <main aria-label="Gestión de usuarios">
      <h1>Usuarios</h1>
      <Table
        columns={COLUMNS}
        data={data?.content ?? []}
        emptyMessage="No hay usuarios registrados."
      />
    </main>
  );
}

export default UsuariosPage;
