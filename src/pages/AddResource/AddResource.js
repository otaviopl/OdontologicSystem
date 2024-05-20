import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addResource } from '../../services/resourceService';
import './AddResource.css';

function AddResource() {
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (name && age) {
      const newResource = { id: Date.now(), name, age };
      addResource(newResource);
      setMessage('Paciente cadastrado com sucesso!');
      setTimeout(() => navigate('/view'), 2000);
    } else {
      setMessage('Por favor, preencha todos os campos.');
    }
  };

  return (
    <div className="add-resource">
      <h1>Adicionar Paciente</h1>
      <form onSubmit={handleSubmit}>
        <label>
          Nome:
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
        </label>
        <label>
          Idade:
          <input type="number" value={age} onChange={(e) => setAge(e.target.value)} />
        </label>
        <button type="submit">Cadastrar</button>
        {message && <p>{message}</p>}
      </form>
    </div>
  );
}

export default AddResource;
