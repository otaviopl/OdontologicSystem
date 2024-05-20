import React, { useState, useEffect } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { getResources, updateResource } from '../../services/resourceService';
import './EditResource.css';

function EditResource() {
  const { id } = useParams();
  const history = useHistory();
  const resources = getResources();
  const resource = resources.find(r => r.id === parseInt(id));

  const [name, setName] = useState(resource ? resource.name : '');
  const [age, setAge] = useState(resource ? resource.age : '');
  const [message, setMessage] = useState('');

  useEffect(() => {
    if (!resource) {
      history.push('/view');
    }
  }, [resource, history]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (name && age) {
      const updatedResource = { ...resource, name, age };
      updateResource(resource.id, updatedResource);
      setMessage('Paciente atualizado com sucesso!');
      setTimeout(() => history.push('/view'), 2000);
    } else {
      setMessage('Por favor, preencha todos os campos.');
    }
  };

  return (
    <div className="edit-resource">
      <h1>Editar Paciente</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Nome:
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
        </label>
        <label>
          Idade:
          <input type="number" value={age} onChange={(e) => setAge(e.target.value)} />
        </label>
        <button type="submit">Salvar</button>
        {message && <p>{message}</p>}
      </form>
    </div>
  );
}

export default EditResource;
