import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import Home from './pages/Home/Home';
import AddResource from '../src/pages/AddResource/AddResource';
import ViewResources from '../src/pages/ViewResouces/ViewResources';
import EditResource from '../src/pages/EditResource/EditResouce';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <main>
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/add" component={AddResource} />
            <Route path="/view" component={ViewResources} />
            <Route path="/edit/:id" component={EditResource} />
          </Switch>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
