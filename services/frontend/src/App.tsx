import React from 'react';
import './styles/App.css'
import WorklogDebts from "./pages/WorklogDebts";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Employees from "./pages/Employees";
import NavigationBar from "./components/navbar/NavigationBar";

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <NavigationBar/>
                <Routes>
                    <Route path="/worklog-debts" element={<WorklogDebts/>}/>
                    <Route path="/employees" element={<Employees/>}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
