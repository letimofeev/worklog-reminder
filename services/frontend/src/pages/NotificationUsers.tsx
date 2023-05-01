import React, {useEffect, useState} from 'react';
import {useRequest} from "../hooks/useRequest";
import {NotificationUser} from "../models/notification/NotificationUser";
import NotificationService from "../services/NotificationService";
import Loader from "../components/loader/Loader";
import NotificationUserList from "../components/NotificationUserList";

const NotificationUsers = () => {
    const [users, setUsers] = useState<NotificationUser[]>([]);

    const [fetchUsers, isUsersLoading, error] = useRequest(async () => {
        const response = await NotificationService.getAllUsers();
        setUsers([...users, ...response]);
    });

    useEffect(() => {
        fetchUsers();
    }, [])

    return (
        <div className="content">
            <div className="content__container">
                <div className="content__header">
                    Notification Users Management
                </div>
                <div className="content__subheader">
                    <div className="content__subheader__text">
                        Manage users registered in notification service
                    </div>
                </div>
                {isUsersLoading ?
                    <div className="content__loader">
                        <Loader/>
                    </div>
                    :
                    <NotificationUserList
                        users={users}
                        setUsers={setUsers}
                    />
                }
            </div>
        </div>
    );
};

export default NotificationUsers;
