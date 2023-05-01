import React, {useState} from 'react';
import CustomModal from "./CustomModal";
import {useRequest} from "../../hooks/useRequest";
import FormLoader from "../loader/FormLoader";
import SuccessToast from "../SuccessToast";
import {NotificationUser} from "../../models/notification/NotificationUser";
import {NotificationUserFormErrors} from "../../validation/NotificationUserFormErrors";
import EditNotificationUserContent from "../EditNotificationUserFormContent";
import NotificationService from "../../services/NotificationService";

type EditNotificationUserProps = {
    user: NotificationUser
    setUser: (user: NotificationUser) => void;
    isVisible: boolean;
    setIsVisible: (isVisible: boolean) => void;
}

const EditNotificationUserModal: React.FC<EditNotificationUserProps> = (
    {
        user,
        setUser,
        isVisible,
        setIsVisible,
    }) => {
    const getFormData = (user: NotificationUser): NotificationUser => {
        return {...user, login: user.login ?? '', displayName: user.displayName ?? ''}
    }

    const [formData, setFormData] = useState(getFormData(user));
    const [formErrors, setFormErrors] = useState<NotificationUserFormErrors>({});
    const [isSuccessToast, setIsSuccessToast] = useState(false);

    const [updateUser, isUserUpdating, error] = useRequest(async (user) => {
        setIsSuccessToast(false);
        const response = await NotificationService.updateUser(user);
        setIsSuccessToast(true);
        setUser(response);
    })

    const handleClose = () => {
        setFormData(getFormData(user));
        setIsVisible(false);
        setIsSuccessToast(false);
        setFormErrors({});
    }

    const handleSubmit = () => {
        updateUser(formData);
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                {isUserUpdating ?
                    <FormLoader/>
                    :
                    <EditNotificationUserContent
                        formData={formData}
                        setFormData={setFormData}
                        formErrors={formErrors}
                        setFormErrors={setFormErrors}
                        onUpdate={handleSubmit}
                        onClose={handleClose}
                    />
                }
                <SuccessToast
                    message="User updated successfully!"
                    show={isSuccessToast}
                    onHide={() => setIsSuccessToast(false)}
                />
            </CustomModal>
        </div>
    );
};

export default EditNotificationUserModal;
