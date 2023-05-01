import React, {useState} from 'react';
import '../styles/notificationUserList.scss'
import {useRequest} from "../hooks/useRequest";
import Loader from "./loader/Loader";
import {NotificationUser} from "../models/notification/NotificationUser";
import NotificationService from "../services/NotificationService";
import EditNotificationUserModal from "./modal/EditNotificationUserModal";

type NotificationUserItemProps = {
    rowNumber: number,
    user: NotificationUser
    setUser: (user: NotificationUser) => void;
    deleteUser: (user: NotificationUser) => void;
}

const NotificationUserItem: React.FC<NotificationUserItemProps> = (
    {
        rowNumber,
        user,
        setUser,
        deleteUser
    }) => {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    const [removeUser, isDeleteLoading, error] = useRequest(async (user: NotificationUser) => {
        await NotificationService.deleteUser(user);
        deleteUser(user);
    })

    return (
        <div className="employee-list__body-row">
            <EditNotificationUserModal
                user={user}
                setUser={setUser}
                isVisible={isEditModalOpen}
                setIsVisible={setIsEditModalOpen}
            />
            <div className="notification-user-list__no__body-cell">{rowNumber}</div>
            <div className="notification-user-list__display-name__body-cell">{user.displayName}</div>
            <div className="notification-user-list__skype__body-cell">{user.login}</div>
            <div className="notification-user-list__status__body-cell">
                {user.enabled ?
                    <div className="emp-notification-status__connected">
                        Enabled
                    </div>
                    :
                    <div className="emp-notification-status__disconnected">
                        Disabled
                    </div>
                }
            </div>
            <div className="notification-user-list__actions__body-cell">
                <button
                    onClick={() => setIsEditModalOpen(true)}
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                    Edit
                </button>
                <div className="inline-flex items-center ml-2 relative">
                    <button
                        onClick={() => removeUser(user)}
                        className={`bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ${
                            isDeleteLoading ? 'opacity-0' : ''
                        }`}
                    >
                        Delete
                    </button>
                    {isDeleteLoading && (
                        <div className="absolute inset-0 flex items-center justify-center">
                            <Loader style={
                                {
                                    height: '25px',
                                    width: '25px',
                                    border: '7px solid #f4f6fa',
                                    borderTop: '7px solid #3498db'
                                }}/>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default NotificationUserItem;
