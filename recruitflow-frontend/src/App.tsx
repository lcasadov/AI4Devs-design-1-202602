import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';

import AppLayout from '@/shared/infrastructure/ui/layout/AppLayout';
import CandidatosPage from '@/candidatos/infrastructure/ui/pages/CandidatosPage';
import PipelinePage from '@/pipeline/infrastructure/ui/pages/PipelinePage';
import UsuariosPage from '@/usuarios/infrastructure/ui/pages/UsuariosPage';
import VacanteDetailPage from '@/vacantes/infrastructure/ui/pages/VacanteDetailPage';
import VacantesPage from '@/vacantes/infrastructure/ui/pages/VacantesPage';

function App(): JSX.Element {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<AppLayout />}>
          <Route index element={<Navigate to="/vacantes" replace />} />
          <Route path="vacantes" element={<VacantesPage />} />
          <Route path="vacantes/:id" element={<VacanteDetailPage />} />
          <Route path="candidatos" element={<CandidatosPage />} />
          <Route path="pipeline" element={<PipelinePage />} />
          <Route path="usuarios" element={<UsuariosPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
