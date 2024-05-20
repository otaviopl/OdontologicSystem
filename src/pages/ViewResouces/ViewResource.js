import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getResources, deleteResource } from '../../services/resourceService';
import './ViewResource.css';

function ViewResources() {
  const [resources, setResources] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    setResources(getResources());
  }, []);

  const handleDelete = (id) => {
    const success = deleteResource(id);
    if (success) {
      setMessage('Paciente excluído com sucesso!');
      setResources(resources.filter(resource => resource.id !== id));
    } else {
      setMessage('Ocorreu um problema ao excluir o paciente.');
    }
    setTimeout(() => {
      setMessage('');
    }, 2000);
  };

  return (
    <div className="view-resources">
      <h1>Pacientes Cadastrados</h1>
      {message && <p className="message">{message}</p>}
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
