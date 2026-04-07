import React, { useId } from 'react';

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
}

// TODO: implement full Modal component with portal, overlay, and focus trap
function Modal({ isOpen, onClose, title, children }: ModalProps): JSX.Element | null {
  const titleId = useId();

  if (!isOpen) return null;

  return (
    <div role="dialog" aria-modal="true" aria-labelledby={titleId}>
      <div id={titleId}>{title}</div>
      <button type="button" onClick={onClose} aria-label="Cerrar modal">X</button>
      <div>{children}</div>
    </div>
  );
}

export default Modal;
