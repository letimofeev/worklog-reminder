import React from 'react';
import {Navigate, Route, Routes} from "react-router-dom";
import WorklogDebts from "../pages/WorklogDebts";
import Employees from "../pages/Employees";
import NotFound from "./error/NotFound";

const AppRouter = () => {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/worklog-debts"/>}/>
            <Route path="/worklog-debts" element={<WorklogDebts/>}/>
            <Route path="/employees" element={<Employees/>}/>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    );
};

export default AppRouter;
