import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import Home from './pages/Home/Home';
import AddResource from '../src/pages/AddResource/AddResource';
import ViewResources from '../src/pages/ViewResouces/ViewResource';
import EditResource from '../src/pages/EditResource/EditResource';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/add" element={<AddResource />} />
            <Route path="/view" element={<ViewResources />} />
            <Route path="/edit/:id" element={<EditResource />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;







