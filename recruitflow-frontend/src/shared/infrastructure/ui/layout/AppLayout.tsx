import { Outlet } from 'react-router-dom';

import Navbar from './Navbar';
import Sidebar from './Sidebar';

// TODO: apply grid/flex layout with Tailwind CSS once styling is added
function AppLayout(): JSX.Element {
  return (
    <div>
      <Navbar />
      <div style={{ display: 'flex' }}>
        <Sidebar />
        <main style={{ flex: 1 }}>
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default AppLayout;
