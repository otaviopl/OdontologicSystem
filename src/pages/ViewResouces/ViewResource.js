import React from 'react';
import { Link } from 'react-router-dom';
import { getResources, deleteResource } from '../../services/resourceService';
import './ViewResource.css';

function ViewResources() {
  const resources = getResources();

  const handleDelete = (id) => {
    deleteResource(id);
    window.location.reload();
  };

  return (
    <div className="view-resources">
      <h1>Pacientes Cadastrados</h1>
      <ul>
        {resources.map(resource => (
          <li key={resource.id}>
            {resource.name} - {resource.age} anos
            <button onClick={() => handleDelete(resource.id)}>Excluir</button>
            <Link to={`/edit/${resource.id}`}>Editar</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ViewResources;
