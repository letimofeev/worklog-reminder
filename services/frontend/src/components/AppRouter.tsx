import React from 'react';
import {Navigate, Route, Routes} from "react-router-dom";
import WorklogDebts from "./pages/worklogdebt/WorklogDebts";
import Employees from "./pages/employee/Employees";
import NotFound from "./error/NotFound";
import NotificationUsers from "./pages/notification-users/NotificationUsers";

const AppRouter = () => {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/worklog-debts"/>}/>
            <Route path="/worklog-debts" element={<WorklogDebts/>}/>
            <Route path="/employees" element={<Employees/>}/>
            <Route path="/notification-users" element={<NotificationUsers/>}/>
            <Route path="*" element={<NotFound/>}/>
        </Routes>
    );
};

export default AppRouter;
