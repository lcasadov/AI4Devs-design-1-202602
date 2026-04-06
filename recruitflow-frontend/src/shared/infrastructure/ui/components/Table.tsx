import React from 'react';

export interface TableColumn<T> {
  key: keyof T;
  header: string;
  render?: (value: T[keyof T], row: T) => React.ReactNode;
}

export interface TableProps<T> {
  columns: TableColumn<T>[];
  data: T[];
  isLoading?: boolean;
  emptyMessage?: string;
}

// TODO: implement full Table component with sorting, pagination, and Tailwind styles
function Table<T extends { id: number | string }>({
  columns,
  data,
  isLoading = false,
  emptyMessage = 'No hay datos disponibles',
}: TableProps<T>): JSX.Element {
  if (isLoading) return <div>Cargando...</div>;
  if (data.length === 0) return <div>{emptyMessage}</div>;

  return (
    <table>
      <thead>
        <tr>
          {columns.map((col) => (
            <th key={String(col.key)}>{col.header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row) => (
          <tr key={row.id}>
            {columns.map((col) => (
              <td key={String(col.key)}>
                {col.render ? col.render(row[col.key], row) : String(row[col.key] ?? '')}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default Table;
