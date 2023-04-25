import React from 'react';
import './styles/App.css'
import {BrowserRouter} from "react-router-dom";
import NavigationBar from "./components/navbar/NavigationBar";
import AppRouter from "./components/AppRouter";

function App() {
    return (
        <BrowserRouter>
            <div className="mt-16">
                <NavigationBar/>
                <AppRouter/>
            </div>
        </BrowserRouter>
    );
}

export default App;
