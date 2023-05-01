import React, {useEffect, useState} from 'react';
import {useRequest} from "../../../hooks/useRequest";
import {NotificationUser} from "../../../models/notification/NotificationUser";
import NotificationService from "../../../api/NotificationService";
import Loader from "../../loader/Loader";
import NotificationUserList from "./NotificationUserList";

const NotificationUsers = () => {
    const [users, setUsers] = useState<NotificationUser[]>([]);

    const [fetchUsers, isUsersLoading, error] = useRequest(async () => {
        const response = await NotificationService.getAllUsers();
        setUsers([...response]);
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
