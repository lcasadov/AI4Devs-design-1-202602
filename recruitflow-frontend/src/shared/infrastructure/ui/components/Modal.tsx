import React from 'react';

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
}

// TODO: implement full Modal component with portal, overlay, and focus trap
function Modal({ isOpen, onClose, title, children }: ModalProps): JSX.Element | null {
  if (!isOpen) return null;

  return (
    <div role="dialog" aria-modal="true" aria-labelledby="modal-title">
      <div id="modal-title">{title}</div>
      <button onClick={onClose} aria-label="Cerrar modal">X</button>
      <div>{children}</div>
    </div>
  );
}

export default Modal;
