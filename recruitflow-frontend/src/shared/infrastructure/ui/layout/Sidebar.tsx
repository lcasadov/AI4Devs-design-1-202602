import { NavLink } from 'react-router-dom';

const NAV_ITEMS = [
  { label: 'Vacantes', to: '/vacantes' },
  { label: 'Candidatos', to: '/candidatos' },
  { label: 'Pipeline', to: '/pipeline' },
  { label: 'Usuarios', to: '/usuarios' },
] as const;

// TODO: implement Sidebar with collapse toggle, active state highlight, and RBAC-aware items
function Sidebar(): JSX.Element {
  return (
    <aside aria-label="Menu lateral">
      <nav>
        <ul>
          {NAV_ITEMS.map((item) => (
            <li key={item.to}>
              <NavLink to={item.to}>{item.label}</NavLink>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;
