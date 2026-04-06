// TODO: implement useToast with a toast notification queue (e.g. react-hot-toast or custom)
export type ToastType = 'success' | 'error' | 'warning' | 'info';

export interface UseToastReturn {
  showToast: (message: string, type?: ToastType) => void;
}

function useToast(): UseToastReturn {
  return {
    showToast: (_message: string, _type: ToastType = 'info') => {
      // TODO: enqueue toast notification
    },
  };
}

export default useToast;
