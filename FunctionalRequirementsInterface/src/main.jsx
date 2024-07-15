import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Requirements from './requirements/Requirements';
import Requirement from './requirement/Requirement';
import NewRequirement from './new-requirement/NewRequirement';
import Statistics from './statistics/Statistics';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Requirements />}></Route>
        <Route path="/requirement/:id" element={<Requirement />}></Route>
        <Route path="/new-functional-requirement" element={<NewRequirement />}></Route>
        <Route path="/statistics" element={<Statistics />}></Route>
      </Routes>
    </Router>
  </React.StrictMode>
)
