import React, {useState} from 'react';
import {NotificationUser} from "../models/notification/NotificationUser";
import '../styles/notificationUserList.scss'
import SuccessToast from "./SuccessToast";
import NotificationUserItem from "./NotificationUserItem";

type NotificationUserListProps = {
    users: NotificationUser[];
    setUsers: (users: NotificationUser[]) => void;
}

const NotificationUserList: React.FC<NotificationUserListProps> = ({users, setUsers}) => {
    const [isUserDeletedSuccessToast, setIsUserDeletedSuccessToast] = useState(false);

    const setUser = (user: NotificationUser) => {
        const index = users.findIndex(usr => usr.id === user.id);
        const updatedUsers = [
            ...users.slice(0, index),
            user,
            ...users.slice(index + 1)
        ];
        setUsers(updatedUsers);
    }

    const deleteUser = (user: NotificationUser) => {
        setIsUserDeletedSuccessToast(false);
        setUsers(users.filter(usr => usr.id !== user.id));
        setIsUserDeletedSuccessToast(true);
    }

    return (
        <div className="content-list">
            <div className="content-list__header">
                <div className="notification-user-list__no__header-cell">No</div>
                <div className="notification-user-list__display-name__header-cell">Display Name</div>
                <div className="notification-user-list__skype__header-cell">Skype Login</div>
                <div className="notification-user-list__status__header-cell">Enabled</div>
                <div className="notification-user-list__actions__header-cell">Actions</div>
            </div>
            <div className="content-list__body">
                {users.length
                    ?
                    users.map((user, index) => (
                        <NotificationUserItem
                            key={user.id}
                            rowNumber={index + 1}
                            user={user}
                            setUser={setUser}
                            deleteUser={deleteUser}
                        />
                    ))
                    :
                    <div className="employee-list__empty">
                        <div className="employee-list__empty__text">
                            No users found
                        </div>
                    </div>
                }
            </div>
            <SuccessToast
                message={"User deleted successfully!"}
                show={isUserDeletedSuccessToast}
                onHide={() => setIsUserDeletedSuccessToast(false)}
            />
        </div>
    );
};

export default NotificationUserList;