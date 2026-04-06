import axios, { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

import type { ApiError } from '@/shared/domain/types';

export const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
});

// Request interceptor — attach JWT token from cookie-managed session
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    // TODO: retrieve token from auth store (Zustand) once auth module is implemented
    return config;
  },
  (error: AxiosError) => Promise.reject(error),
);

// Response interceptor — normalize API errors
apiClient.interceptors.response.use(
  (response: AxiosResponse) => response,
  (error: AxiosError<ApiError>) => {
    const status = error.response?.status;

    if (status === 401) {
      // TODO: redirect to /login and clear auth state when auth module is implemented
      window.location.href = '/login';
    }

    return Promise.reject(error);
  },
);
