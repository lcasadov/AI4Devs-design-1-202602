export type UsuarioRol = 'ADMIN' | 'RECRUITER' | 'HIRING_MANAGER' | 'RRHH';
export type UsuarioEstado = 'ACTIVO' | 'INACTIVO' | 'BLOQUEADO';

export interface Usuario {
  id: string;
  nombre: string;
  apellidos: string;
  email: string;
  rol: UsuarioRol;
  estado: UsuarioEstado;
  fechaCreacion: string;
  ultimoAcceso?: string;
}

export interface CreateUsuarioRequest {
  nombre: string;
  apellidos: string;
  email: string;
  rol: UsuarioRol;
  password: string;
}

export interface UpdateUsuarioRequest {
  nombre?: string;
  apellidos?: string;
  rol?: UsuarioRol;
  estado?: UsuarioEstado;
}
