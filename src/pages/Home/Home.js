import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home = () => {
  return (
    <div className="home-container">
      <h1>Bem-vindo ao Sistema de Administração de Pacientes</h1>
      <div className="dashboard">
        <Link to="/add" className="dashboard-item">
          <div className="icon">➕</div>
          <p>Adicionar Paciente</p>
        </Link>
        <Link to="/view" className="dashboard-item">
          <div className="icon">👁️</div>
          <p>Visualizar Pacientes</p>
        </Link>
        <Link to="/edit/:id" className="dashboard-item">
          <div className="icon">✏️</div>
          <p>Editar Paciente</p>
        </Link>
        <Link to="/remove/:id" className="dashboard-item">
          <div className="icon">🗑️</div>
          <p>Remover Paciente</p>
        </Link>
      </div>
    </div>
  );
}

export default Home;
