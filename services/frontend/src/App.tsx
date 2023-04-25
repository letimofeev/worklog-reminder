import React from 'react';
import './styles/App.css'
import WorklogDebts from "./pages/WorklogDebts";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Employees from "./pages/Employees";
import NavigationBar from "./components/navbar/NavigationBar";
import NotFound from "./components/error/NotFound";
import AppRouter from "./components/AppRouter";

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <NavigationBar/>
                <AppRouter/>
            </div>
        </BrowserRouter>
    );
}

export default App;
