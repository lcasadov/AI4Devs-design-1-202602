// TODO: implement useAuth with Zustand store — login, logout, token refresh, role checks
export interface AuthUser {
  id: number;
  nombre: string;
  email: string;
  rol: 'ADMIN' | 'RECRUITER' | 'HIRING_MANAGER' | 'RRHH';
}

export interface UseAuthReturn {
  user: AuthUser | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

function useAuth(): UseAuthReturn {
  // TODO: replace with Zustand store subscription
  return {
    user: null,
    isAuthenticated: false,
    isLoading: false,
    login: async (_email: string, _password: string) => {
      // TODO: call POST /api/auth/login
    },
    logout: () => {
      // TODO: call POST /api/auth/logout and clear store
    },
  };
}

export default useAuth;
